package duet.ui;

import java.util.Scanner;

/**
 * Represents a class that deals with user interactions.
 * It shows welcome and good bye messages as well as error message.
 * 
 * @author: Loh Wei Hung
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints Duet Chatbot greeting message.
     */
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Duet");
        System.out.println("What can I do for you?");
    }

    public void showGoodByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an error message when there is an issue running the bot.
     */
    public void showLoadingError() {
        System.out.println("Encountered an error loading tasks from file");
    }

    /**
     * Returns a line of user input.
     * 
     * @return A string consisting of user input.
     */
    public String nextLine() {
        return scanner.nextLine();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}
