package com.company;

import com.example.webchat.ChatMessage;
import com.example.webchat.Message;
import com.example.webchat.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    private static Connection databaseConnection;
    private static ServerSocket socket;
    private static ArrayList<User> users;
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/android_chat",
                    "root",
                    "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        users = new ArrayList<>();

        try {
            socket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new ChatListener()).start();
        
    }
    
    private static class ChatListener implements Runnable{

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    System.out.println("Listening...");
                    Socket client = socket.accept();
                    System.out.println("Connection established!");
                    ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                
                    Message inputMessage = (Message)inputStream.readObject();


                    switch(inputMessage.getMethod()){
                        case METHOD_LOGIN:

                            System.out.println("Login request!");
                            System.out.println("Login: " + inputMessage.getLogin());
                            System.out.println("Password: " + inputMessage.getPassword());

                            User outputUser = null;

                            users = getUsers();

                            for (User user: users){
                                if (user.getLogin().equals(inputMessage.getLogin()) && user.getPassword().equals(inputMessage.getPassword())){
                                    outputUser = user;
                                    break;
                                }
                            }

                            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                            outputStream.writeObject(new Message(outputUser));

                            if (outputUser != null){
                                PreparedStatement statement = databaseConnection.prepareStatement(
                                        "update users set isOnline = true where id = ?");
                                statement.setLong(1, outputUser.getId());
                                statement.executeUpdate();
                            }

                            outputStream.close();
                            break;
                            
                        case METHOD_REGISTRATION:

                            System.out.println("Registration request!");
                            System.out.println("Login: " + inputMessage.getLogin());
                            System.out.println("Password: " + inputMessage.getPassword());
                            System.out.println("Nickname: " + inputMessage.getNickname());

                            String login = inputMessage.getLogin();
                            String password = inputMessage.getPassword();
                            String nickname = inputMessage.getNickname();
                            
                            PreparedStatement registerStatement = databaseConnection.prepareStatement(
                                    "insert into users (login, password, nickname) values (?, ?, ?)");

                            registerStatement.setString(1, login);
                            registerStatement.setString(2, password);
                            registerStatement.setString(3, nickname);

                            registerStatement.executeUpdate();
                            break;

                        case METHOD_SEND_MESSAGE:
                            PreparedStatement sendMessageStatement = databaseConnection.prepareStatement(
                                    "insert into messages (author, text) values (?, ?)");

                            sendMessageStatement.setString(1, inputMessage.getNewMessage().getAuthor());
                            sendMessageStatement.setString(2, inputMessage.getNewMessage().getText());

                            sendMessageStatement.executeUpdate();

                            break;

                        case METHOD_GET_MESSAGES:
                            PreparedStatement getMessagesStatement = databaseConnection.prepareStatement(
                                    "select * from messages where id > ?");
                            getMessagesStatement.setLong(1, inputMessage.getFromId());

                            ResultSet messagesResult = getMessagesStatement.executeQuery();

                            ArrayList<ChatMessage> newMessages = new ArrayList<>();
                            while (messagesResult.next()){
                                newMessages.add(new ChatMessage(
                                        messagesResult.getLong(1),
                                        messagesResult.getString(2),
                                        messagesResult.getString(3)));
                            }

                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                            objectOutputStream.writeObject(new Message(newMessages));
                            objectOutputStream.close();
                    }

                    inputStream.close();

                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
                
            }
        }
    }
    
    private static ArrayList<User> getUsers() throws SQLException{
        ArrayList<User> users = new ArrayList<>();
        
        Statement statement = databaseConnection.createStatement();

        ResultSet result = statement.executeQuery("select * from users");

        while (result.next()){
            Long id = result.getLong(1);
            String login = result.getString(2);
            String password = result.getString(3);
            String nickname = result.getString(4);
            boolean isOnline = result.getBoolean(5);
            users.add(new User(id, login, password, nickname, isOnline));
        }
        
        return users;
    }
}
