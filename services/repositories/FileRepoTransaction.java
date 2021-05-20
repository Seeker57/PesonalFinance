package services.repositories;

import java.util.List;
import models.BankTransactionClient;

// файловое хранилище для транзакций
public class FileRepoTransaction extends FileRepository<BankTransactionClient> {

    public FileRepoTransaction(String fileName) {
        super(fileName);
    }

    @Override
    public boolean save(BankTransactionClient transaction) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public BankTransactionClient get(int id) {
        return null;
    }

    @Override
    public List<BankTransactionClient> getAll() {
        return null;
    }
}