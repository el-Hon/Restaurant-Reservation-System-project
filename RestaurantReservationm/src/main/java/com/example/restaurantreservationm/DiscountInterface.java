package com.example.restaurantreservationm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DiscountInterface {
    double calculateDiscount(Order order);

    double calculateDiscount(Order order, int numItems);

}
