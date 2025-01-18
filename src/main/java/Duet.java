import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents main class for Duet chatbot application.
 * Duet is a chatbot to help users manage a list of tasks to perform.
 * Users can add, mark, unmark, list, delete tasks.
 * 
 * @author: Loh Wei Hung
 */
public class Duet {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
        
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> messages = new ArrayList<>();

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
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription());
            } else if (command[0].equals("unmark")) {
                int idx = Integer.parseInt(command[1]) - 1; // decrement index since ArrayList is zero-indexed

                if (Integer.parseInt(command[1]) > messages.size()) {
                    throw new IndexOutOfBoundsException();
                }

                messages.get(idx).unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  [" + messages.get(idx).getStatusIcon() + "] " + messages.get(idx).getDescription());
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
                String byWhen = dates[1];
                String[] dueDate = byWhen.split(" ");

                for (int i = 1; i < dueDate.length; i++) {
                    if (i > 1) {
                        date += " ";
                    } 

                    date += dueDate[i];
                }
            
                messages.add(new Deadline(desc, date));
                Task currentTask = messages.get(messages.size() - 1); 
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + currentTask.toString());
                
                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }
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

                messages.add(new Event(desc, fromDate, toDate));
                Task currentTask = messages.get(messages.size() - 1); 
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + currentTask.toString());

                if (messages.size() > 1) {
                    System.out.println("Now you have " + messages.size() + " tasks in the list.");
                } else {
                    System.out.println("Now you have " + messages.size() + " task in the list.");
                }
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
            } else if (message.equals("")) {
                try {
                    throw new EmptyInputException("The description cannot be empty.");
                } catch (EmptyInputException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            } else {
                messages.add(new Task(message));
                System.out.println("added: " + message);
            }
        }

        scan.close();
    }
}
