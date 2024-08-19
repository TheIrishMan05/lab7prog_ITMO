package se.ifmo.database.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String fieldName, String condition) {
        super(String.format("Error during initialization of the field %s: %s", fieldName, condition));
    }

    public InvalidArgumentException(String fieldName) {
        super(String.format("Error during initialization of the field %s", fieldName));
    }
}
