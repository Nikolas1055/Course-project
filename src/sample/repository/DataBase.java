package sample.repository;

import sample.domain.Department;
import sample.domain.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс DataBase хранит информацию о сотрудниках организации (List<Employee>),
 * структуре её отделов (List<Department>) и список должностей (List<String>).
 */
public class DataBase implements Serializable {
    private List<Department> departments;
    private List<Employee> employees;
    private List<String> posts;

    /**
     * Конструктор по умолчанию.
     */
    public DataBase() {
        departments = new ArrayList<>();
        employees = new ArrayList<>();
        posts = new ArrayList<>();
    }

    /**
     * Конструктор с параметрами.
     *
     * @param departments - список отделов.
     * @param employees   - список сотрудников.
     * @param posts       - список должностей.
     */
    public DataBase(ArrayList<Department> departments, ArrayList<Employee> employees, ArrayList<String> posts) {
        this.departments = departments;
        this.employees = employees;
        this.posts = posts;
    }

    /**
     * Геттеры и сеттеры класса.
     */
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<String> posts) {
        this.posts = posts;
    }
}
