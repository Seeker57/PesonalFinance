package services;

import models.Group;
import models.Category;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// класс для создания категорий
public class CategoryCreator extends GroupCreator {
    @Override
    public Group factoryMethod() {
        String name = "", description = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Введите название категории: ");
            name = reader.readLine();
            System.out.print("Введите описание категории: ");
            description = reader.readLine();
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return new Category(name, description);
    }
}
