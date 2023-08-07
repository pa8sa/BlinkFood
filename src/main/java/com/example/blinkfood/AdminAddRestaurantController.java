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
    private Text TypeError;

    @FXML
    private Text ImgTakenError;

    @FXML
    private Text ImgError1;

    @FXML
    private Text AddressError;

    @FXML
    void Add(MouseEvent event) throws IOException, SQLException {
        FillError.setVisible(false);
        CountError.setVisible(false);
        NameError.setVisible(false);
        TypeError.setVisible(false);
        ImgError1.setVisible(false);
        ImgTakenError.setVisible(false);
        AddressError.setVisible(false);
        if (NameTextField.getText().isEmpty() || AddressTextField.getText().isEmpty() || WorkTimeTextField.getText().isEmpty() ||
        TypeTextField.getText().isEmpty() || ChairDeliveryTextField.getText().isEmpty() || ImgPathField.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        String phrase = Server.ClientHandler.addRestaurants(NameTextField.getText(), AddressTextField.getText(), WorkTimeTextField.getText(),
                TypeTextField.getText(), ChairDeliveryTextField.getText(), ImgPathField.getText());
        if (phrase.equals("typeWrong")) {
            TypeError.setVisible(true);
        }
        else if (phrase.equals("chairDeliveryWrong") || !FoodCountTextField.getText().matches("\\d+")) {
            CountError.setVisible(true);
        }
        else if (phrase.equals("imgPathWrong")) {
            ImgError1.setVisible(true);
        }
        else if (phrase.equals("nameTaken")) {
            NameError.setVisible(true);
        }
        else if (phrase.equals("addressTaken")) {
            AddressError.setVisible(true);
        }
        else if (phrase.equals("imgPathTaken")) {
            ImgTakenError.setVisible(true);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminAddFood.fxml"));
            Parent root = loader.load();
            AdminAddFoodController adminAddFoodController = loader.getController();
            adminAddFoodController.setFoodCount(Integer.parseInt(FoodCountTextField.getText()), Server.ClientHandler.getRestaurants().size());
            Stage stage = (Stage) ImgPathField.getScene().getWindow();
            stage.setTitle("Admin");
            stage.setScene(new Scene(root));
        }
    }
}
