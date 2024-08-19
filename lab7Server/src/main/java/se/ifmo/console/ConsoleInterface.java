package se.ifmo.console;

public interface ConsoleInterface extends AutoCloseable {
    String read();

    boolean ready();

    void write(String message);

    void writeWithoutNewLine(String message);

    default void write(String message, Object... args) {
        write(String.format(message, args));
    }

    default String writeAndRead(String message) {
        writeWithoutNewLine(message);
        return read();
    }

    default String writeAndRead(String message, Object... args) {
        return writeAndRead(String.format(message, args));
    }

    default void skip() {
        write("\n");
    }

    default void clear() {
        write("\n".repeat(60));
    }

    void shoutDownSilently() throws Exception;

    @Override
    default void close() throws Exception {
        shoutDownSilently();
    }
}
