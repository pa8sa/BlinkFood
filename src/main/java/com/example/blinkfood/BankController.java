package com.example.blinkfood;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BankController implements Initializable {
    private Restaurant selectedRestaurant;

    private double TotalCost;

    @FXML
    private Button BackButton;

    @FXML
    private Text Balance;

    @FXML
    private ToggleGroup Bank;

    @FXML
    private Text Cost;

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

    @FXML
    void Done(MouseEvent event) throws IOException {
        if (NormalBank.isSelected()) {
            Bank_Normal NBank = new Bank_Normal(TotalCost, Server.user.getBalance());
            if (NBank.Buy(NBank.getBalance(), NBank.getCost()) != -1) {
                NBank.setBalance(NBank.Buy(NBank.getBalance(), NBank.getCost()));
                Server.ClientHandler.EditUser(Server.user.getUserName(), NBank.getBalance());
                BackMainMenu();
            }
        }
        else if (LimitedBank.isSelected()) {
            Bank_Limit LBank = new Bank_Limit(TotalCost, Server.user.getBalance());
            if (LBank.Buy(LBank.getBalance(), LBank.getCost()) != -1) {
                LBank.setBalance(LBank.Buy(LBank.getBalance(), LBank.getCost()));
                Server.ClientHandler.EditUser(Server.user.getUserName(), LBank.getBalance());
                BackMainMenu();
            }
        }
        else if (TaxBank.isSelected()) {
            Bank_Tax TBank = new Bank_Tax(TotalCost, Server.user.getBalance());
            if (TBank.Buy(TBank.getBalance(), TBank.getCost()) != -1) {
                TBank.setBalance(TBank.Buy(TBank.getBalance(), TBank.getCost()));
                Server.ClientHandler.EditUser(Server.user.getUserName(), TBank.getBalance());
                BackMainMenu();
            }
        }
    }

    private void BackMainMenu () throws IOException {
        Server.ClientHandler.ResetRestaurants();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) TaxBank.getScene().getWindow();
        stage.setTitle("MainMenu");
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Balance.setText(String.valueOf(Server.user.getBalance()));
    }

    public void setTotalCost (double TotalCost) {
        this.TotalCost = TotalCost;
    }

    public void setRestaurant (Restaurant restaurant) {
        selectedRestaurant = restaurant;
    }

    @FXML
    void onToggleButtonClick(MouseEvent event) {
        if (LimitedBank.isSelected()) {
            Cost.setText(String.valueOf(TotalCost));
        } else if (NormalBank.isSelected()) {
            Cost.setText(String.valueOf(TotalCost));
        } else if (TaxBank.isSelected()) {
            Cost.setText(String.valueOf(TotalCost + 0.05 * TotalCost));
        }
    }

}
