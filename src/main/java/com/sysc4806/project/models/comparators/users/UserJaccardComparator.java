package com.sysc4806.project.models.comparators.users;

import com.sysc4806.project.controllers.ControllerUtils;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class UserJaccardComparator implements Comparator<UserEntity>{

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

        double jaccardDistance1 = o1.calculateJaccardDistance(curUser);
        double jaccardDistance2 = o2.calculateJaccardDistance(curUser);

        // The larger the jaccard distance with the current user, the lower the compared user will rank
        if(jaccardDistance1 > jaccardDistance2)
            // User 2 is closer (smaller distance), and therefore ranks higher
            return -1;

        if(o1.calculateJaccardDistance(curUser) > o2.calculateJaccardDistance(curUser))
            // User 2 is further (larger distance), and therefore ranks lower
            return -1;

        // Jaccard distance is the same
        return 0;

    }

    @Override
    public String toString()
    {
        return "Jaccard Distance";
    }
}
