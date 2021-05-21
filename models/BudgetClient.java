package models;

import java.math.BigDecimal;

// интерфейс Бюджета
public interface BudgetClient {
    public void seeBalance(int accountPos);
    public int getHashPin();
    public void addAccount();
    public TransactionLogClient getTransactionLog();
    public BankAccountClient getBankAccount(int accountPos);
    public void transferBetweenAccount(BigDecimal amount, int accPosFrom, int accPosTo);
    public String toString();
}
