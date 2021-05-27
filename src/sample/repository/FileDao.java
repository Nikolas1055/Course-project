package sample.repository;

import sample.domain.DataBase;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для работы с файловыми операциями ввода вывода.
 */
public class FileDao {

    /**
     * Поле с наименованием файла базы данных.
     */
    public static final String DATABASE_FILENAME = "base.txt";

    /**
     * Статический метод для сохранения базы данных в файл.
     *
     * @param dataBase - принимает ссылку на объект базы данных.
     */
    public static void saveDataBaseToFile(DataBase dataBase) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATABASE_FILENAME))) {
            outputStream.writeObject(dataBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Статический метод для загрузки базы данных из файла.
     * При отсутствии файла базы данных инициализирует базу данных дефолтными значениями.
     *
     * @return - возвращает ссылку на объект базы данных.
     */
    public static DataBase loadDataBaseFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATABASE_FILENAME))) {
            return (DataBase) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            DataBase dataBase = new DataBase();
            Initialize initialize = new Initialize(dataBase);
            initialize.postsInit();
            initialize.departmentInit();
            initialize.employeeInit();
            initialize.departmentChiefInit();
            return dataBase;
        }
    }

    /**
     * Статический метод для сохранения отчетов в текстовые файлы.
     * Имя файла генерируется автоматически с использованием текущей даты и времени.
     *
     * @param string - принимает сроку с отчетом для записи в файл.
     * @return - возвращает строку с результатом сохранения отчета в файл.
     */
    public static String saveReportToFile(String string) {
        String reportFileName = "Report_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yy")) + "_" +
                LocalTime.now().format(DateTimeFormatter.ofPattern("hh_mm_ss")) + ".txt";
        try (FileWriter fileWriter = new FileWriter(reportFileName)) {
            fileWriter.write(string);
        } catch (IOException e) {
            return "Не удалось сохранить отчет.";
        }
        return ("Отчет сохранен в файле - " + reportFileName);
    }
}
