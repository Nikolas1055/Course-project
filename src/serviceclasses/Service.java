package serviceclasses;

import java.util.Scanner;

/**
 * Класс для часто повторяющихся операций.
 */
public class Service {

    /**
     * Статический метод для вывода строки в консоль.
     *
     * @param string - принимает строку для вывода.
     */
    public static void println(String string) {
        System.out.println(string);
    }

    /**
     * Статический метод для получения целочисленного значения вводимого в консоль по запросу.
     *
     * @return - возвращает целочисленное значение.
     */
    public static int selector() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }
}
