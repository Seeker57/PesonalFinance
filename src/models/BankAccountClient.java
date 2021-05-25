package models;

import java.math.BigDecimal;
import java.util.Date;

// интерфейс Банковского счёта
public interface BankAccountClient {
    BigDecimal getBalance();
    String getNumber();
    String getBudgetName();
    String getOwner();
    String getNameOfBank();
    Date getValidDate();
    void changeBalance(BigDecimal amount);
    String toString();
}
