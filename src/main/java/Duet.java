import java.util.ArrayList;
import java.util.Scanner;

public class Duet {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
        
        Scanner scan = new Scanner(System.in);
        ArrayList<Task> messages = new ArrayList<>();

        while (true) {
            String message = scan.nextLine(); 
            String[] command = message.split(" ");      

            if (message.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (message.equals("list")) { 
                System.out.println("Here are the tasks in your list:");

                for (int i = 0; i < messages.size(); i++) {
                    String index = String.valueOf(i + 1); // increment index since it starts from 1
                    Task taskName = messages.get(i);
                    System.out.println(index + "." + "[" + taskName.getStatusIcon() + "] " + taskName.getDescription());
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
            } else {
                messages.add(new Task(message));
                System.out.println("added: " + message);
            }
        }

        scan.close();
    }
}
