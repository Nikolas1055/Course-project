package sample.domain;

import java.io.Serializable;

/**
 * Класс Department. Описывает отдел организации.
 */
public class Department implements Serializable {

    private String name;
    private Department superior;
    private Employee chief;

    /**
     * Конструктор по умолчанию.
     */
    public Department() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param name     - имя отдела.
     * @param superior - ссылка на вышестоящий отдел.
     * @param chief    - ссылка на сотрудника который назначен начальником отдела.
     */
    public Department(String name, Department superior, Employee chief) {
        this.name = name;
        this.superior = superior;
        this.chief = chief;
    }

    /**
     * Геттеры и сеттеры.
     */

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
