package services;

import models.BudgetClient;

public interface PersonalFinanceClient {
    public void createBudget();
    public void deleteBudget();
    public BudgetClient chooseBudget();
    public String toString();
}
