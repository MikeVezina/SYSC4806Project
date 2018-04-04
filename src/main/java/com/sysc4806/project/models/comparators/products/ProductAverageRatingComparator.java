package com.sysc4806.project.models.comparators.products;

import com.sysc4806.project.models.Product;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ProductAverageReviewComparator implements Comparator<Product>{

    @Override
    public int compare(Product o1, Product o2) {

        if(o1 == o2)
            return 0;

        if(o1 == null)
            return -1;

        if(o2 == null)
            return 1;

        // The larger the number of reviews, the higher the compared product will rank
        return o1.getAverageRating() - o2.getAverageRating();

    }

    @Override
    public String toString()
    {
        return "Number of Reviews";
    }
}
