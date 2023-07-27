package com.example.blinkfood;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {
    public static User user = new User();
    public static ArrayList<Restaurant> Restaurants = new ArrayList<>();
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blinkfood", "root", "pa8sa1016");
        statement = connection.createStatement();


        final int PORT = 8080;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle the client request
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public static String addUser (String userName, String phoneNumber, String email, String passWord, String address) throws SQLException {
            if (passWord.length() < 8) {
                return "passWordLength";
            }

            if (!email.endsWith("@gmail.com") && email.length() < 11) {
                return "emailWrong";
            }

            if(!phoneNumber.matches("\\d+")) {
                return "phoneNumberWrong";
            }

            String sqlQuery = "SELECT Username FROM users WHERE username = '" + userName + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "userNameTaken";
            }

            sqlQuery = "SELECT Username FROM users WHERE PhoneNumber = '" + phoneNumber + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "phoneNumberTaken";
            }

            sqlQuery = "SELECT Username FROM users WHERE Email = '" + email + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "emailTaken";
            }

            sqlQuery = "INSERT INTO users VALUES(" + userName + ", " + phoneNumber + ", " + email + ", " + passWord + ", " + address + ", " + 0 + ");";
            statement.executeQuery(sqlQuery);
            return "done";
        }

        public static String addRestaurants (String name, String address, String workTime, String type, int chairDeliveryCount, Boolean enable, String imgPath) throws SQLException {
            String sqlQuery = "INSERT INTO restaurants VALUES(" + Restaurants.size() + ", " + name + ", " + address + ", " + workTime + ", " + type + ", "
                    + chairDeliveryCount + ", " + enable + ", " + imgPath + ");";
            statement.executeQuery(sqlQuery);
        }

        public static String checkUsers (String userName, String passWord) throws SQLException {
            String sqlQuery = "SELECT Username, Pass FROM users WHERE username = '" + userName + "'" + " AND '" + passWord + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (!resultSet.next()) {
                return "wrong";
            }
            else if (resultSet.getString("Username").equals("Admin") && resultSet.getString("Pass").equals("adminadmin")) {
                return "admin";
            }
            else return "done";
        }

        public static ArrayList<Restaurant> getRestaurants () throws SQLException {
            Restaurants.clear();
            String sqlQuery = "SELECT * FROM restaurants;";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Restaurants.add(new Restaurant(resultSet.getString("Name"), resultSet.getString("Address"),
                        resultSet.getString("WorkTime"), resultSet.getString("Res_Type"),
                        resultSet.getInt("Chair_Delivery_Count"), resultSet.getBoolean("Enable"),
                        resultSet.getString("IMGpath"), resultSet.getInt("Res_id")));
                String sqlQuery2 = "SELECT * FROM foods WHERE Food_id = '" + resultSet.getInt("Res_id") + "';";
                ResultSet resultSet2 = statement.executeQuery(sqlQuery2);
                while (resultSet2.next()) {
                    Restaurants.get(Restaurants.size() - 1).getFoods().add(new Food(resultSet2.getString("Name"),
                            resultSet2.getDouble("Price"), resultSet2.getDouble("Weight"),
                            resultSet2.getString("Food_Type"), resultSet2.getString("IMGpath"),
                            resultSet2.getInt("Food_id")));
                }
            }
            return Restaurants;
        }

        public static void editBalance (String userName, double newBalance) throws SQLException {
            String sqlQuery = "UPDATE users SET Balance = '" + newBalance + "' WHERE Username = '" + userName + "';";
            statement.executeQuery(sqlQuery);
        }

        public static void ResetRestaurants () {
            for (int i = 0; i < Restaurants.size(); i++) {
                for (int j = 0; j < Restaurants.get(i).getFoods().size(); j++) {
                    Restaurants.get(i).getFoods().get(j).setCount(0);
                }
            }
        }
    }
}
