package gigglebytes.ui;

import java.io.IOException;

import gigglebytes.GiggleBytes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;

/**
 * A GUI for GiggleBytes using FXML.
 */
public class Main extends Application {

    private GiggleBytes giggleBytes = new GiggleBytes();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            StackPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap, 500, 700);

            stage.setTitle("GiggleBytes");

            try {
                Image icon = new Image(getClass().getResourceAsStream("/images/GiggleBytes.png"));
                stage.getIcons().add(icon);
            } catch (Exception e) {
                System.out.println("Icon image not found, using default");
            }

            stage.setMinWidth(400);
            stage.setMinHeight(500);
            stage.setResizable(true);

            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            controller.setGiggleBytes(giggleBytes);

            controller.showWelcome();

            stage.setOnCloseRequest((WindowEvent event) -> {
                System.out.println("Window closing - saving tasks...");
                giggleBytes.saveTasks();
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}