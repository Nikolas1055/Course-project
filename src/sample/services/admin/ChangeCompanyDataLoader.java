package sample.services.admin;

import sample.domain.Employee;
import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Класс Loader для раздела изменения информации о компании (головном отделе)
 */
public class ChangeCompanyDataLoader {
    private final DataBase dataBase = DBSingleton.getInstance().getDataBase();

    /**
     * Метод возвращающий текущее наименование головного отдела
     *
     * @return - строка с наименованием
     */
    public String getCompanyName() {
        return dataBase.getDepartments().get(0).getName();
    }

    /**
     * Метод возвращает текущего начальника головного отдела
     *
     * @return - текущий начальник головного отдела
     */
    public Employee getCompanyChief() {
        return dataBase.getDepartments().get(0).getChief();
    }

    /**
     * Метод проверки должности выбранного нового начальника для головного отдела.
     *
     * @param employee - выбранный сотрудник
     * @return - возвращает результат проверки
     */
    public boolean checkNewChiefPost(Employee employee) {
        return employee.getPost().equals(dataBase.getPosts().get(0));
    }

    /**
     * Метод изменения информации о головном отделе (компания)
     *
     * @param newName  - новое наиманование
     * @param newChief - новый начальник
     */
    public void updateCompanyData(String newName, Employee newChief) {
        dataBase.getDepartments().get(0).setName(newName);
        dataBase.getDepartments().get(0).setChief(newChief);
    }
}
