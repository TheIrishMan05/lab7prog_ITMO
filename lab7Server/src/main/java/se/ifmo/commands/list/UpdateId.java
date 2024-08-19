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
public class UpdateId extends Command {
    StudyGroupService studyGroupService;

    public UpdateId(StudyGroupService studyGroupService) {
        super("update", "<ID> {element} - update value of the element by its ID", 1);
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null || request.getText().isEmpty() || request.getText().isBlank()) {
            return new Response("Error! Command is typed without ID of element for update");
        }

        if (!request.getText().matches("\\d+")) {
            return new Response("Error! Command arg is not a number");
        }

        if (request.getResource().isEmpty()) {
            return new Response("Collection is empty!");
        }

        long idForUpdate = Long.parseLong(request.getText());
        Optional<StudyGroup> optionalStudyGroup = studyGroupService.findById(idForUpdate);
        if (optionalStudyGroup.isEmpty()) {
            return new Response(String.format("Error! StudyGroup with ID %d not found", idForUpdate));
        }
        if (!optionalStudyGroup.get().getUser().getId().equals(request.getUser().getId())) {
            return new Response("Error! This study group does not belong to this user");
        }
        StudyGroup studyGroup = request.getResource().first();
        studyGroup.setId(idForUpdate);
        studyGroupService.update(studyGroup, request.getUser());

        return new Response(String.format("Element with ID %d has been updated", idForUpdate));
    }
}
