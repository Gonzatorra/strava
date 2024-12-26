package es.deusto.sd.strava.MAuth;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuthGateway {
    private IRemoteAuthFacade authFacade;

    // Constructor que establece la conexión con el servidor remoto
    public AuthGateway(String host, int port) throws Exception {
        Registry registry = LocateRegistry.getRegistry(host, port);
        this.authFacade = (IRemoteAuthFacade) registry.lookup("RemoteAuthFacade");
    }

    // Métodos que encapsulan las interacciones con el servidor remoto
    public String registerUser(String username, String password, String email) throws Exception {
        return authFacade.registerUser(username, password, email);
    }

    public String login(String username, String password) throws Exception {
        return authFacade.login(username, password);
    }

    public boolean validateToken(String token) throws Exception {
        return authFacade.validateToken(token);
    }

    public String getUserInfo(String token) throws Exception {
        return authFacade.getUserInfo(token);
    }
}
