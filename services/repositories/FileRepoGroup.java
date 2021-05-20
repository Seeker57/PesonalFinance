package services.repositories;

import java.util.List;
import models.Group;

// файловое хранилище для групп транзакций
public class FileRepoGroup extends FileRepository<Group> {

    FileRepoGroup(String fileName) {
        super(fileName);
    }

    @Override
    public boolean save(Group group) {
        return true;
    }

    @Override
    public Group get(int id) {
        return null;
    }

    @Override
    public List<Group> getAll() {
        return null;
    }
}
