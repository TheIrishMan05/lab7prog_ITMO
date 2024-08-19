package se.ifmo.database.repository;


import se.ifmo.database.models.User;

import java.util.Optional;

public interface UserRepository {
    void save(String username, String password);

    boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean checkPassword(User user, String password);
}
