package sample.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.domain.*;
import sample.repository.DBSingleton;
import sample.services.Service;

public class AddNewEmployeeController {

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

        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        Role authRole = DBSingleton.getInstance().getAuthEmployee().getRole();
        setNewEmployeePresets();
        addNewEmployeeTextArea.setText("Введите данные нового сотрудника.");

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
                loginTextField.setText("guest");
                loginTextField.setEditable(false);
                passwordTextField.setText("guest");
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
            String login = "guest";
            String password = "guest";
            if (authRole == Role.ADMINISTRATOR) {
                newEmployeeRole = groupRole.getSelectedToggle().equals(roleEmployeeRadioButton) ? Role.EMPLOYEE :
                        groupRole.getSelectedToggle().equals(roleHrOfficerRadioButton) ? Role.HR_OFFICER :
                                Role.ADMINISTRATOR;
                login = loginTextField.getText().trim();
                password = passwordTextField.getText().trim();
            }
            if (postComboBox.getValue() == null || departmentComboBox.getValue() == null) {
                addNewEmployeeTextArea.setText("Должность и/или отдел должны быть обязательно установлены.");
            } else {
                dataBase.getEmployees().add(new Employee(
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
                        password));
                setNewEmployeePresets();
                postComboBox.getSelectionModel().clearAndSelect(0);
                departmentComboBox.getSelectionModel().clearAndSelect(0);
                chiefComboBox.getSelectionModel().clearAndSelect(0);
                addNewEmployeeTextArea.setText("Сотрудник успешно добавлен в базу данных.");
            }
        });
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
        departmentComboBox.setOnMouseClicked(mouseEvent -> {
            departmentComboBox.setItems(FXCollections.observableArrayList(dataBase.getDepartments()));
            Service.setDepartment(departmentComboBox);
        });
        chiefComboBox.setOnMouseClicked(mouseEvent -> {
            chiefComboBox.setItems(FXCollections.observableArrayList(dataBase.getEmployees()));
            Service.setEmployee(chiefComboBox);
        });
    }

    private void setNewEmployeePresets() {
        chiefComboBox.setTooltip(new Tooltip("Непосредственный начальник"));
        departmentComboBox.setTooltip(new Tooltip("Отдел"));
        postComboBox.setTooltip(new Tooltip("Должность"));
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

