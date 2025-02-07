package duet.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.Deadline;
import duet.task.Event;
import duet.task.Task;
import duet.task.TaskList;
import duet.task.ToDo;
import duet.ui.Ui;

/**
 * Represents a class that deals with user commands.
 * It parses user commands and updates the TaskList.
 *
 * @author: Loh Wei Hung
 */
public class Parser {
    /**
     * Reads user input and perform the corresponding action via Duet chatbot.
     * like adding, removing or marking tasks as done.
     *
     * @param messages Tasks in TaskList.
     * @param ui Ui to read user input.
     * @param storage Storage to load and save data.
     * @throws InvalidInputException If Deadline or Event class does not have /by or /from when parsing user input.
     * @throws EmptyInputException If user enters without typing a command.
     */
    public static String parseTaskGui(String message, TaskList messages, Ui ui, Storage storage)
            throws EmptyInputException, InvalidInputException {
        String[] command = message.split(" ");
        String[] dates = message.split("/");

        if (message.equals("bye")) {
            return getByeMessage();
        } else if (message.equals("list")) {
            return getTaskList(messages);
        } else if (command[0].equals("find")) {
            return getFindResults(command, messages);
        } else if (command[0].equals("mark")) {
            return getMarkedTask(storage, messages, command);
        } else if (command[0].equals("unmark")) {
            return getUnmarkedTask(storage, messages, command);
        } else if (command[0].equals("deadline")) {
            return getDeadlineTask(storage, messages, dates);
        } else if (command[0].equals("event")) {
            return getEventTask(storage, dates, messages);
        } else if (command[0].equals("todo")) {
            return getToDoTask(storage, messages, command);
        } else if (command[0].equals("delete")) {
            return getDeletedTask(storage, messages, message, command);
        } else {
            return getAddMessage(storage, messages, message);
        }
    }

