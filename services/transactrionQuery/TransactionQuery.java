package services.transactrionQuery;

import models.BankTransactionClient;

// интерфейс для создание запроса на поиск транзакции
public interface TransactionQuery {
    public boolean match(BankTransactionClient transaction);
}
