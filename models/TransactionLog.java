package models;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import services.CategoryCreator;
import services.GroupManagmentClient;
import services.repositories.Repository;
import services.transactrionQuery.TransactionQuery;

// конкретный класс Журнала транзакций, реализующий наш интерфейс
public class TransactionLog implements TransactionLogClient {
    private List<BankTransactionClient> transactionList;
    private GroupManagmentClient groupManagment;
    private Repository<BankTransactionClient> transactionRepo;

    public TransactionLog(GroupManagmentClient groupManagment, Repository<BankTransactionClient> transactionRepo) {
        this.groupManagment = groupManagment;
        this.transactionRepo = transactionRepo;
        transactionRepo.connect();
        this.transactionList = transactionRepo.getAll();
    }

    @Deprecated
    public void createTransaction(BankAccountClient account) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите сумму: ");
            double amount = Double.parseDouble(reader.readLine());
            System.out.print("Введите дату и время: ");
            String strDate = reader.readLine();
            System.out.print("Введите контрагента: ");
            String counterparty = reader.readLine();

            System.out.println(groupManagment.toString());
            System.out.print("Введите номер категории или 0, чтобы создать новую: ");
            int choice = Integer.parseInt(reader.readLine());

            Group newGroup;
            if (choice == 0) {
                newGroup = groupManagment.createGroup(new CategoryCreator());
            } else {
                newGroup = groupManagment.getGroup(choice - 1);
            }

            BankTransactionClient newTransaction = new BankTransaction(BigDecimal.valueOf(amount), 
                new Date(strDate), counterparty, newGroup.getName(), account.getNumber());

            account.changeBalance(newTransaction.getAmount());
            transactionRepo.save(newTransaction);
            transactionList.add(newTransaction);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteTransaction(List<BankAccountClient> accounts) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println(this.toString());
            System.out.print("Введите номер транзакции для удаления или 0 для выхода: ");
            int choice = Integer.parseInt(reader.readLine()) - 1;

            if (choice != -1) {
                var deletedTransaction = transactionList.get(choice);
                String accountNum = deletedTransaction.getAccount();

                for (var account : accounts) {
                    if (account.getNumber().equals(accountNum)) {
                        account.changeBalance(deletedTransaction.getAmount().negate());
                    }
                }
                transactionRepo.delete(choice);
                transactionList.remove(choice);
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<BankTransactionClient> find(TransactionQuery query) {
        List<BankTransactionClient> result = new ArrayList<>();
        for (var transaction : transactionList) {
            if (query.match(transaction)) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<BankTransactionClient> getIncomes() {
        List<BankTransactionClient> result = new ArrayList<>();
        for (var transaction : transactionList) {
            if (transaction.getAmount().doubleValue() > 0) {
                result.add(transaction);
            }
        }
        return result;
    }

    public List<BankTransactionClient> getExpences() {
        List<BankTransactionClient> result = new ArrayList<>();
        for (var transaction : transactionList) {
            if (transaction.getAmount().doubleValue() < 0) {
                result.add(transaction);
            }
        }
        return result;
    }

    public void setBalance(BudgetClient budget) {
        for (var transaction : transactionList) {
            BankAccountClient account = budget.getBankAccountOnNum(transaction.getAccount());
            if (account != null) {
                account.changeBalance(transaction.getAmount());
            }
        }
    }

    public void addTransaction(BankTransactionClient transaction) {
        transactionList.add(transaction);
        transactionRepo.save(transaction);
    }

    public BankTransactionClient getTransaction(int pos) {
        return transactionList.get(pos);
    }

    public GroupManagmentClient getGroupManagment() {
        return groupManagment;
    }

    public void disconnectRepo() {
        transactionRepo.disconnect();
    }

    public String toString() {
        String info = "\nЖурнал транзакций:\n";
        int pos = 1;
        for (var transaction : transactionList) {
            info += String.format("%d) %s\n", pos++, transaction.toString());
        }
        return info;
    }
}
