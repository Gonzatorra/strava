package es.deusto.sd.strava.MAuth;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuthClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IRemoteAuthFacade authFacade = (IRemoteAuthFacade) registry.lookup("RemoteAuthFacade");

            //Registrar usuario
            System.out.println(authFacade.registerUser("newuser", "newpass", "newuser@example.com"));

            //LogIn y obtener token
            String token = authFacade.login("newuser", "newpass");
            System.out.println("Token: " + token);

            //validar token
            boolean isValid = authFacade.validateToken(token);
            System.out.println("Token valid: " + isValid);

            //Obtener info usuario
            String userInfo = authFacade.getUserInfo(token);
            System.out.println("User Info: " + userInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
