package services.repositories;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.BankTransaction;
import models.BankTransactionClient;

// файловое хранилище для транзакций
public class FileRepoTransaction extends FileRepository<BankTransactionClient> {

    public FileRepoTransaction(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BankTransactionClient transaction) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YYYY HH:mm:ss", Locale.ENGLISH);
        String saveInfo = String.format("{\n\tTransaction on account: %s,\n\tamount: %s,\n", transaction.getAccount(),
            transaction.getAmount().toString()) + String.format("\tdate: %s,\n\tcounterparty: %s,\n", 
            formatter.format(transaction.getDate()), transaction.getCounterparty()) + 
            String.format("\tgroup: %s \n}\n", transaction.getGroup());
        writeInFile(saveInfo);
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public BankTransactionClient get(int id) {
        return null;
    }

    @Override
    public List<BankTransactionClient> getAll() {
        try {
            RandomAccessFile cursor = getCursor();
            cursor.seek(0);
            List<BankTransactionClient> transactions = new ArrayList<>();

            while (cursor.getFilePointer() != cursor.length()) {
                String strFile = cursor.readLine();

                if (strFile.equals("{")) {
                    String number = cursor.readLine();
                    number = number.substring(number.indexOf(':') + 2, number.length() - 1);
                    String amount = cursor.readLine();
                    amount = amount.substring(amount.indexOf(':') + 2, amount.length() - 1);
                    String date = cursor.readLine();
                    date = date.substring(date.indexOf(':') + 2, date.length() - 1);
                    String counterparty = cursor.readLine();
                    counterparty = counterparty.substring(counterparty.indexOf(':') + 2, counterparty.length() - 1);
                    String group = cursor.readLine();
                    group = group.substring(group.indexOf(':') + 2, group.length() - 1);

                    BankTransactionClient newTransaction = new BankTransaction(BigDecimal.valueOf(Double.parseDouble(amount)), 
                        new Date(date), counterparty, group, number);
                    transactions.add(newTransaction);
                }
                cursor.readLine();
            }
            return transactions;
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}