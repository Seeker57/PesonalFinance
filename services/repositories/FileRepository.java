package services.repositories;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

// абстрактный класс, реализующий файловое хранилище
public abstract class FileRepository<T> implements Repository<T> {
    private String fileName;
    private File file;
    private BufferedReader fileReader;

    public FileRepository(String fileName) {
        this.fileName = fileName;
    }

    public void connect() {
        try {
            file = new File(fileName);
            fileReader = new BufferedReader(new FileReader(file));
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void disconnect() {
        try {
            fileReader.close();
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public abstract boolean save(T object);
    public abstract boolean delete(int id);
    public abstract T get(int id);
    public abstract List<T> getAll();

}
