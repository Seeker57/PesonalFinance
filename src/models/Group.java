package models;

// интерфейс для групп, на которые можно разбить банковские транзакции
public interface Group {
    public String getName();
    public String getDescription();
    public String toString();
    public boolean equals(Object obj);
}
