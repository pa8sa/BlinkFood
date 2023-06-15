package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    private int TotalCost;
    private Restaurant selectedRestaurant;

    @FXML
    private Button BackButton;

    @FXML
    private Button DoneButton;

    @FXML
    private Button ChargeButton;

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
            for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
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
        Parent restaurantRoot = loader.load();
        RestaurantController restaurantController = loader.getController();

        // Pass the selectedRestaurant to the RestaurantController
        restaurantController.setRestaurant(selectedRestaurant);

        // Create a new stage for the Restaurant scene
        Stage restaurantStage = new Stage();
        Scene restaurantScene = new Scene(restaurantRoot);
        restaurantStage.setTitle(selectedRestaurant.getName());
        restaurantStage.setScene(restaurantScene);
        restaurantStage.show();
        Stage currentStage = (Stage) ListView.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void Charge(MouseEvent event) throws IOException {
        Stage stage = (Stage) ChargeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Charge.fxml"));
        stage.setTitle("Charge");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Done(MouseEvent event) throws IOException {
        if (Server.user.getBalance() - TotalCost >= 0) {
            Stage stage = (Stage) ChargeButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Done.fxml"));
            stage.setTitle("Done!");
            stage.setScene(new Scene(root));
        }
    }


    public void setRestaurant(Restaurant restaurant) {
        selectedRestaurant = restaurant;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
            if (selectedRestaurant.getFoods().get(i).getCount() > 0) {
                ListView.getItems().add(selectedRestaurant.getFoods().get(i).getName() + "   x" + selectedRestaurant.getFoods().get(i).getCount());
                TotalCost += selectedRestaurant.getFoods().get(i).getCount() * selectedRestaurant.getFoods().get(i).getPrice();
            }
        }
        UsernameText.setText(Server.user.getUserName());
        PhoneNumberText.setText(Server.user.getPhoneNumber());
        AddressText.setText(Server.user.getAddress());
        EmailText.setText(Server.user.getEmail());
        CreditText.setText(String.valueOf(Server.user.getBalance()));
        CostText.setText(String.valueOf(TotalCost));
        RestaurantText.setText(selectedRestaurant.getName());
    }
}