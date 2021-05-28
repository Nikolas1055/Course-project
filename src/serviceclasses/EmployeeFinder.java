package serviceclasses;

import baseclasses.Department;
import baseclasses.Employee;
import baseclasses.Role;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для поиска сотрудников в базе данных.
 */
public class EmployeeFinder {

    /**
     * Статический метод для поиска сотрудников в базе данных по ФИО и вывод информации обо всех найденных.
     *
     * @param employees    - принимает ссылку на список сотрудников организации.
     * @param authUserRole - принимает ссылку на роль/права текущего авторизовавшегося пользователя.
     */
    public static void findEmployeeByFullName(List<Employee> employees, Role authUserRole) {
        StringBuilder stringDataToFile = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        Service.println("Введите ФИО искомого сотрудника:");
        String fullName = scanner.nextLine();
        String header = "Данные для поиска: " + fullName;
        stringDataToFile.append(header).append(System.lineSeparator());
        Service.println(header);
        for (Employee employee : employees) {
            if (employee.getFullName().contains(fullName)) {
                found = true;
                String employeeData = getEmployeeStringByAuthRole(employee, authUserRole);
                stringDataToFile.append(employeeData).append(System.lineSeparator());
                Service.println(employeeData);
            }
        }
        String footer = "";
        if (!found) {
            footer = "Сотрудника(ов) с таким ФИО нет в базе данных.";
            Service.println(footer);
        }
        stringDataToFile.append(footer);
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }

    /**
     * Статический метод для вывода информации о найденных сотрудниках
     * на основании роли/прав авторизовавшегося пользователя.
     *
     * @param employee     - принимает ссылку на список сотрудников организации.
     * @param authUserRole - принимает ссылку на роль/права текущего авторизовавшегося пользователя.
     * @return - возвращает строку с информацией о сотруднике.
     */
    public static String getEmployeeStringByAuthRole(Employee employee, Role authUserRole) {
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

    /**
     * Статический метод для поиска сотрудников в базе данных по должности и вывод информации обо всех найденных.
     *
     * @param employees    - принимает ссылку на список сотрудников организации.
     * @param posts        - принимает ссылку на список должностей организации.
     * @param authUserRole - принимает ссылку на роль/права текущего авторизовавшегося пользователя.
     */
    public static void findEmployeeByPost(List<Employee> employees, List<String> posts, Role authUserRole) {
        StringBuilder stringDataToFile = new StringBuilder();
        Service.println("Выберите должность:");
        String post = getPost(posts);
        boolean found = false;
        String header = "Сотрудники с должностью: " + post;
        Service.println(header);
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : employees) {
            if (employee.getPost().equals(post)) {
                found = true;
                String employeeData = getEmployeeStringByAuthRole(employee, authUserRole);
                stringDataToFile.append(employeeData).append(System.lineSeparator());
                Service.println(employeeData);
            }
        }
        String footer = "";
        if (!found) {
            footer = "отсутствуют.";
            Service.println(footer);
        }
        stringDataToFile.append(footer);
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }

    /**
     * Статический метод для получения должности из списка должностей.
     *
     * @param posts - принимает ссылку на список должностей организации.
     * @return - возвращает строку с должностью.
     */
    public static String getPost(List<String> posts) {
        while (true) {
            for (int i = 0; i < posts.size(); i++) {
                Service.println((i + 1) + ". " + posts.get(i));
            }
            try {
                return posts.get(Service.selector() - 1);
            } catch (IndexOutOfBoundsException e) {
                Service.println("Такой должности в списке нет. Повторите выбор.");
            }
        }
    }

    /**
     * Статический метод для поиска сотрудников в базе данных по отделу и вывод информации обо всех найденных.
     *
     * @param employees    - принимает ссылку на список сотрудников организации.
     * @param departments  - принимает ссылку на список отделов организации.
     * @param authUserRole - принимает ссылку на роль/права текущего авторизовавшегося пользователя.
     */
    public static void findEmployeeByDepartment(List<Employee> employees, List<Department> departments, Role authUserRole) {
        StringBuilder stringDataToFile = new StringBuilder();
        Service.println("Выберите отдел:");
        Department department = getDepartment(departments);
        boolean found = false;
        String header = "Сотрудники в отделе: " + department.getName();
        Service.println(header);
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(department)) {
                found = true;
                String employeeData = getEmployeeStringByAuthRole(employee, authUserRole);
                stringDataToFile.append(employeeData).append(System.lineSeparator());
                Service.println(employeeData);
            }
        }
        String footer = "";
        if (!found) {
            footer = "отсутствуют.";
            Service.println(footer);
        }
        stringDataToFile.append(footer);
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }

    /**
     * Статический метод для получения ссылки на отдел из списка отделов.
     *
     * @param departments - принимает ссылку на список отделов организации.
     * @return - возвращает ссылку на отдел.
     */
    public static Department getDepartment(List<Department> departments) {
        while (true) {
            for (int i = 0; i < departments.size(); i++) {
                Service.println((i + 1) + ". " + departments.get(i).getName());
            }
            try {
                return departments.get(Service.selector() - 1);
            } catch (IndexOutOfBoundsException e) {
                Service.println("Такого отдела в списке нет. Повторите выбор.");
            }
        }
    }

    /**
     * Статический метод для поиска сотрудников в базе данных по непосредственному начальнику
     * и вывод информации обо всех найденных.
     *
     * @param employees    - принимает ссылку на список сотрудников организации.
     * @param authUserRole - принимает ссылку на роль/права текущего авторизовавшегося пользователя.
     */
    public static void findEmployeeByChief(List<Employee> employees, Role authUserRole) {
        StringBuilder stringDataToFile = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        Service.println("Введите ФИО начальника:");
        String chiefFullName = scanner.nextLine();
        String header = "Сотрудники у которых начальник: " + chiefFullName;
        Service.println(header);
        stringDataToFile.append(header).append(System.lineSeparator());
        for (Employee employee : employees) {
            if (employee.getChief() != null && employee.getChief().getFullName().contains(chiefFullName)) {
                found = true;
                String employeeData = getEmployeeStringByAuthRole(employee, authUserRole);
                Service.println(employeeData);
                stringDataToFile.append(employeeData).append(System.lineSeparator());
            }
        }
        String footer = "";
        if (!found) {
            footer = "отсутствуют.";
            Service.println(footer);
        }
        stringDataToFile.append(footer);
        Service.println(FileOperations.saveReportToFile(stringDataToFile.toString()));
    }
}
