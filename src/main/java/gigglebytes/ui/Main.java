package gigglebytes.ui;

import java.io.IOException;

import gigglebytes.GiggleBytes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for GiggleBytes using FXML.
 */
public class Main extends Application {

    private GiggleBytes giggleBytes = new GiggleBytes();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("GiggleBytes");
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            controller.setGiggleBytes(giggleBytes);

            // Show welcome message
            controller.showWelcome();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}