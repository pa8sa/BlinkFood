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
    private static PreparedStatement preparedStatement;


    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blinkfood", "root", "pa8sa1016");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
//        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blinkfood", "root", "pa8sa1016");
//        statement = connection.createStatement();


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

            String sqlQuery = "SELECT Username FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "userNameTaken";
            }

            sqlQuery = "SELECT Username FROM users WHERE PhoneNumber = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, phoneNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "phoneNumberTaken";
            }

            sqlQuery = "SELECT Username FROM users WHERE Email = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "emailTaken";
            }

            sqlQuery = "INSERT INTO users VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, passWord);
            preparedStatement.setString(5, address);
            preparedStatement.setDouble(6, 0);
            preparedStatement.executeUpdate();
            user = new User(userName, passWord, phoneNumber, address, email, 0);
            return "done";
        }

        public static String addRestaurants (String name, String address, String workTime, String type, String chairDeliveryCount, String imgPath) throws SQLException {
            if (!type.equals("BaMiz") && !type.equals("BiroonBar")) {
                return "typeWrong";
            }

            if (!chairDeliveryCount.matches("\\d+")) {
                return "chairDeliveryWrong";
            }

            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            String sqlQuery = "SELECT Name FROM restaurants WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "nameTaken";
            }

            sqlQuery = "SELECT Name FROM restaurants WHERE Address = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, address);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "addressTaken";
            }

            sqlQuery = "SELECT Name FROM restaurants WHERE IMGpath = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, imgPath);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "imgPathTaken";
            }

            sqlQuery = "SELECT MAX(Res_id) FROM restaurants";
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            sqlQuery = "INSERT INTO restaurants VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id + 1);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, workTime);
            preparedStatement.setString(5, type);
            preparedStatement.setInt(6, Integer.parseInt(chairDeliveryCount));
            preparedStatement.setBoolean(7, true);
            preparedStatement.setString(8, imgPath.replace("\\", "/"));
            preparedStatement.executeUpdate();
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

            if (!type.equals("Sonati") && !type.equals("FastFood")) {
                return "typeWrong";
            }

            String sqlQuery = "SELECT Name FROM foods WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "nameTaken";
            }

            sqlQuery = "SELECT Name FROM foods WHERE IMGpath = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, imgPath);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return "imgPathTaken";
            }

            sqlQuery = "INSERT INTO foods VALUES(?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, food_id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, Double.parseDouble(price));
            preparedStatement.setDouble(4, Double.parseDouble(weight));
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, imgPath.replace("\\", "/"));
            preparedStatement.executeUpdate();
            return "done";
        }

        public static void removeFood (String name) throws SQLException {
            String sqlQuery = "DELETE FROM foods WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
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

            if (!type.equals("Sonati") && !type.equals("FastFood")) {
                return "typeWrong";
            }

            String sqlQuery = "SELECT Name FROM foods WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !oldName.equals(name)) {
                return "nameTaken";
            }

            String sqlQuery2 = "SELECT IMGpath FROM foods WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, oldName);
            ResultSet resultSet2 = preparedStatement.executeQuery();
            resultSet2.next();
            sqlQuery = "SELECT Name FROM foods WHERE IMGpath = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, imgPath);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !resultSet2.getString("IMGpath").equals(imgPath)) {
                return "imgPathTaken";
            }

            sqlQuery = "UPDATE foods SET Food_id = ?, Name = ?, Price = ?, Weight = ?, Food_Type = ?, IMGpath = ? WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, food_id);
            preparedStatement.setString(2, name);
            preparedStatement.setDouble(3, Double.parseDouble(price));
            preparedStatement.setDouble(4, Double.parseDouble(weight));
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, imgPath.replace("\\", "/"));
            preparedStatement.executeUpdate();
            return "done";
        }

        public static String checkUsers (String userName, String passWord) throws SQLException {
            if (userName.equals("Admin") && passWord.equals("adminadmin")) {
                return "admin";
            }
            String sqlQuery = "SELECT Username, Pass, PhoneNumber, Email, Address, Balance FROM users WHERE Username = ? AND Pass = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return "wrong";
            }
            user = new User(userName, passWord, resultSet.getString("PhoneNumber"), resultSet.getString("Address"), resultSet.getString("Email"), resultSet.getDouble("Balance"));
            return "done";
        }

        public static ArrayList<Restaurant> getRestaurants() throws SQLException {
            Restaurants.clear();
            String sqlQuery = "SELECT * FROM restaurants";
            preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Restaurants.add(new Restaurant(resultSet.getString("Name"), resultSet.getString("Address"),
                        resultSet.getString("WorkTime"), resultSet.getString("Res_Type"),
                        resultSet.getInt("Chair_Delivery_Count"), resultSet.getBoolean("Enable"),
                        resultSet.getString("IMGpath"), resultSet.getInt("Res_id")));
                String sqlQuery2 = "SELECT * FROM foods WHERE Food_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2);
                preparedStatement2.setInt(1, resultSet.getInt("Res_id"));
                ResultSet resultSet2 = preparedStatement2.executeQuery();
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
            String sqlQuery = "UPDATE users SET Balance = ? WHERE Username = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setString(2, userName);
            preparedStatement.executeQuery();
        }

        public static void resetRestaurants () {
            for (int i = 0; i < Restaurants.size(); i++) {
                for (int j = 0; j < Restaurants.get(i).getFoods().size(); j++) {
                    Restaurants.get(i).getFoods().get(j).setCount(0);
                }
            }
        }

        public static void removeRestaurant (String name) throws SQLException {
            String sqlQuery = "DELETE FROM restaurants WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }

        public static String editRestaurant (String oldName, String name, String address, String workTime, String type, String chairDeliveryCount, String imgPath) throws SQLException {
            if (!type.equals("BaMiz") && !type.equals("BiroonBar")) {
                return "typeWrong";
            }

            if (!chairDeliveryCount.matches("\\d+")) {
                return "chairDeliveryWrong";
            }

            if (!imgPath.endsWith(".jpg") || imgPath.length() < 5) {
                return "imgPathWrong";
            }

            String sqlQuery = "SELECT Name FROM restaurants WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !oldName.equals(name)) {
                return "nameTaken";
            }

            String sqlQuery2 = "SELECT Address FROM restaurants WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, oldName);
            ResultSet resultSet2 = preparedStatement.executeQuery();
            resultSet2.next();
            sqlQuery = "SELECT Name FROM restaurants WHERE Address = ?";
            preparedStatement = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, address);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !resultSet2.getString("Address").equals(address)) {
                return "addressTaken";
            }

            sqlQuery2 = "SELECT IMGpath FROM restaurants WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, oldName);
            resultSet2 = preparedStatement.executeQuery();
            resultSet2.next();
            sqlQuery = "SELECT Name FROM restaurants WHERE IMGpath = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, imgPath);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !resultSet2.getString("IMGpath").equals(imgPath)) {
                return "imgPathTaken";
            }

            sqlQuery = "UPDATE restaurants SET Name = ?, Address = ?, Worktime = ?, Res_Type = ?, Chair_Delivery_Count = ?, IMGpath = ? WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, workTime);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, Integer.parseInt(chairDeliveryCount));
            preparedStatement.setString(6, imgPath.replace("\\", "/"));
            preparedStatement.setString(7, oldName);
            preparedStatement.executeUpdate();
            return "done";
        }

        public static void setEnableDisable (String name, String value) throws SQLException {
            String sqlQuery = "UPDATE restaurants SET Enable = ? WHERE Name = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }
    }
}
