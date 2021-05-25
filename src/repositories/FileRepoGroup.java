package repositories;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import models.Group;

// файловое хранилище для групп транзакций
public class FileRepoGroup extends FileRepository<Group> {

    public FileRepoGroup(String fileName) {
        super(fileName);
    }

    @Override
    public void save(Group group) {
        String saveInfo = String.format("{\n\tGroup: %s,\n\tdescription: %s \n}\n", 
            group.getName(), group.getDescription());
        writeInFile(saveInfo);
    }

    @Override
    public Group get(int id) {
        return null;
    }

    @Override
    public List<Group> getAll() throws IOException {
        RandomAccessFile cursor = getCursor();
        cursor.seek(0);
        List<Group> groups = new ArrayList<>();

        while (cursor.getFilePointer() != cursor.length()) {
            String strFile = cursor.readLine();

            if (strFile.equals("{")) {
                String name = cursor.readLine();
                name = name.substring(name.indexOf(':') + 2, name.length() - 1);
                String description = cursor.readLine();
                description = description.substring(description.indexOf(':') + 2, description.length() - 1);
                groups.add(new Category(name, description));
            }
            cursor.readLine();
        }
        return groups;
    }
}
