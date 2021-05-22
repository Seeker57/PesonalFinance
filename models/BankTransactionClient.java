package models;

import java.math.BigDecimal;
import java.util.Date;

// интерфейс Банковской транзакции
public interface BankTransactionClient {
    public BigDecimal getAmount();
    public Date getDate();
    public String getCounterparty();
    public String getGroup();
    public String getAccount();
    public String toString();
}
