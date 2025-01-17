import java.util.Scanner;

public class Duet {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
        
        Scanner scan = new Scanner(System.in);

        while (true) {
            String message = scan.nextLine(); 

            if (message.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else {
                System.out.println(message);
            }
        }

        scan.close();
    }
}
