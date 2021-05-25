package services;

import java.io.IOException;

import models.Group;

// абстрактный класс, ответственный за создание Группы
public abstract class GroupCreator {
    protected abstract Group factoryMethod() throws IOException;
    public Group createGroup() throws IOException {
        return this.factoryMethod();
    }
}
