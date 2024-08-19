package se.ifmo.commands.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.Comparator;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddIfMax extends Command {
    StudyGroupService studyGroupService;

    public AddIfMax(StudyGroupService studyGroupService) {
        super("add_if_max", "{element} - add new element if its name is greater" +
                " than element with max one", 1);
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (!request.getText().isEmpty()) System.out.println("Command add_if_max don't have to have any arguments");

        Optional<StudyGroup> toAddOptional = request.getResource().stream().findAny();

        if (toAddOptional.isEmpty()) {
            return new Response("Element for adding is absent.");
        }

        StudyGroup groupToAdd = toAddOptional.get();

        StudyGroup maxElement = studyGroupService.findAllByOwner(request.getUser())
                .stream().max(Comparator.naturalOrder()).orElse(null);

        if (maxElement == null) {
            return new Response("Element for adding is absent.");
        }

        if (groupToAdd.compareTo(maxElement) <= 0) {
            return new Response("Element should be greater than max element.");
        } else {
            studyGroupService.save(groupToAdd, request.getUser());
            return new Response("Element has been successfully added.");
        }

    }
}
