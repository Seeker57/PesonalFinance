package services;

import java.io.IOException;

import models.BudgetClient;

public interface PersonalFinanceClient {
    public void createBudget(String name, String pin) throws IOException;
    public void deleteBudget(int budgetPos, String pin);
    public BudgetClient chooseBudget(int budgetPos, String pin);
    public String toString();
}
