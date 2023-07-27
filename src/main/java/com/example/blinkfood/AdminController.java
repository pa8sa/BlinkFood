package com.example.blinkfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    static ObservableList<Restaurant> list;

    @FXML
    private TableColumn<Restaurant, String> AddressColumn;

    @FXML
    private RadioButton AddressToggleButton;

    @FXML
    private TableColumn<Restaurant, Integer> ChairDeliveryColumn;

    @FXML
    private TableColumn<Restaurant, String> NameColumn;

    @FXML
    private RadioButton NameToggleButton;

    @FXML
    private TextField SearchTextField;

    @FXML
    private TableColumn<Restaurant, Boolean> EnableColumn;

    @FXML
    private TableView<Restaurant> TableView;

    @FXML
    private TableColumn<Restaurant, ?> TypeColumn;

    @FXML
    private TableColumn<Restaurant, String> WorkingTimeColumn;

    @FXML
    void handleTableViewDoubleClick(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Restaurant selectedRestaurant = TableView.getSelectionModel().getSelectedItem();
            if (selectedRestaurant != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRestaurant.fxml"));
                Parent root = loader.load();
                AdminRestaurantController restaurantController = loader.getController();
                restaurantController.setRestaurant(selectedRestaurant);
                Stage stage = (Stage) TableView.getScene().getWindow();
                stage.setTitle(selectedRestaurant.getName() + " Panel");
                stage.setScene(new Scene(root));
            }
        }
    }

    public void setTableView() throws SQLException {
        if (list != null) {
            list.clear();
        }
        list = FXCollections.observableArrayList(Server.ClientHandler.getRestaurants());
        NameColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("Address"));
        WorkingTimeColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("WorkTime"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        ChairDeliveryColumn.setCellValueFactory(new PropertyValueFactory<Restaurant, Integer>("Chair_Delivery_Count"));
        EnableColumn.setCellValueFactory(new PropertyValueFactory<>("Enable"));
        TableView.setItems(list);
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

    @FXML
    private void Add(MouseEvent event) throws IOException {
        Stage stage = (Stage) TableView.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AdminAddRestaurant.fxml"));
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
    }
}
