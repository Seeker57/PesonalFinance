package services.transactrionQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import models.BankTransactionClient;

// запрос на поиск по дате
public class DateQuery implements TransactionQuery {
    private Date date;

    public DateQuery(Date date) {
        this.date = date;
    }

    public boolean match(BankTransactionClient transaction) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY", Locale.ENGLISH);
        String formatDate = formatter.format(date);
        return formatter.format(transaction.getDate()).equals(formatDate);
    }
}
