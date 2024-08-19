package se.ifmo.transfer;

import lombok.*;
import se.ifmo.database.models.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text = "";
    private TreeSet<StudyGroup> resource = new TreeSet<>();

    private HashMap<String, Integer> commands = new HashMap<>();
    private ArrayDeque<String> inboundRequests = new ArrayDeque<>();

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

    public Response(ArrayDeque<String> inboundRequests, String text) {
        this.inboundRequests = inboundRequests;
        this.text = text;
    }
}