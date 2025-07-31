package com.example.restaurantreservationm;

import java.util.List;

class Order {
    public double calculateTotalPrice;
    private List<MenuItem> items;
    private Table table;
    private double totalPrice;

    public Order(List<MenuItem> items, Table table) {
        this.items = items;
        this.table = table;
        calculateTotalPrice();
    }

    public void addMenuItem(MenuItem item) {
        items.add(item);
        calculateTotalPrice();
    }

    public void removeMenuItem(MenuItem item) {
        items.remove(item);
        calculateTotalPrice();
    }
    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private void calculateTotalPrice() {
        totalPrice = 0.0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
    }

    public Table getTable() {
        return table;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
