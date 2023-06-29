package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAddRestaurantController {

    @FXML
    private TextField AddressTextField;

    @FXML
    private TextField ChairDeliveryTextField;

    @FXML
    private TextField FoodCountTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField TypeTextField;

    @FXML
    private TextField WorkTimeTextField;

    @FXML
    void Add(MouseEvent event) throws IOException {
        if (Server.ClientHandler.writeToFile("AddRestaurant", NameTextField.getText() + "," + AddressTextField.getText() + "," +
                WorkTimeTextField.getText() + "," + TypeTextField.getText() + "," + FoodCountTextField.getText() + "," + ChairDeliveryTextField.getText()) == 1 ){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminAddFood.fxml"));
            Parent root = loader.load();
            AdminAddFoodController adminAddFoodController = loader.getController();
            adminAddFoodController.setFoodCount(Integer.parseInt(FoodCountTextField.getText()));
            Stage stage = (Stage) AddressTextField.getScene().getWindow();
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
        }
    }

}
