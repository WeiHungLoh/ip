package duet;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Duet duet = new Duet();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Set up the chatbot instance properly
            MainWindow controller = fxmlLoader.getController();
            controller.setDuet(duet);

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Duet Chatbot"); // Give a title to the window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
