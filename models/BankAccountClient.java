package models;

import java.math.BigDecimal;

// интерфейс Банковского счёта
public interface BankAccountClient {
    BigDecimal getBalance();
    String getNumber();
    void changeBalance(BigDecimal amount);
    String toString();
}
