package com.sysc4806.project.models.comparators.users;

import com.sysc4806.project.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class UserFollowingComparator implements Comparator<UserEntity>{

    @Override
    public int compare(UserEntity o1, UserEntity o2) {

        if(o1 == o2)
            return 0;

        if(o1 == null)
            return -1;

        if(o2 == null)
            return 1;

        // The larger the number of following, the higher the compared user will rank
        return o1.getFollowing().size() - o2.getFollowing().size();

    }

    @Override
    public String toString()
    {
        return "Following";
    }
}
