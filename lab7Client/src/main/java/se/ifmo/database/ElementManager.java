package se.ifmo.database;

import se.ifmo.database.exceptions.FieldIsNullException;
import se.ifmo.database.models.*;
import se.ifmo.io.ConsoleInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Function;

public class ElementManager {
    public static StudyGroup collect(Deque<String> requests) {
        try {
            StudyGroup studyGroup = new StudyGroup();

            while (!input(studyGroup::setName, Function.identity(), requests.pollFirst()));

            Coordinates coordinates = new Coordinates();

            while (!input(coordinates::setX, Long::parseLong, requests.pollFirst()));
            while (!input(coordinates::setY, Float::parseFloat, requests.pollFirst()));

            studyGroup.setCoordinates(coordinates);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


            studyGroup.setCreationDate(LocalDateTime.parse(LocalDateTime.now().format(dateFormat), dateFormat));


            while (!input(studyGroup::setStudentsCount, Integer::parseInt, requests.pollFirst()));
            while (!input(studyGroup::setExpelledStudents, Integer::parseInt, requests.pollFirst()));
            while (!input(studyGroup::setShouldBeExpelled, Long::parseLong, requests.pollFirst()));
            while (!input(studyGroup::setSemesterEnum, Semester::valueOf, requests.pollFirst()));

            Person groupAdmin = new Person();

            while (!input(groupAdmin::setName, Function.identity(), requests.pollFirst()));
            while (!input(groupAdmin::setBirthday, s -> LocalDate.parse(s, dateFormat), requests.pollFirst()));
            while (!input(groupAdmin::setEyeColor, Color::valueOf, requests.pollFirst()));

            Location location = new Location();

            while (!input(location::setX, Long::parseLong, requests.pollFirst()));
            while (!input(location::setY, Integer::parseInt, requests.pollFirst()));
            while (!input(location::setName, Function.identity(), requests.pollFirst()));

            groupAdmin.setLocation(location);

            studyGroup.setGroupAdmin(groupAdmin);

            return studyGroup;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static StudyGroup collect(ConsoleInterface consoleInterface) {
        try {
            StudyGroup studyGroup = new StudyGroup();

            while (!input("name", studyGroup::setName, s -> s, consoleInterface));

            Coordinates coordinates = new Coordinates();

            while (!input("x", coordinates::setX, Long::parseLong, consoleInterface));
            while (!input("y", coordinates::setY, Float::parseFloat, consoleInterface));

            studyGroup.setCoordinates(coordinates);

            DateTimeFormatter dateFormatCD = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


            studyGroup.setCreationDate(LocalDateTime.parse(LocalDateTime.now().format(dateFormatCD), dateFormatCD));


            while (!input("studentsCount", studyGroup::setStudentsCount, Integer::parseInt, consoleInterface));
            while (!input("expelledStudents", studyGroup::setExpelledStudents, Integer::parseInt, consoleInterface));
            while (!input("shouldBeExpelled", studyGroup::setShouldBeExpelled, Long::parseLong, consoleInterface));
            while (!input("semester" + Arrays.toString(Semester.values()), studyGroup::setSemesterEnum, Semester::valueOf, consoleInterface));

            Person groupAdmin = new Person();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            while (!input("name", groupAdmin::setName, Function.identity(), consoleInterface));
            while (!input("birthday", groupAdmin::setBirthday, s -> LocalDate.parse(s, dateFormat), consoleInterface));
            while (!input("color", groupAdmin::setEyeColor, Color::valueOf, consoleInterface));

            Location location = new Location();

            while (!input("x", location::setX, Long::parseLong, consoleInterface));
            while (!input("y", location::setY, Integer::parseInt, consoleInterface));
            while (!input("name", location::setName, Function.identity(), consoleInterface));
            groupAdmin.setLocation(location);

            studyGroup.setGroupAdmin(groupAdmin);
            return studyGroup;
        } catch (Throwable e) {
            consoleInterface.write(e.getMessage());
            return null;
        }
    }

    private static <K> boolean input(String fieldname, Consumer<K> setter,
                                     Function<String, K> parser, ConsoleInterface console) throws Throwable {
        try {
            String line = console.writeAndRead(" - %s ", fieldname);
            if (line == null || line.equals("return")) throw new Throwable("return is called");

            setter.accept(parser.apply(line));
            return true;
        } catch (Exception e) {
            console.write(e.getMessage());
            return false;
        }
    }

    private static <K> boolean input(Consumer<K> setter, Function<String, K> parser, String value) throws Exception {
        try {
            if (value == null || value.equals("return")) throw new Exception("return is called");

            setter.accept(parser.apply(value));
            return true;
        } catch (FieldIsNullException | IllegalArgumentException e) {
            return false;
        }
    }
}
