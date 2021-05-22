package services.repositories;

import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

import models.BankAccount;
import models.BankAccountClient;

// файловое хранилище для банковских аккаунтов
public class FileRepoAccount extends FileRepository<BankAccountClient> {

    public FileRepoAccount(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BankAccountClient account) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY", Locale.ENGLISH);
        String saveInfo = String.format("{\n\tAccount: %s,\n\tbudgetName: %s,\n", account.getNumber(), account.getBudgetName()) +
            String.format("\towner: %s,\n\tvalidDate: %s,\n\tbank: %s \n}\n", account.getOwner(), 
            formatter.format(account.getValidDate()), account.getNameOfBank());
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
        try {
            RandomAccessFile cursor = getCursor();
            cursor.seek(0);
            List<BankAccountClient> accounts = new ArrayList<>();

            while (cursor.getFilePointer() != cursor.length()) {
                String strFile = cursor.readLine();

                if (strFile.equals("{")) {
                    String number = cursor.readLine();
                    number = number.substring(number.indexOf(':') + 2, number.length() - 1);
                    String budgetName = cursor.readLine();
                    budgetName = budgetName.substring(budgetName.indexOf(':') + 2, budgetName.length() - 1);
                    String owner = cursor.readLine();
                    owner = owner.substring(owner.indexOf(':') + 2, owner.length() - 1);
                    String date = cursor.readLine();
                    date = date.substring(date.indexOf(':') + 2, date.length() - 1);
                    String bank = cursor.readLine();
                    bank = bank.substring(bank.indexOf(':') + 2, bank.length() - 1);

                    BankAccountClient newAccount = new BankAccount(budgetName, number, owner, new Date(date), bank);
                    accounts.add(newAccount);
                }
                cursor.readLine();
            }
            return accounts;
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}