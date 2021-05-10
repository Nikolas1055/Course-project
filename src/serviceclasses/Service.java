package serviceclasses;

import java.util.Scanner;

public class Service {

    public static void println(String string) {
        System.out.println(string);
    }

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
