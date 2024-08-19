package se.ifmo.commands;

import lombok.Getter;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

@Getter
public abstract class Command {
    private final String name;
    private final String help;
    private final int requiredElements;

    public Command(String name, String help) {
        this.name = name;
        this.help = help;
        this.requiredElements = 0;
    }

    public Command(String name, String help, int requiredElements) {
        this.name = name;
        this.help = help;
        this.requiredElements = requiredElements;
    }

    public Command(String name) {
        this(name, String.format("there's no help for command %s", name));
    }

    public abstract Response execute(Request request);
}
