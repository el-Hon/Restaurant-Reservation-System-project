package com.example.restaurantreservationm;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class RestaurantReservation {
    private static RestaurantReservation instance;

    private String restaurantName;
    private List<Table> tables;
    private List<MenuItem> menu;
    private List<Order> orders;
    private DiscountInterface discountInterface;

    private RestaurantReservation(String name) {
        restaurantName = name;
        tables = new ArrayList<>();
        menu = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public static RestaurantReservation getInstance() {
        if (instance == null) {
            instance = new RestaurantReservation("My Restaurant");
        }
        return instance;
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void createOrder(Order order) {
        orders.add(order);
    }
    public void removeOrder(MenuItem order) {
        orders.remove(order);
    }

    public List<Table> getAvailableTables(Date date, int partySize) {
        List<Table> availableTables = new ArrayList<>();
        for (Table table : tables) {
            if (table.isAvailable() && table.getCapacity() >= partySize) {
                availableTables.add(table);
            }
        }
        return availableTables;
    }

    public List<Table> getAvailableTables(int partySize) {
        return getAvailableTables(new Date(), partySize);
    }

    public List<MenuItem> getMenuItems() {
        return menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public DiscountInterface getDiscountInterface() {
        return discountInterface;
    }

    public void setDiscountInterface(DiscountInterface discountInterface) {
        this.discountInterface = discountInterface;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

}