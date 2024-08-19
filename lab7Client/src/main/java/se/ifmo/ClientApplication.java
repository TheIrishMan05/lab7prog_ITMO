package se.ifmo;
import se.ifmo.client.Client;
import se.ifmo.client.ClientConfiguration;
import se.ifmo.io.Console;
import se.ifmo.io.ConsoleInterface;

import java.net.InetAddress;

public class ClientApplication {
    public static void main(String[] args){
        try (ConsoleInterface console = new Console();
        Client client = new Client(new ClientConfiguration(InetAddress.getLocalHost(), 9835), console)) {
            client.start();
        } catch (Exception e) {
            System.err.println("Error while running: " + e.getMessage());
        }
    }
}