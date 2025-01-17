import java.util.ArrayList;
import java.util.Scanner;

public class Duet {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
        
        Scanner scan = new Scanner(System.in);
        ArrayList<String> messages = new ArrayList<>();

        while (true) {
            String message = scan.nextLine(); 
            messages.add(message); // store message into ArrayList

            if (message.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (message.equals("list")) { 
                for (int i = 0; i < messages.size(); i++) {
                    String index = String.valueOf(i + 1);
                    System.out.println(index + ". " + messages.get(i));
                }
            } else {
                System.out.println("added: " + message);
            }
        }

        scan.close();
    }
}
