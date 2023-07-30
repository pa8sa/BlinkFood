package com.example.blinkfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class AdminFoodController {

    public static ObservableList<Food> list;

    private Restaurant selectedRestaurant;

    @FXML
    private TextField AddImgPath;

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
    private TextField EditImgPath;

    @FXML
    private Text FillError;

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
    void Add(MouseEvent event) throws IOException, SQLException {
        FillError.setVisible(false);
        if (AddTypeField.getText().isEmpty() || AddWeightField.getText().isEmpty() || AddPriceField.getText().isEmpty() || AddNameField.getText().isEmpty() ||
        AddImgPath.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        String phrase = Server.ClientHandler.addFood(AddNameField.getText(), AddPriceField.getText(), AddWeightField.getText(), AddTypeField.getText(),
                AddImgPath.getText(), selectedRestaurant.getRes_ID());
        if (phrase.equals("imgPathWrong")) {
            return;
        }
        else if (phrase.equals("priceWrong")) {
            return;
        }
        else if (phrase.equals("weightWrong")) {
            return;
        }
        else if (phrase.equals("typeWrong")) {
            return;
        }
        else if (phrase.equals("nameTaken")) {
            return;
        }
        else if (phrase.equals("imgPathTaken")) {
            return;
        }
    }

    @FXML
    void Back(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminRestaurant.fxml"));
        Parent root = loader.load();
        AdminRestaurantController adminRestaurantController = loader.getController();
        adminRestaurantController.setRestaurant(selectedRestaurant);
        Stage stage = (Stage) EditNameField.getScene().getWindow();
        stage.setTitle(selectedRestaurant.getName() + " Panel");
        stage.setScene(new Scene(root));
    }

    @FXML
    void Edit(MouseEvent event) throws IOException, SQLException {
        FillError.setVisible(false);
        if (EditNameField.getText().isEmpty() || EditPriceField.getText().isEmpty() || EditWeightField.getText().isEmpty() || EditTypeField.getText().isEmpty() ||
        EditImgPath.getText().isEmpty()) {
            FillError.setVisible(true);
        }
        String phrase = Server.ClientHandler.editFood(TableView.getSelectionModel().getSelectedItem().getName(), EditNameField.getText(), EditPriceField.getText(), EditWeightField.getText(), EditTypeField.getText(),
                EditImgPath.getText(), selectedRestaurant.getRes_ID());
        if (phrase.equals("imgPathWrong")) {
            return;
        }
        else if (phrase.equals("priceWrong")) {
            return;
        }
        else if (phrase.equals("weightWrong")) {
            return;
        }
        else if (phrase.equals("typeWrong")) {
            return;
        }
        else if (phrase.equals("nameTaken")) {
            return;
        }
        else if (phrase.equals("imgPathTaken")) {
            return;
        }
    }

    @FXML
    void Remove(MouseEvent event) throws IOException, SQLException {
        if (!TableView.getSelectionModel().isEmpty()) {
            Server.ClientHandler.removeFood(TableView.getSelectionModel().getSelectedItem().getName());
            setTableView(TableView.getSelectionModel().getSelectedItem());
        }
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
            EditImgPath.clear();
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
                EditImgPath.setText(selectedFood.getIMGpath());
            }
        });
    }
}
