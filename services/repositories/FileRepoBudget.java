package services.repositories;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import models.Budget;
import models.BudgetClient;
import models.TransactionLog;
import models.TransactionLogClient;
import services.GroupManagment;
import services.GroupManagmentClient;
import services.PersonalFinance;

// файловое хранилище для бюджетов
public class FileRepoBudget extends FileRepository<BudgetClient> {

    public FileRepoBudget(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BudgetClient budget) {
        String saveInfo = String.format("{\n\tBudget: %s,\n\thashPin: %d \n}\n", budget.getName(), budget.getHashPin());
        writeInFile(saveInfo);
    }

    @Override
    public BudgetClient get(int id) {
        return null;
    }

    @Override
    public List<BudgetClient> getAll() {
        try {
            RandomAccessFile cursor = getCursor();
            cursor.seek(0);
            List<BudgetClient> budgets = new ArrayList<>();

            while (cursor.getFilePointer() != cursor.length()) {
                String strFile = cursor.readLine();

                if (strFile.equals("{")) {
                    String name = cursor.readLine();
                    name = name.substring(name.indexOf(':') + 2, name.length() - 1);
                    String hashPin = cursor.readLine();
                    hashPin = hashPin.substring(hashPin.indexOf(':') + 2, hashPin.length() - 1);

                    GroupManagmentClient grpManage = new GroupManagment(new FileRepoGroup(PersonalFinance.FILE_GROUPS));
                    TransactionLogClient log = new TransactionLog(grpManage, 
                        new FileRepoTransaction(PersonalFinance.FILE_TRANSACTIONS + name + ".txt"));
                    budgets.add(new Budget(name, Integer.parseInt(hashPin), log, 
                        new FileRepoAccount(PersonalFinance.FILE_ACCOUTNS + name + ".txt")));
                }
                cursor.readLine();
            }
            return budgets;
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}