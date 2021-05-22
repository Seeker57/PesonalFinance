package models;

import java.util.List;

import services.GroupManagmentClient;
import services.transactrionQuery.TransactionQuery;

// интерфейс для Журнала транзакций
public interface TransactionLogClient {
    public void createTransaction(BankAccountClient account);
    public void deleteTransaction(List<BankAccountClient> accounts);
    public List<BankTransactionClient> find(TransactionQuery query);
    public List<BankTransactionClient> getIncomes();
    public List<BankTransactionClient> getExpences();
    public void setBalance(BudgetClient budget);
    public void addTransaction(BankTransactionClient transaction);
    public BankTransactionClient getTransaction(int pos);
    public GroupManagmentClient getGroupManagment();
    public void disconnectRepo();
    public String toString();
}
