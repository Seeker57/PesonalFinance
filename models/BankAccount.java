package models;

import java.math.BigDecimal;
import java.util.Date;

// конкретный класс банковского счет, реализующий наш интерфейс
public class BankAccount implements BankAccountClient {
    private String number;
    private String owner;
    private Date validUntil;
    private String nameOfBank;
    private BigDecimal balance;

    public BankAccount(String number, String owner, Date validUntil, String nameOfBank) {
        this.number = number;
        this.owner = owner;
        this.validUntil = validUntil;
        this.nameOfBank = nameOfBank;
        this.balance = new BigDecimal(0);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getNumber() {
        return number;
    }

    public void changeBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public String toString() {
        return String.format("Cчёт №%s в банке %s, владелец: %s, действителен до %s, остаток на счету: %s", 
            number, nameOfBank, owner, validUntil, balance.toString());
    }
}
