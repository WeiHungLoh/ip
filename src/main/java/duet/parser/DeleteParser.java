package duet.parser;

import java.util.ArrayList;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.Task;
import duet.task.TaskList;

/**
 * Represents a class that handles delete commands.
 */
public class DeleteParser {
    public static String getDeletedTask(Storage storage, TaskList messages,
                                        String message, String[] command) {
        if (command.length == 1) {
            try {
                throw new EmptyInputException("The description for delete cannot be empty.");
            } catch (EmptyInputException e) {
                return e.getMessage();
            }
        }
        int taskNum = Integer.parseInt(command[1]);
        assert taskNum <= messages.size() || taskNum >= 1 : "Task does not exist";

        if (taskNum > messages.size() || taskNum < 1) {
            try {
                throw new InvalidInputException("The task that you want to delete does not exist.");
            } catch (InvalidInputException e) {
                return e.getMessage();
            }
        }

        int idx = Integer.parseInt(command[1]) - 1; // decrements index since ArrayList is zero-indexed
        Task deletedTask = messages.get(idx);
        messages.getTasks().remove(idx);
        String otherDesc = "";
        otherDesc += Parser.updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "SO CLOSE YOU CAN DO IT!\nNoted. I've removed this task:\n" + " " + deletedTask.toString()
                + "\n" + otherDesc;
    }

    public static String getMultipleDeletedTasks(Storage storage, TaskList messages,
                                                 String message, String[] command) {
        String[] tasksToBeDeleted = command[1].split(",");
        int[] tasksIdx = new int[tasksToBeDeleted.length];
        ArrayList<Task> oldTasks = new ArrayList<>();
        try {
            for (int i = 0; i < tasksIdx.length; i++) {
                tasksIdx[i] = Integer.parseInt(tasksToBeDeleted[i]);
                if (tasksIdx[i] > messages.size()) {
                    throw new InvalidInputException("Task that you want to delete does not exist");
                }
                oldTasks.add(messages.get(tasksIdx[i] - 1));
            }
            boolean hasDuplicate = checkDuplicateTasks(tasksIdx);
            if (hasDuplicate) {
                throw new InvalidInputException("You cannot delete the same task more than once!");
            }
        } catch (InvalidInputException e) {
            return e.getMessage();
        }

        String deletedTaskList = "";
        String otherDesc = "";
        for (Task task : oldTasks) {
            int deletedIdx = messages.getTasks().indexOf(task);
            messages.getTasks().remove(deletedIdx);
            if (deletedTaskList.length() > 0) {
                deletedTaskList += "\n ";
            }
            deletedTaskList += task.toString();
        }
        storage.save(messages.getTasks());
        otherDesc += Parser.updateCurrentTaskMessage(messages, otherDesc);
        return "SEE YOU CAN DO IT!\nNoted. I've removed these tasks:\n" + deletedTaskList + "\n" + otherDesc;
    }

    /**
     * Returns a boolean value to check for duplicate tasks to be deleted.
     *
     * @param tasksIdx An array on integer containing task number.
     * @return Boolean value to check for duplicate tasks.
     */
    public static boolean checkDuplicateTasks(int[] tasksIdx) {
        for (int j = 0; j < tasksIdx.length; j++) {
            for (int k = j + 1; k < tasksIdx.length; k++) {
                if (tasksIdx[j] == tasksIdx[k]) {
                    return true;
                }
            }
        }
        return false;
    }
}
