package sample.presenter.hr;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Gender;
import sample.domain.Role;
import sample.services.hr.AddNewEmployeeLoader;
import sample.services.DBSingleton;
import sample.ui.CommonUiService;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewEmployeeController {
    private static final String DEFAULT_LOGIN = "guest";
    private static final String DEFAULT_PASSWORD = "guest";
    private final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
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
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private Button addNewEmployeeButton;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private RadioButton genderFemaleRadioButton;
    @FXML
    private TextArea addNewEmployeeTextArea;
    @FXML
    private RadioButton roleEmployeeRadioButton;
    @FXML
    private RadioButton roleHrOfficerRadioButton;
    @FXML
    private RadioButton roleAdministratorRadioButton;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private DatePicker employmentDatePicker;
    @FXML
    private ComboBox<Employee> chiefComboBox;

    @FXML
    void initialize() {
        ToggleGroup groupGender = new ToggleGroup();
        genderMaleRadioButton.setToggleGroup(groupGender);
        genderFemaleRadioButton.setToggleGroup(groupGender);

        ToggleGroup groupRole = new ToggleGroup();
        roleEmployeeRadioButton.setToggleGroup(groupRole);
        roleHrOfficerRadioButton.setToggleGroup(groupRole);
        roleAdministratorRadioButton.setToggleGroup(groupRole);

        Role authRole = DBSingleton.getInstance().getAuthEmployee().getRole();
        setNewEmployeePresets();

        addNewEmployeeTextArea.setText(resourceBundle.getString("new_emp_msg1"));

        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                salaryTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        if (authRole == Role.ADMINISTRATOR) {
            addNewEmployeeButton.disableProperty().bind(
                    Bindings.isEmpty(surnameTextField.textProperty())
                            .or(Bindings.isEmpty(nameTextField.textProperty()))
                            .or(Bindings.isEmpty(patronymicTextField.textProperty()))
                            .or(Bindings.isEmpty(salaryTextField.textProperty()))
                            .or(Bindings.isEmpty(loginTextField.textProperty()))
                            .or(Bindings.isEmpty(passwordTextField.textProperty()))
            );
        } else {
            addNewEmployeeButton.disableProperty().bind(
                    Bindings.isEmpty(surnameTextField.textProperty())
                            .or(Bindings.isEmpty(nameTextField.textProperty()))
                            .or(Bindings.isEmpty(patronymicTextField.textProperty()))
                            .or(Bindings.isEmpty(salaryTextField.textProperty()))
            );
        }

        groupRole.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (roleEmployeeRadioButton.isSelected()) {
                loginTextField.setText(DEFAULT_LOGIN);
                loginTextField.setEditable(false);
                passwordTextField.setText(DEFAULT_PASSWORD);
                passwordTextField.setEditable(false);
            } else {
                loginTextField.setText("");
                loginTextField.setEditable(true);
                passwordTextField.setText("");
                passwordTextField.setEditable(true);
            }
        });

        addNewEmployeeButton.setOnAction(actionEvent -> {
            Role newEmployeeRole = Role.EMPLOYEE;
            String login = DEFAULT_LOGIN;
            String password = DEFAULT_PASSWORD;
            if (authRole == Role.ADMINISTRATOR) {
                newEmployeeRole = groupRole.getSelectedToggle().equals(roleEmployeeRadioButton) ? Role.EMPLOYEE :
                        groupRole.getSelectedToggle().equals(roleHrOfficerRadioButton) ? Role.HR_OFFICER :
                                Role.ADMINISTRATOR;
                login = loginTextField.getText().trim();
                password = passwordTextField.getText().trim();
            }
            if (new AddNewEmployeeLoader().addNewEmployee(
                    surnameTextField.getText().trim(),
                    nameTextField.getText().trim(),
                    patronymicTextField.getText().trim(),
                    birthDatePicker.getValue(),
                    genderMaleRadioButton.isSelected() ? Gender.MALE : Gender.FEMALE,
                    phoneNumberTextField.getText().trim(),
                    postComboBox.getValue(),
                    departmentComboBox.getValue(),
                    chiefComboBox.getValue(),
                    employmentDatePicker.getValue(),
                    Double.parseDouble(salaryTextField.getText()),
                    newEmployeeRole,
                    login,
                    password)) {
                setNewEmployeePresets();
                postComboBox.getSelectionModel().clearAndSelect(0);
                departmentComboBox.getSelectionModel().clearAndSelect(0);
                chiefComboBox.getSelectionModel().clearAndSelect(0);
                addNewEmployeeTextArea.setText(resourceBundle.getString("new_emp_msg3"));
            } else {
                addNewEmployeeTextArea.setText(resourceBundle.getString("new_emp_msg2"));
            }
        });
        postComboBox.setOnMouseClicked(mouseEvent -> postComboBox.setItems(CommonUiService.getPosts()));
        departmentComboBox.setOnMouseClicked(mouseEvent -> {
            departmentComboBox.setItems(CommonUiService.getDepartments());
            CommonUiService.setDepartment(departmentComboBox);
        });
        chiefComboBox.setOnMouseClicked(mouseEvent -> {
            chiefComboBox.setItems(CommonUiService.getEmployees());
            CommonUiService.setEmployee(chiefComboBox);
        });
    }

    private void setNewEmployeePresets() {
        chiefComboBox.setTooltip(new Tooltip(resourceBundle.getString("new_emp_msg4")));
        departmentComboBox.setTooltip(new Tooltip(resourceBundle.getString("new_emp_msg5")));
        postComboBox.setTooltip(new Tooltip(resourceBundle.getString("new_emp_msg6")));
        salaryTextField.clear();
        phoneNumberTextField.clear();
        surnameTextField.clear();
        nameTextField.clear();
        patronymicTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
        genderMaleRadioButton.setSelected(true);
        roleHrOfficerRadioButton.setSelected(true);
        birthDatePicker.setValue(LocalDate.now());
        employmentDatePicker.setValue(LocalDate.now());
        if (DBSingleton.getInstance().getAuthEmployee().getRole() == Role.HR_OFFICER) {
            roleEmployeeRadioButton.setVisible(false);
            roleHrOfficerRadioButton.setVisible(false);
            roleAdministratorRadioButton.setVisible(false);
            loginTextField.setVisible(false);
            passwordTextField.setVisible(false);
        }
    }
}

