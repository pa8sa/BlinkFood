package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAddFoodController {

    private static int i = 0;
    private int FoodCount;

    @FXML
    private Text FillError;

    @FXML
    private TextField ImgPathField;

    @FXML
    private Text NameError;

    @FXML
    private Text NumberError;

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
        FillError.setVisible(false);
        NameError.setVisible(false);
        NumberError.setVisible(false);
        if (NameTextField.getText().isEmpty() || PriceTextField.getText().isEmpty() || WeightTextField.getText().isEmpty() || TypeTextField.getText().isEmpty() ||
        ImgPathField.getText().isEmpty()) {
            FillError.setVisible(true);
            return;
        }
        else if (Server.ClientHandler.writeToFile("AddFood", NameTextField.getText() + "," + PriceTextField.getText() + "," +
                WeightTextField.getText() + "," + TypeTextField.getText() + "," + ImgPathField.getText()) == 1) {
            i++;
            NameTextField.clear();
            PriceTextField.clear();
            WeightTextField.clear();
            TypeTextField.clear();
        }
        else if (Server.ClientHandler.writeToFile("AddFood", NameTextField.getText() + "," + PriceTextField.getText() + "," +
                WeightTextField.getText() + "," + TypeTextField.getText() + "," + ImgPathField.getText()) == -2) {
            NameError.setVisible(true);
            return;
        }
        else if (Server.ClientHandler.writeToFile("AddFood", NameTextField.getText() + "," + PriceTextField.getText() + "," +
                WeightTextField.getText() + "," + TypeTextField.getText() + "," + ImgPathField.getText()) == -4) {
            NumberError.setVisible(true);
            return;
        }
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
