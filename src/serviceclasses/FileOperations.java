package serviceclasses;

import baseclasses.DataBase;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FileOperations {

    public static final String DATABASE_FILENAME = "base.txt";

    public static void saveDataBaseToFile(DataBase dataBase) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATABASE_FILENAME))) {
            outputStream.writeObject(dataBase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
