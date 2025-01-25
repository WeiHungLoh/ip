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
     */
    public void run() {
        ui.showWelcomeMessage();
        Parser.parseTask(tasks, ui, storage);
    }

    public static void main(String[] args) {
        new Duet("data/duet.txt").run();
    }
}
