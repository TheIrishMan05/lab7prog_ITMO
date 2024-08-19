package se.ifmo.dbutils;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static se.ifmo.dbutils.DatabaseConfiguration.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Log4j2
@Setter
public class DatabaseManager {
    Connection connection;
    static DatabaseManager instance;
    private DatabaseManager() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                   "jdbc:postgresql://pg:5432/studs?currentSchema=s409109",
                    POSTGRES_USER,
                    POSTGRES_PASSWORD
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            System.exit(1);
        }
    }

    @SneakyThrows
    public PreparedStatement prepareStatement(String query) {
        return connection.prepareStatement(query);
    }

    @SneakyThrows
    public static DatabaseManager getInstance() {
        if (instance == null || instance.getConnection().isClosed()) {
            return instance = new DatabaseManager();
        } else {
            return instance;
        }
    }
}
