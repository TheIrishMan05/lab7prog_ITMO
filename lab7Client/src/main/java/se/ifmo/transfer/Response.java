package se.ifmo.transfer;

import lombok.*;
import se.ifmo.database.models.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.TreeSet;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor @AllArgsConstructor
@Setter
@Getter
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text = "";
    private TreeSet<StudyGroup> resource = new TreeSet<>();

    private HashMap<String, Integer> commands = new HashMap<>();
    private Deque<String> inboundRequests = new ArrayDeque<>();


    public Response(String text) {
        this.text = text;
    }

    public Response(TreeSet<StudyGroup> resource) {
        this.resource = resource;
    }

    public Response(String text, TreeSet<StudyGroup> resource) {
        this.text = text;
        this.resource = resource;
    }

}
