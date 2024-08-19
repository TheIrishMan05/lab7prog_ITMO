package se.ifmo.database.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    Long id;

    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id) {
        this.id = id;
    }

}
