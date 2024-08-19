package se.ifmo.io;

import java.io.*;

public class Console implements ConsoleInterface {
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Console() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    @Override
    public boolean ready() {
        try {
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void write(String message) {
        try {
            writer.append(message).append("\n").flush();
        } catch (IOException ignored) {}
    }

    @Override
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e){
            return null;
        }
    }

    @Override
    public void writeWithoutNewLine(String message) {
        try{
            writer.append(message).flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void shoutDownSilently() throws IOException {
        this.reader.close();
        this.writer.close();
    }
}
