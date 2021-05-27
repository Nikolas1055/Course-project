package sample.controllers;

import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.repository.DBSingleton;
import sample.repository.FileDao;
import sample.domain.*;

public class FinderMenuController {

    @FXML
    private ChoiceBox<String> departmentChoiceBox;

    @FXML
    private TextArea findByChiefFullNameResultTextField;

    @FXML
    private TextArea findByDepartmentResultTextField;

    @FXML
    private ChoiceBox<String> postChoiceBox;

    @FXML
    private Button findByFullNameButton;

    @FXML
    private Label messageTextField;

    @FXML
    private TextArea findByPostResultTextField;

    @FXML
    private TextArea findByFullNameResultTextField;

    @FXML
    private TextField chiefFullNameTextField;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button findByChiefFullNameButton;


    @FXML
    void initialize() {
        messageTextField.setText("Выберите тип поиска.");
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        Role authRole = DBSingleton.getInstance().getAuthEmployee() == null ? Role.EMPLOYEE :
                DBSingleton.getInstance().getAuthEmployee().getRole();

        postChoiceBox.getItems().setAll(dataBase.getPosts());
        postChoiceBox.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            String post = postChoiceBox.getValue();
            boolean found = false;
            String header = "Сотрудники с должностью: " + post + System.lineSeparator();
            stringDataToFile.append(header).append(System.lineSeparator());
            for (Employee employee : dataBase.getEmployees()) {
                if (employee.getPost().equals(post)) {
                    found = true;
                    String employeeData = getEmployeeStringByAuthRole(employee, authRole);
                    stringDataToFile.append(employeeData).append(System.lineSeparator()).append(System.lineSeparator());
                }
            }
            String footer = "";
            if (!found) {
                footer = "отсутствуют.";
            }
            stringDataToFile.append(footer);
            textFieldOperations(stringDataToFile.toString(), findByPostResultTextField);
        });

        List<String> departmentsNames = new ArrayList<>();
        for (Department department : dataBase.getDepartments()) {
            departmentsNames.add(department.getName());
        }
        departmentChoiceBox.getItems().setAll(departmentsNames);
        departmentChoiceBox.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            Department department = dataBase.getDepartments().stream().
                    filter(dep -> dep.getName().equals(departmentChoiceBox.getValue())).
                    findFirst().get();
            boolean found = false;
            String header = "Сотрудники в отделе: " + department.getName() + System.lineSeparator();
            stringDataToFile.append(header).append(System.lineSeparator());
            for (Employee employee : dataBase.getEmployees()) {
                if (employee.getDepartment().equals(department)) {
                    found = true;
                    String employeeData = getEmployeeStringByAuthRole(employee, authRole);
                    stringDataToFile.append(employeeData).append(System.lineSeparator()).append(System.lineSeparator());
                }
            }
            String footer = "";
            if (!found) {
                footer = "отсутствуют.";
            }
            stringDataToFile.append(footer);
            textFieldOperations(stringDataToFile.toString(), findByDepartmentResultTextField);
        });

        findByFullNameButton.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            boolean found = false;
            String fullName = fullNameTextField.getText().trim();
            String header = "Данные для поиска: " + fullName;
            stringDataToFile.append(header).append(System.lineSeparator());
            for (Employee employee : dataBase.getEmployees()) {
                if (employee.getFullName().contains(fullName)) {
                    found = true;
                    String employeeData = getEmployeeStringByAuthRole(employee, authRole);
                    stringDataToFile.append(employeeData).append(System.lineSeparator());
                }
            }
            String footer = "";
            if (!found) {
                footer = "Сотрудника(ов) с таким ФИО нет в базе данных.";
            }
            stringDataToFile.append(footer);
            textFieldOperations(stringDataToFile.toString(), findByFullNameResultTextField);
        });

        findByChiefFullNameButton.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            boolean found = false;
            String chiefFullName = chiefFullNameTextField.getText().trim();
            String header = "Сотрудники у которых начальник: " + chiefFullName + System.lineSeparator();
            stringDataToFile.append(header).append(System.lineSeparator());
            for (Employee employee : dataBase.getEmployees()) {
                if (employee.getChief() != null && employee.getChief().getFullName().contains(chiefFullName)) {
                    found = true;
                    String employeeData = getEmployeeStringByAuthRole(employee, authRole);
                    stringDataToFile.append(employeeData).append(System.lineSeparator()).append(System.lineSeparator());
                }
            }
            String footer = "";
            if (!found) {
                footer = "отсутствуют.";
            }
            stringDataToFile.append(footer);
            textFieldOperations(stringDataToFile.toString(), findByChiefFullNameResultTextField);
        });

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });
    }

    private void textFieldOperations(String stringDataToFile, TextArea textArea) {
        textArea.setText(stringDataToFile);
        messageTextField.setText(FileDao.saveReportToFile(stringDataToFile));
    }

    public String getEmployeeStringByAuthRole(Employee employee, Role authUserRole) {
        String employeeData;
        if (authUserRole.equals(Role.EMPLOYEE)) {
            employeeData = employee.getRole().getRoleType() + ": " + employee.getFullName() + System.lineSeparator() +
                    "Должность: " + employee.getPost() + System.lineSeparator() +
                    "Отдел: " + employee.getDepartment().getName();
        } else if (authUserRole.equals(Role.HR_OFFICER)) {
            employeeData = employee.toString();
        } else {
            employeeData = employee.toString() + System.lineSeparator() +
                    "Логин: " + employee.getLogin() + System.lineSeparator() +
                    "Пароль: " + employee.getPassword();
        }
        return employeeData;
    }
}
