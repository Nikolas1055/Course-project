package sample.services;

import sample.domain.Employee;
import sample.repository.DataBase;
import sample.repository.FileDao;
import sample.ui.views.Config;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Класс Singleton для предоставления доступа к базе данных, роли авторизованного пользователя и локализации
 */
public class DBSingleton {
    private static final DBSingleton dbSingleton = new DBSingleton();
    private final ResourceBundle resourceBundle = ResourceBundle
            .getBundle(Config.BUNDLE_RU, new Locale("ru"));
    private DataBase dataBase;
    private Employee authEmployee;

    private DBSingleton() {
        dataBase = FileDao.loadDataBaseFromFile();
    }

    public static DBSingleton getInstance() {
        return dbSingleton;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Employee getAuthEmployee() {
        return authEmployee;
    }

    public void setAuthEmployee(Employee authEmployee) {
        this.authEmployee = authEmployee;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
