package services;

import java.util.ArrayList;
import java.util.List;
import models.Group;
import services.repositories.Repository;

public class GroupManagment implements GroupManagmentClient {
    private List<Group> groupList;
    private Repository groupRepo;

    public GroupManagment(Repository groupRepo) {
        this.groupList = new ArrayList<>();
        this.groupRepo = groupRepo;
    }

    public Group createGroup (GroupCreator creator) {
        Group newGroup = creator.createGroup();
        groupList.add(newGroup);
        groupRepo.save(newGroup);
        return newGroup;
    }

    public Group getGroup(int pos) {
        return groupList.get(pos);
    }

    public String toString() {
        int pos = 1;
        String info = "\nДоступные категории:\n";
        for (var group : groupList) {
            info += String.format("%d) %s\n", pos++, group.toString());
        }
        return info;
    }
}
