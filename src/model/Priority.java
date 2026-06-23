package model;

public enum Priority {
    LOW(1, "Низкий"),
    MEDIUM(2, "Средний"),
    HIGH(3, "Высокий");

    private final int weight;
    private final String description;

    Priority(int weight, String description) {
        this.weight = weight;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }
}
