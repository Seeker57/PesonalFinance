package services.transactrionQuery;

import models.BankTransactionClient;

// запрос на поиск по контрагенту 
public class CounterpartyQuery implements TransactionQuery {
    private String counterparty;

    public CounterpartyQuery(String counterparty) {
        this.counterparty = counterparty;
    }

    public boolean match(BankTransactionClient transaction) {
        return transaction.getCounterparty().equals(counterparty);
    }
}
