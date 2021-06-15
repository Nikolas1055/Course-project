package sample.repository;

import sample.domain.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс для первичной инициализации базы данных дефолтными значениями.
 */
public class DbInitializer {
    private final DataBase dataBase;

    /**
     * Конструктор с параметрами.
     *
     * @param dataBase - принимает ссылку на чистую базу данных.
     */
    public DbInitializer(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Метод инициализации/заполнения списка должностей.
     */
    public void postsInit() {
        List<String> posts = dataBase.getPosts();
        posts.add("Генеральный директор");
        posts.add("Руководитель департамента");
        posts.add("Начальник отдела");
        posts.add("Кадровик");
        posts.add("Сотрудник");
        posts.add("Администратор");
        posts.add("Электрик");
        posts.add("Мастер");
        posts.add("Уборщица");
        posts.add("Бухгалтер");
        posts.add("Казначей");
        posts.add("Финансист");
        posts.add("Завхоз");
        posts.add("Экономист");
    }

    /**
     * Метод для заполнения/инициализации списка отделов.
     */
    public void departmentInit() {
        List<Department> departments = dataBase.getDepartments();
        departments.add(new Department("АО Энергосетевая Компания", null, null));
        departments.add(new Department("Финансово экономический департамент", departments.get(0), null));
        departments.add(new Department("Планово экономическое бюро", departments.get(1), null));
        departments.add(new Department("Группа бухгалтерского учета", departments.get(1), null));
        departments.add(new Department("Казначей", departments.get(1), null));
        departments.add(new Department("Кадровый отдел", departments.get(0), null));
        departments.add(new Department("Цех районных котельных", departments.get(0), null));
        departments.add(new Department("Цех районных электросетей", departments.get(0), null));
        departments.add(new Department("Административно хозяйственная часть", departments.get(7), null));
        departments.add(new Department("Служба ремонта электрооборудования", departments.get(7), null));
        departments.add(new Department("Служба ремонта распредсетей", departments.get(7), null));
        departments.add(new Department("Оперативно диспетчерская служба", departments.get(7), null));
        departments.add(new Department("Группа технического аудита", departments.get(11), null));
        departments.add(new Department("Группа ремонта и капитального строительства", departments.get(11), null));
    }

    /**
     * Метод для заполнения/инициализации списка сотрудников.
     */
    public void employeeInit() {
        List<Employee> employees = dataBase.getEmployees();
        employees.add(new Employee("Лебедев", "Дмитрий", "Сергеевич", LocalDate.of(1982, 4, 3),
                Gender.MALE, "+79998887744", dataBase.getPosts().get(0), dataBase.getDepartments().get(0),
                null, LocalDate.of(2020, 4, 20), 200_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Брюзгина", "Наталья", "Владимировна", LocalDate.of(1973, 12, 2),
                Gender.FEMALE, "+79995556699", dataBase.getPosts().get(1), dataBase.getDepartments().get(1),
                employees.get(0), LocalDate.of(2018, 12, 20), 100_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Горская", "Людмила", "Викторовна", LocalDate.of(1985, 11, 25),
                Gender.FEMALE, "+79993335544", dataBase.getPosts().get(1), dataBase.getDepartments().get(2),
                employees.get(1), LocalDate.of(2015, 6, 15), 90_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Емельянова", "Светлана", "Николаевна", LocalDate.of(1977, 5, 17),
                Gender.FEMALE, "+79997774411", dataBase.getPosts().get(1), dataBase.getDepartments().get(3),
                employees.get(1), LocalDate.of(2019, 6, 3), 80_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Соханев", "Дмитрий", "Сергеевич", LocalDate.of(1983, 8, 12),
                Gender.MALE, "+79889995533", dataBase.getPosts().get(10), dataBase.getDepartments().get(4),
                employees.get(1), LocalDate.of(2019, 8, 20), 70_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Фионина", "Вероника", "Константиновна", LocalDate.of(1982, 6, 6),
                Gender.FEMALE, "+79889991111", dataBase.getPosts().get(3), dataBase.getDepartments().get(5),
                employees.get(0), LocalDate.of(2015, 5, 7), 50_000, Role.HR_OFFICER, "hr1", "password"));
        employees.add(new Employee("Гнетнева", "Татьяна", "Евгеньевна", LocalDate.of(1971, 10, 4),
                Gender.FEMALE, "+79889992222", dataBase.getPosts().get(3), dataBase.getDepartments().get(5),
                employees.get(0), LocalDate.of(2015, 3, 2), 50_000, Role.HR_OFFICER, "hr2", "password"));
        employees.add(new Employee("Королев", "Александр", "Сергеевич", LocalDate.of(1975, 5, 22),
                Gender.MALE, "+79889992289", dataBase.getPosts().get(2), dataBase.getDepartments().get(6),
                employees.get(0), LocalDate.of(2012, 2, 28), 80_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Маланьин", "Кирилл", "Валерьевич", LocalDate.of(1981, 11, 24),
                Gender.MALE, "+79889994455", dataBase.getPosts().get(2), dataBase.getDepartments().get(7),
                employees.get(0), LocalDate.of(2011, 3, 24), 110_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Бармина", "Наталья", "Александровна", LocalDate.of(1975, 9, 24),
                Gender.FEMALE, "+79889991225", dataBase.getPosts().get(2), dataBase.getDepartments().get(8),
                employees.get(8), LocalDate.of(2019, 12, 10), 50_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Галанин", "Георгий", "Олегович", LocalDate.of(1987, 5, 28),
                Gender.MALE, "+79889999999", dataBase.getPosts().get(12), dataBase.getDepartments().get(8),
                employees.get(9), LocalDate.of(2020, 7, 23), 40_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Тенишев", "Камиль", "Равильевич", LocalDate.of(1970, 12, 26),
                Gender.MALE, "+79889997894", dataBase.getPosts().get(2), dataBase.getDepartments().get(10),
                employees.get(8), LocalDate.of(2012, 1, 16), 65_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Брежнев", "Владимир", "Александрович", LocalDate.of(1980, 9, 10),
                Gender.MALE, "+79889993265", dataBase.getPosts().get(7), dataBase.getDepartments().get(10),
                employees.get(11), LocalDate.of(2011, 12, 19), 45_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Бурдин", "Илья", "Александрович", LocalDate.of(1988, 1, 5),
                Gender.MALE, "+79889993200", dataBase.getPosts().get(2), dataBase.getDepartments().get(9),
                employees.get(8), LocalDate.of(2012, 3, 19), 65_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Абдуллин", "Марат", "Рафикович", LocalDate.of(1992, 2, 29),
                Gender.MALE, "+79889993235", dataBase.getPosts().get(7), dataBase.getDepartments().get(9),
                employees.get(13), LocalDate.of(2013, 9, 9), 35_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Лобанов", "Александр", "Александрович", LocalDate.of(1957, 3, 28),
                Gender.MALE, "+79889997845", dataBase.getPosts().get(2), dataBase.getDepartments().get(11),
                employees.get(8), LocalDate.of(2012, 2, 1), 65_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Ащепкин", "Михаил", "Геннадьевич", LocalDate.of(1988, 10, 26),
                Gender.MALE, "+79889997868", dataBase.getPosts().get(7), dataBase.getDepartments().get(11),
                employees.get(15), LocalDate.of(2011, 6, 8), 35_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Зыков", "Станислав", "Александрович", LocalDate.of(1969, 6, 4),
                Gender.MALE, "+79888889977", dataBase.getPosts().get(2), dataBase.getDepartments().get(12),
                employees.get(8), LocalDate.of(2016, 11, 7), 75_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Киреев", "Евгений", "Алексеевич", LocalDate.of(1963, 11, 6),
                Gender.MALE, "+79888889922", dataBase.getPosts().get(6), dataBase.getDepartments().get(12),
                employees.get(17), LocalDate.of(2012, 3, 1), 45_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Козлов", "Иван", "Николаевич", LocalDate.of(1963, 6, 22),
                Gender.MALE, "+79888889919", dataBase.getPosts().get(2), dataBase.getDepartments().get(13),
                employees.get(8), LocalDate.of(2020, 8, 4), 65_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Сальшина", "Ирина", "Геннадьевна", LocalDate.of(1957, 4, 29),
                Gender.FEMALE, "+79888889989", dataBase.getPosts().get(7), dataBase.getDepartments().get(13),
                employees.get(19), LocalDate.of(2013, 2, 18), 45_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Гришунина", "Наталья", "Львовна", LocalDate.of(1977, 2, 4),
                Gender.FEMALE, "+79888880089", dataBase.getPosts().get(8), dataBase.getDepartments().get(8),
                employees.get(9), LocalDate.of(2015, 2, 3), 25_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Алешина", "Светлана", "Викторовна", LocalDate.of(1962, 12, 2),
                Gender.FEMALE, "+79888880009", dataBase.getPosts().get(9), dataBase.getDepartments().get(3),
                employees.get(3), LocalDate.of(2012, 10, 1), 55_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Сафьянова", "Ксения", "Андреевна", LocalDate.of(1981, 4, 8),
                Gender.FEMALE, "+79888880006", dataBase.getPosts().get(13), dataBase.getDepartments().get(2),
                employees.get(2), LocalDate.of(2012, 7, 13), 65_000, Role.EMPLOYEE, "guest", "guest"));
        employees.add(new Employee("Кабаргин", "Николай", "Юрьевич", LocalDate.of(1979, 1, 14),
                Gender.MALE, "+79119110000", dataBase.getPosts().get(5), dataBase.getDepartments().get(0),
                employees.get(0), LocalDate.of(2012, 5, 15), 65_000, Role.ADMINISTRATOR, "admin", "admin"));
    }

    /**
     * Метод для заполнения/инициализации начальников отделов.
     */
    public void departmentChiefInit() {
        List<Department> departments = dataBase.getDepartments();
        List<Employee> employees = dataBase.getEmployees();
        departments.get(0).setChief(employees.get(0));
        departments.get(1).setChief(employees.get(1));
        departments.get(2).setChief(employees.get(2));
        departments.get(3).setChief(employees.get(3));
        departments.get(4).setChief(employees.get(4));
        departments.get(5).setChief(employees.get(0));
        departments.get(6).setChief(employees.get(7));
        departments.get(7).setChief(employees.get(8));
        departments.get(8).setChief(employees.get(9));
        departments.get(9).setChief(employees.get(13));
        departments.get(10).setChief(employees.get(11));
        departments.get(11).setChief(employees.get(15));
        departments.get(12).setChief(employees.get(17));
        departments.get(13).setChief(employees.get(19));
    }
}
