package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HrOfficeMenuController {

    @FXML
    private Tab addNewEmployeeTab;

    @FXML
    private Tab addNewDepartmentTab;

    @FXML
    private Tab dismissEmployeeTab;

    @FXML
    private Tab addNewPostTab;

    @FXML
    private Tab changeEmployeeTab;

    @FXML
    private Button backButton;

    @FXML
    private Tab changeDepartmentTab;

    @FXML
    private Label messageTextField;

    @FXML
    void initialize() {

        if (addNewEmployeeTab.isSelected()) {
            messageTextField.setText("Добавление новых сотрудников.");
        }

        changeDepartmentTab.setOnSelectionChanged(event -> messageTextField.setText("Изменение информации об отделах."));
        changeEmployeeTab.setOnSelectionChanged(event -> messageTextField.setText("Изменение информации о сотрудниках."));
        addNewPostTab.setOnSelectionChanged(event -> messageTextField.setText("Добавление новых должностей."));
        dismissEmployeeTab.setOnSelectionChanged(event -> messageTextField.setText("Увольнение сотрудников."));
        addNewDepartmentTab.setOnSelectionChanged(event -> messageTextField.setText("Добавление новых отделов."));
        addNewEmployeeTab.setOnSelectionChanged(event -> messageTextField.setText("Добавление новых сотрудников."));

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }
}
