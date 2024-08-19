package se.ifmo.commands.list;


import se.ifmo.commands.Command;
import se.ifmo.console.ConsoleInterface;
import se.ifmo.console.FileWorker;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script", "<file_name> - execute script from the written file.");
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null)
            return new Response("Type file's name");
        if (request.getText().isEmpty() || request.getText().isBlank())
            return new Response("Name of file is empty");

        Path selectedFile = Path.of(request.getText());

        if (Files.notExists(selectedFile))
            return new Response("File does not exist");
        if (!Files.isReadable(selectedFile))
            return new Response("File cannot be read");

        try(ConsoleInterface worker = new FileWorker(selectedFile)) {
            String script = worker.read();

            if (script == null) return new Response("Error reading script");
            if (script.isBlank() || script.isEmpty()) return new Response("File is empty");

            ArrayDeque<String> requests = new ArrayDeque<>(Arrays.stream(script.split("\n")).toList());
            Response response = new Response("");
            response.setInboundRequests(requests);
            return response;
        } catch (Exception e) {
            return new Response("Error during reading the file: " + e.getMessage());
        }
    }
}
