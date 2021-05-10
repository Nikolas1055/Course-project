package baseclasses;

import java.io.Serializable;

public class Department implements Serializable {

    private String name; // название отдела
    private Department superior; // вышестоящий отдел
    private Employee chief; // начальник отдела

    public Department() {
    }

    public Department(String name, Department superior, Employee chief) {
        this.name = name;
        this.superior = superior;
        this.chief = chief;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getSuperior() {
        return superior;
    }

    public void setSuperior(Department superior) {
        this.superior = superior;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }
}
