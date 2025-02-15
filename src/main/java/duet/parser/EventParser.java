package duet.parser;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.Event;
import duet.task.Task;
import duet.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventParser {
    /**
     * Returns a String consists of event description, start and end dates.
     *
     * @param storage Storage to save and load data.
     * @param dates String array containing start and end dates.
     * @param messages The current tasks in a TaskList.
     * @return A Strings consists of event tasks.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If start or end date is empty.
     */
    public static String getEventTask(Storage storage, String[] dates, TaskList messages)
            throws EmptyInputException, InvalidInputException {
        String desc = "";
        String[] descArray = dates[0].split(" ");
        assert dates.length > 2 : "Both start and end dates must be provided.";
        try {
            if (dates.length == 1) {
                throw new EmptyInputException("Event must have a description, start and end date");
            } else if (dates.length < 3) {
                throw new InvalidInputException("Event must have a start and end date in YYYY-MM-dd format");
            }
        } catch (EmptyInputException | InvalidInputException e) {
            return e.getMessage();
        }
        for (int i = 1; i < descArray.length; i++) {
            if (i > 1) {
                desc += " ";
            }
            desc += descArray[i];
        }
        String fromDate = getFromDate(dates);
        String toDate = getToDate(dates);
        Task currentTask = getFormattedEventTask(messages, desc, fromDate, toDate);
        String otherDesc = "";
        otherDesc += Parser.updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "I CAN DO IT!\nGot it. I've added this task:\n" + " " + currentTask.toString() + "\n"
                + "\n" + otherDesc;
    }

    /**
     * Returns a String consists of end date for event task.
     *
     * @param dates A String array containing start and end date.
     * @return A String consists of end date date.
     */
    public static String getToDate(String[] dates) {
        String to = dates[2];
        String toDate = "";
        String[] toArray = to.split(" ");
        for (int k = 1; k < toArray.length; k++) {
            if (k > 1) {
                toDate += " ";
            }
            toDate += toArray[k];
        }
        return toDate;
    }

    /**
     * Returns a String consists of start date for event task.
     *
     * @param dates A String array consists of start and end date.
     * @return A String of start date.
     */
    public static String getFromDate(String[] dates) {
        String from = dates[1];
        String fromDate = "";
        String[] fromArray = from.split(" ");
        for (int j = 1; j < fromArray.length; j++) {
            if (j > 1) {
                fromDate += " ";
            }
            fromDate += fromArray[j];
        }
        return fromDate;
    }

    public static Task getFormattedEventTask(TaskList messages, String desc, String fromDate, String toDate)
            throws EmptyInputException, InvalidInputException {
        DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("MMM d yyyy");
        LocalDate newTime = LocalDate.parse(fromDate, formatterIn);
        String newFromDate = newTime.format(formatterOut);
        LocalDate secondNewTime = LocalDate.parse(toDate, formatterIn);
        String newToDate = secondNewTime.format(formatterOut);
        messages.add(new Event(desc, newFromDate, newToDate));
        Task currentTask = messages.get(messages.size() - 1);
        return currentTask;
    }
}
