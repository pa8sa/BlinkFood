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
import java.sql.SQLException;

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
        ImgPathField.setText(selectedRestaurant.getIMGpath());
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
    void Remove(MouseEvent event) throws IOException, SQLException {
        Server.ClientHandler.removeRestaurant(selectedRestaurant.getName());
    }

    @FXML
    void Submit(MouseEvent event) throws IOException, SQLException {
        FillError.setVisible(false);
        NameError.setVisible(false);
        NumberError.setVisible(false);
        if (NameTextField.getText().isEmpty() || AddressTextField.getText().isEmpty() || WorkTimeTextField.getText().isEmpty() ||
        TypeTextField.getText().isEmpty() || ChairDeliveryTextField.getText().isEmpty() || ImgPathField.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        else if (EnableButton.isSelected()) {
            Server.ClientHandler.setEnableDisable(selectedRestaurant.getName(), "true");
            String phrase = Server.ClientHandler.editRestaurant(selectedRestaurant.getName(), NameTextField.getText(), AddressTextField.getText(),
                    WorkTimeTextField.getText(), TypeTextField.getText(), ChairDeliveryTextField.getText(), ImgPathField.getText());
            if (phrase.equals("typeWrong")) {
                return;
            } else if (phrase.equals("ChairDeliveryWrong")) {
                return;
            } else if (phrase.equals("imgPathWrong")) {
                return;
            } else if (phrase.equals("nameTaken")) {
                return;
            } else if (phrase.equals("addressTaken")) {
                return;
            } else if (phrase.equals("imgPathWrong")) {
                return;
            }
        }
        else if (DisableButton.isSelected()) {
            Server.ClientHandler.setEnableDisable(selectedRestaurant.getName(), "false");
        }
    }
}
