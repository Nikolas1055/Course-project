package sample.services.hr;

import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Gender;
import sample.domain.Role;
import sample.repository.DataBase;
import sample.services.DBSingleton;

import java.time.LocalDate;

/**
 * Loader для вкладки Добавить нового сотрудника
 */
public class AddNewEmployeeLoader {
    /**
     * Метод добавления нового сотрудника в базу данных, перед добавлением проводится проверка - установлены ли
     * значения должности и отдела
     *
     * @param surname        - Фамилия
     * @param name           - Имя
     * @param patronymic     - Отчество
     * @param birthDate      - дата рождения
     * @param gender         - пол
     * @param phoneNumber    - номер телефона
     * @param post           - должность
     * @param department     - отдел
     * @param chief          - непосредственный начальник
     * @param employmentDate - дата приема на работу
     * @param salary         - зарплата
     * @param role           - роль сотрудника
     * @param login          - логин
     * @param password       - пароль
     * @return - возвращает результат проверки и добавления сотрудника в базу данных
     */
    public boolean addNewEmployee(String surname,
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
        DataBase dataBase = DBSingleton.getInstance().getDataBase();
        if (post == null || department == null) {
            return false;
        } else {
            dataBase.getEmployees().add(new Employee(surname, name, patronymic, birthDate, gender, phoneNumber,
                    post, department, chief, employmentDate, salary, role, login, password));
        }
        return true;
    }
}
