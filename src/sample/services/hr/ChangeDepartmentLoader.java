package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.services.DBSingleton;

/**
 * Loader для вкладки - Изменить отдел
 */
public class ChangeDepartmentLoader {
    /**
     * Метод для изменения данных отдела, проверяет не пытается ли сотрудник изменить данные организации
     * (головной отдел)
     *
     * @param departmentToChange - отдел для изменения
     * @param newDepartmentName  - новое имя отдела
     * @param newSuperior        - новый вышестоящий отдел
     * @param newChief           - новый начальник отдел
     * @return - возвращает результат изменения
     */
    public boolean changeDepartment(Department departmentToChange,
                                    String newDepartmentName,
                                    Department newSuperior,
                                    Employee newChief) {
        if (departmentToChange.equals(DBSingleton.getInstance().getDataBase().getDepartments().get(0)) &&
                newSuperior != null) {
            return false;
        }
        departmentToChange.setName(newDepartmentName);
        departmentToChange.setSuperior(newSuperior);
        departmentToChange.setChief(newChief);
        return true;
    }
}
