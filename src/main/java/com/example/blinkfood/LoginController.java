package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button ButtonOK;

    @FXML
    private Button ButtonSignUp;

    @FXML
    private RadioButton RadioButtonShowPass;

    @FXML
    private TextField TextPassword;

    @FXML
    private PasswordField TextPasswordField;

    @FXML
    private TextField TextUsername;

    @FXML
    void NextScene(MouseEvent event) throws IOException {
//        Stage stage = (Stage) ButtonOK.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource(""));
//        stage.setTitle("");
//        stage.setScene(new Scene(root));
    }

    @FXML
    void ShowPass(MouseEvent event) {
        if (RadioButtonShowPass.isSelected()) {
            TextPassword.setText(TextPasswordField.getText());
            TextPasswordField.setVisible(false);
            TextPassword.setVisible(true);
        }
        else {
            TextPasswordField.setVisible(true);
            TextPassword.setVisible(false);
        }
    }

    @FXML
    void SignUpScene(MouseEvent event) throws IOException {
        Stage stage = (Stage) ButtonSignUp.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage.setTitle("SignUp");
        stage.setScene(new Scene(root));
    }

}