package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Gender;
import sample.domain.Role;

import java.time.LocalDate;

/**
 * Loader для изменения данных сотрудника
 */
public class ChangeEmployeeLoader {
    /**
     * Метод для изменения данных сотрудника
     *
     * @param employeeToChange - сотрудник для изменения
     * @param surname          - новая фамилия
     * @param name             - новое имя
     * @param patronymic       - новое отчество
     * @param birthDate        - новая дата рождения
     * @param gender           - новый пол
     * @param phoneNumber      - новый номер телефона
     * @param post             - новая должность
     * @param department       - новый отдел
     * @param chief            - новый непосредственный начальник
     * @param employmentDate   - новая дата приема на работу
     * @param salary           - новая зарплата
     * @param role             - новая роль
     * @param login            - новый логин
     * @param password         - новый пароль
     */
    public void changeEmployee(Employee employeeToChange,
                               String surname,
                               String name,
                               String patronymic,
                               LocalDate birthDate,
                               Gender gender,
                               String phoneNumber,
                               String post,
                               Department department,
                               Employee chief,
                               LocalDate employmentDate,
                               double salary,
                               Role role,
                               String login,
                               String password) {
        employeeToChange.setSurname(surname);
        employeeToChange.setName(name);
        employeeToChange.setPatronymic(patronymic);
        employeeToChange.setPhoneNumber(phoneNumber);
        employeeToChange.setBirthDate(birthDate);
        employeeToChange.setGender(gender);
        employeeToChange.setPost(post);
        employeeToChange.setDepartment(department);
        employeeToChange.setChief(chief);
        employeeToChange.setEmploymentDate(employmentDate);
        employeeToChange.setSalary(salary);
        employeeToChange.setRole(role);
        employeeToChange.setLogin(login);
        employeeToChange.setPassword(password);
    }
}
