package com.example.restaurantreservationm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class BronzeMember implements DiscountInterface {
    private double discountPercent;

    public BronzeMember() {
        this.discountPercent = 0.1;
    }

    @Override
    public double calculateDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return discountPercent * totalPrice;
    }

    @Override
    public double calculateDiscount(Order order, int numItems) {
        double totalPrice = order.getTotalPrice();
        return (discountPercent * totalPrice) + (0.03 * numItems);
    }
    public  double applyDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return totalPrice * 0.96;
    }
}