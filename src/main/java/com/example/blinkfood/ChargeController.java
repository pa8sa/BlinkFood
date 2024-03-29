package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class ChargeController {
    private Restaurant selectedRestaurant;

    @FXML
    private TextField AmountTextField;

    @FXML
    private RadioButton PrivacyRadioButton;

    @FXML
    void Back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Parent root = loader.load();
        PaymentController paymentController = loader.getController();
        paymentController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) AmountTextField.getScene().getWindow();
        stage.setTitle(selectedRestaurant.getName());
        stage.setScene(new Scene(root));
    }

    @FXML
    void Done(MouseEvent event) throws IOException, SQLException {
        if (PrivacyRadioButton.isSelected() && AmountTextField.getText().matches("^[0-9]+(?:\\.[0-9]+)?$")) {
            Server.user.AddToBalance(Double.parseDouble(AmountTextField.getText()));
            Server.ClientHandler.editBalance(Server.user.getUserName(), Server.user.getBalance());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Parent root = loader.load();
            PaymentController paymentController = loader.getController();
            paymentController.setRestaurant(selectedRestaurant);
            Stage stage = (Stage) AmountTextField.getScene().getWindow();
            stage.setTitle(selectedRestaurant.getName());
            stage.setScene(new Scene(root));
        }
    }

    public void setRestaurant(Restaurant restaurant) {
        selectedRestaurant = restaurant;
    }
}
