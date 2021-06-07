package sample.presenter.admin;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.domain.Employee;
import sample.services.DBSingleton;
import sample.services.admin.ChangeCompanyDataLoader;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class ChangeCompanyDataController {
    private final ChangeCompanyDataLoader changeCompanyDataLoader = new ChangeCompanyDataLoader();
    @FXML
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
    @FXML
    private ComboBox<Employee> companyChiefComboBox;
    @FXML
    private TextArea changeCompanyTextArea;
    @FXML
    private TextField companyNameTextField;
    @FXML
    private Button changeCompanyDataButton;

    @FXML
    void initialize() {
        setPresets();
        changeCompanyTextArea.setText(resourceBundle.getString("change_company_msg1"));

        changeCompanyDataButton.disableProperty().bind(
                Bindings.isEmpty(companyNameTextField.textProperty())
        );

        changeCompanyDataButton.setOnAction(event -> {
            if (changeCompanyDataLoader.checkNewChiefPost(companyChiefComboBox.getValue())) {
                changeCompanyDataLoader.updateCompanyData(companyNameTextField.getText(),
                        companyChiefComboBox.getValue());
                changeCompanyTextArea.setText(resourceBundle.getString("change_company_msg2"));
                setPresets();
            } else {
                changeCompanyTextArea.setText(resourceBundle.getString("change_company_msg3"));
            }
        });
    }

    private void setPresets() {
        companyChiefComboBox.setItems(CommonUiService.getEmployees());
        CommonUiService.setEmployee(companyChiefComboBox);
        companyNameTextField.setText(changeCompanyDataLoader.getCompanyName());
        companyChiefComboBox.getSelectionModel().select(changeCompanyDataLoader.getCompanyChief());
    }
}
