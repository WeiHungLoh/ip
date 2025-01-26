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
    private static final String FILE_NAME = "./data/duet.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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
     * Runs Duet Chatbot so that users can add, remove or mark tasks as done.
     * @throws InvalidInputException If Deadline or Event class does not contain /by or /from respectively.
     * @throws EmptyInputException If nothing is entered after running the bot.
     */
    public void run() throws EmptyInputException, InvalidInputException {
        ui.showWelcomeMessage();
        Parser.parseTask(tasks, ui, storage);
    }

    public static void main(String[] args) throws EmptyInputException, InvalidInputException {
        new Duet("data/duet.txt").run();
    }
}
