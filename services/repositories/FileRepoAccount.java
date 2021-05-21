package services.repositories;

import java.text.SimpleDateFormat;
import java.util.List;
import models.BankAccountClient;

// файловое хранилище для банковских аккаунтов
public class FileRepoAccount extends FileRepository<BankAccountClient> {

    public FileRepoAccount(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BankAccountClient account) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        String saveInfo = String.format("{\n\tAccount: %s,\n\tbudgetName: %s,\n", account.getNumber(), account.getBudgetName()) +
            String.format("\towner: %s,\n\tvalidDate: %s,\n\tbank: %s,\n\tbalance: %s\n}\n", account.getOwner(), 
            formatter.format(account.getValidDate()), account.getNameOfBank(), account.getBalance().toString());
        writeInFile(saveInfo);
    }

    @Override
    public void delete(int id) {
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