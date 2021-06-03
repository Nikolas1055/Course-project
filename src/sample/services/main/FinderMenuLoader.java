package sample.services.main;

import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Role;
import sample.repository.DataBase;
import sample.repository.FileDao;
import sample.services.DBSingleton;
import sample.ui.CommonUiService;

import java.util.ResourceBundle;

/**
 * Loader для меню поиска сотрудника
 */
public class FinderMenuLoader {
    DataBase dataBase = DBSingleton.getInstance().getDataBase();
    Role authRole = DBSingleton.getInstance().getAuthEmployee() == null ? Role.EMPLOYEE :
            DBSingleton.getInstance().getAuthEmployee().getRole();
    ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();

    /**
     * Метод поиска сотрудника по должности
     *
     * @param post - должность для поиска
     * @return - возвращает строку с результатом поиска
     */
    public String findEmployeeByPost(String post) {
        StringBuilder stringDataToFile = new StringBuilder();
        boolean found = false;
        String header = resourceBundle.getString("finder_msg2") + post + System.lineSeparator();
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getPost().equals(post)) {
                found = true;
                stringDataToFile.append(getEmployeeStringByAuthRole(employee, authRole))
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }
        }
        String footer = "";
        if (!found) {
            footer = resourceBundle.getString("finder_msg3");
        }
        stringDataToFile.append(footer);
        return stringDataToFile.toString();
    }

    /**
     * Метод для поиска сотрудника по отделу
     *
     * @param department - отдел для поиска
     * @return - возвращает строку с результатом поиска
     */
    public String findByDepartment(Department department) {
        StringBuilder stringDataToFile = new StringBuilder();
        boolean found = false;
        String header = resourceBundle.getString("finder_msg4") + department.getName() + System.lineSeparator();
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getDepartment().equals(department)) {
                found = true;
                stringDataToFile.append(getEmployeeStringByAuthRole(employee, authRole))
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }
        }
        String footer = "";
        if (!found) {
            footer = resourceBundle.getString("finder_msg3");
        }
        stringDataToFile.append(footer);
        return stringDataToFile.toString();
    }

    /**
     * Метод для поиска сотрудника по ФИО
     *
     * @param fullName - полное или частичное ФИО сотрудника
     * @return - возвращает строку с результатом поиска
     */
    public String findByFullName(String fullName) {
        StringBuilder stringDataToFile = new StringBuilder();
        boolean found = false;
        String header = resourceBundle.getString("finder_msg5") + fullName;
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getFullName().contains(fullName)) {
                found = true;
                stringDataToFile.append(getEmployeeStringByAuthRole(employee, authRole))
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }
        }
        String footer = "";
        if (!found) {
            footer = resourceBundle.getString("finder_msg6");
        }
        stringDataToFile.append(footer);
        return stringDataToFile.toString();
    }

    /**
     * Метод для поиска сотрудника/ов по ФИО непосредственного начальника
     *
     * @param chiefFullName - полное или частичное ФИО непосредственного начальника
     * @return - возвращает строку с результатом поиска
     */
    public String findByChiefFullName(String chiefFullName) {
        StringBuilder stringDataToFile = new StringBuilder();
        boolean found = false;
        String header = resourceBundle.getString("finder_msg7") + chiefFullName + System.lineSeparator();
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getChief() != null && employee.getChief().getFullName().contains(chiefFullName)) {
                found = true;
                stringDataToFile.append(getEmployeeStringByAuthRole(employee, authRole))
                        .append(System.lineSeparator()).append(System.lineSeparator());
            }
        }
        String footer = "";
        if (!found) {
            footer = resourceBundle.getString("finder_msg3");
        }
        stringDataToFile.append(footer);
        return stringDataToFile.toString();
    }

    private String getEmployeeStringByAuthRole(Employee employee, Role authUserRole) {
        String employeeData;
        if (authUserRole.equals(Role.EMPLOYEE)) {
            employeeData = CommonUiService.getRole(employee) + ": " + employee.getFullName() + System.lineSeparator() +
                    resourceBundle.getString("finder_msg10") + employee.getPost() + System.lineSeparator() +
                    resourceBundle.getString("finder_msg11") + employee.getDepartment().getName();
        } else if (authUserRole.equals(Role.HR_OFFICER)) {
            employeeData = CommonUiService.employeeData(employee);
        } else {
            employeeData = CommonUiService.employeeData(employee) + System.lineSeparator() +
                    resourceBundle.getString("finder_msg8") + employee.getLogin() + System.lineSeparator() +
                    resourceBundle.getString("finder_msg9") + employee.getPassword();
        }
        return employeeData;
    }

    /**
     * Метод для сохранения результатов поиска в файл отчета о поиске
     *
     * @param stringDataToFile - строка отчета для сохранения
     * @return - возвращает строку с резльтатом сохранения и именем файла отчета о поиске
     */
    public String saveFindResultToFile(String stringDataToFile) {
        return FileDao.saveReportToFile(stringDataToFile);
    }
}
