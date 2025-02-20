package duet.parser;

import java.util.ArrayList;

import duet.exception.EmptyInputException;
import duet.task.Task;
import duet.task.TaskList;

/**
 * Represents a class to handle find commands.
 */
public class FindParser {
    /**
     * Returns a list of find results that match a keyword.
     *
     * @param command The type of command like deadline, todo or event.
     * @param messages A tasklist of messages.
     * @return A string consists of a list of find results.
     * @throws EmptyInputException If no keyword is provided.
     */
    public static String getFindResults(String[] command, TaskList messages) throws EmptyInputException {
        String keyword = command[1];
        ArrayList<Task> newTasks = new ArrayList<>();
        String desc = "";

        if (command.length < 2) {
            throw new EmptyInputException("The keyword cannot be empty");
        }

        for (Task task : messages.getTasks()) {
            if (task.toString().contains(keyword)) {
                newTasks.add(task);
            }
        }

        if (newTasks.size() > 0) {
            desc += "YOU CAN DO IT! Here are the matching tasks in your list:\n";
            for (Task task : newTasks) {
                desc += task.toString() + "\n";
            }
            return desc;
        } else {
            return "Task is not found.";
        }
    }
}
