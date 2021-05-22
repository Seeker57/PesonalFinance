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
        this.transactionLog = transactionLog;
        this.accountRepo = accountRepo;
        accountRepo.connect();
        this.accounts = accountRepo.getAll();
        transactionLog.setBalance(this);
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

            BankAccountClient newAccount = new BankAccount(name, num, owner, new Date(strDate), nameOfBank);
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

    public void transferBetweenAccount() {
        System.out.println(this.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите счет снятия: ");
            int accPosFrom = Integer.parseInt(reader.readLine());
            System.out.print("Введите счет пополнения: ");
            int accPosTo = Integer.parseInt(reader.readLine());
            System.out.print("Введите сумму: ");
            BigDecimal amount = new BigDecimal(Integer.parseInt(reader.readLine()));

            accounts.get(accPosFrom).changeBalance(amount.negate());
            accounts.get(accPosTo).changeBalance(amount);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
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
