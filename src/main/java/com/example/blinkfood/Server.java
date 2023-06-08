package com.example.blinkfood;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

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

        public static String readFile(String Type, int lineNumber) {
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
            }
            return null;
        }

        public static int checkFile(String Type, String data) {
            if (Type.equals("UserLogin")) {
                try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PARSA-PC\\BlinkFood\\Files\\UsersInfo.txt"))) {
                    String line = reader.readLine();
                    while (line != null) {
                        System.out.println(line);
                        System.out.println(data);
                        if (line.split(",")[0].equals(data.split(",")[0]) && line.split(",")[1].equals(data.split(",")[1])) {
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
    }
}
