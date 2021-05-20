package models;

// класс Категория банковской транзакции 
// реализует интерфейс Группа
public class Category implements Group {
    private String name;
    private String description;

    Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return String.format("Категория: %s, описание: %s", name, description);
    }
}
