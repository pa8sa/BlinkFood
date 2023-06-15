package com.example.blinkfood;

import java.io.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private Button ButtonLogin;

    @FXML
    private Button ButtonOK;

    @FXML
    private RadioButton RadioButtonShowPass;

    @FXML
    private TextField TextAddress;

    @FXML
    private TextField TextEmail;

    @FXML
    private TextField TextPassword;

    @FXML
    private PasswordField TextPasswordField;

    @FXML
    private TextField TextPhoneNumber;

    @FXML
    private TextField TextUsername;

    @FXML
    private Text UsernameError;

    @FXML
    private Text UsernameDone;

    @FXML
    private Text EmptyError;

    @FXML
    private Text EmailError;

    @FXML
    private Text PhoneNumberError;

    @FXML
    private Text PasswordError;

    @FXML
    private Text EmailTakenError;

    @FXML
    private Text PhoneNumberTakenError;

    @FXML
    void NextScene(MouseEvent event) throws InterruptedException, IOException {
        EmptyError.setVisible(false);
        UsernameError.setVisible(false);
        EmailError.setVisible(false);
        PhoneNumberError.setVisible(false);
        PasswordError.setVisible(false);
        UsernameDone.setVisible(false);
        EmailTakenError.setVisible(false);
        PhoneNumberTakenError.setVisible(false);
        String Content = TextUsername.getText() + "," + TextPasswordField.getText() + "," + TextPhoneNumber.getText() + "," + TextAddress.getText() + "," + TextEmail.getText();
        if (Server.ClientHandler.writeToFile("UserSignUp", Content) == 1) {
            EmptyError.setVisible(false);
            UsernameError.setVisible(false);
            EmailError.setVisible(false);
            PhoneNumberError.setVisible(false);
            PasswordError.setVisible(false);
            UsernameDone.setVisible(true);
            Stage stage = (Stage) ButtonOK.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setTitle("Menu");
            stage.setScene(new Scene(root));
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -1) {
            EmptyError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -2) {
            UsernameError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -3) {
            EmailError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -4) {
            PhoneNumberError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -5) {
            PasswordError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -6) {
            PhoneNumberTakenError.setVisible(true);
        }
        else if (Server.ClientHandler.writeToFile("UserSignUp", Content) == -7) {
            EmailTakenError.setVisible(true);
        }
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
    void LoginScene(MouseEvent event) throws IOException {
        Stage stage = (Stage) ButtonLogin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
    }

}
