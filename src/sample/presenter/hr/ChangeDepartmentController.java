package sample.presenter.hr;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.domain.Department;
import sample.domain.Employee;
import sample.services.hr.ChangeDepartmentLoader;
import sample.services.DBSingleton;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

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
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg1"));
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
                changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg2"));
            } else {
                if (new ChangeDepartmentLoader().changeDepartment(
                        departmentToChangeComboBox.getValue(),
                        changeDepartmentNameTextField.getText().trim(),
                        changeSuperiorDepartmentComboBox.getValue(),
                        changeChiefDepartmentComboBox.getValue())) {
                    departmentToChangeComboBox.getSelectionModel().clearSelection();
                    changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg3"));
                } else {
                    changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg4"));
                }
            }
        });

        departmentToChangeComboBox.setOnMouseClicked(mouseEvent -> {
            departmentToChangeComboBox.setItems(CommonUiService.getDepartments());
            CommonUiService.setDepartment(departmentToChangeComboBox);
            changeSuperiorDepartmentComboBox.setItems(CommonUiService.getDepartments());
            CommonUiService.setDepartment(changeSuperiorDepartmentComboBox);
            changeChiefDepartmentComboBox.setItems(CommonUiService.getEmployees());
            CommonUiService.setEmployee(changeChiefDepartmentComboBox);
        });
    }
}

