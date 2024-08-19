package se.ifmo.console;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWorker implements ConsoleInterface{

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public FileWorker(Path path, boolean append) throws IOException {
        if (Files.notExists(path)) throw new FileNotFoundException();

        if (!Files.isReadable(path)) System.err.println("[WARN] can't read from file" + path);
        if (!Files.isWritable(path)) System.err.println("[WARN] can't write to file" + path);

        this.reader = new BufferedReader(new FileReader(path.toFile()));
        this.writer = new BufferedWriter(new FileWriter(path.toFile(), append));
    }

    public FileWorker(Path path) throws IOException {
        this(path, true);
    }

    @Override
    public String read() {
        try {
            StringBuilder data = new StringBuilder();
            while (reader.ready())
                data.append(reader.readLine()).append("\n");
            return data.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean ready() {
        return true;
    }

    @Override
    public void write(String message) {
        try{
            writer.append(message).append("\n").flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void writeWithoutNewLine(String message) {
        try{
            writer.append(message).flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void shoutDownSilently() throws Exception {
        reader.close();
        writer.close();
    }
}
