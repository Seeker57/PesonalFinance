package services.transactrionQuery;

import java.util.Date;
import models.BankTransactionClient;

// запрос на поиск по дате
public class DateQuery implements TransactionQuery {
    private Date date;

    public DateQuery(Date date) {
        this.date = date;
    }

    public boolean match(BankTransactionClient transaction) {
        return transaction.getDate().equals(date);
    }
}
