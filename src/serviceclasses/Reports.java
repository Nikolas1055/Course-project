package serviceclasses;

import baseclasses.DataBase;
import baseclasses.Department;
import baseclasses.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для формирования и вывода отчетов в консоль и в текстовые файлы.
 */
public class Reports {

    public static String STRUCT = "";
    private DataBase dataBase;

    /**
     * Конструктор с параметрами.
     *
     * @param dataBase - принимает ссылку на базу данных.
     */
    public Reports(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Метод для вывода отчета древовидной структуры организации.
     */
    public void printCompanyStructure() {
        STRUCT += "Структура организации: " + System.lineSeparator();
        printDep(dataBase.getDepartments(), dataBase.getDepartments().get(0), "");
        Service.println(STRUCT);
        Service.println(FileOperations.saveReportToFile(STRUCT));
        STRUCT = "";
    }

    /**
     * Метод для рекурсивного обхода отделов организации.
     *
     * @param departments - принимает ссылку на список отделов.
     * @param department  - принимает ссылку на текущий отдел при обходе.
     * @param string      - строка "форматтер" древовидного вида структуры.
     */
    private void printDep(List<Department> departments, Department department, String string) {
        STRUCT += string + department.getName() + " (Начальник: " + department.getChief().getFullName() + ")" +
                System.lineSeparator();
        for (Department dep : departments) {
            if (dep.getSuperior() == null) {
                continue;
            }
            if (dep.getSuperior().equals(department)) {
                printDep(departments, dep, string + "  ");
            }
        }
    }

    /**
     * Метод для вывода отчета о средней зарплаты по организации и по отделам.
     */
    public void printAverageSalaries() {
        double averageSalary = 0;
        StringBuilder stringDataToFile = new StringBuilder();
        for (Employee employee : dataBase.getEmployees()) {
            averageSalary += employee.getSalary();
        }
        averageSalary /= dataBase.getEmployees().size();
        stringDataToFile.append("Средняя зарплата по организации - ").append(averageSalary).append(" руб.")
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
            averageSalary /= departmentEmployeeCount;
            stringDataToFile.append(department.getName()).
                    append(" - ").append(averageSalary).append(" руб.").append(System.lineSeparator());
        }
        Service.println(stringDataToFile.toString());
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }

    /**
     * Метод для вывода отчета о ТОП-10 самых дорогих сотрудников по зарплате.
     */
    public void printMostExpensiveEmployees() {
        StringBuilder stringDataToFile = new StringBuilder();
        List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());
        for (Employee employee : sortedEmployeesBySalary) {
            stringDataToFile.append(employee.getPost()).append(" ").append(employee.getFullName())
                    .append(" зарплата - ").append(employee.getSalary()).append(System.lineSeparator());
        }
        Service.println(stringDataToFile.toString());
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }

    /**
     * Метод для вывода отчета о ТОП-10 самых преданных сотрудников по количеству лет работы в организации.
     */
    public void printMostDedicatedEmployees() {
        StringBuilder stringDataToFile = new StringBuilder();
        List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getEmploymentDate))
                .limit(10)
                .collect(Collectors.toList());
        for (Employee employee : sortedEmployeesBySalary) {
            stringDataToFile.append(employee.getPost()).append(" ").append(employee.getFullName())
                    .append(" работает с - ").append(employee.getEmploymentDate()).append(System.lineSeparator());
        }
        Service.println(stringDataToFile.toString());
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }
}
