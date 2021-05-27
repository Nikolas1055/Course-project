package sample.controllers;

import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.repository.FileDao;
import sample.repository.DBSingleton;
import sample.domain.DataBase;
import sample.domain.Employee;
import sample.services.StageFabric;

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
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        messageTextField.setText("Введите логин и пароль для входа. Или войдите как гость.");

        authUserButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            List<Employee> employees = dataBase.getEmployees();
            if (!loginText.equals("") && !passwordText.equals("")) {
                for (Employee employee : employees) {
                    if (employee.getLogin().equals(loginText) && employee.getPassword().equals(passwordText)) {
                        DBSingleton.getInstance().setAuthEmployee(employee);
                        openMenuScene("../view/mainMenu.fxml");
                    } else {
                        messageTextField.setText("Неправильная пара логин/пароль. Попробуйте еще раз.");
                    }
                }
            } else {
                messageTextField.setText("Вы ничего не ввели.");
            }
        });

        authGuestButton.setOnAction(actionEvent -> {
            DBSingleton.getInstance().setAuthEmployee(null);
            openMenuScene("../view/mainMenu.fxml");
        });
    }

    public void openMenuScene(String window) {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        Stage previousStage = (Stage) authGuestButton.getScene().getWindow();
        previousStage.close();
        Stage stage = new StageFabric(window).stage();
        stage.setOnCloseRequest(windowEvent -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, "Вы действительно хотите закрыть программу?");
            dialog.setTitle("Выход из программы");
            dialog.setHeaderText(null);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                FileDao.saveDataBaseToFile(dataBase);
                System.exit(0);
            } else {
                windowEvent.consume();
            }
        });
        stage.showAndWait();
    }
}
