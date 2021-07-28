package by.nintendo.clevertec.constant;

public enum Status {
    NOT_CREATED("NOT_CREATED"),
    CREATED("CREATED"),
    OK("OK"),
    BAD("BAD"),
    DELETE("DELETE"),
    UPDATE("UPDATE");


    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
