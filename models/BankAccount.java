package models;

import java.math.BigDecimal;
import java.util.Date;

// конкретный класс банковского счет, реализующий наш интерфейс
public class BankAccount implements BankAccountClient {
    private long number;
    private String owner;
    private Date validUntil;
    private String nameOfBank;
    private BigDecimal balance;

    BankAccount(long number, String owner, Date validUntil, String nameOfBank, BigDecimal balance) {
        this.number = number;
        this.owner = owner;
        this.validUntil = validUntil;
        this.nameOfBank = nameOfBank;
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public long getNumber() {
        return number;
    }

    public void changeBalance(BankTransactionClient transaction) {
        balance = balance.add(transaction.getAmount());
    }

    public String toString() {
        return String.format("Cчёт №%ld в банке %s, владелец: %s, действителен до %s, остаток на счету: %d", 
            number, nameOfBank, owner, validUntil, balance);
    }
}
