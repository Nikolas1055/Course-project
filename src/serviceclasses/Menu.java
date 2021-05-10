package serviceclasses;

import baseclasses.Role;

public class Menu {

    public static void employeeType() {
        Service.println("1 - Администратор.");
        Service.println("2 - Кадровый работник.");
        Service.println("Любой другой выбор - Сотрудник.");
    }

    public static void mainMenu(Role authUserRole) {
        Service.println("Система учета сотрудников");
        Service.println("1. Поиск сотрудника." + System.lineSeparator() +
                "2. Отчеты.");
        if (authUserRole == Role.HR_OFFICER || authUserRole == Role.ADMINISTRATOR) {
            Service.println("3. Кадровый отдел");
        }
        if (authUserRole == Role.ADMINISTRATOR) {
            Service.println("4. Административный отдел");
        }
        Service.println("Любой другой выбор - выход.");
    }

    public static void findMenu() {
        Service.println("Поиск сотрудника по:");
        Service.println("1. ФИО" + System.lineSeparator() +
                "2. Должности" + System.lineSeparator() +
                "3. Названию отдела" + System.lineSeparator() +
                "4. ФИО начальника");
    }

    public static void reportMenu() {
        Service.println("1. Структура организации." + System.lineSeparator() +
                "2. Средняя зарплата по организации и по отделам." + System.lineSeparator() +
                "3. ТОП-10 самых дорогих сотрудников по зарплате." + System.lineSeparator() +
                "4. ТОП-10 самых преданных сотрудников.");
    }

    public static void hrMenu() {
        Service.println("1. Добавить нового сотрудника." + System.lineSeparator() +
                "2. Добавить новую должность." + System.lineSeparator() +
                "3. Добавить новый отдел." + System.lineSeparator() +
                "4. Изменить данные сотрудника." + System.lineSeparator() +
                "5. Изменить данные отдела." + System.lineSeparator() +
                "6. Уволить сотрудника");
    }

    public static void adminMenu() {
        Service.println("1. Добавить нового сотрудника." + System.lineSeparator() +
                "2. Удалить отдел");
    }
}
