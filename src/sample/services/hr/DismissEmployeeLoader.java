package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Role;
import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Loader для вкладки - Уволить сотрудника
 */
public class DismissEmployeeLoader {
    DataBase dataBase = DBSingleton.getInstance().getDataBase();

    /**
     * Метод удаляющий сотрудника из базы данных
     *
     * @param employee - сотрудник для увольнения/удаления
     */
    public void dismissEmployee(Employee employee) {
        dataBase.getEmployees().remove(employee);
    }

    /**
     * Метод длля проверки возможности увольнения сотрудника
     *
     * @param employee - сотрудник
     * @return - результат проверки
     */
    public boolean checkDismissible(Employee employee) {
        for (Department department : dataBase.getDepartments()) {
            if (department.getChief() != null && department.getChief().equals(employee)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод для проверки не пытается ли пользователь уволить сам себя
     *
     * @param employee - сотрудник для проверки
     * @return - результат проверки
     */
    public boolean checkAuthEmployee(Employee employee) {
        if (employee != null) {
            return employee.equals(DBSingleton.getInstance().getAuthEmployee());
        }
        return false;
    }

    /**
     * Метод для проверки количества сотрудников с ролью Администратор, в системе должен оставаться как минимум
     * один сотрудник с ролью Администратор и его уволить нельзя.
     *
     * @return - возвращает результат проверки
     */
    public boolean checkAdministrator() {
        int adminCounter = 0;
        for (Employee emp : dataBase.getEmployees()) {
            if (emp.getRole() == Role.ADMINISTRATOR) {
                adminCounter++;
            }
        }
        return adminCounter > 1;
    }
}
