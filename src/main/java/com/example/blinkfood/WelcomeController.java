package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button ButtonContinue;

    @FXML
    void NextScene(MouseEvent event) throws IOException {
        Stage stage = (Stage) ButtonContinue.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
    }

}
