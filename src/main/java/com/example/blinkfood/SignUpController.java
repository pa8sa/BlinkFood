package com.example.blinkfood;

import java.io.*;
import java.sql.SQLException;

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
    private Text FillError;

    @FXML
    void NextScene(MouseEvent event) throws SQLException, IOException {
        if (RadioButtonShowPass.isSelected()) {
            TextPasswordField.setText(TextPassword.getText());
        }
        else {
            TextPassword.setText(TextPasswordField.getText());
        }
        FillError.setVisible(false);
        EmptyError.setVisible(false);
        UsernameError.setVisible(false);
        EmailError.setVisible(false);
        PhoneNumberError.setVisible(false);
        PasswordError.setVisible(false);
        EmailTakenError.setVisible(false);
        PhoneNumberTakenError.setVisible(false);
        if (TextUsername.getText().isEmpty() || TextPasswordField.getText().isEmpty() || TextPhoneNumber.getText().isEmpty() || TextAddress.getText().isEmpty() || TextEmail.getText().isEmpty()) {
            FillError.setVisible(true);
            return;
        }
        String phrase = Server.ClientHandler.addUser(TextUsername.getText(), TextPhoneNumber.getText(), TextEmail.getText(), TextPassword.getText(),
                TextAddress.getText());
        if (phrase.equals("passWordLength")) {
            PasswordError.setVisible(true);
        }
        else if (phrase.equals("emailWrong")) {
            EmailError.setVisible(true);
        }
        else if (phrase.equals("phoneNumberWrong")) {
            PhoneNumberError.setVisible(true);
        }
        else if (phrase.equals("userNameTaken")) {
            UsernameError.setVisible(true);
        }
        else if (phrase.equals("phoneNumberTaken")) {
            PhoneNumberTakenError.setVisible(true);
        }
        else if (phrase.equals("emailTaken")) {
            EmailTakenError.setVisible(true);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTableView();
            Stage stage = (Stage) FillError.getScene().getWindow();
            stage.setTitle("MainMenu");
            stage.setScene(new Scene(root));
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
            TextPasswordField.setText(TextPassword.getText());
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
