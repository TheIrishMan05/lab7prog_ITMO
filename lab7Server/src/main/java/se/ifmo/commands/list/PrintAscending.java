package se.ifmo.commands.list;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.StudyGroup;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PrintAscending extends Command {
    StudyGroupService studyGroupService;

    public PrintAscending(StudyGroupService studyGroupService) {
        super("print_ascending", "print all elements of database in ascending order");
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        return new Response(studyGroupService.findAll().stream()
                .map(StudyGroup::getName)
                .sorted()
                .map(name -> "\n" + name)
                .collect(Collectors.joining()));
    }
}
