package serviceclasses;

import baseclasses.Role;

/**
 * Класс для отображения текстовых меню.
 */
public class Menu {

    /**
     * Статический метод для отображения меню авторизации.
     */
    public static void employeeType() {
        Service.println("1 - Администратор.");
        Service.println("2 - Кадровый работник.");
        Service.println("Любой другой выбор - Сотрудник.");
    }

    /**
     * Статический метод для отображения главного меню программы.
     * Пункты меню отображаются в зависимости от роли/ прав авторизовавшегося пользователя.
     *
     * @param authUserRole - принимает ссылку на роль/права авторизовавшегося пользователя.
     */
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

    /**
     * Статический метод для отображения меню поиска сотрудников по различным параметрам.
     */
    public static void findMenu() {
        Service.println("Поиск сотрудника по:");
        Service.println("1. ФИО" + System.lineSeparator() +
                "2. Должности" + System.lineSeparator() +
                "3. Названию отдела" + System.lineSeparator() +
                "4. ФИО начальника");
    }

    /**
     * Статический метод для отображения меню отчетов.
     */
    public static void reportMenu() {
        Service.println("1. Структура организации." + System.lineSeparator() +
                "2. Средняя зарплата по организации и по отделам." + System.lineSeparator() +
                "3. ТОП-10 самых дорогих сотрудников по зарплате." + System.lineSeparator() +
                "4. ТОП-10 самых преданных сотрудников.");
    }

    /**
     * Статический метод для отображения меню отдела кадров.
     */
    public static void hrMenu() {
        Service.println("1. Добавить нового сотрудника." + System.lineSeparator() +
                "2. Добавить новую должность." + System.lineSeparator() +
                "3. Добавить новый отдел." + System.lineSeparator() +
                "4. Изменить данные сотрудника." + System.lineSeparator() +
                "5. Изменить данные отдела." + System.lineSeparator() +
                "6. Уволить сотрудника");
    }

    /**
     * Статический метод для отображения меню IT отдела.
     */
    public static void adminMenu() {
        Service.println("1. Добавить нового сотрудника." + System.lineSeparator() +
                "2. Удалить отдел");
    }
}
