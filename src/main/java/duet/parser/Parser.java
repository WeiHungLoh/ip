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
     * Reads user input and perform the corresponding action
     * like adding, removing or marking tasks as done.
     *
     * @param messages Tasks in TaskList.
     * @param ui Ui to read user input.
     * @param storage Storage to load and save data.
     * @throws InvalidInputException If Deadline or Event class does not have /by or /from when parsing user input.
     * @throws EmptyInputException If user enters without typing a command.
     */
    public static void parseTask(TaskList messages, Ui ui, Storage storage)
            throws EmptyInputException, InvalidInputException {
        while (true) {
            String message = ui.nextLine();
            String[] command = message.split(" ");
            String[] dates = message.split("/");

            if (message.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (message.equals("list")) {
                System.out.println("Here are the tasks in your list:");

                for (int i = 0; i < messages.size(); i++) {
                    String index = String.valueOf(i + 1); // increment index since it starts from 1
                    Task taskName = messages.get(i);
                    System.out.println(index + "." + taskName.toString());
                }
            } else if (command[0].equals("find")) {
                String keyword = command[1];
                ArrayList<Task> newTasks = new ArrayList<>();

                if (command.length < 2) {
                    throw new EmptyInputException("The keyword cannot be empty");
                }

                for (Task task : messages.getTasks()) {
                    if (task.toString().contains(keyword)) {
                        newTasks.add(task);
                    }
                }

                if (newTasks.size() > 0) {
                    System.out.println("Here are the matching tasks in your list:");

                    for (Task task : newTasks) {
                        System.out.println(task.toString());
                    }
                } else {
                    System.out.println("Task is not found.");
                }
            } else if (command[0].equals("mark")) {
                int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed

                if (Integer.parseInt(command[1]) > messages.size()) {
                    throw new IndexOutOfBoundsException();
                }

                messages.get(idx).markAsDone();;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] "
                        + messages.get(idx).getDescription());
                storage.save(messages.getTasks());
            } else if (command[0].equals("unmark")) {
                int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed

                if (Integer.parseInt(command[1]) > messages.size()) {
                    throw new IndexOutOfBoundsException();
                }

                messages.get(idx).unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] "
                        + messages.get(idx).getDescription());
                storage.save(messages.getTasks());
            } else if (command[0].equals("deadline")) {
                String desc = "";
                String[] descArray = dates[0].split(" ");

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

                DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("MMM d yyyy");
                LocalDate newTime = LocalDate.parse(date, formatterIn);
                String formattedDate = newTime.format(formatterOut);
                messages.add(new Deadline(desc, formattedDate));
                Task currentTask = messages.get(messages.size() - 1);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + currentTask.toString());

                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }

                storage.save(messages.getTasks());
            } else if (command[0].equals("event")) {
                String desc = "";
                String[] descArray = dates[0].split(" ");

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

                DateTimeFormatter formatterIn = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatterOut = DateTimeFormatter.ofPattern("MMM d yyyy");
                LocalDate newTime = LocalDate.parse(fromDate, formatterIn);
                String newFromDate = newTime.format(formatterOut);
                LocalDate secondNewTime = LocalDate.parse(toDate, formatterIn);
                String newToDate = secondNewTime.format(formatterOut);
                messages.add(new Event(desc, newFromDate, newToDate));
                Task currentTask = messages.get(messages.size() - 1);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + currentTask.toString());

                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }

                storage.save(messages.getTasks());
            } else if (command[0].equals("todo")) {
                String desc = "";

                for (int i = 1; i < command.length; i++) {
                    if (i > 1) {
                        desc += " ";
                    }
                    desc += command[i];
                }

                messages.add(new ToDo(desc));
                Task currentTask = messages.get(messages.size() - 1);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + currentTask.toString());

                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }

                storage.save(messages.getTasks());
            } else if (command[0].equals("delete")) {
                if (message.trim().equals("delete")) {
                    try {
                        throw new EmptyInputException("The description for delete cannot be empty.");
                    } catch (EmptyInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                if (Integer.parseInt(command[1]) > messages.size() || Integer.parseInt(command[1]) < 1) {
                    try {
                        throw new InvalidInputException("The task that you want to delete does not exist.");
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                int idx = Integer.parseInt(command[1]) - 1; // decrements index since ArrayList is zero-indexed
                Task deletedTask = messages.get(idx);
                messages.remove(idx);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + deletedTask.toString());

                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }

                storage.save(messages.getTasks());
            } else {
                messages.add(new Task(message));
                System.out.println("added: " + message);
                storage.save(messages.getTasks());
            }
        }
    }
}

