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

    public void delete(int id) {
        try {
            RandomAccessFile cursor = getCursor();
            cursor.seek(0);
            String newFile = "";
            int newLength = 0;
            int currentPos = 0;

            while (cursor.getFilePointer() != cursor.length()) {
                String strFile = cursor.readLine();

                if (strFile.equals("{")) {
                    if (currentPos == id) {
                        while (!strFile.equals("}")) {
                            strFile = cursor.readLine();
                        }
                    } else {
                        newFile += strFile + "\n";
                        newLength += strFile.length() + 1;
                        while (!strFile.equals("}")) {
                            strFile = cursor.readLine();
                            newFile += strFile + "\n";
                            newLength += strFile.length() + 1;
                        }
                    }
                    currentPos++;
                }
            }

            cursor.seek(0);
            cursor.writeBytes(newFile);
            cursor.setLength(newLength);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public abstract void save(T object);
    public abstract T get(int id);
    public abstract List<T> getAll();

}
