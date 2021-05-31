package sample.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.repository.FileDao;
import sample.repository.DBSingleton;
import sample.domain.DataBase;
import sample.domain.Department;
import sample.domain.Employee;

public class ReportsMenuController {

    public static String STRUCT = "";

    @FXML
    private Button topTenSalariesButton;

    @FXML
    private Button companyStructureButton;

    @FXML
    private Button backButton;

    @FXML
    private Button averageSalaryButton;

    @FXML
    private Button topTenLoyalButton;

    @FXML
    private TextArea reportTextField;

    @FXML
    private Label messageTextField;

    @FXML
    void initialize() {
        messageTextField.setText("Выберите отчет.");
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        companyStructureButton.setOnAction(actionEvent -> {
            STRUCT += "Структура организации: " + System.lineSeparator();
            printDep(dataBase.getDepartments(), dataBase.getDepartments().get(0), "");
            textFieldOperations(STRUCT);
            STRUCT = "";
        });

        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });

        averageSalaryButton.setOnAction(actionEvent -> {
            double averageSalary = 0;
            StringBuilder stringDataToFile = new StringBuilder();
            for (Employee employee : dataBase.getEmployees()) {
                averageSalary += employee.getSalary();
            }
            averageSalary /= dataBase.getEmployees().size();
            stringDataToFile.append("Средняя зарплата по организации - ")
                    .append(String.format("%.2f", averageSalary)).append(" руб.")
                    .append(System.lineSeparator());
            stringDataToFile.append("Средняя зарплата по отделу:").append(System.lineSeparator());
            for (Department department : dataBase.getDepartments()) {
                averageSalary = 0;
                int departmentEmployeeCount = 0;
                for (Employee employee : dataBase.getEmployees()) {
                    if (employee.getDepartment().equals(department)) {
                        departmentEmployeeCount++;
                        averageSalary += employee.getSalary();
                    }
                }
                averageSalary = averageSalary == 0 ? 0 : (averageSalary / departmentEmployeeCount);
                stringDataToFile.append(department.getName()).append(" - ")
                        .append(String.format("%.2f", averageSalary))
                        .append(" руб.").append(System.lineSeparator());
            }
            textFieldOperations(stringDataToFile.toString());
        });

        topTenSalariesButton.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .limit(10)
                    .collect(Collectors.toList());
            for (Employee employee : sortedEmployeesBySalary) {
                stringDataToFile.append(employee.getPost()).append(": ").append(employee.getFullName())
                        .append(" зарплата - ").append(employee.getSalary()).append(System.lineSeparator());
            }
            textFieldOperations(stringDataToFile.toString());
        });

        topTenLoyalButton.setOnAction(actionEvent -> {
            StringBuilder stringDataToFile = new StringBuilder();
            List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                    .sorted(Comparator.comparing(Employee::getEmploymentDate))
                    .limit(10)
                    .collect(Collectors.toList());
            for (Employee employee : sortedEmployeesBySalary) {
                stringDataToFile.append(employee.getPost()).append(" ").append(employee.getFullName())
                        .append(" работает с - ").append(employee.getEmploymentDate()).append(System.lineSeparator());
            }
            textFieldOperations(stringDataToFile.toString());
        });
    }

    private void textFieldOperations(String stringDataToFile) {
        reportTextField.setText(stringDataToFile);
        messageTextField.setText(FileDao.saveReportToFile(stringDataToFile));
    }

    private void printDep(List<Department> departments, Department department, String string) {
        STRUCT += string + department.getName() + " (Начальник: " + department.getChief().getFullName() + ")" +
                System.lineSeparator();
        for (Department dep : departments) {
            if (dep.getSuperior() == null) {
                continue;
            }
            if (dep.getSuperior().equals(department)) {
                printDep(departments, dep, string + "    ");
            }
        }
    }
}
