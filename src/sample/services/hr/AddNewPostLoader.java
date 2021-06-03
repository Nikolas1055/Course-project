package sample.services.hr;

import sample.repository.DataBase;
import sample.services.DBSingleton;

/**
 * Loader для добавления новой должности
 */
public class AddNewPostLoader {
    /**
     * Метод добавления новой должности в базу данных, перед добавлением проверяет существует ли должность
     * с таким же названием в базе данных
     *
     * @param newPost - название новой должности
     * @return - результат проверки и добавления должности
     */
    public boolean addNewPost(String newPost) {
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        for (String post : dataBase.getPosts()) {
            if (post.equals(newPost)) {
                return true;
            }
        }
        dataBase.getPosts().add(newPost);
        return false;
    }
}
