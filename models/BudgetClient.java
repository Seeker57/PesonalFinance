package models;

// интерфейс Бюджета
public interface BudgetClient {
    public String seeBalance(int accountPos);
    public int getHashPin();
    public void addAccount();
    public TransactionLogClient getTransactionLog();
    public BankAccountClient getBankAccount(int accountPos);
    public void transferBetweenAccount();
    public String toString();
}
