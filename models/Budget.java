package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import services.repositories.Repository;

// конкретный класс бюджета, реализующий наш интерфейс
public class Budget implements BudgetClient {
    private String name;
    private int pin;
    private List<BankAccountClient> accounts;
    private TransactionLogClient transactionLog;
    private Repository accountRepo;

    public Budget(String name, int pin, TransactionLogClient transactionLog, Repository accountRepo) {
        this.name = name;
        this.pin = pin;
        this.accounts = new ArrayList<>();
        this.transactionLog = transactionLog;
        this.accountRepo = accountRepo;
    }

    public boolean addAccount() {
        return true;
    }

    public void seeBalance(int accountPos) {
        System.out.println("Баланс счёт: " + accounts.get(accountPos).getBalance().toString());
    }

    public TransactionLogClient getTransactionLog() {
        return transactionLog;
    }

    public BankAccountClient getBankAccount(int accountPos) {
        return accounts.get(accountPos);
    }

    public void transferBetweenAccount(BigDecimal amount, int accPosFrom, int accPosTo) {
        accounts.get(accPosFrom).changeBalance(amount.negate());
        accounts.get(accPosTo).changeBalance(amount.negate());
    }

    public String toString() {
        String info = String.format("Бюджет %s, привязанные счета:\n", name);
        info += accounts.stream().map(BankAccountClient::toString)
            .reduce("", (x, y) -> String.format("%s\n - %s", x, y));
        return info;
    }
}
