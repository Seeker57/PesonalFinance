package services;

import java.io.IOException;

import models.Group;

public interface GroupManagmentClient {
    public Group createGroup(GroupCreator creator) throws IOException;
    public Group getGroup(int pos);
    public String toString();
}
