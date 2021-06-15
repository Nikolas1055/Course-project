package sample.services.admin;

import sample.repository.FileDao;
import sample.services.DBSingleton;

/**
 * Класс Loader для раздела сохранения базы данных в файл
 */
public class DataBaseOperationsLoader {
    /**
     * Метод возвращает строку с информацией о результате сохранения базы данных в файл
     *
     * @return - строка с результатом сохранения
     */
    public String saveDataBase() {
        return FileDao.saveDataBaseToFile(DBSingleton.getInstance().getDataBase());
    }
}
