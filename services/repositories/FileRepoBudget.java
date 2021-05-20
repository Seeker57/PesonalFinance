package services.repositories;

import java.util.List;
import models.BudgetClient;

// файловое хранилище для бюджетов
public class FileRepoBudget extends FileRepository<BudgetClient> {

    public FileRepoBudget(String fileName) {
        super(fileName);
    }

    @Override
    public boolean save(BudgetClient budget) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
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