package es.deusto.sd.strava.MAuth;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAuthFacadeM extends Remote {
    String registerUser(String username, String password, String email) throws RemoteException;

    String loginUser(String username, String password, String proveedor) throws RemoteException;

    String getUserInfo(String token) throws RemoteException;

	void logout(String username) throws RemoteException;
}
