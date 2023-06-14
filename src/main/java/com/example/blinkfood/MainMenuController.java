package com.example.blinkfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private static int sw = 0;

    @FXML
    private TableView<Restaurant> TableView;

    @FXML
    private TableColumn<Restaurant, String> AddressColumn;

    @FXML
    private TableColumn<Restaurant, String> NameColumn;

    @FXML
    private TableColumn<Restaurant, ?> TypeColumn;

    @FXML
    private TableColumn<Restaurant, String> WorkingTimeColumn;

    static ObservableList<Restaurant> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (sw == 0) {
            list = FXCollections.observableArrayList(Server.ClientHandler.getRestaurants());
            sw++;
        }
        NameColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Address"));
        WorkingTimeColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("WorkTime"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        TableView.setItems(list);
    }

    @FXML
    private void handleTableViewDoubleClick(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Restaurant selectedRestaurant = TableView.getSelectionModel().getSelectedItem();
            if (selectedRestaurant != null) {
                // Load the Restaurant.fxml scene
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

                // Close the current stage (if needed)
                Stage currentStage = (Stage) TableView.getScene().getWindow();
                currentStage.close();
            }
        }
    }
}