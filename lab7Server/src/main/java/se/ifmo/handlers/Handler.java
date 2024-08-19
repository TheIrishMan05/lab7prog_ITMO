package se.ifmo.handlers;

import lombok.Getter;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;


@Getter
public class Handler {
    public static volatile Deque<String> HISTORY = new ArrayDeque<>();

    private final HandlerConfiguration handlerConfiguration;

    public Handler(HandlerConfiguration handlerConfiguration) {
        this.handlerConfiguration = handlerConfiguration;
    }

    public Response execute(Request request) {
        Handler.HISTORY.add(request.getCommand());

        if (request.getCommand() == null || request.getCommand().isBlank() || request.getCommand().isEmpty()) {
            return new Response("Command cannot be empty");
        }
        if ("help".equalsIgnoreCase(request.getCommand())) return new Response(getHelp());

        return handlerConfiguration.commandList().stream()
                .filter(temp -> temp.getName().equalsIgnoreCase(request.getCommand()))
                .findFirst()
                .map(temp -> temp.execute(request))
                .orElse(new Response(String.format("Command %s not found, type help to get info", request.getCommand())));
    }

    public int getRequiredElements(String command) {
        return handlerConfiguration.commandList().stream()
                .filter(temp -> temp.getName().equalsIgnoreCase(command))
                .findFirst()
                .map(Command::getRequiredElements)
                .orElse(0);
    }

    public String getHelp() {
        return "All available commands: " + handlerConfiguration.commandList()
                .stream()
                .map(command -> String.format("%n%s - %s", command.getName(), command.getHelp()))
                .collect(Collectors.joining());
    }
}
