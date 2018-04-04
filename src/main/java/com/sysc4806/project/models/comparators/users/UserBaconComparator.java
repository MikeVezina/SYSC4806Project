package com.sysc4806.project.models.comparators.users;

import com.sysc4806.project.controllers.ControllerUtils;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class UserBaconComparator implements Comparator<UserEntity>{

    @Autowired
    private ControllerUtils controllerUtils;

    @Override
    public int compare(UserEntity o1, UserEntity o2) {
        UserEntity curUser = controllerUtils.getCurrentUser();

        if(o1 == o2)
            return 0;

        if(o1 == null)
            return -1;

        if(o2 == null)
            return 1;

        int baconNumber1 = curUser.findBaconNumber(o1);
        int baconNumber2 = curUser.findBaconNumber(o2);

        if(baconNumber1 == baconNumber2)
            return 0;

        if(baconNumber1 == -1)
            return -1;

        if(baconNumber2 == -1)
            return 1;

        return baconNumber2 - baconNumber1;

    }

    @Override
    public String toString()
    {
        return "Bacon Number";
    }
}
