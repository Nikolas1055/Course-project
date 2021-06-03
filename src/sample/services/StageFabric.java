package sample.services;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.ui.views.Config;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Унифицированная фабрика создания окон программы
 */
public class StageFabric {
    private final String view;

    public StageFabric(String view) {
        this.view = view;
    }

    /**
     * Создает новое окно на основе переданного FXML файла
     *
     * @return - возвращает STAGE
     */
    public Stage stage() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(view));
        loader.setResources(resourceBundle);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream(Config.ICON))));
        stage.setTitle(resourceBundle.getString("title"));
        stage.setResizable(false);
        fade.setNode(root);
        fade.play();
        return stage;
    }
}