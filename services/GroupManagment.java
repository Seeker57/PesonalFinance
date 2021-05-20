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

    public void createGroup (GroupCreator creator) {
        Group newGroup = creator.createGroup();
        groupList.add(newGroup);
        groupRepo.save(newGroup);
    }

    public String toString() {
        String info = "Доступные категории:";
        info += groupList.stream().map(Group::toString)
            .reduce("", (x, y) -> String.format("%s\n - %s", x, y));
        return info;
    }
}
