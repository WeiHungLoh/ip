package duet.parser;

import duet.exception.EmptyInputException;
import duet.storage.Storage;
import duet.task.Task;
import duet.task.TaskList;
import duet.task.ToDo;

/**
 * Represents a class to handle ToDo commands.
 */
public class ToDoParser {
    public static String getToDoTask(Storage storage, TaskList messages, String[] command)
                throws EmptyInputException {
        String desc = "";
        for (int i = 1; i < command.length; i++) {
            if (i > 1) {
                desc += " ";
            }
            desc += command[i];
        }
        messages.add(new ToDo(desc));
        Task currentTask = messages.get(messages.size() - 1);
        String otherDesc = "";
        otherDesc += Parser.updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "Got it. I've added this task:\n" + " " + currentTask.toString()
                + "\n" + otherDesc;
    }
}
