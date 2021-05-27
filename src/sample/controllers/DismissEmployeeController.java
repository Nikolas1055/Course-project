package sample.controllers;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import sample.repository.DBSingleton;
import sample.domain.Department;
import sample.domain.Employee;
import sample.services.Service;

public class DismissEmployeeController {

    @FXML
    private ComboBox<Employee> employeeToDismissComboBox;

    @FXML
    private TextArea dismissMessageTextArea;

    @FXML
    private Button dismissEmployeeButton;

    @FXML
    void initialize() {
        dismissMessageTextArea.setText("Выберите сотрудника для увольнения.");

        employeeToDismissComboBox.setOnMouseClicked(mouseEvent -> setDismissPresets());

        employeeToDismissComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    AtomicBoolean dismissible = new AtomicBoolean(false);
                    for (Department department : DBSingleton.getInstance().getDataBase().getDepartments()) {
                        if (department.getChief() != null && department.getChief().equals(newValue)) {
                            dismissible.set(true);
                            dismissEmployeeButton.setDisable(dismissible.get());
                            dismissMessageTextArea.setText("Вы не можете уволить сотрудника - " +
                                    System.lineSeparator() +
                                    employeeToDismissComboBox.getValue().getFullName() +
                                    System.lineSeparator() +
                                    "Он является начальником отдела - " + department.getName() +
                                    System.lineSeparator() +
                                    "Назначьте другого сотрудника начальником и попробуйте еще раз.");
                        }
                    }
                    if (!dismissible.get() && newValue != null) {
                        if (newValue.equals(DBSingleton.getInstance().getAuthEmployee())) {
                            dismissMessageTextArea.setText("Вы не можете уволить сами себя.");
                        } else {
                            dismissEmployeeButton.setDisable(dismissible.get());
                            dismissMessageTextArea.setText("Уволить сотрудника? " + System.lineSeparator() +
                                    employeeToDismissComboBox.getValue());
                        }
                    }
                });

        dismissEmployeeButton.setOnAction(actionEvent -> {
            dismissMessageTextArea.setText("Сотрудник уволен.");
            DBSingleton.getInstance().getDataBase().getEmployees().remove(employeeToDismissComboBox.getValue());
            employeeToDismissComboBox.getSelectionModel().clearSelection();
        });
    }

    public void setDismissPresets() {
        employeeToDismissComboBox.setItems(FXCollections.observableArrayList(
                DBSingleton.getInstance().getDataBase().getEmployees()));
        Service.setEmployee(employeeToDismissComboBox);
        dismissEmployeeButton.setDisable(true);
    }
}
