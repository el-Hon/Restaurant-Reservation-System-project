module com.example.restaurantreservationm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.restaurantreservationm to javafx.fxml;
    exports com.example.restaurantreservationm;
}