package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.domain.*;
import sample.repository.DBSingleton;
import sample.services.Service;

import java.util.ArrayList;
import java.util.List;

public class ChangeEmployeeController {

    private final ToggleGroup groupRole = new ToggleGroup();
    private final ToggleGroup groupGender = new ToggleGroup();
    private final Role authRole = DBSingleton.getInstance().getAuthEmployee().getRole();
    private Employee employeeToChange;
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
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        changeEmployeeTextArea.setText("Выберите сотрудника для изменения");
        setPresets();

        employeeToChangeComboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setSelected(newValue);
                        employeeToChange = newValue;
                    } else {
                        clearSelection();
                    }
                });

        employeeToChangeComboBox.setOnMouseClicked(mouseEvent -> fillComboBoxValues(dataBase));

        changeEmployeeButton.setOnAction(actionEvent -> {
            Role employeeToChangeRole = employeeToChange.getRole();
            String login = employeeToChange.getLogin();
            String password = employeeToChange.getPassword();
            if (authRole == Role.ADMINISTRATOR) {
                employeeToChangeRole = groupRole.getSelectedToggle().equals(roleEmployeeRadioButton) ? Role.EMPLOYEE :
                        groupRole.getSelectedToggle().equals(roleHrOfficerRadioButton) ? Role.HR_OFFICER :
                                Role.ADMINISTRATOR;
                login = loginTextField.getText().trim();
                password = passwordTextField.getText().trim();
            }
            employeeToChange.setSurname(surnameTextField.getText());
            employeeToChange.setName(nameTextField.getText());
            employeeToChange.setPatronymic(patronymicTextField.getText());
            employeeToChange.setPhoneNumber(phoneNumberTextField.getText());
            employeeToChange.setBirthDate(birthDatePicker.getValue());
            employeeToChange.setGender(genderMaleRadioButton.isSelected() ? Gender.MALE : Gender.FEMALE);
            employeeToChange.setPost(postComboBox.getValue());
            employeeToChange.setDepartment(departmentComboBox.getValue());
            employeeToChange.setChief(chiefComboBox.getValue());
            employeeToChange.setEmploymentDate(employmentDatePicker.getValue());
            employeeToChange.setSalary(Double.parseDouble(salaryTextField.getText()));
            employeeToChange.setRole(employeeToChangeRole);
            employeeToChange.setLogin(login);
            employeeToChange.setPassword(password);
            changeEmployeeTextArea.setText("Данные сотрудника обновлены");
            setPresets();
        });
    }

    private void setPresets() {
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
                loginTextField.setText("guest");
                loginTextField.setEditable(false);
                passwordTextField.setText("guest");
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

    private void fillComboBoxValues(DataBase dataBase) {
        employeeToChangeComboBox.setItems(FXCollections.observableArrayList(dataBase.getEmployees()));
        Service.setEmployee(employeeToChangeComboBox);
        postComboBox.setOnMouseClicked(mouseEvent -> {
            List<String> postNames = new ArrayList<>();
            for (String post : dataBase.getPosts()) {
                if (DBSingleton.getInstance().getAuthEmployee().getRole() == Role.HR_OFFICER &&
                        (post.equals("Администратор") || post.equals("Кадровик"))) {
                    continue;
                }
                postNames.add(post);
            }
            postComboBox.setItems(FXCollections.observableArrayList(postNames));
        });
        departmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getDepartments()));
        Service.setDepartment(departmentComboBox);
        chiefComboBox.setItems(FXCollections.observableArrayList(dataBase.getEmployees()));
        Service.setEmployee(chiefComboBox);
    }
}
