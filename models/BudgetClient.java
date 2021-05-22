package models;

import java.util.List;

// интерфейс Бюджета
public interface BudgetClient {
    public String seeBalance(int accountPos);
    public int getHashPin();
    public String getName();
    public void addAccount();
    public TransactionLogClient getTransactionLog();
    public BankAccountClient getBankAccount(int accountPos);
    public BankAccountClient getBankAccountOnNum(String num);
    public List<BankAccountClient> getBankAccounts();
    public int howAccounts();
    public void transferBetweenAccount();
    public void disconnectRepo();
    public String toString();
}
