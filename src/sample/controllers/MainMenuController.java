package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.repository.DBSingleton;
import sample.domain.Employee;
import sample.domain.Role;
import sample.services.StageFabric;

public class MainMenuController {

    @FXML
    private Button hrOfficeButton;

    @FXML
    private Button adminPanelButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Label messageTextField;

    @FXML
    private Button findEmployeeButton;

    @FXML
    void initialize() {
        Employee authEmployee = DBSingleton.getInstance().getAuthEmployee();
        if (authEmployee == null) {
            hrOfficeButton.setVisible(false);
            adminPanelButton.setVisible(false);
        } else if (authEmployee.getRole() == Role.HR_OFFICER) {
            adminPanelButton.setVisible(false);
        }
        messageTextField.setText("Отчеты и поиск сотрудников в базе данных.");
        hrOfficeButton.setOnAction(actionEvent -> new StageFabric("../view/hrOfficeMenu.fxml").stage().show());
        reportsButton.setOnAction(actionEvent -> new StageFabric("../view/reportsMenu.fxml").stage().show());
        findEmployeeButton.setOnAction(actionEvent -> new StageFabric("../view/finderMenu.fxml").stage().show());
    }
}
