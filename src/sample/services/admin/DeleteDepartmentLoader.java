package sample.services.admin;

import sample.domain.Department;
import sample.domain.Employee;
import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Класс Loader для раздела удаления отдела
 */
public class DeleteDepartmentLoader {
    private final DataBase dataBase = DBSingleton.getInstance().getDataBase();

    /**
     * Метод проверяющий является ли удаляемый отдел головным
     *
     * @param department - отдел для удаления
     * @return - результат проверки
     */
    public boolean checkIfDepartmentIsCompanyHead(Department department) {
        return department.equals(dataBase.getDepartments().get(0));
    }

    /**
     * Метод для проверки привязаны ли к отедлу сотрудники
     *
     * @param department - отдел для проверки
     * @return - результат проверки
     */
    public boolean checkDepartmentEmployees(Department department) {
        for (Employee employee : dataBase.getEmployees()) {
            if (employee.getDepartment().equals(department)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки есть ли начальник у отдела
     *
     * @param department - отдел для проверки
     * @return - результат проверки
     */
    public boolean checkDepartmentChief(Department department) {
        return department.getChief() == null;
    }

    /**
     * Метод для удаления отдела
     *
     * @param department - удаляемый отдел
     * @return - результат удаления
     */
    public boolean deleteDepartment(Department department) {
        return dataBase.getDepartments().remove(department);
    }
}
