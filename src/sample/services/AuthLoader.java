package sample.services;

import sample.domain.Employee;

/**
 * Loader для проверки данных для входа
 */
public class AuthLoader {
    /**
     * Метод проверки пары - логин/пароль
     *
     * @param loginText    - логин
     * @param passwordText - пароль
     * @return - результат проверки
     */
    public boolean authUser(String loginText, String passwordText) {
        for (Employee employee : DBSingleton.getInstance().getDataBase().getEmployees()) {
            if (employee.getLogin().equals(loginText) && employee.getPassword().equals(passwordText)) {
                DBSingleton.getInstance().setAuthEmployee(employee);
                return true;
            }
        }
        return false;
    }

    /**
     * Метод аутентификации гостя
     */
    public void authGuest() {
        DBSingleton.getInstance().setAuthEmployee(null);
    }
}
