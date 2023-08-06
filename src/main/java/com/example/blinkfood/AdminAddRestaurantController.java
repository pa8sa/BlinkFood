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
import java.sql.SQLException;

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
    private TextField ImgPathField;

    @FXML
    private Text NameError;

    @FXML
    private Text CountError;

    @FXML
    private Text FillError;

    @FXML
    void Add(MouseEvent event) throws IOException, SQLException {
        FillError.setVisible(false);
        CountError.setVisible(false);
        NameError.setVisible(false);
        if (NameTextField.getText().isEmpty() || AddressTextField.getText().isEmpty() || WorkTimeTextField.getText().isEmpty() ||
        TypeTextField.getText().isEmpty() || ChairDeliveryTextField.getText().isEmpty() || ImgPathField.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        String phrase = Server.ClientHandler.addRestaurants(NameTextField.getText(), AddressTextField.getText(), WorkTimeTextField.getText(),
                TypeTextField.getText(), ChairDeliveryTextField.getText(), ImgPathField.getText());
        if (phrase.equals("typeWrong")) {

            return;
        }
        else if (phrase.equals("ChairDeliveryWrong")) {
            return;
        }
        else if (phrase.equals("imgPathWrong")) {
            return;
        }
        else if (phrase.equals("nameTaken")) {
            return;
        }
        else if (phrase.equals("addressTaken")) {
            return;
        }
        else if (phrase.equals("imgPathWrong")) {
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminAddFood.fxml"));
            Parent root = loader.load();
            AdminAddFoodController adminAddFoodController = loader.getController();
            adminAddFoodController.setFoodCount(Integer.parseInt(FoodCountTextField.getText()), Server.ClientHandler.getRestaurants().size() - 1);
            Stage stage = (Stage) ImgPathField.getScene().getWindow();
            stage.setTitle("Admin");
            stage.setScene(new Scene(root));
        }
    }
}
