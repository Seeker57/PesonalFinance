package models;

import java.util.List;

import services.GroupManagmentClient;
import services.transactrionQuery.TransactionQuery;

// интерфейс для Журнала транзакций
public interface TransactionLogClient {
    public boolean createTransaction(BankAccountClient account);
    public boolean deleteTransaction(BankAccountClient account);
    public List<BankTransactionClient> find(TransactionQuery query);
    public BankTransactionClient getTransaction(int pos);
    public GroupManagmentClient getGroupManagment();
}
