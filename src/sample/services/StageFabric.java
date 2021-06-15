package sample.services;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.domain.Role;
import sample.ui.views.Config;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Унифицированная фабрика создания окон программы
 */
public class StageFabric {
    private final String view;
    private final ResourceBundle resourceBundle;

    public StageFabric(String view) {
        resourceBundle = DBSingleton.getInstance().getResourceBundle();
        this.view = view;
    }

    /**
     * Создает новое окно на основе переданного FXML файла
     *
     * @return - возвращает STAGE
     */
    public Stage stage() {
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
        stage.setTitle(setTitle(view));
        stage.setOnCloseRequest(windowEvent -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("main_message1"));
            Stage dialogStage = (Stage) dialog.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream(Config.ICON))));
            dialog.setTitle(resourceBundle.getString("main_message2"));
            dialog.setHeaderText(null);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new MainMenuLoader().saveDataBaseAndExit();
            } else {
                windowEvent.consume();
            }
        });
        stage.setResizable(false);
        fade.setNode(root);
        fade.play();
        return stage;
    }

    private String setTitle(String fxml) {
        String title = resourceBundle.getString("title");
        if (fxml.equals(Config.AUTH)) {
            return title;
        } else {
            return title + getAuthUserData();
        }
    }

    private String getAuthUserData() {
        if (DBSingleton.getInstance().getAuthEmployee() == null) {
            return resourceBundle.getString("auth_user_guest");
        } else if (DBSingleton.getInstance().getAuthEmployee().getRole() == Role.HR_OFFICER) {
            return resourceBundle.getString("auth_user_hr");
        } else {
            return resourceBundle.getString("auth_user_admin");
        }
    }
}