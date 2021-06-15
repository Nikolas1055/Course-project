package sample.services;

import sample.domain.Role;
import sample.repository.FileDao;

/**
 * Loader для главного меню
 */
public class MainMenuLoader {
    /**
     * Метод для сохранения базы данных в файл и выхода из программы
     */
    public void saveDataBaseAndExit() {
        FileDao.saveDataBaseToFile(DBSingleton.getInstance().getDataBase());
        System.exit(0);
    }

    /**
     * Мето для проверки авторизации гостя
     *
     * @return - результат проверки
     */
    public boolean checkAuthRoleIsNull() {
        return DBSingleton.getInstance().getAuthEmployee() == null;
    }

    /**
     * Метод для проверки авторизации кадровика
     *
     * @return - результат проверки
     */
    public boolean checkAuthRoleIsHrOfficer() {
        return DBSingleton.getInstance().getAuthEmployee().getRole() == Role.HR_OFFICER;
    }
}
