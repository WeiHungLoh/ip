package duet;

import java.util.ArrayList;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import duet.parser.Parser;
import duet.storage.Storage;
import duet.task.Task;
import duet.task.TaskList;
import duet.ui.Ui;

/**
 * Represents main class for Duet chatbot application.
 * Duet is a chatbot to help users manage a list of tasks to perform.
 * Users can add, mark, unmark, list, delete tasks.
 *
 * @author: Loh Wei Hung
 */
public class Duet {
    private static final String FILE_NAME = "src/main/data/duet.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates an object of Duet chatbot.
     * Initialises user interface, storage and tasklist.
     *
     * @param filePath The path where tasks are stored and loaded.
     */
    public Duet(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Creates an object of Duet
     */
    public Duet() {
        ui = new Ui();
        storage = new Storage(FILE_NAME);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs Duet Chatbot so that users can add, remove or mark tasks as done.
     * @throws InvalidInputException If Deadline or Event class does not contain /by or /from respectively.
     * @throws EmptyInputException If nothing is entered after running the bot.
     */
    public void run() throws EmptyInputException, InvalidInputException {
        ui.showWelcomeMessage();
        Parser.parseTaskCLI(tasks, ui, storage);
    }

    /**
     * Returns response by Duet chatbot.
     *
     * @param input A string consists of user input.
     * @return A string consists of Duet's response.
     * @throws EmptyInputException If user enters empty input.
     * @throws InvalidInputException If dates for deadline or event task is omitted.
     */
    public String getResponse(String input) throws EmptyInputException, InvalidInputException {
        return Parser.parseTaskGUI(input, tasks, ui, storage);
    }

    public static void main(String[] args) throws EmptyInputException, InvalidInputException {
        new Duet("data/duet.txt").run();
    }
}
