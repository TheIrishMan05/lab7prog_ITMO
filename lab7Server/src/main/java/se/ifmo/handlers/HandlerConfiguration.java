package se.ifmo.handlers;


import se.ifmo.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public record HandlerConfiguration(List<Command> commandList) {
    public HashMap<String, Integer> requiredElements() {
        return new HashMap<>(commandList
                .stream()
                .collect(Collectors.toMap(Command::getName, Command::getRequiredElements)));
    }
}
