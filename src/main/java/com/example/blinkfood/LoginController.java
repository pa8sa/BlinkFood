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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

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
    private Text Error;

    @FXML
    private Text FillError;

    @FXML
    void NextScene(MouseEvent event) throws SQLException, IOException {
        Error.setVisible(false);
        FillError.setVisible(false);
        if (RadioButtonShowPass.isSelected()) {
            TextPasswordField.setText(TextPassword.getText());
        }
        else {
            TextPassword.setText(TextPasswordField.getText());
        }
        if (TextPassword.getText().isEmpty() || TextUsername.getText().isEmpty()) {
            FillError.setVisible(true);
            return;
        }
        String phrase = Server.ClientHandler.checkUsers(TextUsername.getText(), TextPassword.getText());
        if (phrase.equals("wrong")){
            Error.setVisible(true);
        }
        else if (phrase.equals("admin")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();
            AdminController adminController = loader.getController();
            adminController.setTableView();
            Stage stage = (Stage) FillError.getScene().getWindow();
            stage.setTitle("Admin");
            stage.setScene(new Scene(root));
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
    void SignUpScene(MouseEvent event) throws IOException {
        Stage stage = (Stage) ButtonSignUp.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage.setTitle("SignUp");
        stage.setScene(new Scene(root));
    }
}