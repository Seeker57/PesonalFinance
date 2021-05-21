package models;

// интерфейс Бюджета
public interface BudgetClient {
    public String seeBalance(int accountPos);
    public int getHashPin();
    public String getName();
    public void addAccount();
    public TransactionLogClient getTransactionLog();
    public BankAccountClient getBankAccount(int accountPos);
    public int howAccounts();
    public void transferBetweenAccount();
    public String toString();
}
