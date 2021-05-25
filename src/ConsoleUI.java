import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import models.BudgetClient;
import models.BankTransactionClient;
import models.TransactionLogClient;
import services.PersonalFinanceClient;
import services.transactionQuery.And;
import services.transactionQuery.CounterpartyQuery;
import services.transactionQuery.DateQuery;
import services.transactionQuery.GroupQuery;
import services.transactionQuery.TransactionQuery;

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
        int budgetPos = -1;
        String pin = "";

        switch (choice) {
            case (1):
                clearScreen();
                System.out.print("Введите название бюджета: ");
                String name = reader.readLine();
                System.out.print("Введите 4-значный пин-код: ");
                pin = reader.readLine();

                finance.createBudget(name, pin);
                clearScreen();
                mainMenu();
                break;
            case (2):
                clearScreen();
                System.out.println(finance);
                System.out.print("Введите id бюджета, который хотите удалить или 0 для выхода: ");
                budgetPos = Integer.parseInt(reader.readLine()) - 1;
                System.out.print("Введите 4-значный пин-код: ");
                pin = reader.readLine();

                BudgetClient budget = finance.chooseBudget(budgetPos, pin);
                if (budget != null) {
                    budgetMenu(budget);
                } else {
                    System.out.println("\nНевверный номер бюджета или пин-код!");
                }
                mainMenu();
                break;
            case (3):
                clearScreen();
                System.out.println(finance);
                System.out.print("Введите id бюджета, который хотите удалить или 0 для выхода: ");
                budgetPos = Integer.parseInt(reader.readLine()) - 1;
                System.out.print("Введите 4-значный пин-код: ");
                pin = reader.readLine();
                
                finance.deleteBudget(budgetPos, pin);
                clearScreen();
                mainMenu();
                break;
            case (4):
                System.exit(0);
                break;
            default:
                System.out.println("Неверный ввод!");
                clearScreen();
                mainMenu();
        }
    }

    public void budgetMenu(BudgetClient budget) throws IOException {
        clearScreen();
        String budgetMenu = budget.toString();
        budgetMenu += "\n1) Добавить счёт\n2) Посмотреть журнал транзакций\n" + 
            "3) Перевод между счетами\n4) Вернуться в меню бюджетов";
        System.out.println(budgetMenu);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите цифру: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case (1):
                clearScreen();
                System.out.print("Введите номер счёта: ");
                String num = reader.readLine();
                System.out.print("Введите ФИО владельца: ");
                String owner = reader.readLine();
                System.out.print("Введите название банка: ");
                String nameOfBank = reader.readLine();
                System.out.print("Введите дату, до которой действителен счёт: ");
                String strDate = reader.readLine();

                budget.addAccount(num, owner, nameOfBank, strDate);
                budgetMenu(budget);
                break;
            case (2):
                clearScreen();
                transactionLogMenu(budget.getTransactionLog(), budget);
                budgetMenu(budget);
                break;
            case (3):
                clearScreen();
                System.out.println(budget);
                System.out.print("Введите счет снятия: ");
                int accPosFrom = Integer.parseInt(reader.readLine()) - 1;
                System.out.print("Введите счет пополнения: ");
                int accPosTo = Integer.parseInt(reader.readLine()) - 1;
                System.out.print("Введите сумму: ");
                BigDecimal amount = new BigDecimal(Double.parseDouble(reader.readLine()));

                budget.transferBetweenAccount(accPosTo, accPosFrom, amount);
                budgetMenu(budget);
                break;
            case (4):
                clearScreen();
                mainMenu();
                break;
            default:
                System.out.println("Неверный ввод!");
                budgetMenu(budget);
        }
    }

    public void transactionLogMenu(TransactionLogClient log, BudgetClient budget) throws IOException {
        String logMenu = "\n1) Посмотреть все транзакции\n2) Добавить транзакцию\n" +
            "3) Удалить транзакцию\n4) Посмотреть расходы\n5) Посмотреть доходы\n6) Поиск транзакций\n7) Вернуться к меню бюджета";
        System.out.println(logMenu);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите цифру: ");
        int choice = Integer.parseInt(reader.readLine());

        switch (choice) {
            case (1):
                clearScreen();
                System.out.println(log);
                transactionLogMenu(log, budget);
                break;
            case (2):
                clearScreen();
                System.out.println(budget);
                System.out.print("Выберете счёт: "); 
                int accountPos = Integer.parseInt(reader.readLine()) - 1;

                System.out.print("Введите сумму: ");
                double amount = Double.parseDouble(reader.readLine());
                System.out.print("Введите дату и время: ");
                String strDate = reader.readLine();
                System.out.print("Введите контрагента: ");
                String counterparty = reader.readLine();

                System.out.println(log.getGroupManagment().toString());
                System.out.print("Введите номер категории или 0, чтобы создать новую: ");
                int numGroup = Integer.parseInt(reader.readLine());
                log.createTransaction(budget.getBankAccount(accountPos), amount, strDate, counterparty, numGroup);
                clearScreen();
                transactionLogMenu(log, budget);
                break;
            case (3):
                clearScreen();
                System.out.println(log);
                System.out.print("Введите номер транзакции для удаления или 0 для выхода: ");
                int transactionPos = Integer.parseInt(reader.readLine()) - 1;

                log.deleteTransaction(budget.getBankAccounts(), transactionPos);
                clearScreen();
                transactionLogMenu(log, budget);
                break;
            case (5):
                clearScreen();
                List<BankTransactionClient> incomes = log.getIncomes();
                System.out.println("\nРезультаты поиска: ");
                for (var transaction : incomes) {
                    System.out.print(transaction + "\n");
                }
                transactionLogMenu(log, budget);
                break;
            case (4):
                clearScreen();
                List<BankTransactionClient> expenses = log.getExpences();
                System.out.println("\nРезультаты поиска: ");
                for (var transaction : expenses) {
                    System.out.print(transaction + "\n");
                }
                transactionLogMenu(log, budget);
                break;
            case (6):
                clearScreen();
                findTransactionMenu(log);
                transactionLogMenu(log, budget);
                break;
            case (7):
                budgetMenu(budget);
                break;
            default:
                System.out.println("Неверный ввод!\n");
                transactionLogMenu(log, budget);
        }
    }

    @Deprecated
    public void findTransactionMenu(TransactionLogClient log) throws IOException {
        clearScreen();
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
        System.out.println("\nРезультаты поиска: ");
        for (var transaction : resultOfSearch) {
            System.out.print(transaction + "\n");
        }

    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
