package com.example.blinkfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AdminFoodController {

    static ObservableList<Food> list;

    private Restaurant selectedRestaurant;

    @FXML
    private TextField AddNameField;

    @FXML
    private TextField AddPriceField;

    @FXML
    private TextField AddTypeField;

    @FXML
    private TextField AddWeightField;

    @FXML
    private TextField EditNameField;

    @FXML
    private TextField EditPriceField;

    @FXML
    private TextField EditTypeField;

    @FXML
    private TextField EditWeightField;

    @FXML
    private TableColumn<Food, String> NameColumn;

    @FXML
    private TableColumn<Food, Integer> PriceColumn;

    @FXML
    private TableView<Food> TableView;

    @FXML
    private TableColumn<Food, String> TypeColumn;

    @FXML
    private TableColumn<Food, Integer> WeightTimeColumn;

    @FXML
    void Add(MouseEvent event) throws IOException {
        if (Server.ClientHandler.EditFoods("Add", null, AddNameField.getText() + "," + AddPriceField.getText() + "," +
                AddWeightField.getText() + "," + AddTypeField.getText(), selectedRestaurant) == 1) {

        }
    }

    @FXML
    void Back(MouseEvent event) {

    }

    @FXML
    void Edit(MouseEvent event) throws IOException {
        if (Server.ClientHandler.EditFoods("Edit", TableView.getSelectionModel().getSelectedItem().getName(), EditNameField.getText() + "," +
                EditPriceField.getText() + "," + EditWeightField.getText() + "," + EditTypeField.getText(), null) == 1) {

        }
    }

    @FXML
    void Remove(MouseEvent event) throws IOException {
        Server.ClientHandler.EditFoods("Remove", TableView.getSelectionModel().getSelectedItem().getName(), selectedRestaurant.getName(), selectedRestaurant);
        setTableView(TableView.getSelectionModel().getSelectedItem());
    }

    public void setRestaurant (Restaurant restaurant) {
        selectedRestaurant = restaurant;
        setTableView(null);
    }

    public void setTableView (Food selectedItem) {
        if (list != null) {
            list.clear();
        }
        list = FXCollections.observableArrayList(selectedRestaurant.getFoods());
        NameColumn.setCellValueFactory(new PropertyValueFactory<Food, String>("Name"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("Price"));
        WeightTimeColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("Weight"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<Food, String>("Type"));
        if (selectedItem != null) {
            list.remove(selectedItem);
            EditTypeField.clear();
            EditWeightField.clear();
            EditPriceField.clear();
            EditNameField.clear();
        }
        TableView.setItems(list);
    }

    @FXML
    void initialize() {
        TableView.setOnMouseClicked(event -> {
            Food selectedFood = TableView.getSelectionModel().getSelectedItem();
            if (selectedFood != null) {
                EditNameField.setText(selectedFood.getName());
                EditPriceField.setText(String.valueOf(selectedFood.getPrice()));
                EditTypeField.setText(String.valueOf(selectedFood.getType()));
                EditWeightField.setText(String.valueOf(selectedFood.getWeight()));
            }
        });
    }
}
