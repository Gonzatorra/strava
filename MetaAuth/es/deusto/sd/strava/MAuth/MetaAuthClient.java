package es.deusto.sd.strava.MAuth;

import java.io.*;
import java.net.*;

public class MetaAuthClient {
    private final String host;
    private final int port;

    public MetaAuthClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sendRequest(String request) throws IOException {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            return in.readLine();
        }
    }

    public String registerUser(String username, String password, String email) throws IOException {
        return sendRequest("REGISTER;" + username + ";" + password + ";" + email);
    }

    public String login(String username, String password) throws IOException {
        return sendRequest("LOGIN;" + username + ";" + password);
    }

    public boolean validateToken(String token) throws IOException {
        return Boolean.parseBoolean(sendRequest("VALIDATE;" + token));
    }

    public String getUserInfo(String token) throws IOException {
        return sendRequest("GETINFO;" + token);
    }
    
    public void logout(String username) throws IOException {
        sendRequest("LOGOUT;" + username);
        System.out.println("Logout exitoso para el usuario: " + username);
    }
}
