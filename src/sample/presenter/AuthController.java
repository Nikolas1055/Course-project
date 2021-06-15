package sample.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.services.AuthLoader;
import sample.services.DBSingleton;
import sample.services.StageFabric;
import sample.ui.animations.ShakeField;
import sample.ui.views.Config;

import java.util.ResourceBundle;

public class AuthController {
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
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
        messageTextField.setText(resourceBundle.getString("auth_message1"));

        authUserButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                if (new AuthLoader().authUser(loginText, passwordText)) {
                    openMenuScene(authUserButton, Config.MAIN_MENU);
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
            openMenuScene(authGuestButton, Config.MAIN_MENU);
        });
    }

    /**
     * Метод открывающий новое окно после авторизации пользователя, закрывает предыдущее окно
     *
     * @param fxml - на вход передается предыдущая сцена для закрытия
     */
    public void openMenuScene(Button button, String fxml) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        new StageFabric(fxml).stage().show();
    }
}
