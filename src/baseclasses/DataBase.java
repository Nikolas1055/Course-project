package baseclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataBase implements Serializable {

    private List<Department> departments; // отделы
    private List<Employee> employees; // сотрудники
    private List<String> posts; // должности

    public DataBase() {
        departments = new ArrayList<>();
        employees = new ArrayList<>();
        posts = new ArrayList<>();
    }

    public DataBase(ArrayList<Department> departments, ArrayList<Employee> employees, ArrayList<String> posts) {
        this.departments = departments;
        this.employees = employees;
        this.posts = posts;
    }

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
