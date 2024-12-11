package es.deusto.sd.strava.MAuth;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RemoteAuthFacadeM implements IRemoteAuthFacadeM {

    private final Map<String, String> userStore = new HashMap<>();
    private final Map<String, String> tokenStore = new HashMap<>();
    private final Map<String, String> userInfoStore = new HashMap<>();

    public RemoteAuthFacadeM() throws RemoteException {
        //usuarios de prueba
        userStore.put("user1", "password1");
        userInfoStore.put("user1", "User One, user1@example.com");

        userStore.put("user2", "password2");
        userInfoStore.put("user2", "User Two, user2@example.com");
    }

    @Override
    public String registerUser(String username, String password, String email) throws RemoteException {
        if (userStore.containsKey(username)) {
            return "User already exists";
        }
        userStore.put(username, password);
        userInfoStore.put(username, username + ", " + email);
        return "User registered successfully";
    }

    @Override
    public String loginUser(String username, String password) throws RemoteException {
        if (userStore.containsKey(username) && userStore.get(username).equals(password)) {
            String token = UUID.randomUUID().toString();
            tokenStore.put(token, username);
            return token;
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) throws RemoteException {
        return tokenStore.containsKey(token);
    }

    @Override
    public String getUserInfo(String token) throws RemoteException {
        String username = tokenStore.get(token);
        if (username != null && userInfoStore.containsKey(username)) {
            return userInfoStore.get(username);
        }
        return "Invalid token";
    }
}
