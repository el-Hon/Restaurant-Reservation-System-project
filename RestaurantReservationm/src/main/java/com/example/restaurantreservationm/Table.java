package com.example.restaurantreservationm;

class Table {
    private static Table tables;
    private int tableNumber;
    private int capacity;
    private boolean isAvailable;

    public Table(int number, int capacity) {
        tableNumber = number;
        this.capacity = capacity;
        isAvailable = true;
    }

    public void reserveTable() {
        isAvailable = false;
    }

    public void freeTable() {
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTableNumber() {
        return tableNumber;
    }


    public String toString() {
        return "Table " + tableNumber;
    }
    public static int size() {
        return tables.size();
    }

    }
