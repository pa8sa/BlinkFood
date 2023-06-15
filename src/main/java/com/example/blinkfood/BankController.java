package com.example.blinkfood;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BankController implements Initializable {

    @FXML
    private Button BackButton;

    @FXML
    private Text Balance;

    @FXML
    private ToggleGroup Bank;

    @FXML
    private Text Cost;

    @FXML
    private Text CostWithTax;

    @FXML
    private Button DoneButton;

    @FXML
    private ToggleButton LimitedBank;

    @FXML
    private ToggleButton NormalBank;

    @FXML
    private ToggleButton TaxBank;

    @FXML
    void Back(MouseEvent event) {

    }

//    @FXML
//    void Done(MouseEvent event) {
//        if (NormalBank.isSelected()) {
//            Bank_Normal NBank = new Bank_Normal(PaymentController.getTotalCost(), Server.user.getBalance());
//            if (NBank.Buy(NBank.getBalance(), NBank.getCost()) != -1) {
//                NBank.setBalance(NBank.Buy(NBank.getBalance(), NBank.getCost()));
//            }
//        }
//        else if (LimitedBank.isSelected()) {
//            Bank_Limit LBank = new Bank_Limit(PaymentController.getTotalCost(), Server.user.getBalance());
//            if (LBank.Buy(LBank.getBalance(), LBank.getCost()) != -1) {
//                LBank.setBalance(LBank.Buy(LBank.getBalance(), LBank.getCost()));
//            }
//        }
//        else if (TaxBank.isSelected()) {
//            Bank_Limit TBank = new Bank_Limit(PaymentController.getTotalCost(), Server.user.getBalance());
//            if (TBank.Buy(TBank.getBalance(), TBank.getCost()) != -1) {
//                TBank.setBalance(TBank.Buy(TBank.getBalance(), TBank.getCost()));
//            }
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Balance.setText(String.valueOf(Server.user.getBalance()));
//        Cost.setText();
    }

//    private void setTotalCost (double TotalCost) {
//        Cost = TotalCost;
//        CostWithTax = TotalCost + TotalCost *
//    }
}
