package sample.presenter.hr;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.domain.Department;
import sample.domain.Employee;
import sample.services.hr.AddNewDepartmentLoader;
import sample.services.DBSingleton;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class AddNewDepartmentController {
    @FXML
    public Button addNewDepartmentButton;
    @FXML
    public TextArea newDepartmentTextArea;
    @FXML
    public ComboBox<Department> superiorNewDepartmentComboBox;
    @FXML
    public TextField newDepartmentTextField;
    @FXML
    public ComboBox<Employee> chiefNewDepartmentComboBox;

    @FXML
    void initialize() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        setNewDepartmentPresets();
        newDepartmentTextArea.setText(resourceBundle.getString("new_dep_msg1"));

        addNewDepartmentButton.disableProperty().bind(
                Bindings.isEmpty(newDepartmentTextField.textProperty())
        );

        addNewDepartmentButton.setOnAction(actionEvent -> {
            if (new AddNewDepartmentLoader().addNewDepartment(
                    newDepartmentTextField.getText().trim(),
                    superiorNewDepartmentComboBox.getValue(),
                    chiefNewDepartmentComboBox.getValue())) {
                newDepartmentTextArea.setText(resourceBundle.getString("new_dep_msg2"));
            } else {
                newDepartmentTextArea.setText(resourceBundle.getString("new_dep_msg3"));
                setNewDepartmentPresets();
            }
        });
    }

    private void setNewDepartmentPresets() {
        chiefNewDepartmentComboBox.setItems(CommonUiService.getEmployees());
        superiorNewDepartmentComboBox.setItems(CommonUiService.getDepartments());
        CommonUiService.setEmployee(chiefNewDepartmentComboBox);
        CommonUiService.setDepartment(superiorNewDepartmentComboBox);
    }
}
