package es.deusto.sd.strava.MAuth;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthServerMeta {

    private static RemoteAuthFacadeM facade;

    public AuthServerMeta() {
        try {
            this.facade = new RemoteAuthFacadeM();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(1101);
                registry.list(); 
                System.out.println("RMI registry already exists.");
            } catch (RemoteException e) {
                System.out.println("Creating new RMI registry.");
                registry = LocateRegistry.createRegistry(1101);
            }

            AuthServerMeta servidor = new AuthServerMeta();

            IRemoteAuthFacadeM stub = (IRemoteAuthFacadeM) UnicastRemoteObject.exportObject(servidor.facade, 0);
            registry.rebind("RemoteAuthFacadeM", stub);
            
            

            System.out.println("AuthServer is ready and waiting for connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
