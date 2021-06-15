package sample.presenter.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import sample.domain.Department;
import sample.services.DBSingleton;
import sample.services.admin.DeleteDepartmentLoader;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class DeleteDepartmentController {
    @FXML
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
    @FXML
    private Button deleteDepartmentButton;
    @FXML
    private ComboBox<Department> departmentToDeleteComboBox;
    @FXML
    private TextArea deleteDepartmentTextArea;

    @FXML
    void initialize() {
        DeleteDepartmentLoader deleteDepartmentLoader = new DeleteDepartmentLoader();
        deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg1"));
        deleteDepartmentButton.setDisable(true);

        departmentToDeleteComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    deleteDepartmentButton.setDisable(true);
                    if (newValue != null) {
                        if (deleteDepartmentLoader.checkIfDepartmentIsCompanyHead(newValue)) {
                            deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg2"));
                        } else if (!deleteDepartmentLoader.checkDepartmentEmployees(newValue)) {
                            deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg6"));
                        } else if (!deleteDepartmentLoader.checkDepartmentChief(newValue)) {
                            deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg7"));
                        } else {
                            deleteDepartmentButton.setDisable(false);
                            deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg3"));
                        }
                    }
                });

        departmentToDeleteComboBox.setOnMouseClicked(mouseEvent -> {
            departmentToDeleteComboBox.setItems(CommonUiService.getDepartments());
            CommonUiService.setDepartment(departmentToDeleteComboBox);
        });

        deleteDepartmentButton.setOnAction(actionEvent -> {
            if (deleteDepartmentLoader.deleteDepartment(departmentToDeleteComboBox.getValue())) {
                deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg5"));
                departmentToDeleteComboBox.getSelectionModel().clearSelection();
            } else {
                deleteDepartmentTextArea.setText(resourceBundle.getString("delete_dep_msg4"));
            }
        });
    }
}
