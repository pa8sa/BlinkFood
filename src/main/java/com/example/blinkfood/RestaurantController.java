package com.example.blinkfood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantController implements Initializable {

    @FXML
    private Text Count;

    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button DoneButton;

    @FXML
    private Button RemoveButton;

    @FXML
    private Label nameLabel;

    private Restaurant selectedRestaurant;

    @FXML
    private ChoiceBox<String> ChoiceBox;

    @FXML
    void Add(MouseEvent event) {
        for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                selectedRestaurant.getFoods().get(i).addCount();
                Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                break;
            }
        }
    }

    @FXML
    void Back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        // Get the controller instance for the previous scene
        MainMenuController mainMenuController = loader.getController();
        // Pass any necessary data back to the previous scene's controller
        // mainMenuController.setData(...);

        Scene scene = new Scene(root);
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void NextScene(MouseEvent event) throws IOException {
        PaymentController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) DoneButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        stage.setTitle("Payment");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Remove(MouseEvent event) {
        for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                if (selectedRestaurant.getFoods().get(i).getCount() > 0) {
                    selectedRestaurant.getFoods().get(i).removeCount();
                    Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                }
                break;
            }
        }
    }

    public void setRestaurant(Restaurant restaurant) {
        selectedRestaurant = restaurant;
        nameLabel.setText(restaurant.getName());
        for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
            ChoiceBox.getItems().add(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice());
        }
    }

    @FXML
    void onChoiceBoxSelectionChanged(ActionEvent event) {
        String selectedItem = ChoiceBox.getSelectionModel().getSelectedItem();
        for (int i = 0; i < selectedRestaurant.getFoodsCount(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBox.setOnAction(this::onChoiceBoxSelectionChanged);
    }
}