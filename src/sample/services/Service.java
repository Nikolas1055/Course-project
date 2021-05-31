package sample.services;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.domain.Department;
import sample.domain.Employee;

/**
 * Класс для часто повторяющихся операций.
 */
public class Service {

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
}
