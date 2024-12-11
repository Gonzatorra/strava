package es.deusto.sd.strava.GAuth;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAuthFacadeG extends Remote {
    String registerUser(String username, String password, String email) throws RemoteException;

    String loginUser(String username, String password) throws RemoteException;

    boolean validateToken(String token) throws RemoteException;

    String getUserInfo(String token) throws RemoteException;
}
