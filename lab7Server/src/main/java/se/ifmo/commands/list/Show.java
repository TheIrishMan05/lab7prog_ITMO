package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Show extends Command {
    StudyGroupService studyGroupService;

    public Show(StudyGroupService studyGroupService) {
        super("show", "print all elements of the database");
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        return new Response(String.format("All elements of the database: %s", studyGroupService.findAll().toString()));
    }
}
