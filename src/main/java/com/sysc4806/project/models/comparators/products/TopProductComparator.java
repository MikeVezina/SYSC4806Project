package com.sysc4806.project.models.comparators.products;

import com.sysc4806.project.controllers.ControllerUtils;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;

public class TopProductComparator implements Comparator<Product>{

    @Autowired
    private ControllerUtils controllerUtils;

    @Override
    public int compare(Product o1, Product o2) {

        if(o1 == o2)
            return 0;

        if(o1 == null)
            return -1;

        if(o2 == null)
            return 1;

        UserEntity curUser = controllerUtils.getCurrentUser();

        return o1.getAverageRating() - o2.getAverageRating();
        //return o1.getReviews().stream().mapToDouble(review -> review.getAuthor().calculateJaccardDistance(curUser)).sum() - o2.getReviews().size();

    }

    @Override
    public String toString()
    {
        return "Recommended By Us";
    }
}
