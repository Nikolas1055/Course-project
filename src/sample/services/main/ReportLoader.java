package sample.services.main;

import sample.domain.Department;
import sample.domain.Employee;
import sample.repository.DataBase;
import sample.repository.FileDao;
import sample.services.DBSingleton;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Loader для меню отчетов
 */
public class ReportLoader {
    public static String STRUCT = "";
    DataBase dataBase = DBSingleton.getInstance().getDataBase();
    ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();

    /**
     * Метод вывода структуры организации
     *
     * @return - возвращает строку со структурой
     */
    public String reportStruct() {
        STRUCT = "";
        STRUCT += resourceBundle.getString("report_struct") + System.lineSeparator();
        printDep(dataBase.getDepartments(), dataBase.getDepartments().get(0), "");
        return STRUCT;
    }

    private void printDep(List<Department> departments, Department department, String string) {
        STRUCT += string + department.getName() + "(" + resourceBundle.getString("report_chief") +
                department.getChief().getFullName() + ")" + System.lineSeparator();
        for (Department dep : departments) {
            if (dep.getSuperior() == null) {
                continue;
            }
            if (dep.getSuperior().equals(department)) {
                printDep(departments, dep, string + "    ");
            }
        }
    }

    /**
     * Метод для вывода средней зарплаты по организации и по отделам
     *
     * @return - возвращает строку с отчетом
     */
    public String reportAverageSalary() {
        double averageSalary = 0;
        StringBuilder stringDataToFile = new StringBuilder();
        for (Employee employee : dataBase.getEmployees()) {
            averageSalary += employee.getSalary();
        }
        averageSalary /= dataBase.getEmployees().size();
        stringDataToFile.append(resourceBundle.getString("report_salary1"))
                .append(String.format("%.2f", averageSalary)).append(resourceBundle.getString("report_rub"))
                .append(System.lineSeparator());
        stringDataToFile.append(resourceBundle.getString("report_salary2")).append(System.lineSeparator());
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
                    .append(resourceBundle.getString("report_rub")).append(System.lineSeparator());
        }
        return stringDataToFile.toString();
    }

    /**
     * Метод для вывода отчета о ТОП-10 самых высоких зарплат
     *
     * @return - строка с отчетом
     */
    public String reportTopTenSalaries() {
        StringBuilder stringDataToFile = new StringBuilder();
        List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());
        for (Employee employee : sortedEmployeesBySalary) {
            stringDataToFile.append(employee.getPost()).append(": ").append(employee.getFullName())
                    .append(resourceBundle.getString("report_salary3")).append(employee.getSalary())
                    .append(System.lineSeparator());
        }
        return stringDataToFile.toString();
    }

    /**
     * Метод для вывода отчета о ТОП-10 самых преданных сотрудников
     *
     * @return - строка с отчетом
     */
    public String reportTopTenLoyal() {
        StringBuilder stringDataToFile = new StringBuilder();
        List<Employee> sortedEmployeesBySalary = dataBase.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getEmploymentDate))
                .limit(10)
                .collect(Collectors.toList());
        for (Employee employee : sortedEmployeesBySalary) {
            stringDataToFile.append(employee.getPost()).append(" ").append(employee.getFullName())
                    .append(resourceBundle.getString("report_work")).append(employee.getEmploymentDate())
                    .append(System.lineSeparator());
        }
        return stringDataToFile.toString();
    }

    /**
     * Метод сохранения отчета в файл
     *
     * @param stringDataToFile - строка с отчетом
     * @return - результат сохранения и имя файла отчета
     */
    public String saveReportToFileResult(String stringDataToFile) {
        return FileDao.saveReportToFile(stringDataToFile);
    }
}
