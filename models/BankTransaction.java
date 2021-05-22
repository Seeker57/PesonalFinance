package models;

import java.math.BigDecimal;
import java.util.Date;

// конкретный класс Банковской транзакции, реализующий наш интерфейс
public class BankTransaction implements BankTransactionClient {
    private BigDecimal amount; // сумма
    private Date date;
    private String counterparty; // контрагент - одна из сторон договора о транзакции 
    private String group;    
    private String account;

    public BankTransaction(BigDecimal amount, Date date, String counterparty, String group, String account) {
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

    public String getGroup() {
        return group;
    }

    public String getAccount() {
        return account;
    }

    public String toString() {
        return String.format("Транзакция на сумму: %+d, совершена: %s со счёта №%s, контрагент: %s, категория: %s", 
            amount.intValue(), date, account, counterparty, group);
    }
}
