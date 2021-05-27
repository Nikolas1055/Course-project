package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.repository.DBSingleton;
import sample.domain.DataBase;
import sample.domain.Department;
import sample.domain.Employee;
import sample.services.Service;

public class AddNewDepartmentController {

    @FXML
    private Button addNewDepartmentButton;

    @FXML
    private TextArea newDepartmentTextArea;

    @FXML
    private ComboBox<Department> superiorNewDepartmentComboBox;

    @FXML
    private TextField newDepartmentTextField;

    @FXML
    private ComboBox<Employee> chiefNewDepartmentComboBox;

    @FXML
    void initialize() {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        setNewDepartmentPresets(dataBase);
        newDepartmentTextArea.setText("Введите данные нового отдела.");

        addNewDepartmentButton.disableProperty().bind(
                Bindings.isEmpty(newDepartmentTextField.textProperty())
        );

        addNewDepartmentButton.setOnAction(actionEvent -> {
            boolean isDepartmentExist = false;
            for (Department department : dataBase.getDepartments()) {
                if (department.getName().equals(newDepartmentTextField.getText().trim())) {
                    isDepartmentExist = true;
                    newDepartmentTextArea.setText("Отдел с таким названием уже существует.");
                }
            }
            if (!isDepartmentExist) {
                dataBase.getDepartments().add(new Department(
                        newDepartmentTextField.getText().trim(),
                        superiorNewDepartmentComboBox.getValue(),
                        chiefNewDepartmentComboBox.getValue()
                ));
                newDepartmentTextArea.setText("Новый отдел успешно добавлен.");
                setNewDepartmentPresets(dataBase);
            }
        });
    }

    private void setNewDepartmentPresets(DataBase dataBase) {
        chiefNewDepartmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getEmployees()));
        superiorNewDepartmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getDepartments()));
        Service.setEmployee(chiefNewDepartmentComboBox);
        Service.setDepartment(superiorNewDepartmentComboBox);
    }
}
