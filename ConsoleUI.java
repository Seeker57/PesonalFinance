import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import models.BudgetClient;
import models.BankTransactionClient;
import models.TransactionLogClient;
import services.PersonalFinanceClient;
import services.transactrionQuery.And;
import services.transactrionQuery.CounterpartyQuery;
import services.transactrionQuery.DateQuery;
import services.transactrionQuery.GroupQuery;
import services.transactrionQuery.TransactionQuery;

public class ConsoleUI {
    private PersonalFinanceClient finance;    

    public ConsoleUI(PersonalFinanceClient finance) {
        this.finance = finance;
    }

    public void mainMenu() throws IOException {
        String menu = finance.toString();
        menu += "\n1) Cоздать бюджет\n2) Выбрать бюджет\n3) Удалить бюджет\n4) Выход";
        System.out.println(menu);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите цифру: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case (1):
                finance.createBudget();
                mainMenu();
                break;
            case (2):
                BudgetClient budget = finance.chooseBudget();
                if (budget != null) {
                    budgetMenu(budget);
                }
                mainMenu();
                break;
            case (3):
                finance.deleteBudget();
                mainMenu();
                break;
            case (4):
                System.exit(0);
                break;
            default:
                System.out.println("Неверный ввод!");
                mainMenu();
        }
    }

    public void budgetMenu(BudgetClient budget) throws IOException {
        String budgetMenu = budget.toString();
        budgetMenu += "\n1) Добавить счёт\n2) Посмотреть журнал транзакций\n" + 
            "3) Перевод между счетами\n4) Вернуться в меню бюджетов";
        System.out.println(budgetMenu);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите цифру: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case (1):
                budget.addAccount();
                budgetMenu(budget);
                break;
            case (2):
                transactionLogMenu(budget.getTransactionLog(), budget);
                budgetMenu(budget);
                break;
            case (3):
                budget.transferBetweenAccount();
                budgetMenu(budget);
                break;
            case (4):
                mainMenu();
                break;
            default:
                System.out.println("Неверный ввод!");
                budgetMenu(budget);
        }
    }

    public void transactionLogMenu(TransactionLogClient log, BudgetClient budget) throws IOException {
        String logMenu = "\n1) Посмотреть все транзакции\n2) Добавить транзакцию\n" +
            "3) Удалить транзакцию\n4) Поиск транзакций\n5) Вернуться к меню бюдета";
        System.out.println(logMenu);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите цифру: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case (1):
                System.out.println(log);
                transactionLogMenu(log, budget);
                break;
            case (2):
                System.out.println(budget);
                System.out.print("Выберете счёт: "); 
                int accountPos = Integer.parseInt(reader.readLine()) - 1;
                log.createTransaction(budget.getBankAccount(accountPos));
                transactionLogMenu(log, budget);
                break;
            case (3):
                log.deleteTransaction();
                transactionLogMenu(log, budget);
                break;
            case 4:
                findTransactionMenu(log);
                transactionLogMenu(log, budget);
                break;
            case (5):
                budgetMenu(budget);
                break;
            default:
                System.out.println("Неверный ввод!\n");
                transactionLogMenu(log, budget);
        }
    }

    public void findTransactionMenu(TransactionLogClient log) throws IOException {
        TransactionQuery query = null;
        boolean stopCreateQuery = false;
        String queryChoice = "\n1) Дата\n2) Контрагент\n3) Группа\n4) Закончить формирование запроса";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!stopCreateQuery) {
            System.out.print(queryChoice + "\nВведите цифру: ");
            int choice = Integer.parseInt(reader.readLine());
            TransactionQuery newQuery = null;

            switch (choice) {
                case (1):
                    System.out.print("Введите дату: ");
                    String strDate = reader.readLine();
                    newQuery = new DateQuery(new Date(strDate));
                    break;
                case (2):
                    System.out.print("Введите контрагента: ");
                    String counterparty = reader.readLine();
                    newQuery = new CounterpartyQuery(counterparty);
                    break;
                case (3):
                    System.out.println(log.getGroupManagment().toString());
                    System.out.print("Выберете группу: ");
                    int groupPos = Integer.parseInt(reader.readLine()) - 1;
                    newQuery = new GroupQuery(log.getGroupManagment().getGroup(groupPos));
                    break;
                case (4):
                    stopCreateQuery = true;
                    break;
                default:
                    System.out.println("Неверный ввод!\n");
            }

            if (query != null) {
                if (newQuery != null) {
                    query = new And(query, newQuery);
                }
            } else {
                query = newQuery;
            }

        }

        List<BankTransactionClient> resultOfSearch = log.find(query);
        System.out.println("Результаты поиска: ");
        for (var transaction : resultOfSearch) {
            System.out.println(transaction + "\n");
        }
    }
}
