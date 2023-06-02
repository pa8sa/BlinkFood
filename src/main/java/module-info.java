module com.example.blinkfood {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blinkfood to javafx.fxml;
    exports com.example.blinkfood;
}