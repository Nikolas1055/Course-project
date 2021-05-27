package sample.repository;

import sample.domain.DataBase;
import sample.domain.Employee;

public class DBSingleton {

    private static final DBSingleton dbSingleton = new DBSingleton();
    private DataBase dataBase;
    private Employee authEmployee;

    private DBSingleton(){
        dataBase = FileDao.loadDataBaseFromFile();
    }

    public static DBSingleton getInstance() {
        return dbSingleton;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Employee getAuthEmployee() {
        return authEmployee;
    }

    public void setAuthEmployee(Employee authEmployee) {
        this.authEmployee = authEmployee;
    }
}
