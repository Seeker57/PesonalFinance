package models;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;
import java.math.BigDecimal;

import services.CategoryCreator;
import services.GroupManagmentClient;
import repositories.Repository;
import services.transactionQuery.TransactionQuery;

// конкретный класс Журнала транзакций, реализующий наш интерфейс
public class TransactionLog implements TransactionLogClient {
    private List<BankTransactionClient> transactionList;
    private GroupManagmentClient groupManagment;
    private Repository<BankTransactionClient> transactionRepo;

    public TransactionLog(GroupManagmentClient groupManagment, 
        Repository<BankTransactionClient> transactionRepo) throws IOException {

        this.groupManagment = groupManagment;
        this.transactionRepo = transactionRepo;
        transactionRepo.connect();
        this.transactionList = transactionRepo.getAll();
    }

    @Deprecated
    public void createTransaction(BankAccountClient account, double amount, String strDate, 
        String counterparty, int numGroup) throws IOException {

        Group group;
        if (numGroup == 0) {
            group = groupManagment.createGroup(new CategoryCreator());
        } else {
            group = groupManagment.getGroup(numGroup - 1);
        }

        BankTransactionClient newTransaction = new BankTransaction(BigDecimal.valueOf(amount), 
            new Date(strDate), counterparty, group.getName(), account.getNumber());

        account.changeBalance(newTransaction.getAmount());
        transactionRepo.save(newTransaction);
        transactionList.add(newTransaction);
    }

    public void deleteTransaction(List<BankAccountClient> accounts, int transactionPos) {
        if (transactionPos != -1) {
            var deletedTransaction = transactionList.get(transactionPos);
            String accountNum = deletedTransaction.getAccount();

            for (var account : accounts) {
                if (account.getNumber().equals(accountNum)) {
                    account.changeBalance(deletedTransaction.getAmount().negate());
                }
            }
            transactionRepo.delete(transactionPos);
            transactionList.remove(transactionPos);
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
