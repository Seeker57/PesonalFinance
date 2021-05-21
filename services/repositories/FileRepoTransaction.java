package services.repositories;

import java.util.List;
import models.BankTransactionClient;

// файловое хранилище для транзакций
public class FileRepoTransaction extends FileRepository<BankTransactionClient> {

    public FileRepoTransaction(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BankTransactionClient transaction) {
        String saveInfo = String.format("{\n\tTransaction on account: %s,\n\tamount: %s,\n", transaction.getAccount().getNumber(),
            transaction.getAmount().toString()) + String.format("\tdate: %s,\n\tcounterparty: %s,\n", transaction.getDate(),
            transaction.getCounterparty()) + String.format("\tgroup: %s\n}\n", transaction.getGroup().getName());
        writeInFile(saveInfo);
    }

    @Override
    public void delete(int id) {
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