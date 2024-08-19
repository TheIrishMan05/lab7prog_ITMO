package se.ifmo.commands.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Add extends Command {
    StudyGroupService studyGroupService;

    public Add(StudyGroupService studyGroupService) {
        super("add", "{element} - add new element to the database", 1);

        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (!request.getText().isEmpty())
            return new Response(String.format("Command %s doesn't any args", request.getCommand()));

        if (request.getResource().isEmpty()) return new Response("At least one element have to be specified");


        StudyGroup inputElement = request.getResource().iterator().next();
        inputElement.setUser(request.getUser());

        studyGroupService.save(inputElement, request.getUser());
        return new Response("Element has been added successfully");
    }
}
