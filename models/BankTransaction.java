package models;

import java.math.BigDecimal;
import java.util.Date;

// конкретный класс Банковской транзакции, реализующий наш интерфейс
public class BankTransaction implements BankTransactionClient {
    private BigDecimal amount; // сумма
    private Date date;
    private String counterparty; // контрагент - одна из сторон договора о транзакции 
    private Group group;    
    private BankAccountClient account;

    public BankTransaction(BigDecimal amount, Date date, String counterparty, Group group, BankAccountClient account) {
        this.amount = amount;
        this.date = date;
        this.counterparty = counterparty;
        this.group = group;
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
   
    public String getCounterparty() {
        return counterparty;
    }

    public Group getGroup() {
        return group;
    }

    public BankAccountClient getAccount() {
        return account;
    }

    public String toString() {
        return String.format("Транзакция на сумму: %+d, совершена: %s, контрагент: %s, категория: %s", 
            amount.intValue(), date, counterparty, group.getName());
    }
}
