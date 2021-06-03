package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.domain.Department;
import sample.domain.Employee;
import sample.domain.Gender;
import sample.domain.Role;
import sample.repository.DataBase;
import sample.services.DBSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Класс для часто повторяющихся операций.
 */
public class CommonUiService {
    private static final DataBase dataBase = DBSingleton.getInstance().getDataBase();
    private static final ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();

    /**
     * Метод для отображения в ComboBox названий отделов
     *
     * @param departmentComboBox - ComboBox с названиями отделов
     */
    public static void setDepartment(ComboBox<Department> departmentComboBox) {
        departmentComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Department> call(ListView<Department> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Department item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getName());
                    }
                };
            }
        });
        departmentComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department department) {
                if (department == null) {
                    return null;
                } else {
                    return department.getName();
                }
            }

            @Override
            public Department fromString(String s) {
                return null;
            }
        });
    }

    /**
     * Метод для отображения в ComboBox ФИО сотрудников
     *
     * @param employeeComboBox - ComboBox с ФИО сотрудников
     */
    public static void setEmployee(ComboBox<Employee> employeeComboBox) {
        employeeComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Employee> call(ListView<Employee> p) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Employee item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getFullName());
                    }
                };
            }
        });
        employeeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Employee employee) {
                return employee == null ? null : employee.getFullName();
            }

            @Override
            public Employee fromString(String s) {
                return null;
            }
        });
    }

    /**
     * Метод возвращает ObservableList для заполнения ComboBox сотрудниками
     *
     * @return - ObservableList с сотрудниками
     */
    public static ObservableList<Employee> getEmployees() {
        return FXCollections.observableArrayList(dataBase.getEmployees());
    }

    /**
     * Метод возвращает ObservableList для заполнения ComboBox значениями отделов
     *
     * @return - ObservableList с отделами
     */
    public static ObservableList<Department> getDepartments() {
        return FXCollections.observableArrayList(dataBase.getDepartments());
    }

    /**
     * Метод возвращает ObservableList для заполнения ComboBox значениями должностей
     * учитывает роль сотрудника при заполнении
     *
     * @return - ObservableList с должностями
     */
    public static ObservableList<String> getPosts() {
        ResourceBundle resourceBundle = DBSingleton.getInstance().getResourceBundle();
        List<String> postNames = new ArrayList<>();
        for (String post : dataBase.getPosts()) {
            if (DBSingleton.getInstance().getAuthEmployee().getRole() == Role.HR_OFFICER &&
                    (post.equals(resourceBundle.getString("common_field1")) ||
                            post.equals(resourceBundle.getString("common_field2")))) {
                continue;
            }
            postNames.add(post);
        }
        return FXCollections.observableArrayList(postNames);
    }

    /**
     * Метод возвращает ObservableList для заполнения ComboBox значениями должностей
     *
     * @return - ObservableList с должностями
     */
    public static ObservableList<String> getPostsForFinder() {
        return FXCollections.observableArrayList(dataBase.getPosts());
    }

    /**
     * Метод возвращающий данные по сотруднику
     *
     * @param employee - сотрудник
     * @return - строка с данными о сотруднике
     */
    public static String employeeData(Employee employee) {
        return getRole(employee) + ": " + employee.getSurname() + " " + employee.getName() + " " +
                employee.getPatronymic() + System.lineSeparator() +
                resourceBundle.getString("common_field6") + employee.getPost() + System.lineSeparator() +
                resourceBundle.getString("common_field7") + employee.getBirthDate() + System.lineSeparator() +
                resourceBundle.getString("common_field8") + getGender(employee) + System.lineSeparator() +
                resourceBundle.getString("common_field9") + employee.getPhoneNumber() + System.lineSeparator() +
                resourceBundle.getString("common_field10") + (employee.getDepartment() == null ?
                resourceBundle.getString("common_field11") : employee.getDepartment().getName()) +
                System.lineSeparator() +
                resourceBundle.getString("common_field12") + (employee.getChief() == null ?
                resourceBundle.getString("common_field13") : employee.getChief().getFullName()) +
                System.lineSeparator() +
                resourceBundle.getString("common_field14") + employee.getEmploymentDate() +
                System.lineSeparator() +
                resourceBundle.getString("common_field15") + employee.getSalary();
    }

    /**
     * Метод возвращающий роль сотрудника
     *
     * @param employee - сотрудник
     * @return - строка с ролью
     */
    public static String getRole(Employee employee) {
        return employee.getRole() == Role.EMPLOYEE ? resourceBundle.getString("common_field3") :
                employee.getRole() == Role.HR_OFFICER ? resourceBundle.getString("common_field2") :
                        resourceBundle.getString("common_field1");
    }

    /**
     * Метод возвращающий пол сотрудника
     *
     * @param employee - сотрудник
     * @return - строка с полом
     */
    public static String getGender(Employee employee) {
        return employee.getGender() == Gender.MALE ? resourceBundle.getString("common_field4") :
                resourceBundle.getString("common_field5");
    }
}
