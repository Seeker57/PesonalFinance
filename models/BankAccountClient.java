package models;

import java.math.BigDecimal;

// интерфейс Банковского счёта
public interface BankAccountClient {
    BigDecimal getBalance();
    long getNumber();
    void changeBalance(BankTransactionClient transaction);
    String toString();
}
