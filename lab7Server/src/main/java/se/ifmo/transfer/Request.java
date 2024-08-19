package se.ifmo.transfer;

import lombok.*;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.models.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.TreeSet;

@AllArgsConstructor
@Setter @Getter
@ToString @EqualsAndHashCode
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String command = "";
    private String text = "";
    private TreeSet<StudyGroup> resource = new TreeSet<>();
    private String username;
    private String password;
    private User user;

    public Request() {
    }

    public Request(String command, TreeSet<StudyGroup> resource) {
        this.command = command;
        this.resource = resource;
    }

    public Request(String command, String text) {
        this.command = command;
        this.text = text;
    }

    public Request(String command, String text, TreeSet<StudyGroup> resource) {
        this.command = command;
        this.text = text;
        this.resource = resource;
    }
}