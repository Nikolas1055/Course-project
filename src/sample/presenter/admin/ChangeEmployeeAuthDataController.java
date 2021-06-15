package sample.presenter.admin;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.domain.Employee;
import sample.domain.Role;
import sample.services.DBSingleton;
import sample.services.admin.ChangeEmployeeAuthDataLoader;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class ChangeEmployeeAuthDataController {
    private static final String DEFAULT_LOGIN = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    private final ToggleGroup groupRole = new ToggleGroup();
    @FXML
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
    @FXML
    private TextField passwordTextField;
    @FXML
    private RadioButton roleEmployeeRadioButton;
    @FXML
    private RadioButton roleHrOfficerRadioButton;
    @FXML
    private RadioButton roleAdministratorRadioButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private Button changeEmployeeButton;
    @FXML
    private ComboBox<Employee> employeeToChangeComboBox;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextArea changeEmployeeAuthDataTextArea;
    @FXML
    private TextField loginTextField;

    @FXML
    void initialize() {
        setPresets();

        employeeToChangeComboBox.setOnMouseClicked(mouseEvent -> fillComboBoxValues());

        employeeToChangeComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setSelected(newValue);
                    } else {
                        clearSelection();
                    }
                });

        changeEmployeeButton.setOnAction(actionEvent -> {
            Role employeeToChangeRole = groupRole.getSelectedToggle().equals(roleEmployeeRadioButton) ? Role.EMPLOYEE :
                    groupRole.getSelectedToggle().equals(roleHrOfficerRadioButton) ? Role.HR_OFFICER :
                            Role.ADMINISTRATOR;
            String login = loginTextField.getText().trim();
            String password = passwordTextField.getText().trim();

            new ChangeEmployeeAuthDataLoader().changeEmployeeAuthData(
                    employeeToChangeComboBox.getValue(),
                    employeeToChangeRole,
                    login,
                    password);
            changeEmployeeAuthDataTextArea.setText(resourceBundle.getString("change_emp_auth_msg2"));
            setPresets();
        });
    }

    private void setSelected(Employee newValue) {
        resourceBundle.getString("change_emp_auth_msg1");
        surnameTextField.setText(newValue.getSurname());
        nameTextField.setText(newValue.getName());
        patronymicTextField.setText(newValue.getPatronymic());
        if (newValue.getRole() == Role.EMPLOYEE) {
            roleEmployeeRadioButton.setSelected(true);
        } else if (newValue.getRole() == Role.HR_OFFICER) {
            roleHrOfficerRadioButton.setSelected(true);
        } else {
            roleAdministratorRadioButton.setSelected(true);
        }
        loginTextField.setText(newValue.getLogin());
        passwordTextField.setText(newValue.getPassword());
    }

    private void clearSelection() {
        surnameTextField.clear();
        nameTextField.clear();
        patronymicTextField.clear();
        groupRole.getToggles().clear();
        loginTextField.clear();
        passwordTextField.clear();
    }

    private void setPresets() {
        employeeToChangeComboBox.getSelectionModel().clearSelection();
        roleEmployeeRadioButton.setToggleGroup(groupRole);
        roleHrOfficerRadioButton.setToggleGroup(groupRole);
        roleAdministratorRadioButton.setToggleGroup(groupRole);
        groupRole.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (roleEmployeeRadioButton.isSelected()) {
                loginTextField.setText(DEFAULT_LOGIN);
                loginTextField.setEditable(false);
                passwordTextField.setText(DEFAULT_PASSWORD);
                passwordTextField.setEditable(false);
            } else {
                loginTextField.setEditable(true);
                passwordTextField.setEditable(true);
            }
        });
        changeEmployeeButton.disableProperty().bind(
                Bindings.isEmpty(surnameTextField.textProperty())
                        .or(Bindings.isEmpty(nameTextField.textProperty()))
                        .or(Bindings.isEmpty(patronymicTextField.textProperty()))
                        .or(Bindings.isEmpty(loginTextField.textProperty()))
                        .or(Bindings.isEmpty(passwordTextField.textProperty()))
        );
    }

    private void fillComboBoxValues() {
        employeeToChangeComboBox.setItems(CommonUiService.getEmployees());
        CommonUiService.setEmployee(employeeToChangeComboBox);
    }
}
