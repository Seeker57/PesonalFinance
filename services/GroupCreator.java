package services;

import models.Group;

// абстрактный класс, ответственный за создание Группы
public abstract class GroupCreator {
    protected abstract Group factoryMethod();
    public Group createGroup() {
        return this.factoryMethod();
    }
}
