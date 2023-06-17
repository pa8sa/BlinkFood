package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DoneController {

    @FXML
    private Button BackButton;

    @FXML
    void Back(MouseEvent event) throws IOException {
        Server.ClientHandler.ResetRestaurants();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) BackButton.getScene().getWindow();
        stage.setTitle("MainMenu");
        stage.setScene(new Scene(root));
    }

}
