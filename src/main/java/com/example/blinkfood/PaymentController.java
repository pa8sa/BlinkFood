package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class PaymentController {
    private int TotalCost;
    private Restaurant selectedRestaurant;

    @FXML
    private Text AddressText;

    @FXML
    private Text CostText;

    @FXML
    private Text CreditText;

    @FXML
    private Text EmailText;

    @FXML
    private ListView<String> ListView;

    @FXML
    private Text PhoneNumberText;

    @FXML
    private Text RestaurantText;

    @FXML
    private Text UsernameText;

    @FXML
    void Remove(MouseEvent event) {
        String selectedItem = ListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
                String foodItem = selectedRestaurant.getFoods().get(i).getName() + "   x" + selectedRestaurant.getFoods().get(i).getCount();
                if (selectedItem.equals(foodItem)) {
                    Food food = selectedRestaurant.getFoods().get(i);
                    if (food.getCount() == 1) {
                        selectedRestaurant.getFoods().remove(i);
                        ListView.getItems().remove(selectedItem);
                    } else {
                        food.reduceCount();
                        ListView.getItems().set(ListView.getSelectionModel().getSelectedIndex(), food.getName() + "   x" + food.getCount());
                    }
                    TotalCost -= food.getPrice();
                    CostText.setText(String.valueOf(TotalCost));
                    break;
                }
            }
        }
    }

    @FXML
    void Back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Restaurant.fxml"));
        Parent root = loader.load();
        RestaurantController restaurantController = loader.getController();
        restaurantController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) ListView.getScene().getWindow();
        stage.setTitle(selectedRestaurant.getName());
        stage.setScene(new Scene(root));
    }

    @FXML
    void Charge(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Charge.fxml"));
        Parent root = loader.load();
        ChargeController chargeController = loader.getController();
        chargeController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) ListView.getScene().getWindow();
        stage.setTitle("Charge");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Done(MouseEvent event) throws IOException {
        if (Server.user.getBalance() - TotalCost >= 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bank.fxml"));
            Parent root = loader.load();
            BankController bankController = loader.getController();
            bankController.setRestaurant(selectedRestaurant);
            bankController.setTotalCost(TotalCost);
            Stage stage = (Stage) ListView.getScene().getWindow();
            stage.setTitle("Bank");
            stage.setScene(new Scene(root));
        }
    }

    public void setRestaurant(Restaurant restaurant) {
        selectedRestaurant = restaurant;
        initializeListView();
        setFields();
    }

    private void initializeListView() {
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            if (selectedRestaurant.getFoods().get(i).getCount() > 0) {
                ListView.getItems().add(selectedRestaurant.getFoods().get(i).getName() + "   x" + selectedRestaurant.getFoods().get(i).getCount());
                TotalCost += selectedRestaurant.getFoods().get(i).getCount() * selectedRestaurant.getFoods().get(i).getPrice();
            }
        }
        CostText.setText(String.valueOf(TotalCost));
        RestaurantText.setText(selectedRestaurant.getName());
    }

    public void setFields() {
        UsernameText.setText(Server.user.getUserName());
        PhoneNumberText.setText(Server.user.getPhoneNumber());
        AddressText.setText(Server.user.getAddress());
        EmailText.setText(Server.user.getEmail());
        CreditText.setText(String.valueOf(Server.user.getBalance()));
    }
}
