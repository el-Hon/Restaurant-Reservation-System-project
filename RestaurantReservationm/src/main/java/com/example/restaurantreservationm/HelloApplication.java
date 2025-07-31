package com.example.restaurantreservationm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelloApplication extends Application {
    private static final String TABLES_FILE = "tables.txt";
    private static final String MENU_ITEMS_FILE = "menuitems";
    private static final String ORDERS_FILE = "orders.txt";

    private RestaurantReservation restaurant;
    private DiscountCalculator discountCalculator;
    private String selectedMembership;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        restaurant = RestaurantReservation.getInstance();
        discountCalculator = new DiscountCalculator();

        // Create some initial tables
        restaurant.addTable(new Table(1, 4));
        restaurant.addTable(new Table(2, 6));
        restaurant.addTable(new Table(3, 2));
        restaurant.addTable(new Table(4, 9));
        restaurant.addTable(new Table(5, 14));
        restaurant.addTable(new Table(6, 1));

        // Create some initial menu items
        restaurant.addMenuItem(new MenuItem("Burger", "cheesy with mushroom", 110));
        restaurant.addMenuItem(new MenuItem("Pizza", "Stuffed Crust", 130));
        restaurant.addMenuItem(new MenuItem("Salad", "Healthy", 50));
        restaurant.addMenuItem(new MenuItem("7wawshy", "UnHealthy But Tasty", 90));
        restaurant.addMenuItem(new MenuItem("Chicken", "Shawerma", 70));
        restaurant.addMenuItem(new MenuItem("Meat", "Shawerma", 85));

        primaryStage.setTitle("Restaurant Reservation System");
        primaryStage.setScene(createScene());
        primaryStage.show();
    }

    private Scene createScene() {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Restaurant Reservation System");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button viewTablesButton = new Button("View Available Tables");
        viewTablesButton.setOnAction(e -> displayAvailableTables());

        Button createOrderButton = new Button("Create an Order");
        createOrderButton.setOnAction(e -> createOrder());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        root.getChildren().addAll(titleLabel, viewTablesButton, createOrderButton, exitButton);

        return new Scene(root, 300, 200);
    }
    private void showerror(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    private void displayAvailableTables() {
        Stage stage = new Stage();
        stage.setTitle("Available Tables");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Available Tables");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        int partySize = getPartySize();

        List<Table> availableTables = restaurant.getAvailableTables(partySize);
        if (availableTables.isEmpty()) {
            Label noTablesLabel = new Label("No available tables for the given party size.");
            root.getChildren().addAll(titleLabel, noTablesLabel);
        } else {
            for (Table table : availableTables) {
                Label tableLabel = new Label("Table: " + table.getTableNumber() + ", Capacity: " + table.getCapacity());
                root.getChildren().add(tableLabel);
            }
        }

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private int getPartySize() {
        Stage stage = new Stage();
        stage.setTitle("Party Size");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Enter Party Size:");

        TextField partySizeField = new TextField();
        partySizeField.setPromptText("Party Size");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            try {
                int partySize = Integer.parseInt(partySizeField.getText());
                displayAvailableTables(partySize);
                stage.close();
            } catch (NumberFormatException ex) {
                showerror("Invalid input. Please enter a valid party size.");
            }
        });

        root.getChildren().addAll(titleLabel, partySizeField, submitButton);

        Scene scene = new Scene(root, 200, 150);
        stage.setScene(scene);
        stage.showAndWait();

        return Integer.parseInt(partySizeField.getText());
    }

    private void displayAvailableTables(int partySize) {
        Stage stage = new Stage();
        stage.setTitle("Available Tables");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Available Tables");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        List<Table> availableTables = restaurant.getAvailableTables(partySize);
        if (availableTables.isEmpty()) {
            Label noTablesLabel = new Label("No available tables for the given party size.");
            root.getChildren().addAll(titleLabel, noTablesLabel);
        } else {
            for (Table table : availableTables) {
                Label tableLabel = new Label("Table: " + table.getTableNumber() + ", Capacity: " + table.getCapacity());
                root.getChildren().add(tableLabel);
            }
        }

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void createOrder() {
        Stage stage = new Stage();
        stage.setTitle("Create Order");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Create Order");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        int partySize = getPartySize();
        List<Table> availableTables = restaurant.getAvailableTables(partySize);

        if (availableTables.isEmpty()) {
            Label noTablesLabel = new Label("No available tables for the given party size.");
            root.getChildren().addAll(titleLabel, noTablesLabel);
        } else {
            Label selectTableLabel = new Label("Select a table:");

            ListView<Table> tableListView = new ListView<>();
            tableListView.getItems().addAll(availableTables);


            Button createOrderButton = new Button("Create Order");
            createOrderButton.setOnAction(e -> {
                Table selectedTable = tableListView.getSelectionModel().getSelectedItem();
                if (selectedTable != null) {
                    List<MenuItem> selectedItems = selectMenuItems();
                    Order order = new Order(selectedItems, selectedTable);



                    restaurant.createOrder(order);

                    displayOrderSummary(order);
                    stage.close();
                }
            });

            root.getChildren().addAll(titleLabel, selectTableLabel, tableListView,  createOrderButton);
        }

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private List<MenuItem> selectMenuItems() {
        Stage stage = new Stage();
        stage.setTitle("Select Menu Items");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Select Menu Items");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        List<MenuItem> menuItems = restaurant.getMenuItems();
        ListView<MenuItem> menuItemListView = new ListView<>();
        menuItemListView.getItems().addAll(menuItems);
        menuItemListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Button createOrderButton = new Button("Create Order");
        createOrderButton.setOnAction(e -> {
            List<MenuItem> selectedItems = menuItemListView.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                Table selectedTable = new Table(0, 0);
                Order order = new Order(selectedItems, selectedTable);
                double discountedPrice = discountCalculator.applyDiscount(order.getTotalPrice(), selectedMembership);
                order.setTotalPrice(discountedPrice);
                restaurant.createOrder(order);

                displayOrderSummary(order);
                stage.close();
            }
        });

        root.getChildren().addAll(titleLabel, menuItemListView, createOrderButton);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.showAndWait();

        return menuItemListView.getSelectionModel().getSelectedItems();
    }

    private void displayOrderSummary(Order order) {
        Stage stage = new Stage();
        stage.setTitle("Order Summary");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Label titleLabel = new Label("Order Summary");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label totalPriceLabel = new Label("Total Price: $" + order.getTotalPrice());

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());

        root.getChildren().addAll(titleLabel, totalPriceLabel, closeButton);

        Scene scene = new Scene(root, 200, 150);
        stage.setScene(scene);
        stage.show();
    }

    private class DiscountCalculator {
        public double applyDiscount(double totalPrice, String selectedMembership) {
            String membershipLevel = getMembershipLevel();


            if (membershipLevel.equalsIgnoreCase("gold")) {
                return totalPrice * 0.8;
            } else if (membershipLevel.equalsIgnoreCase("silver")) {
                return totalPrice * 0.9;
            } else if (membershipLevel.equalsIgnoreCase("bronze")) {
                return totalPrice * 0.95;
            } else {
                return totalPrice;
            }
        }

        private String getMembershipLevel() {
            Stage stage = new Stage();
            stage.setTitle("Membership Level");

            VBox root = new VBox();
            root.setPadding(new Insets(10));
            root.setSpacing(10);

            Label titleLabel = new Label("Select Membership Level:");

            ComboBox<String> membershipLevelComboBox = new ComboBox<>();
            membershipLevelComboBox.getItems().addAll("Gold", "Silver", "Bronze", "Non-Member");
            membershipLevelComboBox.setValue("Non-Member");

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> stage.close());

            root.getChildren().addAll(titleLabel, membershipLevelComboBox, submitButton);

            Scene scene = new Scene(root, 200, 150);
            stage.setScene(scene);
            stage.showAndWait();

            return membershipLevelComboBox.getValue();
        }
    }
    private void loadTablesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TABLES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tableData = line.split(",");
                int tableNumber = Integer.parseInt(tableData[0]);
                int capacity = Integer.parseInt(tableData[1]);
                restaurant.addTable(new Table(tableNumber, capacity));
            }
        } catch (IOException e) {
            System.out.println("Error loading tables from file: " + e.getMessage());
        }
    }

    private void saveTablesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TABLES_FILE))) {
            List<Table> tables = restaurant.getTables();
            for (Table table : tables) {
                writer.write(table.getTableNumber() + "," + table.getCapacity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tables to file: " + e.getMessage());
        }
    }

    private void loadMenuItemsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] menuItemData = line.split(",");
                String name = menuItemData[0];
                String description = menuItemData[1];
                double price = Double.parseDouble(menuItemData[2]);
                restaurant.addMenuItem(new MenuItem(name, description, price));
            }
        } catch (IOException e) {
            System.out.println("Error loading menu items from file: " + e.getMessage());
        }
    }

    private void saveMenuItemsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_ITEMS_FILE))) {
            List<MenuItem> menuItems = restaurant.getMenuItems();
            for (MenuItem menuItem : menuItems) {
                writer.write(menuItem.getName() + "," + menuItem.getDescription() + "," + menuItem.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu items to file: " + e.getMessage());
        }
    }

    private void saveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            List<Order> orders = restaurant.getOrders();
            for (Order order : orders) {
                writer.write(order.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }


}






