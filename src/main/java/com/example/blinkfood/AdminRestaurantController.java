package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.FloatBuffer;

public class AdminRestaurantController {

    @FXML
    private Text FillError;

    @FXML
    private TextField ImgPathField;

    @FXML
    private Text NameError;

    @FXML
    private Text NumberError;

    @FXML
    private RadioButton DisableButton;

    @FXML
    private RadioButton EnableButton;

    private Restaurant selectedRestaurant;

    @FXML
    private TextField AddressTextField;

    @FXML
    private TextField ChairDeliveryTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField TypeTextField;

    @FXML
    private TextField WorkTimeTextField;

    public void setRestaurant (Restaurant restaurant) {
        selectedRestaurant = restaurant;
        setFields();
        if (selectedRestaurant.getEnable()) {
            EnableButton.setSelected(true);
            DisableButton.setSelected(false);
        }
        else if (!selectedRestaurant.getEnable()) {
            DisableButton.setSelected(true);
            EnableButton.setSelected(false);
        }
    }

    public void setFields () {
        NameTextField.setText(selectedRestaurant.getName());
        AddressTextField.setText(selectedRestaurant.getAddress());
        WorkTimeTextField.setText(selectedRestaurant.getWorkTime());
        ChairDeliveryTextField.setText(String.valueOf(selectedRestaurant.getChair_Delivery_Count()));
        TypeTextField.setText(String.valueOf(selectedRestaurant.getType()));
    }

    @FXML
    void Back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) DisableButton.getScene().getWindow();
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
    }

    @FXML
    void EditFoods(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminFood.fxml"));
        Parent root = loader.load();
        AdminFoodController adminFoodController = loader.getController();
        adminFoodController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) DisableButton.getScene().getWindow();
        stage.setTitle(selectedRestaurant.getName() + " Foods Panel");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Remove(MouseEvent event) throws IOException {
        Server.ClientHandler.RemoveRestaurant(selectedRestaurant.getName());
    }

    @FXML
    void Submit(MouseEvent event) throws IOException {
        FillError.setVisible(false);
        NameError.setVisible(false);
        NumberError.setVisible(false);
        if (NameTextField.getText().isEmpty() || AddressTextField.getText().isEmpty() || WorkTimeTextField.getText().isEmpty() ||
        TypeTextField.getText().isEmpty() || ChairDeliveryTextField.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        else if (EnableButton.isSelected()) {
            if (Server.ClientHandler.EditRestaurant(selectedRestaurant.getName(), NameTextField.getText() + "," + AddressTextField.getText() +
                    "," + WorkTimeTextField.getText() + "," + TypeTextField.getText() + "," + ChairDeliveryTextField.getText()) == 1) {

            }
            if (Server.ClientHandler.EditRestaurant(selectedRestaurant.getName(), NameTextField.getText() + "," + AddressTextField.getText() +
                    "," + WorkTimeTextField.getText() + "," + TypeTextField.getText() + "," + ChairDeliveryTextField.getText()) == -2) {
                NameError.setVisible(true);
            }
            if (Server.ClientHandler.EditRestaurant(selectedRestaurant.getName(), NameTextField.getText() + "," + AddressTextField.getText() +
                    "," + WorkTimeTextField.getText() + "," + TypeTextField.getText() + "," + ChairDeliveryTextField.getText()) == -4) {
                NumberError.setVisible(true);
            }
        }
        else if (DisableButton.isSelected()) {
            if (Server.ClientHandler.EditRestaurant(selectedRestaurant.getName(), "false") == 1 ) {

            }
        }
    }
}
