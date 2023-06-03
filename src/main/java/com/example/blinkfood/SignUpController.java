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
    void NextScene(MouseEvent event) throws IOException, InterruptedException {
        int sw = 1;
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"));
        String line;
        for (; (line = reader.readLine()) != null;) {
            if (TextUsername.getText().equals(line.split(",")[0])) {
                sw = 0;
                break;
            }
        }
        if (TextUsername.getText().isEmpty() || TextEmail.getText().isEmpty() || TextAddress.getText().isEmpty() || TextPasswordField.getText().isEmpty() || TextPhoneNumber.getText().isEmpty()) {
            sw = -1;
        }
        if (sw == 1) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt", true));
            writer.write(TextUsername.getText() + ",");
            writer.write(TextPasswordField.getText() + ",");
            writer.write(TextPhoneNumber.getText() + ",");
            writer.write(TextAddress.getText() + ",");
            writer.write(TextEmail.getText() + "," + "\n");
            writer.flush();
            writer.close();
            System.out.println(TextAddress.getText());
            UsernameError.setVisible(false);
            UsernameDone.setVisible(true);
            EmptyError.setVisible(false);
        }
        else if (sw == 0) {
            UsernameError.setVisible(true);
        }
        else if (sw == -1) {
            EmptyError.setVisible(true);
        }
        if (sw == 1) {
            wait(3000);
//            Stage stage = (Stage) ButtonOK.getScene().getWindow();
//            Parent root = FXMLLoader.load(getClass().getResource(""));
//            stage.setTitle("");
//            stage.setScene(new Scene(root));
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
