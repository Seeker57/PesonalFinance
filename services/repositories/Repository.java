package services.repositories;

import java.util.List;

// интерфейс Хранилища для сохранения состояний отдельных объектов
public interface Repository<T> {
    public void connect();
    public boolean save(T object);
    public boolean delete(int id);
    public T get(int id);
    public List<T> getAll();
    public void disconnect();
}
