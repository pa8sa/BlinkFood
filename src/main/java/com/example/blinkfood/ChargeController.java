package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChargeController {

    @FXML
    private TextField AmountTextField;

    @FXML
    private Button BackButton;

    @FXML
    private Button DoneButton;

    @FXML
    private RadioButton PrivacyRadioButton;

    @FXML
    void Back(MouseEvent event) {

    }

    @FXML
    void Done(MouseEvent event) {
        if (PrivacyRadioButton.isSelected()) {
            Server.user.AddToBalance(Integer.parseInt(AmountTextField.getText()));
        }
    }
}
