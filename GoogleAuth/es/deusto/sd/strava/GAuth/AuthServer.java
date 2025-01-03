package es.deusto.sd.strava.GAuth;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthServer {

    private static RemoteAuthFacade facade;

    public AuthServer() {
        try {
            this.facade = new RemoteAuthFacade();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(1100);
                registry.list(); 
                System.out.println("RMI registry already exists.");
            } catch (RemoteException e) {
                System.out.println("Creating new RMI registry.");
                registry = LocateRegistry.createRegistry(1100);
            }

            AuthServer server = new AuthServer();

            IRemoteAuthFacade stub = (IRemoteAuthFacade) UnicastRemoteObject.exportObject(server.facade, 0);
            registry.rebind("RemoteAuthFacade", stub);

            System.out.println("AuthServer is ready and waiting for connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
