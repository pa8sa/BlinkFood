package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAddFoodController {

    private static int i = 0;
    private int FoodCount;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField PriceTextField;

    @FXML
    private TextField TypeTextField;

    @FXML
    private TextField WeightTextField;

    @FXML
    void Add(MouseEvent event) throws IOException {
        if (Server.ClientHandler.writeToFile("AddFood", NameTextField.getText() + "," + PriceTextField.getText() + "," +
                WeightTextField.getText() + "," + TypeTextField.getText()) == 1) {
            i++;
        }
        NameTextField.clear();
        PriceTextField.clear();
        WeightTextField.clear();
        TypeTextField.clear();
        if (i == FoodCount) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) WeightTextField.getScene().getWindow();
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
        }
    }

    public void setFoodCount (int FoodCount) {
        this.FoodCount = FoodCount;
    }
}
