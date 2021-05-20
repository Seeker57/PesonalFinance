package services.repositories;

import java.util.List;
import models.BankAccountClient;

// файловое хранилище для банковских аккаунтов
public class FileRepoAccount extends FileRepository<BankAccountClient> {

    public FileRepoAccount(String fileName) {
        super(fileName);
    }

    @Override
    public boolean save(BankAccountClient account) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public BankAccountClient get(int id) {
        return null;
    }

    @Override
    public List<BankAccountClient> getAll() {
        return null;
    }
}