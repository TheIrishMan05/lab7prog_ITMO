package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.Optional;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveById extends Command {
    StudyGroupService studyGroupService;

    public RemoveById(StudyGroupService studyGroupService) {
        super("remove_by_id", "<ID> - remove element by written ID");
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            return new Response("Error! Command is typed without args");
        }

        if (!request.getText().matches("\\d+")) {
            return new Response("Error! Command arg is not a number");
        }

        long idToFind = Long.parseLong(request.getText());

        Optional<StudyGroup> studyGroupOptional = studyGroupService.findById(idToFind);

        if (studyGroupOptional.isEmpty()) {
            return new Response(String.format("Error! Element with id %d does not exist", idToFind));
        }

        StudyGroup studyGroup = studyGroupOptional.get();

        if (!studyGroup.getUser().getId().equals(request.getUser().getId())) {
            return new Response("Error! This user does not own element");
        }

        studyGroupService.deleteById(idToFind);

        return new Response("Removed element with id " + idToFind);
    }
}