    public static String getByeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a list of tasks that a user has added.
     *
     * @param messages A TaskList of message.
     * @return A string consisting of updated tasks.
     */
    public static String getTaskList(TaskList messages) {
        String desc = "";
        desc += "Here are the tasks in your list:\n";
        for (int i = 0; i < messages.size(); i++) {
            String index = String.valueOf(i + 1);
            Task taskName = messages.get(i);
            desc += index + "." + taskName.toString() + "\n";
        }
        return desc;
    }

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
            desc += "Here are the matching tasks in your list:\n";
            for (Task task : newTasks) {
                desc += task.toString() + "\n";
            }
            return desc;
        } else {
            return "Task is not found.";
        }
    }

    public static String getMarkedTask(Storage storage, TaskList messages, String[] command) {
        if (command[1].length() > 1 && command[1].contains(",")) {
            return getMarkedTasks(storage, messages, command);
        }
        int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed
        int taskNum = Integer.parseInt(command[1]);
        assert taskNum >= 1 && taskNum <= messages.size() : "Cannot mark a task that does not exist";

        if (taskNum > messages.size() || taskNum < 1) {
            throw new IndexOutOfBoundsException();
        }

        storage.save(messages.getTasks());
        messages.get(idx).markAsDone();
        return "Nice! I've marked this task as done:\n" + "  [" + messages.get(idx).getStatusIcon() + "] "
                + messages.get(idx).getDescription();
    }

    public static String getMarkedTasks(Storage storage, TaskList messages, String[] command) {
        String[] tasksToBeMarked = command[1].split(",");
        String taskList = "";
        for (int i = 0; i < tasksToBeMarked.length; i++) {
            int idx = Integer.parseInt(tasksToBeMarked[i]) - 1;
            messages.get(idx).markAsDone();
            if (taskList.length() > 0) {
                taskList += "\n";
            }
            taskList += " [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription();
        }
        storage.save(messages.getTasks());
        return "Nice! I've marked these tasks as done:\n" + taskList;
    }

    public static String getUnmarkedTask(Storage storage, TaskList messages, String[] command) {
        if (command[1].length() > 1 && command[1].contains(",")) {
            return getUnmarkedTasks(storage, messages, command);
        }
        int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed
        int taskNum = Integer.parseInt(command[1]);
        assert taskNum >= 1 || taskNum <= messages.size() : "Cannot unmark a task that does not exist";

        if (taskNum > messages.size() || taskNum < 1) {
            throw new IndexOutOfBoundsException();
        }

        messages.get(idx).unmarkAsDone();
        storage.save(messages.getTasks());
        return "OK, I've marked this task as not done yet:\n" + " [" + messages.get(idx).getStatusIcon()
                + "] " + messages.get(idx).getDescription();
    }

    public static String getUnmarkedTasks(Storage storage, TaskList messages, String[] command) {
        String[] tasksToBeUnmarked = command[1].split(",");
        String taskList = "";
        for (int i = 0; i < tasksToBeUnmarked.length; i++) {
            int idx = Integer.parseInt(tasksToBeUnmarked[i]) - 1;
            messages.get(idx).unmarkAsDone();
            if (taskList.length() > 0) {
                taskList += "\n";
            }
            taskList += " [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription();
        }
        storage.save(messages.getTasks());
        return "Nice! I've unmarked these tasks:\n" + taskList;
    }

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
        assert dates.length > 1 : "Deadline must be provided";
        for (int j = 1; j < descArray.length; j++) {
            if (j > 1) {
                desc += " ";
            }
            desc += descArray[j];
        }
        String date = "";
        String byWhen = dates[1].trim();
        String[] dueDate = byWhen.split(" ");
        for (int i = 1; i < dueDate.length; i++) {
            if (i > 1) {
                date += " ";
            }
            date += dueDate[i];
        }

        Task currentTask = getFormattedDeadlineTask(messages, date, desc);
        String otherDesc = "";
        otherDesc += updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "Got it. I've added this task:\n " + currentTask.toString()
                + "\n" + otherDesc;
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

    public static String getEventTask(Storage storage, String[] dates, TaskList messages)
            throws EmptyInputException, InvalidInputException {
        String desc = "";
        String[] descArray = dates[0].split(" ");
        assert dates.length > 2 : "Both start and end dates must be provided.";
        for (int i = 1; i < descArray.length; i++) {
            if (i > 1) {
                desc += " ";
            }
            desc += descArray[i];
        }
        String fromDate = "";
        String from = dates[1];
        String[] fromArray = from.split(" ");
        for (int j = 1; j < fromArray.length; j++) {
            if (j > 1) {
                fromDate += " ";
            }
            fromDate += fromArray[j];
        }
        String toDate = "";
        String to = dates[2];
        String[] toArray = to.split(" ");
        for (int k = 1; k < toArray.length; k++) {
            if (k > 1) {
                toDate += " ";
            }
            toDate += toArray[k];
        }
        Task currentTask = getFormattedEventTask(messages, desc, fromDate, toDate);
        String otherDesc = "";
        otherDesc += updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "Got it. I've added this task:\n" + " " + currentTask.toString() + "\n"
                + "\n" + otherDesc;
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
        otherDesc += updateCurrentTaskMessage(messages, otherDesc);
        storage.save(messages.getTasks());
        return "Got it. I've added this task:\n" + " " + currentTask.toString()
                + "\n" + otherDesc;
    }

    /**
     * Update a the message of current task.
     *
     * @param messages A TaskList of messages.
     * @param otherDesc A String consists of the number of tasks.
     */
    public static String updateCurrentTaskMessage(TaskList messages, String otherDesc) {
        if (messages.size() > 1) {
            return "Now you have " + messages.size() + " tasks in the list.";
        } else {
            return "Now you have " + messages.size() + " task in the list.";
        }
    }

    public static String getDeletedTask(Storage storage, TaskList messages,
            String message, String[] command) {
        try {
            int taskNum = Integer.parseInt(command[1]);
            assert taskNum <= messages.size() || taskNum >= 1 : "Task does not exist";
            if (message.trim().equals("delete")) {
                try {
                    throw new EmptyInputException("The description for delete cannot be empty.");
                } catch (EmptyInputException e) {
                    return e.getMessage();
                }
            }
            if (taskNum > messages.size() || taskNum < 1) {
                try {
                    throw new InvalidInputException("The task that you want to delete does not exist.");
                } catch (InvalidInputException e) {
                    return e.getMessage();
                }
            }
            int idx = Integer.parseInt(command[1]) - 1; // decrements index since ArrayList is zero-indexed
            Task deletedTask = messages.get(idx);
            messages.remove(idx);
            String otherDesc = "";
            otherDesc += updateCurrentTaskMessage(messages, otherDesc);
            storage.save(messages.getTasks());
            return "Noted. I've removed this task:\n" + " " + deletedTask.toString()
                    + "\n" + otherDesc;
        } catch (NumberFormatException e) {
            return "Please delete one task at a time";
        }
    }

    /**
     * Returns a String consists of a general task that has been added.
     *
     * @param storage Storage where tasks are saved.
     * @param messages A TaskList of messages.
     * @param message A String consists of description of task.
     * @return A String consists of task that has been added.
     * @throws EmptyInputException
     */
    public static String getAddMessage(Storage storage, TaskList messages, String message)
            throws EmptyInputException {
        messages.add(new Task(message));
        storage.save(messages.getTasks());
        return "added: " + message;
    }
}
