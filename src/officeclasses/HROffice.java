package officeclasses;

import baseclasses.*;
import serviceclasses.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Класс описывает работу отдела кадров.
 */
public class HROffice {

    private DataBase dataBase;

    /**
     * Конструктор с параметрами.
     *
     * @param dataBase - принимает ссылку на базу данных.
     */
    public HROffice(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Метод для добавления нового сотрудника в базу данных (прием на работу).
     */
    public void addNewEmployee() {
        dataBase.getEmployees().add(getEmployeeData());
    }

    protected Employee getEmployeeData() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Введите фамилию:");
        String surname = scanner.nextLine();
        Service.println("Введите имя:");
        String name = scanner.nextLine();
        Service.println("Введите отчество:");
        String patronymic = scanner.nextLine();
        Service.println("Введите дату рождения в формате(dd-MM-yyyy):");
        LocalDate birthDate = setDate();
        Service.println("Выберите пол (1. Муж.; 2. Жен.; (любой другой выбор - пол не определен))");
        Gender gender;
        switch (Service.selector()) {
            default -> gender = Gender.GENDERLESS;
            case 1 -> gender = Gender.MALE;
            case 2 -> gender = Gender.FEMALE;
        }
        Service.println("Введите номер телефона:");
        String phoneNumber = scanner.nextLine();
        Service.println("Выберите должность:");
        String post = setPost();
        Service.println("Выберите отдел:");
        Department department = setDepartment();
        Service.println("Выберите начальника:");
        Employee chief = setChief();
        Service.println("Введите дату приема на работу в формате(dd-MM-yyyy):");
        LocalDate employmentDate = setDate();
        Service.println("Введите зарплату:");
        double salary = Service.selector();
        return new Employee(surname, name, patronymic, birthDate, gender, phoneNumber,
                post, department, chief, employmentDate, salary, Role.EMPLOYEE, "guest", "guest");
    }

