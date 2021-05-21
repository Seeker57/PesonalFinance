package services.repositories;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;

// абстрактный класс, реализующий файловое хранилище
public abstract class FileRepository<T> implements Repository<T> {
    private String fileName;
    private File file;
    private RandomAccessFile cursor;

    public FileRepository(String fileName) {
        this.fileName = fileName;
    }

    public void connect() {
        try {
            file = new File(fileName);
            cursor = new RandomAccessFile(file, "rw");
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void disconnect() {
        try {
            cursor.close();
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void writeInFile(String str) {
        try {
            cursor.seek(cursor.length());
            cursor.writeBytes(str);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    protected RandomAccessFile getCursor() {
        return cursor;
    }

    public abstract void save(T object);
    public abstract void delete(int id);
    public abstract T get(int id);
    public abstract List<T> getAll();

}
