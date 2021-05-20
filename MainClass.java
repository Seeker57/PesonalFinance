import models.BankAccount;
import models.BankAccountClient;
import models.TransactionLog;
import models.TransactionLogClient;
import services.GroupManagment;
import services.GroupManagmentClient;
import services.repositories.FileRepoGroup;
import services.repositories.FileRepoTransaction;

import java.math.BigDecimal;
import java.util.Date;

public class MainClass {
    
    public static void main(String[] args) {

        GroupManagmentClient grpManage = new GroupManagment(new FileRepoGroup("groups.txt"));
        TransactionLogClient log = new TransactionLog(grpManage, new FileRepoTransaction("transaction.txt"));

        BankAccountClient acc = new BankAccount(123, "pong", new Date("21 may 2021"), "sber", new BigDecimal(0));
        System.out.println(acc.toString());

        log.createTransaction(acc);
        System.out.println(acc.toString() + "\n");
        log.createTransaction(acc);
        System.out.println(acc.toString() + "\n");
        System.out.println(log.toString() + "\n");
        log.deleteTransaction();
        System.out.println(acc.toString() + "\n");
        System.out.println(log.toString() + "\n");
    }
}
