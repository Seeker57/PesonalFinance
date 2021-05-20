package services;

import models.Group;
import models.Category;
import java.util.Scanner;

// класс для создания категорий
public class CategoryCreator extends GroupCreator {
    @Override
    public Group factoryMethod() {
        String name, description;
        Scanner scanner = new Scanner(System.in);

        name = scanner.nextLine();
        description = scanner.nextLine();

        scanner.close();
        return new Category(name, description);
    }
}
