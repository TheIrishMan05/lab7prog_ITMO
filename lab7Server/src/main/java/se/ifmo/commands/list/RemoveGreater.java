package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveGreater extends Command {
    StudyGroupService studyGroupService;

    public RemoveGreater(StudyGroupService studyGroupService) {
        super("remove_greater", "{element} - remove all elements where name is greater " +
                "than written element has.", 1);
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (request.getResource().isEmpty()) {
            return new Response("Error! At least one element is required.");
        }

        StudyGroup inputElement = request.getResource().iterator().next();

        Set<StudyGroup> groupsToRemove = studyGroupService.findAllByOwner(request.getUser()).stream()
                .filter(e -> e.compareTo(inputElement) >= 0)
                .collect(Collectors.toCollection(TreeSet::new));

        for (StudyGroup group : groupsToRemove) {
            studyGroupService.deleteById(group.getId());
        }
        return new Response(String.format("All elements greater than %s have been removed", inputElement.getName()));
    }

}
