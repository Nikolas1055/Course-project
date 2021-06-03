package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
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
     * @param newValue - сотрудник для проверки
     * @return - результат проверки
     */
    public boolean checkAuthEmployee(Employee newValue) {
        if (newValue != null) {
            return newValue.equals(DBSingleton.getInstance().getAuthEmployee());
        }
        return false;
    }
}
