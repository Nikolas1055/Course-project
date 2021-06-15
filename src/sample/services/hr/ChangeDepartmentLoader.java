package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Loader для вкладки - Изменить отдел
 */
public class ChangeDepartmentLoader {
    private final DataBase dataBase = DBSingleton.getInstance().getDataBase();

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
        if (departmentToChange.equals(dataBase.getDepartments().get(0)) &&
                newSuperior != null) {
            return false;
        }
        departmentToChange.setName(newDepartmentName);
        departmentToChange.setSuperior(newSuperior);
        departmentToChange.setChief(newChief);
        return true;
    }

    /**
     * Метод для проверки есть ли подчиненные отделы у отдела
     *
     * @param department - отдел для проверки
     * @return - результат проверки
     */
    public boolean checkForSubordinateDepartment(Department department) {
        for (Department dep : dataBase.getDepartments()) {
            if (dep.equals(dataBase.getDepartments().get(0))) {
                continue;
            } else if (dep.getSuperior().equals(department)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки является ли отдел головным
     *
     * @param department - проверяемый отдел
     * @return - результат проверки
     */
    public boolean checkIfDepartmentIsCompanyHead(Department department) {
        return department.equals(dataBase.getDepartments().get(0));
    }

}
