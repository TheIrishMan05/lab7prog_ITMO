package se.ifmo;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import se.ifmo.commands.list.*;
import se.ifmo.console.Console;
import se.ifmo.console.ConsoleInterface;
import se.ifmo.database.service.StudyGroupService;
import se.ifmo.database.service.UserService;
import se.ifmo.handlers.Handler;
import se.ifmo.handlers.HandlerConfiguration;
import se.ifmo.server.Server;
import se.ifmo.server.ServerConfiguration;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ServerApplication {
    public static final Handler HANDLER = new Handler(new HandlerConfiguration(List.of(
            new Add(StudyGroupService.getInstance()),
            new AddIfMax(StudyGroupService.getInstance()),
            new Clear(StudyGroupService.getInstance()),
            new CountLessThan(StudyGroupService.getInstance()),
            new ExecuteScript(),
            new Exit(),
            new FilterGreaterThan(StudyGroupService.getInstance()),
            new History(),
            new Info(),
            new PrintAscending(StudyGroupService.getInstance()),
            new RemoveById(StudyGroupService.getInstance()),
            new RemoveGreater(StudyGroupService.getInstance()),
            new RemoveLower(StudyGroupService.getInstance()),
            new Show(StudyGroupService.getInstance()),
            new UpdateId(StudyGroupService.getInstance())
    )));
    public static void main(String[] args) {
        try (ConsoleInterface serverConsole = new Console()) {
            Server server = new Server(UserService.getInstance(), HANDLER, new ServerConfiguration(9835), serverConsole);
            server.run();
        } catch (Exception e) {
            System.err.println("Error while running: " + e.getMessage());
        }
    }
}