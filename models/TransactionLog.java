package models;

import java.util.List;
import java.util.ArrayList;
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

    public boolean createTransaction(BankAccountClient account) {
        return true;
    }

    public boolean deleteTransaction(BankAccountClient account) {
        return true;
    }

    public List<BankTransactionClient> find(TransactionQuery query) {
        return null;
    }

    public BankTransactionClient getTransaction(int pos) {
        return transactionList.get(pos);
    }

    public GroupManagmentClient getGroupManagment() {
        return groupManagment;
    }
}
