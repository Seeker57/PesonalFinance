package services.repositories;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import models.BudgetClient;

// файловое хранилище для бюджетов
public class FileRepoBudget extends FileRepository<BudgetClient> {

    public FileRepoBudget(String fileName) {
        super(fileName);
    }

    @Override
    public void save(BudgetClient budget) {
        String saveInfo = String.format("{\n\tBudget: %s,\n\thashPin: %d\n}\n", budget.getName(), budget.getHashPin());
        writeInFile(saveInfo);
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public BudgetClient get(int id) {
        return null;
    }

    @Override
    public List<BudgetClient> getAll() {
        return null;
    }
}