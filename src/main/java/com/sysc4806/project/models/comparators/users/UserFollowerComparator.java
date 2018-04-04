package com.sysc4806.project.models.comparators;

import com.sysc4806.project.models.UserEntity;

import java.util.Comparator;

public class UserFollowerComparator implements Comparator<UserEntity>{

    @Override
    public int compare(UserEntity o1, UserEntity o2) {

        if(o1 == o2)
            return 0;

        if(o1 == null)
            return -1;

        if(o2 == null)
            return 1;

        // The larger the number of followers, the higher the compared user will rank
        return o1.getFollowers().size() - o2.getFollowers().size();

    }
}
