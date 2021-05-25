package services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import models.Budget;
import models.BudgetClient;
import models.TransactionLog;
import models.TransactionLogClient;
import repositories.Repository;
import repositories.FileRepoGroup;
import repositories.FileRepoBudget;
import repositories.FileRepoAccount;
import repositories.FileRepoTransaction;

// класс Персональные финансы, хранящий бюджеты
public class PersonalFinance implements PersonalFinanceClient {
    private List<BudgetClient> budgets;
    private Repository<BudgetClient> budgetRepo;

    public static final String FILE_GROUPS = "data\\groups.txt";
    public static final String FILE_BUDGETS = "data\\budgets.txt";
    public static final String FILE_ACCOUTNS = "data\\accounts_";
    public static final String FILE_TRANSACTIONS = "data\\transactions_";

    public PersonalFinance() throws IOException {
        this.budgetRepo = new FileRepoBudget(FILE_BUDGETS);
        this.budgetRepo.connect();
        this.budgets = budgetRepo.getAll();
    }

    public void createBudget(String name, String pin) throws IOException {
        GroupManagmentClient grpManage = new GroupManagment(new FileRepoGroup(FILE_GROUPS));
        TransactionLogClient log = new TransactionLog(grpManage, new FileRepoTransaction(FILE_TRANSACTIONS + name + ".txt"));

        BudgetClient newBudget = new Budget(name, pin.hashCode(), log, new FileRepoAccount(FILE_ACCOUTNS + name + ".txt"));
        budgetRepo.save(newBudget);
        budgets.add(newBudget);
    }

    public void deleteBudget(int budgetPos, String pin) {
        if (budgetPos != -1) {

            BudgetClient deletedBudget = budgets.get(budgetPos);
            if (pin.hashCode() == deletedBudget.getHashPin()) {
                budgetRepo.delete(budgetPos);
                budgets.remove(budgetPos);

                deletedBudget.disconnectRepo();
                File accountsFile = new File(FILE_ACCOUTNS + deletedBudget.getName() + ".txt");
                accountsFile.delete();
                File transactionFile = new File(FILE_TRANSACTIONS + deletedBudget.getName() + ".txt");
                transactionFile.delete();
            }
        }
    }

    public BudgetClient chooseBudget(int budgetPos, String pin) {
        BudgetClient choosedBudget = null;

            if (budgetPos != -1) {
                choosedBudget = budgets.get(budgetPos);
                if (pin.hashCode() == choosedBudget.getHashPin()) {
                    return choosedBudget;
                } else {
                    return null;
                }
            }
        return null;
    }

    public String toString() {
        String info = "\nБюджеты:\n";
        int pos = 1;
        for (var budget : budgets) {
            info += String.format("%d) %s", pos++, budget.toString());
        }
        return info;
    }
}
