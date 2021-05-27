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

public class ChangeDepartmentController {

    @FXML
    private Button changeDepartmentButton;

    @FXML
    private ComboBox<Department> changeSuperiorDepartmentComboBox;

    @FXML
    private ComboBox<Employee> changeChiefDepartmentComboBox;

    @FXML
    private ComboBox<Department> departmentToChangeComboBox;

    @FXML
    private TextArea changeDepartmentTextArea;

    @FXML
    private TextField changeDepartmentNameTextField;

    @FXML
    void initialize() {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();

        changeDepartmentButton.disableProperty().bind(
                Bindings.isEmpty(changeDepartmentNameTextField.textProperty())
        );

        departmentToChangeComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        changeDepartmentNameTextField.setText(departmentToChangeComboBox.getValue().getName());
                        changeSuperiorDepartmentComboBox.setValue(departmentToChangeComboBox.getValue().getSuperior());
                        changeChiefDepartmentComboBox.setValue(departmentToChangeComboBox.getValue().getChief());
                    } else {
                        changeDepartmentNameTextField.clear();
                        changeSuperiorDepartmentComboBox.getSelectionModel().clearSelection();
                        changeChiefDepartmentComboBox.getSelectionModel().clearSelection();
                    }
                });

        changeDepartmentButton.setOnAction(actionEvent -> {
            if (departmentToChangeComboBox.getSelectionModel().isEmpty()) {
                changeDepartmentTextArea.setText("Вы не выбрали отдел для редактирования.");
            } else {
                departmentToChangeComboBox.getValue().setName(changeDepartmentNameTextField.getText().trim());
                departmentToChangeComboBox.getValue().setSuperior(changeSuperiorDepartmentComboBox.getValue());
                departmentToChangeComboBox.getValue().setChief(changeChiefDepartmentComboBox.getValue());
                departmentToChangeComboBox.getSelectionModel().clearSelection();
                changeDepartmentTextArea.setText("Данные отдела успешно изменены.");
            }
        });

        departmentToChangeComboBox.setOnMouseClicked(mouseEvent -> {
            departmentToChangeComboBox.setItems(FXCollections.observableArrayList(dataBase.getDepartments()));
            Service.setDepartment(departmentToChangeComboBox);
            changeSuperiorDepartmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getDepartments()));
            Service.setDepartment(changeSuperiorDepartmentComboBox);
            changeChiefDepartmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getEmployees()));
            Service.setEmployee(changeChiefDepartmentComboBox);
        });
    }
}

