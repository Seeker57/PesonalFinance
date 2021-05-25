package models;

import java.math.BigDecimal;
import java.util.List;

// интерфейс Бюджета
public interface BudgetClient {
    public String seeBalance(int accountPos);
    public int getHashPin();
    public String getName();
    public void addAccount(String num, String owner, String nameOfBank, String strDate);
    public TransactionLogClient getTransactionLog();
    public BankAccountClient getBankAccount(int accountPos);
    public BankAccountClient getBankAccountOnNum(String num);
    public List<BankAccountClient> getBankAccounts();
    public int howAccounts();
    public void transferBetweenAccount(int accPosTo, int accPosFrom, BigDecimal amount);
    public void disconnectRepo();
    public String toString();
}
