package services.transactionQuery;

import models.Group;
import models.BankTransactionClient;

// запрос на поиск по группе
public class GroupQuery implements TransactionQuery {
    private Group group;
    
    public GroupQuery(Group group) {
        this.group = group;
    }

    public boolean match(BankTransactionClient transaction) {
        return transaction.getGroup().equals(group.getName());
    }
}
