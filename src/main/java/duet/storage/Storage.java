package duet.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.task.Deadline;
import duet.task.Event;
import duet.task.Task;
import duet.task.ToDo;

/**
 * Represents a class that deals with loading
 * and saving tasks from a file.
 *
 * @author: Loh Wei Hung
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves every single task inside duet.txt file.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            int idx = 1;

            for (Task task : tasks) {
                fw.write(String.valueOf(idx) + "." + task.toString() + System.lineSeparator());
                idx++;
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Unable to save tasks: " + e.getMessage());
        }
    }

    /**
     * Returns ArrayList of Task in the previous interaction with Duet chatbot.
     *
     * @return An ArrayList of Task.
     * @throws InvalidInputException If Event or Deadline task does not have /by or /from respectively.
     * @throws EmptyInputException If user enters without typing a command.
     */
    public ArrayList<Task> load() throws EmptyInputException, InvalidInputException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = createFilePath(filePath);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String task = scan.nextLine();
                String taskType = task.substring(3, 4);
                if (taskType.equals("T")) {
                    loadToDoTask(tasks, task);
                } else if (taskType.equals("D")) {
                    loadDeadlineTask(tasks, task);
                } else if (taskType.equals("E")) {
                    loadEventTask(tasks, task);
                }
            }
            scan.close();
        } catch (IOException e) {
            System.out.println("Unable to load tasks: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Creates a new file if it doesn't exist.
     *
     * @param filePath String consists of name of file path.
     * @return File path.
     * @throws IOException If input is invalid.
     */
    public static File createFilePath(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }

    /**
     * Updates ArrayList with ToDo task.
     *
     * @param tasks An ArrayList of tasks.
     * @param task A String consists of ToDo task.
     * @throws EmptyInputException
     */
    public void loadToDoTask(ArrayList<Task> tasks, String task) throws EmptyInputException {
        boolean isDone = task.substring(6, 7).equals("X") ? true : false;
        String desc = task.substring(9);

        if (isDone) {
            tasks.add(new ToDo(desc));
            tasks.get(tasks.size() - 1).markAsDone();
        } else {
            tasks.add(new ToDo(desc));
        }
    }

    /**
     * Updates ArrayList with deadline task.
     *
     * @param tasks An ArrayList of tasks.
     * @param task A String consists of Deadline task.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If due date is empty.
     */
    public void loadDeadlineTask(ArrayList<Task> tasks, String task)
            throws EmptyInputException, InvalidInputException {
        boolean isDone = task.substring(6, 7).equals("X");
        String body = task.substring(9);
        String[] desc = body.split("\\(");
        String by = desc[1].replace(")", "").replace("by: ", "");

        if (isDone) {
            tasks.get(tasks.size() - 1).markAsDone();
        }
        tasks.add(new Deadline(desc[0].trim(), by));
    }

    /**
     * Updates an ArrayList with event task.
     *
     * @param tasks An ArrayList of tasks.
     * @param task A String consts of Event task.
     * @throws EmptyInputException If description is empty.
     * @throws InvalidInputException If start or end date is empty.
     */
    public void loadEventTask(ArrayList<Task> tasks, String task)
            throws EmptyInputException, InvalidInputException {
        boolean isDone = task.substring(6, 7).equals("X") ? true : false;
        String body = task.substring(9);
        String[] desc = body.split("\\(");
        String[] dateRange = desc[1].split(" to: ");
        String from = dateRange[0].replace("from: ", "");
        String to = dateRange[1].replace(")", "");

        if (isDone) {
            tasks.add(new Event(desc[0].trim(), from, to));
            tasks.get(tasks.size() - 1).markAsDone();
        } else {
            tasks.add(new Event(desc[0].trim(), from, to));
        }
    }
}
