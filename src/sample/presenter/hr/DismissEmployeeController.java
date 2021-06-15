package sample.presenter.hr;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import sample.domain.Employee;
import sample.domain.Role;
import sample.services.DBSingleton;
import sample.services.hr.DismissEmployeeLoader;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class DismissEmployeeController {
    @FXML
    private ComboBox<Employee> employeeToDismissComboBox;
    @FXML
    private TextArea dismissMessageTextArea;
    @FXML
    private Button dismissEmployeeButton;

    @FXML
    void initialize() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        DismissEmployeeLoader dismissEmployeeLoader = new DismissEmployeeLoader();
        dismissEmployeeButton.setDisable(true);
        dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg1"));

        employeeToDismissComboBox.setOnMouseClicked(mouseEvent -> setDismissPresets());

        employeeToDismissComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (dismissEmployeeLoader.checkDismissible(newValue)) {
                        dismissEmployeeButton.setDisable(true);
                        dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg2") +
                                System.lineSeparator() + resourceBundle.getString("dismiss_emp_msg3"));
                    } else if (newValue != null) {
                        if (dismissEmployeeLoader.checkAuthEmployee(newValue)) {
                            dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg4"));
                        } else if (newValue.getRole() == Role.ADMINISTRATOR &&
                                !dismissEmployeeLoader.checkAdministrator()) {
                            dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg7"));
                        } else {
                            dismissEmployeeButton.setDisable(false);
                            dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg5"));
                        }
                    } else {
                        dismissEmployeeButton.setDisable(true);
                    }
                });

        dismissEmployeeButton.setOnAction(actionEvent -> {
            dismissMessageTextArea.setText(resourceBundle.getString("dismiss_emp_msg6"));
            dismissEmployeeLoader.dismissEmployee(employeeToDismissComboBox.getValue());
            employeeToDismissComboBox.getSelectionModel().clearSelection();
        });
    }

    private void setDismissPresets() {
        employeeToDismissComboBox.setItems(CommonUiService.getEmployees());
        CommonUiService.setEmployee(employeeToDismissComboBox);
        dismissEmployeeButton.setDisable(true);
    }
}
