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
    private Button setEmptyChiefButton;
    @FXML
    private Button setEmptySuperiorDepButton;

    @FXML
    void initialize() {
        ChangeDepartmentLoader changeDepartmentLoader = new ChangeDepartmentLoader();
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg1"));
        changeDepartmentButton.disableProperty().bind(
                Bindings.isEmpty(changeDepartmentNameTextField.textProperty())
        );
        setEmptyChiefButton.disableProperty().bind(
                Bindings.isEmpty(changeDepartmentNameTextField.textProperty())
        );
        setEmptySuperiorDepButton.disableProperty().bind(
                Bindings.isEmpty(changeDepartmentNameTextField.textProperty())
        );

        setEmptyChiefButton.setOnAction(event -> {
            changeChiefDepartmentComboBox.getSelectionModel().clearSelection();
            changeChiefDepartmentComboBox.setValue(null);
        });

        setEmptySuperiorDepButton.setOnAction(event -> {
            if (changeDepartmentLoader.checkForSubordinateDepartment(departmentToChangeComboBox.getValue())) {
                changeSuperiorDepartmentComboBox.getSelectionModel().clearSelection();
                changeSuperiorDepartmentComboBox.setValue(null);
            }
            changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg5"));
        });


        departmentToChangeComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (changeDepartmentLoader.checkIfDepartmentIsCompanyHead(newValue)) {
                            clearSelection();
                            changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg6"));
                        } else {
                            changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg7"));
                            changeDepartmentNameTextField.setText(departmentToChangeComboBox.getValue().getName());
                            changeSuperiorDepartmentComboBox.setValue(departmentToChangeComboBox.getValue().getSuperior());
                            changeChiefDepartmentComboBox.setValue(departmentToChangeComboBox.getValue().getChief());
                        }
                    } else {
                        clearSelection();
                    }
                });

        changeDepartmentButton.setOnAction(actionEvent -> {
            if (departmentToChangeComboBox.getSelectionModel().isEmpty()) {
                changeDepartmentTextArea.setText(resourceBundle.getString("change_dep_msg2"));
            } else {
                if (changeDepartmentLoader.changeDepartment(
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

    private void clearSelection() {
        changeDepartmentNameTextField.clear();
        changeSuperiorDepartmentComboBox.getSelectionModel().clearSelection();
        changeChiefDepartmentComboBox.getSelectionModel().clearSelection();
    }
}

