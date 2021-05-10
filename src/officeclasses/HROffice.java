package officeclasses;

import baseclasses.*;
import serviceclasses.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class HROffice {

    private DataBase dataBase;

    public HROffice(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void addNewEmployee() {
        dataBase.getEmployees().add(getEmployeeData());
    }

    public Employee getEmployeeData() {
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

    public void addNewPost() {
        Scanner scanner = new Scanner(System.in);
        Service.println("Введите название должности:");
        dataBase.getPosts().add(scanner.nextLine());
    }

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
                    Service.println("Назначте другого сотрудника начальником этого отдела и попробуйте еще раз.");
                    return;
                }
            }
            dataBase.getEmployees().remove(employeeForDismiss);
        } catch (IndexOutOfBoundsException e) {
            Service.println("Некорректный выбор номера сотрудника.");
        }
    }

    public LocalDate setDate() {
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

    public String setPost() {
        while (true) {
            for (int i = 0; i < dataBase.getPosts().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getPosts().get(i));
            }
            try {
                return dataBase.getPosts().get(Service.selector() - 1);
            } catch (IndexOutOfBoundsException e) {
                Service.println("Такой должности в списке нет. Повторите выбор.");
            }
        }
    }

    public Department setDepartment() {
        while (true) {
            for (int i = 0; i < dataBase.getDepartments().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getDepartments().get(i).getName());
            }
            try {
                return dataBase.getDepartments().get(Service.selector() - 1);
            } catch (IndexOutOfBoundsException e) {
                Service.println("Такого отдела в списке нет. Повторите выбор.");
            }
        }
    }

    public Employee setChief() {
        for (int i = 0; i < dataBase.getEmployees().size(); i++) {
            Service.println((i + 1) + ". " + dataBase.getEmployees().get(i).getFullName());
        }
        Service.println("Если непосредственного начальника нет, сделайте любой другой выбор.");
        try {
            return dataBase.getEmployees().get(Service.selector() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Employee getEmployee() {
        while (true) {
            for (int i = 0; i < dataBase.getEmployees().size(); i++) {
                Service.println((i + 1) + ". " + dataBase.getEmployees().get(i).getFullName());
            }
            try {
                Employee employee = dataBase.getEmployees().get(Service.selector() - 1);
                if (employee.getRole().equals(Role.EMPLOYEE)) {
                    return employee;
                }
                Service.println("Вы не можете изменить данные этого сотрудника. Обратитесь к администратору.");
            } catch (IndexOutOfBoundsException e) {
                Service.println("Некорректный выбор, попробуйте еще раз.");
            }
        }
    }
}
