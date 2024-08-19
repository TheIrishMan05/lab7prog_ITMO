package se.ifmo.database.service;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import se.ifmo.database.models.User;
import se.ifmo.database.repository.UserRepository;
import se.ifmo.dbutils.DatabaseManager;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements UserRepository {
    static UserService instance;
    final String SAVE_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    final String EXISTS_BY_USERNAME = "SELECT COUNT(*) FROM users WHERE username = ?";
    final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
    final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

    private UserService() {
    }

    public static UserService getInstance() {
        return instance == null ? instance = new UserService() : instance;
    }

    @Override
    @SneakyThrows
    public synchronized void save(String username, String password) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(SAVE_USER)) {
            query.setString(1, username);
            query.setString(2, password);
            query.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public synchronized boolean existsByUsername(String username) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(EXISTS_BY_USERNAME)) {
            query.setString(1, username);

            try (ResultSet resultSet = query.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    @SneakyThrows
    public synchronized Optional<User> findById(Long id) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(FIND_BY_ID)) {
            query.setLong(1, id);
            try (ResultSet resultSet = query.executeQuery()) {
                return map(resultSet);
            }
        }
    }

    @Override
    @SneakyThrows
    public synchronized Optional<User> findByUsername(String username) {
        try (PreparedStatement query = DatabaseManager.getInstance().prepareStatement(FIND_BY_USERNAME)) {
            query.setString(1, username);

            try (ResultSet resultSet = query.executeQuery()) {
                return map(resultSet);
            }
        }
    }

    @Override
    @SneakyThrows
    public synchronized boolean checkPassword(User user, String password) {
        return password.equals(user.getPassword());
    }

    @SneakyThrows
    private Optional<User> map(ResultSet resultSet) {
        return resultSet.next() ?
                Optional.of(User.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build())
                : Optional.empty();
    }
}
