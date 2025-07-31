package com.example.restaurantreservationm;

class GoldMember implements DiscountInterface {
    private double discountPercent;

    public GoldMember() {
        this.discountPercent = 0.2;
    }


    public double calculateDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return discountPercent * totalPrice;
    }


    public double calculateDiscount(Order order, int numItems) {
        double totalPrice = order.getTotalPrice();
        return (discountPercent * totalPrice) + (0.1 * numItems);
    }
    public double applyDiscount(Order order) {
        double totalPrice = order.getTotalPrice();
        return totalPrice * 0.9;
    }
}
