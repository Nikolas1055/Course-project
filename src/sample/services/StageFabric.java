package sample.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StageFabric {
    private final String view;

    public StageFabric(String view) {
        this.view = view;
    }

    public Stage stage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(view));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../view/auth_icon.png"))));
        stage.setTitle("Система учета сотрудников");
        return stage;
    }
}
