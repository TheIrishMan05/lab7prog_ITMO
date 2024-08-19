package se.ifmo.commands.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.Command;
import se.ifmo.database.models.Semester;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountLessThan extends Command {
    StudyGroupService studyGroupService;

    public CountLessThan(StudyGroupService studyGroupService) {
        super("count_less_than_semester_enum", "<semesterEnum> - print amount of elements" +
                " where number of semester is less than written one.");
        this.studyGroupService = studyGroupService;
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            return new Response("Error! Command is typed without args");
        }

        if (!request.getText().matches("\\w+")) {
            return new Response("Error! Command arg is not a word");
        }

        Semester inputSemester = Semester.valueOf(request.getText().toUpperCase());

        return new Response(String.format("Amount of elements with semester less than %s: %d",
                inputSemester.name(),
                studyGroupService.findAll().stream()
                        .filter(studyGroup -> studyGroup.getSemesterEnum().ordinal() < inputSemester.ordinal()).count()));
    }
}
