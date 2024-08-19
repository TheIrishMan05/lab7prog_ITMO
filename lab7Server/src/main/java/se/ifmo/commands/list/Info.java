package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class Info extends Command {

    public Info() {
        super("info", "print information about database");

    }

    @Override
    public Response execute(Request request) {

        return new Response("""
                PostgresSQL DB
                Collection elements type: StudyGroup
                Elements in database:""" + request.getResource().size());


    }
}
