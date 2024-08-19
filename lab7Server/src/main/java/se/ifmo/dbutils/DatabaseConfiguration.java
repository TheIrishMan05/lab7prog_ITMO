package se.ifmo.dbutils;

public class DatabaseConfiguration {
    public static final String POSTGRES_USER = System.getenv("POSTGRES_USER");
    public static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");
}
