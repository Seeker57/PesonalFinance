package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.repositories.Repository;

// конкретный класс бюджета, реализующий наш интерфейс
public class Budget implements BudgetClient {
    private String name;
    private int hashPin;
    private List<BankAccountClient> accounts;
    private TransactionLogClient transactionLog;
    private Repository accountRepo;

    public Budget(String name, int hashPin, TransactionLogClient transactionLog, Repository accountRepo) {
        this.name = name;
        this.hashPin = hashPin;
        this.accounts = new ArrayList<>();
        this.transactionLog = transactionLog;
        this.accountRepo = accountRepo;
    }

    public void addAccount() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите номер счёта: ");
            String num = reader.readLine();
            System.out.print("Введите ФИО владельца: ");
            String owner = reader.readLine();
            System.out.print("Введите название банка: ");
            String nameOfBank = reader.readLine();
            System.out.print("Введите дату, до которой действителен счёт: ");
            String strDate = reader.readLine();

            BankAccountClient newAccount = new BankAccount(num, owner, new Date(strDate), nameOfBank);
            accountRepo.save(newAccount);
            accounts.add(newAccount);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public int getHashPin() {
        return hashPin;
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
        accounts.get(accPosTo).changeBalance(amount);
    }

    public String toString() {
        String info = String.format("Бюджет %s, привязанные счета:\n", name);
        info += accounts.stream().map(BankAccountClient::toString)
            .reduce("", (x, y) -> String.format("%s\n - %s", x, y));
        return info;
    }
}
