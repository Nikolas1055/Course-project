package baseclasses;

import java.io.*;
import java.time.LocalDate;
import java.util.Base64;

public class Employee implements Externalizable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String surname; // фамилия
    private String name; // имя
    private String patronymic; // отчество
    private LocalDate birthDate; // дата рождения
    private Gender gender; // пол
    private String phoneNumber; // номер телефона
    private String post; // должность
    private Department department; // отдел
    private Employee chief; // начальник
    private LocalDate employmentDate; // дата приема на работу
    private double salary; // зарплата
    private Role role;
    private String login;
    private String password;

    public Employee() {
    }

    public Employee(String surname, String name, String patronymic, LocalDate birthDate,
                    Gender gender, String phoneNumber, String post, Department department,
                    Employee chief, LocalDate employmentDate, double salary,
                    Role role, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.post = post;
        this.department = department;
        this.chief = chief;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }

    @Override
    public String toString() {
        return role.getRoleType() + ": " + surname + " " + name + " " + patronymic + System.lineSeparator() +
                "Должность: " + post + System.lineSeparator() +
                "Дата рождения: " + birthDate + System.lineSeparator() +
                "Пол: " + gender.getGender() + System.lineSeparator() +
                "Телефон: " + phoneNumber + System.lineSeparator() +
                "Отдел: " + department.getName() + System.lineSeparator() +
                "Непосредственный начальник: " + (chief == null ? "отсутствует" :
                chief.getFullName()) + System.lineSeparator() +
                "Дата приема на работу: " + employmentDate + System.lineSeparator() +
                "Зарплата: " + salary;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(surname);
        out.writeObject(name);
        out.writeObject(patronymic);
        out.writeObject(birthDate);
        out.writeObject(gender);
        out.writeObject(phoneNumber);
        out.writeObject(post);
        out.writeObject(department);
        out.writeObject(chief);
        out.writeObject(employmentDate);
        out.writeObject(salary);
        out.writeObject(role);
        out.writeObject(encryptString(login));
        out.writeObject(encryptString(password));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        surname = (String) in.readObject();
        name = (String) in.readObject();
        patronymic = (String) in.readObject();
        birthDate = (LocalDate) in.readObject();
        gender = (Gender) in.readObject();
        phoneNumber = (String) in.readObject();
        post = (String) in.readObject();
        department = (Department) in.readObject();
        chief = (Employee) in.readObject();
        employmentDate = (LocalDate) in.readObject();
        salary = (double) in.readObject();
        role = (Role) in.readObject();
        login = decryptString((String) in.readObject());
        password = decryptString((String) in.readObject());
    }

    private String encryptString(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    private String decryptString(String data) {
        return new String(Base64.getDecoder().decode(data));
    }



}
