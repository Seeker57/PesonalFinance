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
    private Repository transactionRepo;

    public TransactionLog(GroupManagmentClient groupManagment, Repository transactionRepo) {
        this.transactionList = new ArrayList<>();
        this.groupManagment = groupManagment;
        this.transactionRepo = transactionRepo;
    }

    public void createTransaction(BankAccountClient account) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите сумму: ");
            int amount = Integer.parseInt(reader.readLine());
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

            BankTransactionClient newTransaction = new BankTransaction(new BigDecimal(amount), 
                new Date(strDate), counterparty, newGroup, account);

            account.changeBalance(newTransaction.getAmount());
            transactionRepo.save(newTransaction);
            transactionList.add(newTransaction);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void deleteTransaction() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println(this.toString());
            System.out.print("Введите номер транзакции для удаления или 0 для выхода: ");
            int choice = Integer.parseInt(reader.readLine()) - 1;

            if (choice != -1) {
                var deletedTransaction = transactionList.get(choice);
                deletedTransaction.getAccount().changeBalance(deletedTransaction.getAmount().negate());
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

    public BankTransactionClient getTransaction(int pos) {
        return transactionList.get(pos);
    }

    public GroupManagmentClient getGroupManagment() {
        return groupManagment;
    }

    public String toString() {
        String info = "Журнал транзакций:\n";
        int pos = 1;
        for (var transaction : transactionList) {
            info += String.format("%d) %s\n", pos++, transaction.toString());
        }
        return info;
    }
}