    /**
     * Метод для добавления нового отдела в базу данных.
     */
    public void addNewDepartment() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Введите название отдела:");
        String departmentName = scanner.nextLine();
        Service.println("Выберите вышестоящий отдел:");
        Department superiorDepartment = setDepartment();
        Service.println("Выберите начальника отдела:");
        Employee chief = setChief();
        dataBase.getDepartments().add(new Department(departmentName, superiorDepartment, chief));
    }

    /**
     * Метод для добавления новой должности в базу данных.
     */
    public void addNewPost() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Введите название должности:");
        dataBase.getPosts().add(scanner.nextLine());
    }

    /**
     * Метод для изменения данных существующего отдела.
     */
    public void changeDepartment() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Выберите отдел для изменения:");
        Department departmentForChange = setDepartment();
        Service.println("Введите новое название отдела:");
        String departmentName = scanner.nextLine();
        Service.println("Выберите вышестоящий отдел:");
        Department superiorDepartment = setDepartment();
        Service.println("Выберите начальника отдела:");
        Employee chief = setChief();
        departmentForChange.setName(departmentName);
        departmentForChange.setSuperior(superiorDepartment);
        departmentForChange.setChief(chief);
    }

    /**
     * Метод для изменения данных существующего сотрудника.
     */
    public void changeEmployee() {
        Service.println("Выберите сотрудника для изменения информации:");
        Employee employeeForChange = getEmployee();
        Employee employeeNewData = getEmployeeData();
        employeeForChange.setSurname(employeeNewData.getSurname());
        employeeForChange.setName(employeeNewData.getName());
        employeeForChange.setName(employeeNewData.getName());
        employeeForChange.setPatronymic(employeeNewData.getPatronymic());
        employeeForChange.setBirthDate(employeeNewData.getBirthDate());
        employeeForChange.setGender(employeeNewData.getGender());
        employeeForChange.setPhoneNumber(employeeNewData.getPhoneNumber());
        employeeForChange.setPost(employeeNewData.getPost());
        employeeForChange.setDepartment(employeeNewData.getDepartment());
        employeeForChange.setChief(employeeNewData.getChief());
        employeeForChange.setEmploymentDate(employeeNewData.getEmploymentDate());
        employeeForChange.setSalary(employeeNewData.getSalary());
    }

    /**
     * Метод для удаления сотрудника из базы данных (увольнение).
     */
    public void dismissEmployee() {
        Service.println("Выберите сотрудника для увольнения:");
        for (int i = 0; i < dataBase.getEmployees().size(); i++) {
            Service.println((i + 1) + ". " + dataBase.getEmployees().get(i).getFullName());
        }
        try {
            Employee employeeForDismiss = dataBase.getEmployees().get(Service.selector() - 1);
            for (Department department : dataBase.getDepartments()) {
                if (department.getChief().equals(employeeForDismiss)) {
                    Service.println("Вы не можете уволить этого сотрудника. Он является начальником отдела - " +
                            department.getName());
                    Service.println("Назначьте другого сотрудника начальником этого отдела и попробуйте еще раз.");
                    return;
                }
            }
            dataBase.getEmployees().remove(employeeForDismiss);
        } catch (IndexOutOfBoundsException e) {
            Service.println("Некорректный выбор номера сотрудника.");
        }
    }

    /***
     * Метод для получения объекта LocalDate из введенной строки.
     * @return - возвращает объект LocalDate.
     */
    protected LocalDate setDate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputDate = scanner.nextLine();
            try {
                return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("d-M-yyyy"));
            } catch (DateTimeParseException e) {
                Service.println("Некорректный ввод даты, попробуйте еще раз.");
            }
        }
    }

    /**
     * Метод для получения должности из списка должностей.
     *
     * @return - строка с должностью.
     */
    protected String setPost() {
        while (true) {
            for (int i = 0; i < dataBase.getPosts().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getPosts().get(i));
            }
            String post = getListElement(dataBase.getPosts(), (Service.selector() - 1));
            if (post != null) {
                return post;
            }
            Service.println("Такой должности в списке нет. Повторите выбор.");
        }
    }

    /**
     * Метод для получения ссылки на объект отдела.
     *
     * @return - ссылка на отдел.
     */
    protected Department setDepartment() {
        while (true) {
            for (int i = 0; i < dataBase.getDepartments().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getDepartments().get(i).getName());
            }
            Department department = getListElement(dataBase.getDepartments(), (Service.selector() - 1));
            if (department != null) {
                return department;
            }
            Service.println("Такого отдела в списке нет. Повторите выбор.");
        }
    }

    /**
     * Метод для получения ссылки на сотрудника, который является непосредственным начальником.
     *
     * @return - ссылка на сотрудника.
     */
    protected Employee setChief() {
        for (int i = 0; i < dataBase.getEmployees().size(); i++) {
            Service.println((i + 1) + ". " + dataBase.getEmployees().get(i).getFullName());
        }
        Service.println("Если непосредственного начальника нет, сделайте любой другой выбор.");
        return getListElement(dataBase.getEmployees(), (Service.selector() - 1));
    }

    /**
     * Метод получения ссылки на сотрудника для изменения данных.
     *
     * @return - ссылка на сотрудника.
     */
    protected Employee getEmployee() {
        while (true) {
            for (int i = 0; i < dataBase.getEmployees().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getEmployees().get(i).getFullName());
            }
            Employee employee = getListElement(dataBase.getEmployees(), (Service.selector() - 1));
            if (employee != null) {
                if (employee.getRole().equals(Role.EMPLOYEE)) {
                    return employee;
                }
                Service.println("Вы не можете изменить данные этого сотрудника. Обратитесь к администратору.");
            } else {
                Service.println("Некорректный выбор, попробуйте еще раз.");
            }
        }
    }

    /**
     * Обобщенный метод получения значения из List.
     *
     * @param list   - List объектов любого типа.
     * @param choice - индекс элемента.
     * @param <T>    - обощенный тип списка.
     * @return - возвращает элемент списка по индексу, либо null при выходе за границы списка.
     */
    protected <T> T getListElement(List<T> list, int choice) {
        try {
            return list.get(choice);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
