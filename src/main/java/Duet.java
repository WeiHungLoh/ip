import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 * Represents main class for Duet chatbot application.
 * Duet is a chatbot to help users manage a list of tasks to perform.
 * Users can add, mark, unmark, list, delete tasks.
 * 
 * @author: Loh Wei Hung
 */
public class Duet {
    private static final String FILE_NAME = "./data/duet.txt";

    public static void main(String[] args) {

        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
        
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> messages = new ArrayList<>();
        loadTasks(messages);

        while (true) {
            String message = scan.nextLine(); 
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
            } else if (command[0].equals("mark")) {
                int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed

                if (Integer.parseInt(command[1]) > messages.size()) {
                    throw new IndexOutOfBoundsException();
                }

                messages.get(idx).markAsDone();;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] " 
                        + messages.get(idx).getDescription());
                saveTasks(messages);
            } else if (command[0].equals("unmark")) {
                int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed

                if (Integer.parseInt(command[1]) > messages.size()) {
                    throw new IndexOutOfBoundsException();
                }

                messages.get(idx).unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] " 
                        + messages.get(idx).getDescription());
                saveTasks(messages);
            } else if (command[0].equals("deadline")) {
                if (message.trim().equals("deadline")) {
                    try {
                        throw new EmptyInputException("The description for deadline cannot be empty.");
                    } catch (EmptyInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                if (dates.length == 1) {
                    try {
                        throw new InvalidInputException("Invalid deadline command.");
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

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
                saveTasks(messages);
            } else if (command[0].equals("event")) {
                if (message.trim().equals("event")) {
                    try {
                        throw new EmptyInputException("The description for event cannot be empty.");
                    } catch (EmptyInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

                if (dates.length == 1) {
                    try {
                        throw new InvalidInputException("Invalid event command.");
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

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
                saveTasks(messages);
            } else if (command[0].equals("todo")) {
                if (message.trim().equals("todo")) {
                    try {
                        throw new EmptyInputException("The description for todo cannot be empty.");
                    } catch (EmptyInputException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                }

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
                System.out.println("  "+ currentTask.toString());
                
                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }
                saveTasks(messages);
            } else if (message.equals("")) {
                try {
                    throw new EmptyInputException("The description cannot be empty.");
                } catch (EmptyInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
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
                saveTasks(messages);
            } else {
                messages.add(new Task(message));
                System.out.println("added: " + message);
                saveTasks(messages);
            }
        }

        scan.close();
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
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

    private static void loadTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
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
    }

}
