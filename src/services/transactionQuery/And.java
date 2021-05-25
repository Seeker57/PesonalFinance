package services.transactionQuery;

import models.BankTransactionClient;

// класс для формирования запросов через "И"
public class And extends LogicalOperation {
    public And(TransactionQuery first, TransactionQuery second) {
        super(first, second);
    }

    @Override
    public boolean match(BankTransactionClient transaction) {
        return getFirstQuery().match(transaction) && getSecondQuery().match(transaction);
    }
}
