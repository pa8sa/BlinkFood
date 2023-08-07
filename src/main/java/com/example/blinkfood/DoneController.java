package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class DoneController {

    @FXML
    private Button BackButton;

    @FXML
    void Back(MouseEvent event) throws IOException, SQLException {
        Server.ClientHandler.resetRestaurants();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setTableView();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.setTitle("MainMenu");
        stage.setScene(new Scene(root));
    }

}
