package sample.domain;

/**
 * Класс перечисления пола сотрудников.
 */
public enum Gender {
    MALE("Муж."),
    FEMALE("Жен.");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}