package duet;

import duet.exception.EmptyInputException;
import duet.exception.InvalidInputException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duet duet;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initialises the dialog boxes.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String response = "Hello! I'm Duet\nWhat can I do for you?";
        dialogContainer.getChildren().addAll(
            DialogBox.getDukeDialog(response, dukeImage)
        );
    }

    /** Injects the Duke instance */
    public void setDuet(Duet d) {
        duet = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     *
     * @throws InvalidInputException If deadline or event has no by or from date.
     * @throws EmptyInputException If user input is empty.
     */
    @FXML
    private void handleUserInput() throws EmptyInputException, InvalidInputException {
        String input = userInput.getText();
        try {
            String response = duet.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );

            String byeMessage = "Bye. Hope to see you again soon!";
            if (input.equals("bye")) {
                dialogContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(byeMessage, dukeImage)
                );
                closeWindow();
            }
        } catch (EmptyInputException | InvalidInputException e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(e.getMessage(), dukeImage)
            );
        } catch (NumberFormatException e) {
            dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(e.getMessage(), dukeImage)
            );
        } catch (Exception e) {
            dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog("An unexpected error occurred. Please "
                        + "ensure that you have added task description, start or end date in the correct format"
                        + ". If you're marking/unmarking tasks as done, make sure they already exist",
                        dukeImage)
            );
        }
        userInput.clear();
    }

    private void closeWindow() {
        Stage stage = (Stage) userInput.getScene().getWindow();
        stage.close();
    }
}
