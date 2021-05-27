package sample.domain;

/**
 * Класс перечисления ролей (прав доступа) сотрудников.
 */
public enum Role {
    EMPLOYEE("Сотрудник"),
    HR_OFFICER("Кадровик"),
    ADMINISTRATOR("Администратор");

    private final String roleType;

    Role(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }
}
