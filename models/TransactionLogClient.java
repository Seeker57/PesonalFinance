package models;

import java.util.List;

import services.GroupManagmentClient;
import services.transactrionQuery.TransactionQuery;

// интерфейс для Журнала транзакций
public interface TransactionLogClient {
    public void createTransaction(BankAccountClient account);
    public void deleteTransaction();
    public List<BankTransactionClient> find(TransactionQuery query);
    public BankTransactionClient getTransaction(int pos);
    public GroupManagmentClient getGroupManagment();
    public String toString();
}
