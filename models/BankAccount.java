package models;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

// конкретный класс банковского счет, реализующий наш интерфейс
public class BankAccount implements BankAccountClient {
    private String budgetName;
    private String number;
    private String owner;
    private Date validUntil;
    private String nameOfBank;
    private BigDecimal balance;

    public BankAccount(String budgetName,String number, String owner, Date validUntil, String nameOfBank) {
        this.budgetName = budgetName;
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

    public String getBudgetName() {
        return budgetName;
    }

    public String getOwner() {
        return owner;
    }

    public Date getValidDate() {
        return validUntil;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void changeBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        return String.format("Cчёт №%s в банке %s, владелец: %s, действителен до %s, остаток на счету: %s", 
            number, nameOfBank, owner, formatter.format(validUntil), balance.toString());
    }
}
