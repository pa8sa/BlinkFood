package com.example.blinkfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainMenuController implements Initializable {

    private static int sw = 0;

    @FXML
    private TableView<Restaurant> TableView;

    @FXML
    private RadioButton AddressToggleButton;

    @FXML
    private RadioButton NameToggleButton;

    @FXML
    private TableColumn<Restaurant, String> AddressColumn;

    @FXML
    private TableColumn<Restaurant, String> NameColumn;

    @FXML
    private TableColumn<Restaurant, ?> TypeColumn;

    @FXML
    private TableColumn<Restaurant, String> WorkingTimeColumn;

    @FXML
    private TableColumn<Restaurant, Integer> ChairDeliveryColumn;

    static ObservableList<Restaurant> list;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField SearchTextField;

    private ArrayList<String> Res_Names = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (sw == 0) {
            list = FXCollections.observableArrayList(Server.ClientHandler.getRestaurants());
            for (int i = 0; i < list.size(); i++) {
                Res_Names.add(list.get(i).getName());
            }
            sw++;
        }
        NameColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Address"));
        WorkingTimeColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("WorkTime"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ChairDeliveryColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, Integer>("Chair_Delivery_Count"));
        TableView.setItems(list);
    }

    @FXML
    private void handleTableViewDoubleClick(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Restaurant selectedRestaurant = TableView.getSelectionModel().getSelectedItem();
            if (selectedRestaurant != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Restaurant.fxml"));
                Parent root = loader.load();
                RestaurantController restaurantController = loader.getController();
                restaurantController.setRestaurant(selectedRestaurant);
                Stage stage = (Stage) TableView.getScene().getWindow();
                stage.setTitle(selectedRestaurant.getName());
                stage.setScene(new Scene(root));
            }
        }
    }

    @FXML
    private void Search(MouseEvent event) {
        String searchText = SearchTextField.getText().trim().toLowerCase();
        List<Restaurant> searchResults;

        if (NameToggleButton.isSelected()) {
            searchResults = list.stream()
                    .filter(restaurant -> restaurant.getName().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        } else if (AddressToggleButton.isSelected()) {
            searchResults = list.stream()
                    .filter(restaurant -> restaurant.getAddress().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        } else {
            // Neither toggle button is selected, search in both name and address columns
            searchResults = list.stream()
                    .filter(restaurant -> restaurant.getName().toLowerCase().contains(searchText)
                            || restaurant.getAddress().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        }

        TableView.setItems(FXCollections.observableArrayList(searchResults));
    }




    private List<String> searchList (String searchWords, List<String> listOfString) {
        List <String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        return listOfString .stream().filter(input -> {return searchWordsArray.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));}).collect(Collectors.toList());
    }
}