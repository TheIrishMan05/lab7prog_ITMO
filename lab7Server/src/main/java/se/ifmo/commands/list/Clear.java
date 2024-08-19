package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Clear extends Command {
    StudyGroupService studyGroupService;

    public Clear(StudyGroupService studyGroupService) {
        super("clear_collection", "clear the database");
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        studyGroupService.deleteAllByOwner(request.getUser());
        return new Response("Collection has been cleared");
    }

}
