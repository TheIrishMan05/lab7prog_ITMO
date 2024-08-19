package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveLower extends Command {
    StudyGroupService studyGroupService;


    public RemoveLower(StudyGroupService studyGroupService) {
        super("remove_lower", "{element} - remove all elements from the database" +
                " where name is less than written element has.", 1);
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (request.getResource().isEmpty()) {
            return new Response("Error! At least one element is required.");
        }

        StudyGroup inputElement = request.getResource().iterator().next();

        NavigableSet<StudyGroup> groupsToRemove = studyGroupService.findAllByOwner(request.getUser()).stream()
                .filter(e -> e.compareTo(inputElement) < 0)
                .collect(Collectors.toCollection(TreeSet::new));

        for (StudyGroup group : groupsToRemove) {
            studyGroupService.deleteById(group.getId());
        }

        return new Response(String.format("All elements lower than %s have been removed", inputElement.getName()));
    }
}
