package serviceclasses;

import baseclasses.Employee;
import baseclasses.Role;

import java.util.List;
import java.util.Scanner;

public class Authorization {

    public static Role authUser(List<Employee> employees) {
        Service.println("Авторизация");
        Service.println("1. Ввести логин и пароль");
        Service.println("2. Войти как гость");
        int choice = Service.selector();
        if (choice == 2) {
            return Role.EMPLOYEE;
        } else {
            return enterLoginPassword(employees);
        }
    }

    public static Role enterLoginPassword(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        int attempt = 3;
        while (attempt != 0) {
            Service.println("Введите логин:");
            String login = scanner.nextLine();
            Service.println("Введите пароль:");
            String password = scanner.nextLine();
            for (Employee employee : employees) {
                if (employee.getLogin().equals(login) && employee.getPassword().equals(password)) {
                    return employee.getRole();
                }
            }
            Service.println("Неправильная пара логин/пароль. Попробуйте еще раз. Количество попыток - " + (--attempt));
        }
        Service.println("Вы трижды неверно ввели логин и/или пароль. Осуществлен вход в качестве гостя.");
        return Role.EMPLOYEE;
    }
}
