package baseclasses;

public enum Gender {
    MALE("Муж."),
    FEMALE("Жен."),
    GENDERLESS("NONE");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
