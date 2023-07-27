module com.example.blinkfood {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.blinkfood to javafx.fxml;
    exports com.example.blinkfood;
}