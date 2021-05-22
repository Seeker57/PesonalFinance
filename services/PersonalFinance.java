package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;

import models.Budget;
import models.BudgetClient;
import models.TransactionLog;
import models.TransactionLogClient;
import services.repositories.Repository;
import services.repositories.FileRepoGroup;
import services.repositories.FileRepoBudget;
import services.repositories.FileRepoAccount;
import services.repositories.FileRepoTransaction;

// класс Персональные финансы, хранящий бюджеты
public class PersonalFinance implements PersonalFinanceClient {
    private List<BudgetClient> budgets;
    private Repository<BudgetClient> budgetRepo;

    public static final String FILE_GROUPS = "data\\groups.txt";
    public static final String FILE_BUDGETS = "data\\budgets.txt";
    public static final String FILE_ACCOUTNS = "data\\accounts_";
    public static final String FILE_TRANSACTIONS = "data\\transactions_";

    public PersonalFinance() {
        this.budgetRepo = new FileRepoBudget(FILE_BUDGETS);
        this.budgetRepo.connect();
        this.budgets = budgetRepo.getAll();
    }

    public void createBudget() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите название бюджета: ");
            String name = reader.readLine();
            System.out.print("Введите 4-значный пин-код: ");
            String pin = reader.readLine();

            GroupManagmentClient grpManage = new GroupManagment(new FileRepoGroup(FILE_GROUPS));
            TransactionLogClient log = new TransactionLog(grpManage, new FileRepoTransaction(FILE_TRANSACTIONS + name + ".txt"));

            BudgetClient newBudget = new Budget(name, pin.hashCode(), log, new FileRepoAccount(FILE_ACCOUTNS + name + ".txt"));
            budgetRepo.save(newBudget);
            budgets.add(newBudget);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteBudget() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println(this);
            System.out.print("Введите id бюджета, который хотите удалить или 0 для выхода: ");
            int budgetPos = Integer.parseInt(reader.readLine()) - 1;

            if (budgetPos != -1) {
                System.out.print("Введите 4-значный пин-код: ");
                String pin = reader.readLine();

                if (pin.hashCode() == budgets.get(budgetPos).getHashPin()) {
                    budgetRepo.delete(budgetPos);
                    budgets.remove(budgetPos);
                }
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public BudgetClient chooseBudget() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BudgetClient choosedBudget = null;

        try {
            System.out.println(this);
            System.out.print("Введите id бюджета или 0 для выхода: ");
            int budgetPos = Integer.parseInt(reader.readLine()) - 1;

            if (budgetPos != -1) {
                System.out.print("Введите 4-значный пин-код: ");
                String pin = reader.readLine();

                choosedBudget = budgets.get(budgetPos);
                if (pin.hashCode() == choosedBudget.getHashPin()) {
                    return choosedBudget;
                } else {
                    System.out.println("\nНеверный пин-код!");
                }
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
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
