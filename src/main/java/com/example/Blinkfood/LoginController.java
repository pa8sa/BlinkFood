package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private Button btOK;

    @FXML
    private Button btSignUp;

    @FXML
    private RadioButton rbtSP;

    @FXML
    private PasswordField txtFieldPW;

    @FXML
    private TextField txtPW;

    @FXML
    private TextField txtUR;

    @FXML
    void Next(MouseEvent event) {
        
    }

    @FXML
    void ShowPass(MouseEvent event) {
        if (rbtSP.isSelected()) {
            txtPW.setText(txtFieldPW.getText());
            txtFieldPW.setVisible(false);
            txtPW.setVisible(true);
        }
        else {
            txtFieldPW.setVisible(true);
            txtPW.setVisible(false);
        }
    }

    @FXML
    void SignUpScene(MouseEvent event) {

    }

}
