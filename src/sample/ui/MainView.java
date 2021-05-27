package sample.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/auth.fxml")));
        primaryStage.setTitle("Система учета сотрудников");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../view/auth_icon.png"))));
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }
}
