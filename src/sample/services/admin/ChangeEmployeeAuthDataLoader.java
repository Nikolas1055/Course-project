package sample.services.admin;

import sample.domain.Employee;
import sample.domain.Role;

/**
 * Класс Loader для раздела изменения авторизационной информации сотрудника
 */
public class ChangeEmployeeAuthDataLoader {
    /**
     * Метод изменящий информацию для авторизации пользователя
     *
     * @param employeeToChange - сотрудник для изменения
     * @param role             - новая роль
     * @param login            - новый логин
     * @param password         - новаый пароль
     */
    public void changeEmployeeAuthData(Employee employeeToChange,
                                       Role role,
                                       String login,
                                       String password) {
        employeeToChange.setRole(role);
        employeeToChange.setLogin(login);
        employeeToChange.setPassword(password);
    }
}
