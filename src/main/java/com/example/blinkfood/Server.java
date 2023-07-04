package com.example.blinkfood;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static User user = new User();
    public static ArrayList<Restaurant> Restaurants = new ArrayList<>();
    public static int j = 0;
    public static int k = 0;
    public static int AllCount = 0;

    public static void main(String[] args) {
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

        @Override
        public void run() {
            try (Scanner in = new Scanner(clientSocket.getInputStream());
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                while (true) {
                    String Type = in.nextLine();
                    String data = in.nextLine();

                    if (data.equals("End")) {
                        out.println("End command received. Server stopped.");
                        break;
                    }

                    writeToFile(Type, data);
                    out.println("String received and written to file.");
                }

                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static int writeToFile(String Type, String data) {
            if (Type.equals("UserSignUp")) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt", true))) {

                    if (data.split(",")[0].isEmpty() || data.split(",")[1].isEmpty() || data.split(",")[2].isEmpty() || data.split(",")[3].isEmpty() || data.split(",")[4].isEmpty()) {
                        return -1;
                    }

                    String line = Server.ClientHandler.readFile("UserSignUp", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[0].equals(data.split(",")[0])) {
                            return -2;
                        }
                        line = Server.ClientHandler.readFile("UserSignUp", i);
                    }

                    if (data.split(",")[4].length() <= 10 || !data.split(",")[4].endsWith("@gmail.com")) {
                        return -3;
                    }

                    if (!data.split(",")[2].matches("\\d+") || data.split(",")[2].length() != 11) {
                        return -4;
                    }

                    if (data.split(",")[1].length() <= 7) {
                        return -5;
                    }

                    line = Server.ClientHandler.readFile("UserSignUp", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[2].equals(data.split(",")[2])) {
                            return -6;
                        }
                        line = Server.ClientHandler.readFile("UserSignUp", i);
                    }

                    line = Server.ClientHandler.readFile("UserSignUp", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[4].equals(data.split(",")[4])) {
                            return -7;
                        }
                        line = Server.ClientHandler.readFile("UserSignUp", i);
                    }

                    user.setUserName(data.split(",")[0]);
                    user.setPassWord(data.split(",")[1]);
                    user.setPhoneNumber(data.split(",")[2]);
                    user.setAddress(data.split(",")[3]);
                    user.setEmail(data.split(",")[4]);
                    user.setBalance(0);

                    writer.write(data + ",0" + "\n");
                    writer.flush();
                    writer.close();

                    return 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Type.equals("AddRestaurant")) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt", true))) {

                    String line = Server.ClientHandler.readFile("Restaurants", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[0].equals(data.split(",")[0])) {
                            return -2;
                        }
                        line = Server.ClientHandler.readFile("Restaurants", i);
                    }

                    if (!data.split(",")[4].matches("\\d+") || !data.split(",")[5].matches("\\d+")) {
                        return -4;
                    }

                    writer.write(data.split(",")[0] + "," + data.split(",")[1] + "," + data.split(",")[2] + "," +
                            data.split(",")[3] + "," + data.split(",")[4] + "," + data.split(",")[5] + "," + "true" +
                            "," + data.split(",")[6] + "\n");
                    writer.flush();
                    writer.close();

                    return 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Type.equals("AddFood")) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt", true))) {

                    String line = Server.ClientHandler.readFile("Foods", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[0].equals(data.split(",")[0])) {
                            return -2;
                        }
                        line = Server.ClientHandler.readFile("Foods", i);
                    }

                    if (!data.split(",")[1].matches("[0-9.]+") || !data.split(",")[2].matches("[0-9.]+")) {
                        return -4;
                    }

                    writer.write(data + "\n");
                    writer.flush();
                    writer.close();

                    return 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return 0;
        }

            public static String readFile (String Type,int lineNumber){
                if (Type.equals("UserSignUp")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"))) {
                        String line;
                        int currentLineNumber = -1;
                        while ((line = reader.readLine()) != null) {
                            currentLineNumber++;
                            if (currentLineNumber == lineNumber) {
                                return line;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (Type.equals("Restaurants")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"))) {
                        String line;
                        int currentLineNumber = -1;
                        while ((line = reader.readLine()) != null) {
                            currentLineNumber++;
                            if (currentLineNumber == lineNumber) {
                                return line;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (Type.equals("Foods")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"))) {
                        String line;
                        int currentLineNumber = -1;
                        while ((line = reader.readLine()) != null) {
                            currentLineNumber++;
                            if (currentLineNumber == lineNumber) {
                                return line;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            public static int checkFile (String Type, String data){
                if (Type.equals("UserLogin")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"))) {
                        if (data.split(",")[0].equals("Admin") && data.split(",")[1].equals("adminadmin")) {
                            return 10;
                        }
                        String line = reader.readLine();
                        while (line != null) {
                            if (line.split(",")[0].equals(data.split(",")[0]) && line.split(",")[1].equals(data.split(",")[1])) {
                                user.setUserName(line.split(",")[0]);
                                user.setPassWord(line.split(",")[1]);
                                user.setPhoneNumber(line.split(",")[2]);
                                user.setAddress(line.split(",")[3]);
                                user.setEmail(line.split(",")[4]);
                                user.setBalance(Double.parseDouble(line.split(",")[5]));
                                return 1;
                            }
                            line = reader.readLine();
                        }
                        return -1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }

            public static ArrayList<Restaurant> getRestaurants () {
            k = 0;
                Restaurants.clear();
                String line;
                for (int i = 0; (line = readFile("Restaurants", i)) != null; i++, k++) {
                    if (line.split(",")[6].equals("true")) {
                        Restaurants.add(new Restaurant(line.split(",")[0], line.split(",")[1], line.split(",")[2], line.split(",")[3], Integer.parseInt(line.split(",")[4]), Integer.parseInt(line.split(",")[5]), Boolean.valueOf(line.split(",")[6]), line.split(",")[7]));
                        String line2;
                        AllCount += Restaurants.get(k).getFoodsCount();
                        for (; j < AllCount && (line2 = readFile("Foods", j)) != null; j++) {
                            Restaurants.get(k).addFood(new Food(line2.split(",")[0], Double.parseDouble(line2.split(",")[1]), Double.parseDouble(line2.split(",")[2]), line2.split(",")[3], line2.split(",")[4]));
                        }
                    }
                    else {
                        j += Integer.parseInt(line.split(",")[4]);
                        AllCount += Integer.parseInt(line.split(",")[4]);
                        k--;
                    }
                }
                return Restaurants;
            }

        public static ArrayList<Restaurant> AdmingetRestaurants () {
            k = 0;
            Restaurants.clear();
            String line;
            for (int i = 0; (line = readFile("Restaurants", i)) != null; i++, k++) {
                Restaurants.add(new Restaurant(line.split(",")[0], line.split(",")[1], line.split(",")[2], line.split(",")[3], Integer.parseInt(line.split(",")[4]), Integer.parseInt(line.split(",")[5]), Boolean.valueOf(line.split(",")[6]), line.split(",")[7]));
                String line2;
                AllCount += Restaurants.get(k).getFoodsCount();
                for (; j < AllCount && (line2 = readFile("Foods", j)) != null; j++) {
                    Restaurants.get(k).addFood(new Food(line2.split(",")[0], Double.parseDouble(line2.split(",")[1]), Double.parseDouble(line2.split(",")[2]), line2.split(",")[3], line2.split(",")[4]));
                }
            }
            return Restaurants;
        }

            public static void ResetRestaurants () {
                for (int i = 0; i < Restaurants.size(); i++) {
                    for (int j = 0; j < Restaurants.get(i).getFoodsCount(); j++) {
                        Restaurants.get(i).getFoods().get(j).setCount(0);
                    }
                }
            }

            public static void EditUser (String Username,double Balance) throws IOException {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"));
                    StringBuilder content = new StringBuilder();
                    String line;
                    for (; (line = reader.readLine()) != null; ) {
                        if (Username.equals(line.split(",")[0])) {
                            content.append(line.split(",")[0] + "," + line.split(",")[1] + "," + line.split(",")[2] + "," + line.split(",")[3] + "," + line.split(",")[4] + "," + Balance + "\n");
                        } else content.append(line + "\n");
                    }
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"));
                    writer.write(content.toString());
                    writer.close();
                    user.setBalance(Balance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public static int EditRestaurant (String OldName, String data) throws IOException{
                if (data.equals("false")) {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    StringBuilder content = new StringBuilder();
                    String line;
                    for (; (line = reader.readLine()) != null; ) {
                        if (OldName.equals(line.split(",")[0])) {
                            content.append(line.split(",")[0] + "," + line.split(",")[1] + "," + line.split(",")[2] + "," + line.split(",")[3] +
                                    "," + line.split(",")[4] + "," + line.split(",")[5] + ",false," + line.split(",")[7] +"\n");
                        } else content.append(line + "\n");
                    }
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    writer.write(content.toString());
                    writer.close();
                    return 1;
                }
                else {
                    try {
                        String line = Server.ClientHandler.readFile("Restaurants", 0);
                        for (int i = 1; line != null; i++) {
                            if (line.split(",")[0].equals(data.split(",")[0]) && !OldName.equals(data.split(",")[0])) {
                                return -2;
                            }
                            line = Server.ClientHandler.readFile("Restaurants", i);
                        }

                        if (!data.split(",")[4].matches("\\d+")) {
                            return -4;
                        }

                        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                        StringBuilder content = new StringBuilder();

                        for (line = null; (line = reader.readLine()) != null; ) {
                            if (OldName.equals(line.split(",")[0])) {
                                content.append(data.split(",")[0] + "," + data.split(",")[1] + "," + data.split(",")[2] + "," + data.split(",")[3] +
                                        "," + line.split(",")[4] + "," + data.split(",")[4] + ",true," + data.split(",")[5] +"\n");
                            } else content.append(line + "\n");
                        }
                        reader.close();
                        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                        writer.write(content.toString());
                        writer.close();

                        return 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }

            public static void RemoveRestaurant (String Name) throws IOException {
                BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                StringBuilder content = new StringBuilder();
                StringBuilder contentFoods = new StringBuilder();
                String line;
                String lineFoods;
                int FoodLine = 0;

                for (; (line = reader.readLine()) != null;) {
                    if (!line.split(",")[0].equals(Name)) {
                        content.append(line + "\n");
                        for (int limit = FoodLine + Integer.parseInt(line.split(",")[4]); FoodLine < limit; FoodLine++) {
                            lineFoods = readFile("Foods", FoodLine);
                            contentFoods.append(lineFoods + "\n");
                        }
                    }
                    else {
                        FoodLine += Integer.parseInt(line.split(",")[4]);
                    }
                }
                reader.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                writer.write(content.toString());
                writer.close();
                BufferedWriter writerFoods = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                writerFoods.write(contentFoods.toString());
                writerFoods.close();
            }

            public static int EditFoods (String Type, String OldName, String data, Restaurant restaurant) throws IOException {
                if (Type.equals("Remove")) {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    StringBuilder content = new StringBuilder();
                    String line;
                    for (line = null; (line = reader.readLine()) != null; ) {
                        if (!OldName.equals(line.split(",")[0])) {
                            content.append(line + "\n");
                        }
                    }
                    restaurant.setFoodsCount(restaurant.getFoodsCount() - 1);
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    writer.write(content.toString());
                    writer.close();

                    BufferedReader readerRestaurants = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    StringBuilder contentRestaurants = new StringBuilder();
                    for (line = null; (line = readerRestaurants.readLine()) != null; ) {
                        if (!restaurant.getName().equals(line.split(",")[0])) {
                            contentRestaurants.append(line + "\n");
                        }
                        else {
                            contentRestaurants.append(line.split(",")[0] + "," + line.split(",")[1] + "," + line.split(",")[2] + "," +
                                    line.split(",")[3] + "," + (Integer.parseInt(line.split(",")[4]) - 1) + "," + line.split(",")[5] +
                                    "," + line.split(",")[6] + "," + line.split(",")[7] +"\n");
                        }
                    }
                    readerRestaurants.close();
                    BufferedWriter writerRestaurants = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    writerRestaurants.write(contentRestaurants.toString());
                    writerRestaurants.close();
                    return 1;
                }
                else if (Type.equals("Edit")) {

                    if (data.split(",")[0].isEmpty() || data.split(",")[1].isEmpty() || data.split(",")[2].isEmpty() || data.split(",")[3].isEmpty()) {
                        return -1;
                    }

                    String line = Server.ClientHandler.readFile("Foods", 0);
                    for (int i = 1; line != null; i++) {
                        if (line.split(",")[0].equals(data.split(",")[0])) {
                            return -2;
                        }
                        line = Server.ClientHandler.readFile("Restaurants", i);
                    }

                    if (!data.split(",")[1].matches("[0-9.]+") || !data.split(",")[2].matches("[0-9.]+")) {
                        return -4;
                    }

                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    StringBuilder content = new StringBuilder();

                    for (line = null; (line = reader.readLine()) != null; ) {
                        if (OldName.equals(line.split(",")[0])) {
                            content.append(data.split(",")[0] + "," + data.split(",")[1] + "," + data.split(",")[2] + "," +
                                    data.split(",")[3] + "," + data.split(",")[4] + "\n");
                        } else content.append(line + "\n");
                    }
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    writer.write(content.toString());
                    writer.close();

                    return 1;
                }
                else if (Type.equals("Add")) {
                    BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    StringBuilder contentRestaurants = new StringBuilder();
                    String line;
                    int Count = 0;
                    for (int sw = 0; (line = reader.readLine()) != null; ) {
                        if (line.split(",")[0].equals(restaurant.getName()) && sw == 0) {
                            Count += Integer.parseInt(line.split(",")[4]);
                            sw = 1;
                            contentRestaurants.append(line.split(",")[0] + "," + line.split(",")[1] + "," + line.split(",")[2] + "," +
                                    line.split(",")[3] + "," + (Integer.parseInt(line.split(",")[4]) + 1) + "," + line.split(",")[5] +
                                    "," + line.split(",")[6] + "," + line.split(",")[7] + "\n");
                        }
                        else if (sw == 0){
                            Count += Integer.parseInt(line.split(",")[4]);
                            contentRestaurants.append(line + "\n");
                        }
                        else if (sw == 1) {
                            contentRestaurants.append(line + "\n");
                        }
                    }
                    BufferedWriter writerRestaurants = new BufferedWriter (new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Restaurants.txt"));
                    writerRestaurants.write(contentRestaurants.toString());
                    writerRestaurants.close();
                    reader.close();

                    restaurant.setFoodsCount(restaurant.getFoodsCount() + 1);

                    StringBuilder content = new StringBuilder();
                    BufferedReader readerFoods = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    for (int i = 0; i < Count; i++) {
                        line = readerFoods.readLine();
                        content.append(line + "\n");
                    }
                    content.append(data + "\n");
                    for (; (line = readerFoods.readLine()) != null;) {
                        content.append(line + "\n");
                    }
                    readerFoods.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\Foods.txt"));
                    writer.write(content.toString());
                    writer.close();
                    return 1;
                }
                return 0;
            }
        }
    }
