package duet.parser;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.storage.Storage;
import duet.task.Task;
import duet.task.TaskList;
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
        String taskType = command[0];

        switch (taskType) {
        case "bye":
            return getByeMessage();
        case "list":
            return getTaskList(messages);
        case "find":
            return FindParser.getFindResults(command, messages);
        case "mark":
            return ToggleParser.getMarkedTask(storage, messages, command);
        case "unmark":
            return ToggleParser.getUnmarkedTask(storage, messages, command);
        case "deadline":
            return DeadlineParser.getDeadlineTask(storage, messages, dates);
        case "event":
            return EventParser.getEventTask(storage, dates, messages);
        case "todo":
            return ToDoParser.getToDoTask(storage, messages, command);
        case "delete":
            if (command.length > 1 && command[1].contains(",")) {
                return DeleteParser.getMultipleDeletedTasks(storage, messages, message, command);
            } else {
                return DeleteParser.getDeletedTask(storage, messages, message, command);
            }
        default:
            return getInvalidCommand();
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
        desc += "You are so close to completing it! I CAN DO IT!.\nHere are the tasks in your list:\n";
        for (int i = 0; i < messages.size(); i++) {
            String index = String.valueOf(i + 1);
            Task taskName = messages.get(i);
            desc += index + "." + taskName.toString() + "\n";
        }
        return desc;
    }

    /**
     * Update the message of current task.
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

    /**
     * Returns a String consists of an invalid command message.
     *
     * @return A String consists of error message regarding invalid command.
     * @throws InvalidInputException If unknown command is called.
     */
    public static String getInvalidCommand() throws InvalidInputException {
        try {
            throw new InvalidInputException("Please enter a valid command");
        } catch (InvalidInputException e) {
            return e.getMessage();
        }
    }
}
