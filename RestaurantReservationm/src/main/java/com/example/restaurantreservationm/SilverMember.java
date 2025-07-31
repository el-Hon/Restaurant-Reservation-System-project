package com.example.restaurantreservationm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class SilverMember implements DiscountInterface {
    private double discountPercent;

    public SilverMember() {
        this.discountPercent = 0.15;
    }


    public  double calculateDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return discountPercent * totalPrice;
    }


    public double calculateDiscount(Order order, int numItems) {
        double totalPrice = order.getTotalPrice();
        return (discountPercent * totalPrice) + (0.05 * numItems);
    }
    public double applyDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return totalPrice * 0.94;
    }
}