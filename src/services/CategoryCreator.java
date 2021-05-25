package services;

import models.Group;
import models.Category;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// класс для создания категорий
public class CategoryCreator extends GroupCreator {
    @Override
    public Group factoryMethod() throws IOException {
        String name = "", description = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите название категории: ");
        name = reader.readLine();
        System.out.print("Введите описание категории: ");
        description = reader.readLine();

        return new Category(name, description);
    }
}
