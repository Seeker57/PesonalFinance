package repositories;

import java.io.IOException;
import java.util.List;

// интерфейс Хранилища для сохранения состояний отдельных объектов
public interface Repository<T> {
    public void connect();
    public void save(T object);
    public void delete(int id);
    public T get(int id);
    public List<T> getAll() throws IOException;
    public void disconnect();
}
