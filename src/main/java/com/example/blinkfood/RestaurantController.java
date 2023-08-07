package com.example.blinkfood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantController implements Initializable {

    @FXML
    private Text Count;

    @FXML
    private Label nameLabel;

    private Restaurant selectedRestaurant;

    @FXML
    private ImageView Image;

    @FXML
    private ImageView FoodImage;

    @FXML
    private ChoiceBox<String> ChoiceBox;

    @FXML
    void Add(MouseEvent event) {
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                selectedRestaurant.getFoods().get(i).addCount();
                Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                break;
            }
        }
    }

    @FXML
    void Back(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setTableView();
        Stage stage = (Stage) ChoiceBox.getScene().getWindow();
        stage.setTitle("MainMenu");
        stage.setScene(new Scene(root));
    }

    @FXML
    void NextScene(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Parent root = loader.load();
        PaymentController paymentController = loader.getController();
        paymentController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) ChoiceBox.getScene().getWindow();
        stage.setTitle("Payment");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Remove(MouseEvent event) {
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                if (selectedRestaurant.getFoods().get(i).getCount() > 0) {
                    selectedRestaurant.getFoods().get(i).reduceCount();
                    Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                }
                break;
            }
        }
    }

    public void setRestaurant(Restaurant restaurant) {
        selectedRestaurant = restaurant;
        nameLabel.setText(restaurant.getName());
        Image.setImage(new Image(selectedRestaurant.getIMGpath()));
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            ChoiceBox.getItems().add(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice());
        }
    }

    @FXML
    void onChoiceBoxSelectionChanged(ActionEvent event) {
        String selectedItem = ChoiceBox.getSelectionModel().getSelectedItem();
        for (int i = 0; i < selectedRestaurant.getFoods().size(); i++) {
            if (ChoiceBox.getSelectionModel().getSelectedItem().equals(selectedRestaurant.getFoods().get(i).getName() + "  " + selectedRestaurant.getFoods().get(i).getPrice())) {
                Count.setText("x" + selectedRestaurant.getFoods().get(i).getCount());
                FoodImage.setImage(new Image(selectedRestaurant.getFoods().get(i).getIMGpath()));
                break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBox.setOnAction(this::onChoiceBoxSelectionChanged);
    }
}