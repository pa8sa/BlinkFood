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

        public static String addRestaurants (String name, String address, String workTime, String type, String chairDeliveryCount, String imgPath) throws SQLException {
            if (!type.equals("BaMiz") || !type.equals("BiroonBar")) {
                return "typeWrong";
            }

            if (!chairDeliveryCount.matches("\\d+")) {
                return "chairDeliveryWrong";
            }

            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            String sqlQuery = "SELECT Name FROM restaurants WHERE Name = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "nameTaken";
            }

            sqlQuery = "SELECT Name FROM restaurants WHERE Address = '" + address + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "addressTaken";
            }

            sqlQuery = "SELECT Name FROM restaurants WHERE IMGpath = '" + imgPath + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "imgPathTaken";
            }

            sqlQuery = "INSERT INTO restaurants VALUES(" + Restaurants.size() + ", " + name + ", " + address + ", " + workTime + ", " + type + ", "
                    + chairDeliveryCount + ", " + "true" + ", " + imgPath + ");";
            statement.executeQuery(sqlQuery);
            return "done";
        }

        public static String addFood (String name, String price, String weight, String type, String imgPath, int food_id) throws SQLException {
            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            if (!price.matches("^[0-9.]+$")) {
                return "priceWrong";
            }

            if (!weight.matches("^[0-9.]+$")) {
                return "weightWrong";
            }

            if (!type.equals("Sonati") || !type.equals("FastFood")) {
                return "typeWrong";
            }

            String sqlQuery = "SELECT Name FROM foods WHERE Name = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "nameTaken";
            }

            sqlQuery = "SELECT Name FROM foods WHERE IMGpath = '" + imgPath + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next()) {
                return "imgPathTaken";
            }

            sqlQuery = "INSERT INTO restaurants VALUES(" + food_id + ", " + name + ", " + price + ", " + weight + ", " + type + ", " + imgPath + ");";
            statement.executeQuery(sqlQuery);
            return "done";
        }

        public static void removeFood (String name) throws SQLException {
            String sqlQuery = "DELETE FROM foods WHERE Name = '" + name + "';";
            statement.executeQuery(sqlQuery);
        }

        public static String editFood (String oldName, String name, String price, String weight, String type, String imgPath, int food_id) throws SQLException {
            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            if (!price.matches("^[0-9.]+$")) {
                return "priceWrong";
            }

            if (!weight.matches("^[0-9.]+$")) {
                return "weightWrong";
            }

            if (!type.equals("Sonati") || !type.equals("FastFood")) {
                return "typeWrong";
            }

            String sqlQuery = "SELECT Name FROM foods WHERE Name = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next() && !oldName.equals(name)) {
                return "nameTaken";
            }

            String sqlQuery2 = "SELECT IMGpath FROM foods WHERE Name = '" + oldName + "';";
            ResultSet resultSet2 = statement.executeQuery(sqlQuery2);
            resultSet2.next();
            sqlQuery = "SELECT Name FROM foods WHERE IMGpath = '" + imgPath + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next() && !resultSet2.getString("IMGpath").equals(imgPath)) {
                return "imgPathTaken";
            }

            sqlQuery = "UPDATE foods SET Food_id = '" + food_id + "', Name = '" + name + "', Price = '" + price + "', Weight = '" + weight +
            "', Food_Type = '" + type + "', IMGpath = '" + imgPath + "' WHERE Name = '" + oldName + "';";
            statement.executeQuery(sqlQuery);
            return "done";
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

        public static void resetRestaurants () {
            for (int i = 0; i < Restaurants.size(); i++) {
                for (int j = 0; j < Restaurants.get(i).getFoods().size(); j++) {
                    Restaurants.get(i).getFoods().get(j).setCount(0);
                }
            }
        }

        public static void removeRestaurant (String name) throws SQLException {
            String sqlQuery = "DELETE FROM restaurants WHERE Name = '" + name + "';";
            statement.executeQuery(sqlQuery);
        }

        public static String editRestaurant (String oldName, String name, String address, String workTime, String type, String chairDeliveryCount, String imgPath) throws SQLException {
            if (!type.equals("BaMiz") || !type.equals("BiroonBar")) {
                return "typeWrong";
            }

            if (!chairDeliveryCount.matches("\\d+")) {
                return "chairDeliveryWrong";
            }

            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            String sqlQuery = "SELECT Name FROM restaurants WHERE Name = '" + name + "';";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next() && !oldName.equals(name)) {
                return "nameTaken";
            }

            String sqlQuery2 = "SELECT Address FROM restaurants WHERE Name = '" + oldName + "';";
            ResultSet resultSet2 = statement.executeQuery(sqlQuery2);
            resultSet2.next();
            sqlQuery = "SELECT Name FROM restaurants WHERE Address = '" + address + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next() && !resultSet2.getString("Address").equals(address)) {
                return "addressTaken";
            }

            sqlQuery2 = "SELECT IMGpath FROM restaurants WHERE Name = '" + oldName + "';";
            resultSet2 = statement.executeQuery(sqlQuery2);
            resultSet2.next();
            sqlQuery = "SELECT Name FROM restaurants WHERE IMGpath = '" + imgPath + "';";
            resultSet = statement.executeQuery(sqlQuery);
            if (resultSet.next() && !resultSet2.getString("IMGpath").equals(imgPath)) {
                return "imgPathTaken";
            }

            sqlQuery = "UPDATE restaurants SET Name = '" + name + "', Address = '" + address + "', Worktime = '" + workTime + "', Res_Type = '" + type +
            "', Chair_Delivery_Count = '" + chairDeliveryCount + "', IMGpath = '" + imgPath + "' WHERE Name = '" + oldName + "';";
            statement.executeQuery(sqlQuery);
            return "done";
        }

        public static void setEnableDisable (String name, String value) throws SQLException {
            String sqlQuery = "UPDATE restaurants SET Enable = '" + value + "' WHERE Name = '" + name + "';";
            statement.executeQuery(sqlQuery);
        }
    }
}
