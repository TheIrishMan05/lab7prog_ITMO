package se.ifmo.server;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import se.ifmo.ServerApplication;
import se.ifmo.console.ConsoleInterface;
import se.ifmo.database.models.User;
import se.ifmo.database.service.UserService;
import se.ifmo.handlers.Handler;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class Server implements Runnable {
    UserService userService;
    ServerConfiguration serverConfiguration;
    ConsoleInterface consoleInterface;

    Handler handler;

    ExecutorService readAndProcessPool = Executors.newFixedThreadPool(10);

    public Server(UserService userService, Handler handler, ServerConfiguration serverConfiguration, ConsoleInterface consoleInterface) {
        this.userService = userService;
        this.handler = handler;
        this.serverConfiguration = serverConfiguration;
        this.consoleInterface = consoleInterface;
    }

    @Override
    public void run() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(serverConfiguration.getPort()));
            log.info("Server started on port {}", serverConfiguration.getPort());
            consoleInterface.write("Type 'help' to get info about known commands.");

            ByteBuffer buffer = ByteBuffer.allocate(10000);

            while (true) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);
                log.info("Received request from client {}", clientAddress);

                readAndProcessPool.submit(() -> handleRequest(buffer, clientAddress, channel));
            }
        } catch (IOException e) {
            log.error("Server exception: {}", e.getMessage());
        }
    }

    private void handleRequest(ByteBuffer buffer, InetSocketAddress clientAddress, DatagramChannel channel) {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer.array());
             ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)) {

            Request request = (Request) objectInputStream.readObject();
                        log.info("Request from client ({}): {}", clientAddress, request);

            Optional<User> optionalUser = authorize(request.getUsername());
            Response response;

            if (optionalUser.isEmpty()) {
                userService.save(request.getUsername(), request.getPassword());
                response = new Response("User " + request.getUsername() + " has been successfully registered.");
            } else {
                if (userService.checkPassword(optionalUser.get(), request.getPassword())) {
                    request.setUser(optionalUser.get());
                    response = processRequest(request);
                } else {
                    response = new Response("Incorrect password.");
                }
            }

            response.setCommands(ServerApplication.HANDLER.getHandlerConfiguration().requiredElements());
            new Thread(() -> sendResponse(response, clientAddress, channel)).start();
        } catch (EOFException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error handling client request: {}", e.getMessage());
        }
    }

    private Response processRequest(Request request) throws ExecutionException, InterruptedException, TimeoutException {
        return readAndProcessPool.submit(() -> handler.execute(request)).get(10, TimeUnit.SECONDS);
    }

    private void sendResponse(Response response, InetSocketAddress clientAddress, DatagramChannel channel) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream)) {
            objectOutputStream.writeObject(response);
            objectOutputStream.flush();

            ByteBuffer buffer = ByteBuffer.wrap(byteStream.toByteArray());
            channel.send(buffer, clientAddress);
            log.info("Response sent to client ({}): {}", clientAddress, response);
        } catch (IOException e) {
            log.warn("Failed to send response to client {}: {}", clientAddress, e.getMessage());
        }
    }

    private Optional<User> authorize(String username) {
        return userService.findByUsername(username);
    }
}
