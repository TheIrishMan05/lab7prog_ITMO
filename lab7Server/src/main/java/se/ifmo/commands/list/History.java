package se.ifmo.commands.list;


import se.ifmo.commands.Command;
import se.ifmo.handlers.Handler;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class History extends Command {

    public History() {
        super("history", "print last 14 commands(without arguments)");
    }

    @Override
    public Response execute(Request request) {
        return new Response(Handler.HISTORY
                .stream().limit(14)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("History is empty"));
    }
}
