package services.transactionQuery;

import models.BankTransactionClient;

// абстрактный класс для формирования сложного запроса в виде логических операций
public abstract class LogicalOperation implements TransactionQuery {
    private TransactionQuery firstQuery;
    private TransactionQuery secondQuery;

    public LogicalOperation(TransactionQuery first, TransactionQuery second) {
        this.firstQuery = first;
        this.secondQuery = second;
    }

    public TransactionQuery getFirstQuery() {
        return firstQuery;
    }

    public TransactionQuery getSecondQuery() {
        return secondQuery;
    }

    public abstract boolean match(BankTransactionClient transaction);
}
