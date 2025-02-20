package duet.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.Deadline;
import duet.task.Task;
import duet.task.TaskList;

/**
 * Represents a class to handle deadline commands.
 */
public class DeadlineParser {
    /**
     * Returns the description and deadline of task that has been added.
     *
     * @param storage A Storage where the task is stored.
     * @param messages A TaskList of messages that already exists.
     * @param dates A String consists of deadline.
     * @return A String consists of deadline task.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If no deadline is provided.
     */
    public static String getDeadlineTask(Storage storage, TaskList messages, String[] dates)
            throws EmptyInputException, InvalidInputException {
        String desc = "";
        String[] descArray = dates[0].split(" ");
        try {
            if (descArray.length == 1) {
                throw new EmptyInputException("Deadline task must have a description");
            } else if (descArray.length < 2) {
                throw new InvalidInputException("Deadline task must have a due date in YYYY-MM-dd format");
            }
        } catch (EmptyInputException | InvalidInputException e) {
            return e.getMessage();
        }
        assert dates.length > 1 : "Deadline must be provided";
        for (int j = 1; j < descArray.length; j++) {
            if (j > 1) {
                desc += " ";
            }
            desc += descArray[j];
        }
        String date = getDate(dates);
        Task currentTask = getFormattedDeadlineTask(messages, date, desc);
        String otherDesc = "";
        otherDesc += Parser.updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "YOU CAN DO IT!\nGot it. I've added this task:\n " + currentTask.toString()
                + "\n" + otherDesc;
    }

    public static String getDate(String[] dates) {
        String byWhen = dates[1].trim();
        String date = "";
        String[] dueDate = byWhen.split(" ");
        for (int i = 1; i < dueDate.length; i++) {
            if (i > 1) {
                date += " ";
            }
            date += dueDate[i];
        }
        return date;
    }

    /**
     * Returns a Task that contains a newly formatted deadline.
     *
     * @param messages A TaskList of messages.
     * @param date A String consists of deadline of task.
     * @param desc A String consists of description of task.
     * @return A Task with a formatted deadline.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If no due date is provided.
     */
    public static Task getFormattedDeadlineTask(TaskList messages, String date, String desc)
            throws EmptyInputException, InvalidInputException {
        DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("MMM d yyyy");
        LocalDate newTime = LocalDate.parse(date, formatterIn);
        String formattedDate = newTime.format(formatterOut);
        messages.add(new Deadline(desc, formattedDate));
        Task currentTask = messages.get(messages.size() - 1);
        return currentTask;
    }
}
