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
            System.out.println("Unable to save tasks: "+ e.getMessage());
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
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String task = scan.nextLine();
                String taskType = task.substring(3,4);

                if (taskType.equals("T")) {
                    boolean isDone = task.substring(6,7).equals("X") ? true : false;
                    String desc = task.substring(9);

                    if (isDone) {
                        tasks.add(new ToDo(desc));
                        tasks.get(tasks.size() - 1).markAsDone();
                    } else {
                        tasks.add(new ToDo(desc));
                    }
                } else if (taskType.equals("D")) {
                    boolean isDone = task.substring(6,7).equals("X") ? true : false;
                    String body = task.substring(9);
                    String[] desc = body.split("\\(");
                    String by = desc[1].replace(")", "").replace("by: ", "");

                    if (isDone) {
                        tasks.add(new Deadline(desc[0].trim(), by));
                        tasks.get(tasks.size() - 1).markAsDone();
                    } else {
                        tasks.add(new Deadline(desc[0].trim(), by));
                    }
                } else if (taskType.equals("E")) {
                    boolean isDone = task.substring(6,7).equals("X") ? true : false;
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
                } else {
                    boolean isDone = task.substring(3,4).equals("X") ? true : false;
                    String[] desc = task.split("\\]");

                    if (isDone) {
                        tasks.add(new Task(desc[1].trim()));
                        tasks.get(tasks.size() - 1).markAsDone();
                    } else {
                        tasks.add(new Task(desc[1].trim()));
                    }
                }
            }
            
            scan.close();
        } catch (IOException e) {
            System.out.println("Unable to load tasks: " + e.getMessage());
        }
        return tasks;
    }
}
