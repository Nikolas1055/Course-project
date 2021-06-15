package sample.presenter.hr;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Gender;
import sample.domain.Role;
import sample.services.hr.ChangeEmployeeLoader;
import sample.services.DBSingleton;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

public class ChangeEmployeeController {
    private static final String DEFAULT_LOGIN = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    private final ToggleGroup groupRole = new ToggleGroup();
    private final ToggleGroup groupGender = new ToggleGroup();
    private final Role authRole = DBSingleton.getInstance().getAuthEmployee().getRole();
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ComboBox<String> postComboBox;
    @FXML
    private TextField passwordTextField;
    @FXML
    private RadioButton genderMaleRadioButton;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextArea changeEmployeeTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button changeEmployeeButton;
    @FXML
    private TextField surnameTextField;
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private RadioButton genderFemaleRadioButton;
    @FXML
    private RadioButton roleEmployeeRadioButton;
    @FXML
    private RadioButton roleHrOfficerRadioButton;
    @FXML
    private RadioButton roleAdministratorRadioButton;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private ComboBox<Employee> employeeToChangeComboBox;
    @FXML
    private DatePicker employmentDatePicker;
    @FXML
    private ComboBox<Employee> chiefComboBox;

    @FXML
    void initialize() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        changeEmployeeTextArea.setText(resourceBundle.getString("change_emp_msg1"));
        setPresets();

        employeeToChangeComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setSelected(newValue);
                    } else {
                        clearSelection();
                    }
                });

        employeeToChangeComboBox.setOnMouseClicked(mouseEvent -> fillComboBoxValues());

        changeEmployeeButton.setOnAction(actionEvent -> {
            Role employeeToChangeRole = employeeToChangeComboBox.getValue().getRole();
            String login = employeeToChangeComboBox.getValue().getLogin();
            String password = employeeToChangeComboBox.getValue().getPassword();
            if (authRole == Role.ADMINISTRATOR) {
                employeeToChangeRole = groupRole.getSelectedToggle().equals(roleEmployeeRadioButton) ? Role.EMPLOYEE :
                        groupRole.getSelectedToggle().equals(roleHrOfficerRadioButton) ? Role.HR_OFFICER :
                                Role.ADMINISTRATOR;
                login = loginTextField.getText().trim();
                password = passwordTextField.getText().trim();
            }
            new ChangeEmployeeLoader().changeEmployee(
                    employeeToChangeComboBox.getValue(),
                    surnameTextField.getText(),
                    nameTextField.getText(),
                    patronymicTextField.getText(),
                    birthDatePicker.getValue(),
                    genderMaleRadioButton.isSelected() ? Gender.MALE : Gender.FEMALE,
                    phoneNumberTextField.getText(),
                    postComboBox.getValue(),
                    departmentComboBox.getValue(),
                    chiefComboBox.getValue(),
                    employmentDatePicker.getValue(),
                    Double.parseDouble(salaryTextField.getText()),
                    employeeToChangeRole,
                    login,
                    password
            );
            changeEmployeeTextArea.setText(resourceBundle.getString("change_emp_msg2"));
            setPresets();
        });
    }

    private void setPresets() {
        employeeToChangeComboBox.getSelectionModel().clearSelection();
        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                salaryTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        genderMaleRadioButton.setToggleGroup(groupGender);
        genderFemaleRadioButton.setToggleGroup(groupGender);
        roleEmployeeRadioButton.setToggleGroup(groupRole);
        roleHrOfficerRadioButton.setToggleGroup(groupRole);
        roleAdministratorRadioButton.setToggleGroup(groupRole);

        if (authRole == Role.HR_OFFICER) {
            roleEmployeeRadioButton.setVisible(false);
            roleHrOfficerRadioButton.setVisible(false);
            roleAdministratorRadioButton.setVisible(false);
            loginTextField.setVisible(false);
            passwordTextField.setVisible(false);
        }
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
        if (authRole == Role.ADMINISTRATOR) {
            changeEmployeeButton.disableProperty().bind(
                    Bindings.isEmpty(surnameTextField.textProperty())
                            .or(Bindings.isEmpty(nameTextField.textProperty()))
                            .or(Bindings.isEmpty(patronymicTextField.textProperty()))
                            .or(Bindings.isEmpty(salaryTextField.textProperty()))
                            .or(Bindings.isEmpty(loginTextField.textProperty()))
                            .or(Bindings.isEmpty(passwordTextField.textProperty()))
            );
        } else {
            changeEmployeeButton.disableProperty().bind(
                    Bindings.isEmpty(surnameTextField.textProperty())
                            .or(Bindings.isEmpty(nameTextField.textProperty()))
                            .or(Bindings.isEmpty(patronymicTextField.textProperty()))
                            .or(Bindings.isEmpty(salaryTextField.textProperty()))
            );
        }
    }

    private void clearSelection() {
        surnameTextField.clear();
        nameTextField.clear();
        patronymicTextField.clear();
        birthDatePicker.setValue(null);
        phoneNumberTextField.clear();
        groupGender.getToggles().clear();
        groupRole.getToggles().clear();
        employmentDatePicker.setValue(null);
        postComboBox.getSelectionModel().clearSelection();
        departmentComboBox.getSelectionModel().clearSelection();
        chiefComboBox.getSelectionModel().clearSelection();
        salaryTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
    }

    private void setSelected(Employee newValue) {
        surnameTextField.setText(newValue.getSurname());
        nameTextField.setText(newValue.getName());
        patronymicTextField.setText(newValue.getPatronymic());
        birthDatePicker.setValue(newValue.getBirthDate());
        phoneNumberTextField.setText(newValue.getPhoneNumber());
        if (newValue.getGender() == Gender.MALE) {
            genderMaleRadioButton.setSelected(true);
        } else {
            genderFemaleRadioButton.setSelected(true);
        }
        if (authRole == Role.ADMINISTRATOR) {
            if (newValue.getRole() == Role.EMPLOYEE) {
                roleEmployeeRadioButton.setSelected(true);
            } else if (newValue.getRole() == Role.HR_OFFICER) {
                roleHrOfficerRadioButton.setSelected(true);
            } else {
                roleAdministratorRadioButton.setSelected(true);
            }
        }
        employmentDatePicker.setValue(newValue.getEmploymentDate());
        postComboBox.getSelectionModel().select(newValue.getPost());
        departmentComboBox.getSelectionModel().select(newValue.getDepartment());
        chiefComboBox.getSelectionModel().select(newValue.getChief());
        salaryTextField.setText(String.valueOf(newValue.getSalary()));
        loginTextField.setText(newValue.getLogin());
        passwordTextField.setText(newValue.getPassword());
    }

    private void fillComboBoxValues() {
        employeeToChangeComboBox.setItems(CommonUiService.getEmployees());
        CommonUiService.setEmployee(employeeToChangeComboBox);
        postComboBox.setOnMouseClicked(mouseEvent -> postComboBox.setItems(CommonUiService.getPosts()));
        departmentComboBox.setItems(CommonUiService.getDepartments());
        CommonUiService.setDepartment(departmentComboBox);
        chiefComboBox.setItems(CommonUiService.getEmployees());
        CommonUiService.setEmployee(chiefComboBox);
    }
}
