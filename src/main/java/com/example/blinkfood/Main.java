package com.example.blinkfood;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        Socket socket = new Socket("localhost", 8080);
        System.out.println("Connected to server");

        // Prepare to send data to server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

//        // Close the socket and reader
//        userInputReader.close();
//        socket.close();
        }

    public static void main(String[] args) {
        launch();
    }
}