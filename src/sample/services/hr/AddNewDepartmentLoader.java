package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Loader для вкладки Добавить новый отдел
 */
public class AddNewDepartmentLoader {
    /**
     * Метод проверяет существует ли отдел с таким названием, если нет, добавляет его в базу данных
     *
     * @param newDepartmentName - имя нового отдела
     * @param superior          - вышестоящий отдел
     * @param chief             - начальник отдела
     * @return - возвращает результат проверки
     */
    public boolean addNewDepartment(String newDepartmentName, Department superior, Employee chief) {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        for (Department department : dataBase.getDepartments()) {
            if (department.getName().equals(newDepartmentName)) {
                return true;
            }
        }
        dataBase.getDepartments().add(new Department(newDepartmentName, superior, chief));
        return false;
    }
}
