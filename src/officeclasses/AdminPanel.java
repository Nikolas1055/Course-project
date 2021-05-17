package officeclasses;

import baseclasses.DataBase;
import baseclasses.Department;
import baseclasses.Employee;
import baseclasses.Role;
import serviceclasses.Menu;
import serviceclasses.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс описывает работу IT отдела (унаследован от класса HROffice).
 */
public class AdminPanel extends HROffice {

    private DataBase dataBase;

    /**
     * Конструктор с параметрами.
     *
     * @param dataBase - ссылка на базу данных.
     */
    public AdminPanel(DataBase dataBase) {
        super(dataBase);
        this.dataBase = dataBase;
    }

    /**
     * Переопределенный метод добавления (приема на работу) нового сотрудника.
     * Отличается от метода в классе HROffice тем, что позволяет добавлять
     * сотрудников с ролями HR_OFFICER и ADMINISTRATOR.
     */
    public void addNewEmployee() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Выберите роль сотрудника:");
        Role role;
        Menu.employeeType();
        switch (Service.selector()) {
            default -> role = Role.EMPLOYEE;
            case 1 -> role = Role.ADMINISTRATOR;
            case 2 -> role = Role.HR_OFFICER;
        }
        Employee employee = getEmployeeData();
        String login;
        String password;
        if (!role.equals(Role.EMPLOYEE)) {
            Service.println("Введите логин");
            login = scanner.nextLine();
            Service.println("Введите пароль:");
            password = scanner.nextLine();
            employee.setRole(role);
            employee.setLogin(login);
            employee.setPassword(password);
        }
        dataBase.getEmployees().add(employee);
    }

    /**
     * Метод позволяет удалить отдел.
     */
    public void deleteDepartment() {
        Service.println("Выберите отдел для удаления:");
        List<Employee> departmentEmployees = new ArrayList<>();
        Department departmentToDelete = setDepartment();
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getDepartment().equals(departmentToDelete)) {
                departmentEmployees.add(employee);
            }
        }
        if (departmentEmployees.size() != 0) {
            Service.println("Нельзя удалить отдел - " + departmentToDelete.getName());
            Service.println("У отдела есть сотрудники.");
            for (Employee employee : departmentEmployees) {
                Service.println(employee.getFullName());
            }
            return;
        }
        if (departmentToDelete.getChief() != null) {
            Service.println("Нельзя удалить отдел - " + departmentToDelete.getName());
            Service.println("У отдела есть начальник - " + departmentToDelete.getChief().getFullName());
            return;
        }
        for (Department department : dataBase.getDepartments()) {
            if (department.getSuperior().equals(departmentToDelete)) {
                Service.println("Нельзя удалить отдел - " + departmentToDelete.getName());
                Service.println("У отдела есть подчиненные отделы");
                return;
            }
        }
        dataBase.getDepartments().remove(departmentToDelete);
    }
}
