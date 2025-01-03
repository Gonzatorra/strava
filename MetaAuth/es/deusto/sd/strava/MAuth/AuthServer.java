package es.deusto.sd.strava.MAuth;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuthServer {
    private static final int PORT = 1101; // Puerto del servidor
    private final Map<String, String> userStore = new HashMap<>();
    private final Map<String, String> tokenStore = new HashMap<>();
    private final Map<String, String> userInfoStore = new HashMap<>();

    public AuthServer() {
        // Usuarios de prueba
        userStore.put("user1", "password1");
        userInfoStore.put("user1", "User One, user1@example.com");

        userStore.put("user2", "password2");
        userInfoStore.put("user2", "User Two, user2@example.com");
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("AuthServer is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String login(String username, String password) {
        if (userStore.containsKey(username) && userStore.get(username).equals(password)) {
            String token = UUID.randomUUID().toString();
            tokenStore.put(token, username);
            return token;
        }
        return "Invalid credentials";
    }

    public synchronized String registerUser(String username, String password, String email) {
        if (userStore.containsKey(username)) {
            return "User already exists";
        }

        userStore.put(username, password);
        userInfoStore.put(username, username + ", " + email);
        return "User registered successfully";
    }

    public synchronized boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    public synchronized String getUserInfo(String token) {
        String username = tokenStore.get(token);
        if (username != null && userInfoStore.containsKey(username)) {
            return userInfoStore.get(username);
        }
        return "Invalid token";
    }
    
    public synchronized String logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);
            return ("Logout successful for token: " + token);
        } else {
            return("Invalid token for logout: " + token);
        }
        
    }
    public static void main(String[] args) {
        new AuthServer().start();
    }
}
