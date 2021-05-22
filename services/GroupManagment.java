package services;

import java.util.List;
import models.Group;
import services.repositories.Repository;

public class GroupManagment implements GroupManagmentClient {
    private List<Group> groupList;
    private Repository<Group> groupRepo;

    public GroupManagment(Repository<Group> groupRepo) {
        this.groupRepo = groupRepo;
        groupRepo.connect();
        this.groupList = groupRepo.getAll();
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
