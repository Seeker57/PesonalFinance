package services;

import models.Group;

public interface GroupManagmentClient {
    public Group createGroup(GroupCreator creator);
    public Group getGroup(int pos);
    public String toString();
}
