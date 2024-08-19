package se.ifmo.database.exceptions;

public class FieldIsNullException extends RuntimeException {
    public FieldIsNullException(String fieldName) {
        super("Error: Field " + fieldName + " is null!");
    }
}
