package sample.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.services.AuthLoader;
import sample.services.DBSingleton;
import sample.services.MainMenuLoader;
import sample.services.StageFabric;
import sample.ui.animations.ShakeField;
import sample.ui.views.Config;

import java.util.Optional;
import java.util.ResourceBundle;

public class AuthController {
    @FXML
    private Button authUserButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authGuestButton;
    @FXML
    private Label messageTextField;
    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        messageTextField.setText(resourceBundle.getString("auth_message1"));

        authUserButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                if (new AuthLoader().authUser(loginText, passwordText)) {
                    openMenuScene(Config.MAIN_MENU);
                } else {
                    ShakeField userLoginAnim = new ShakeField(loginField);
                    ShakeField userPasswordAnim = new ShakeField(passwordField);
                    userLoginAnim.playAnimation();
                    userPasswordAnim.playAnimation();
                    messageTextField.setText(resourceBundle.getString("auth_message2"));
                }
            } else {
                messageTextField.setText(resourceBundle.getString("auth_message3"));
            }
        });

        authGuestButton.setOnAction(actionEvent -> {
            new AuthLoader().authGuest();
            openMenuScene(Config.MAIN_MENU);
        });
    }

    /**
     * Метод открывающий новое окно после авторизации пользователя, закрывает предыдущее окно
     *
     * @param window - на вход передается предыдущая сцена для закрытия
     */
    public void openMenuScene(String window) {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        Stage previousStage = (Stage) authGuestButton.getScene().getWindow();
        previousStage.close();
        Stage stage = new StageFabric(window).stage();
        stage.setOnCloseRequest(windowEvent -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("main_message1"));
            dialog.setTitle(resourceBundle.getString("main_message2"));
            dialog.setHeaderText(null);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new MainMenuLoader().saveDataBaseAndExit();
            } else {
                windowEvent.consume();
            }
        });
        stage.showAndWait();
    }
}
