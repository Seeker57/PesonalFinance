package models;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import repositories.Repository;

// конкретный класс бюджета, реализующий наш интерфейс
public class Budget implements BudgetClient {
    private String name;
    private int hashPin;
    private List<BankAccountClient> accounts;
    private TransactionLogClient transactionLog;
    private Repository<BankAccountClient> accountRepo;

    public Budget(String name, int hashPin, TransactionLogClient transactionLog, 
        Repository<BankAccountClient> accountRepo) throws IOException {

        this.name = name;
        this.hashPin = hashPin;
        this.transactionLog = transactionLog;
        this.accountRepo = accountRepo;
        accountRepo.connect();
        this.accounts = accountRepo.getAll();
        transactionLog.setBalance(this);
    }

    @Deprecated
    public void addAccount(String num, String owner, String nameOfBank, String strDate) {
        BankAccountClient newAccount = new BankAccount(name, num, owner, new Date(strDate), nameOfBank);
        accountRepo.save(newAccount);
        accounts.add(newAccount);
    }

    public int getHashPin() {
        return hashPin;
    }

    public String getName() {
        return name;
    }

    public String seeBalance(int accountPos) {
        return "Баланс счётa: " + accounts.get(accountPos).getBalance().toString();
    }

    public TransactionLogClient getTransactionLog() {
        return transactionLog;
    }

    public BankAccountClient getBankAccount(int accountPos) {
        return accounts.get(accountPos);
    }

    public BankAccountClient getBankAccountOnNum(String num) {
        BankAccountClient searchAccount = null;
        for (var account : accounts) {
            if (account.getNumber().equals(num)) {
                searchAccount = account;
            }
        }
        return searchAccount;
    }

    public List<BankAccountClient> getBankAccounts() {
        return accounts;
    }

    public int howAccounts() {
        return accounts.size();
    }

    public void transferBetweenAccount(int accPosTo, int accPosFrom, BigDecimal amount) {
        accounts.get(accPosFrom).changeBalance(amount.negate());
        accounts.get(accPosTo).changeBalance(amount);
        BankTransactionClient newTransactionTo = new BankTransaction(amount, new Date(), "Me", "Transfer", 
            accounts.get(accPosTo).getNumber());
        BankTransactionClient newTransactionFrom = new BankTransaction(amount.negate(), new Date(), "Me", "Transfer", 
            accounts.get(accPosFrom).getNumber());
        transactionLog.addTransaction(newTransactionTo);
        transactionLog.addTransaction(newTransactionFrom);
    }

    public void disconnectRepo() {
        accountRepo.disconnect();
        transactionLog.disconnectRepo();
    }

    public String toString() {
        int pos = 1;
        String info = String.format("Бюджет %s, привязанные счета:\n", name);
        for (var account : accounts) {
            info += String.format("\t%d) %s\n", pos++, account.toString());
        }
        return info;
    }
}
