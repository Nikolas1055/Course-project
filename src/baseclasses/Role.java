package baseclasses;

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
