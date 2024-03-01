package gigglebytes.ui;

import gigglebytes.GiggleBytes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    @FXML
    private ImageView backgroundImage;

    private GiggleBytes giggleBytes;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image giggleBytesImage = new Image(this.getClass().getResourceAsStream("/images/GiggleBytes.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);

        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/images/GiggleBytesBackground.png"));
            backgroundImage.setImage(bgImage);

            javafx.application.Platform.runLater(() -> {
                if (backgroundImage.getScene() != null) {
                    backgroundImage.fitWidthProperty().bind(
                            backgroundImage.getScene().widthProperty()
                    );
                    backgroundImage.fitHeightProperty().bind(
                            backgroundImage.getScene().heightProperty()
                    );
                }
            });

        } catch (Exception e) {
            System.out.println("Background image not found, using default");
            backgroundImage.setImage(null);
            backgroundImage.setStyle("-fx-background-color: #2b2b2b;");
        }
    }

    /**
     * Injects the GiggleBytes instance.
     *
     * @param g The GiggleBytes instance
     */
    public void setGiggleBytes(GiggleBytes g) {
        giggleBytes = g;
    }

    /**
     * Shows the welcome message when the application starts.
     */
    public void showWelcome() {
        String welcome = "Hello! o/ I'm GiggleBytes! Your personal digital task manager!\n"
                + "I can help you track and complete your tasks! >.<\n\n"
                + "Available Commands:\n"
                + "  todo [description]\n"
                + "  deadline [description] /by [date/time]\n"
                + "  event [description] /from [start] /to [end]\n"
                + "  list\n"
                + "  find [keyword]\n"
                + "  sort [date|description|status|type]\n"
                + "  mark [number]\n"
                + "  unmark [number]\n"
                + "  delete [number]\n"
                + "  bye";
        dialogContainer.getChildren().add(
                DialogBox.getGiggleBytesDialog(welcome, giggleBytesImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing GiggleBytes' reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.trim().isEmpty()) {
            userInput.clear();
            return;
        }

        String response = giggleBytes.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGiggleBytesDialog(response, giggleBytesImage)
        );

        userInput.clear();
    }
}