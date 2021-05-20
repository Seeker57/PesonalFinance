package services;

import java.util.ArrayList;
import java.util.List;
import models.BudgetClient;
import services.repositories.Repository;

// класс Персональные финансы, хранящий бюджеты
public class PersonalFinance {
    private List<BudgetClient> budgets;
    private Repository budgetRepo;

    public PersonalFinance(Repository budgetRepo) {
        this.budgets = new ArrayList<>();
        this.budgetRepo =  budgetRepo;
    }

    public boolean createBudget() {
        return true;
    }

    public boolean deleteBudget() {
        return true;
    }

    public BudgetClient getBudget(int budgetPos) {
        return budgets.get(budgetPos);
    }

    public String toString() {
        String info = "Бюджеты:\n";
        info += budgets.stream().map(BudgetClient::toString)
            .reduce("", (x, y) -> String.format("%s\n - %s", x, y));
        return info;
    }
}
