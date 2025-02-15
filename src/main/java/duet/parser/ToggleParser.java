package duet.parser;

import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.TaskList;

public class ToggleParser {
    /**
     * Returns a string consists of tasks marked as done.
     *
     * @param storage Storage to save and load tasks from a file path.
     * @param messages A TaskList of inputs.
     * @param command A String array of command by user.
     * @return A string consists of a task marked as done.
     */
    public static String getMarkedTask(Storage storage, TaskList messages, String[] command) {
        if (command[1].length() > 1 && command[1].contains(",")) {
            return getMarkedTasks(storage, messages, command);
        }
        int idx = Integer.parseInt(command[1]) - 1;
        int taskNum = Integer.parseInt(command[1]);
        assert taskNum >= 1 && taskNum <= messages.size() : "Cannot mark a task that does not exist";

        try {
            if (taskNum > messages.size() || taskNum < 1) {
                throw new InvalidInputException("Task that you want to mark does not exist");
            }
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        storage.save(messages.getTasks());
        messages.get(idx).markAsDone();
        return "I CAN DO IT. SO CLOSE TO COMPLETING\nNice! I've marked this task as done:\n"
                + "  [" + messages.get(idx).getStatusIcon() + "] "
                + messages.get(idx).getDescription();
    }

    /**
     * Returns a list of String of tasks marked as done.
     *
     * @param storage Storage to save and load tasks.
     * @param messages A TaskList of messages.
     * @param command A String array of command.
     * @return Returns a String containing a list of tasks marked as done.
     */
    public static String getMarkedTasks(Storage storage, TaskList messages, String[] command) {
        String[] tasksToBeMarked = command[1].split(",");
        String taskList = "";
        try {
            for (int i = 0; i < tasksToBeMarked.length; i++) {
                int idx = Integer.parseInt(tasksToBeMarked[i]) - 1;
                if (idx + 1 > messages.size() || idx < 0) {
                    throw new InvalidInputException("Please only mark tasks that exist");
                }
                messages.get(idx).markAsDone();
                if (taskList.length() > 0) {
                    taskList += "\n";
                }
                taskList += " [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription();
            }
            storage.save(messages.getTasks());
            return "I CAN DO IT SO CLOSE TO COMPLETING!\nNice! I've marked these tasks as done:\n" + taskList;
        } catch (InvalidInputException e) {
            return e.getMessage();
        }
    }

    /**
     * Returns a string consists of tasks unmarked as done.
     *
     * @param storage Storage to save and load tasks from a file path.
     * @param messages A TaskList of inputs.
     * @param command A String array of command by user.
     * @return A string consists of a task unmarked as done.
    */
    public static String getUnmarkedTask(Storage storage, TaskList messages, String[] command) {
        if (command[1].length() > 1 && command[1].contains(",")) {
            return getUnmarkedTasks(storage, messages, command);
        }
        int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed
        int taskNum = Integer.parseInt(command[1]);
        assert taskNum >= 1 || taskNum <= messages.size() : "Cannot unmark a task that does not exist";

        try {
            if (taskNum > messages.size() || taskNum < 1) {
                throw new InvalidInputException("Task that you want to unmark does not exist");
            }
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        messages.get(idx).unmarkAsDone();
        storage.save(messages.getTasks());
        return "YOU CAN DO IT!\nOK, I've marked this task as not done yet:\n" + " [" + messages.get(idx).getStatusIcon()
                + "] " + messages.get(idx).getDescription();
    }

    /**
     * Returns a list of String of tasks unmarked as done.
     *
     * @param storage Storage to save and load tasks.
     * @param messages A TaskList of messages.
     * @param command A String array of command.
     * @return Returns a String containing a list of tasks unmarked as done.
     */
    public static String getUnmarkedTasks(Storage storage, TaskList messages, String[] command) {
        String[] tasksToBeUnmarked = command[1].split(",");
        String taskList = "";
        try {
            for (int i = 0; i < tasksToBeUnmarked.length; i++) {
                int idx = Integer.parseInt(tasksToBeUnmarked[i]) - 1;
                if (idx + 1 > messages.size() || idx < 0) {
                    throw new InvalidInputException("Task that you want to unmark does not exist");
                }
                messages.get(idx).unmarkAsDone();
                if (taskList.length() > 0) {
                    taskList += "\n";
                }
                taskList += " [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription();
            }
            storage.save(messages.getTasks());
            return "YOU CAN DO IT!\nNice! I've unmarked these tasks:\n" + taskList;
        } catch (InvalidInputException e) {
            return e.getMessage();
        }
    }
}
