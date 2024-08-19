package se.ifmo.commands.list;


import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class Exit extends Command {

    public Exit() {
        super("exit", "finish program running(without saving)");
    }


    @Override
    public Response execute(Request request) {
        System.exit(1);
        return new Response("Program work has been finished.");
    }
}
