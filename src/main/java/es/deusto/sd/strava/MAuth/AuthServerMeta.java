package es.deusto.sd.strava.MAuth;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthServerMeta {

    private static RemoteAuthFacade facade;

    public AuthServerMeta() {
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
                registry = LocateRegistry.getRegistry(1101);
                registry.list(); 
                System.out.println("RMI registry already exists.");
            } catch (RemoteException e) {
                System.out.println("Creating new RMI registry.");
                registry = LocateRegistry.createRegistry(1101);
            }

            AuthServerMeta servidor = new AuthServerMeta();

            IRemoteAuthFacade stub = (IRemoteAuthFacade) UnicastRemoteObject.exportObject(servidor.facade, 0);
            registry.rebind("RemoteAuthFacade", stub);
            
            facade.registerUser("maria123", "claveMaria", "maria123@gmail.com");
            facade.registerUser("jose456", "claveJose", "jose456@gmail.com");
            facade.registerUser("lucia789", "claveLucia", "lucia789@gmail.com");
            facade.registerUser("carlos111", "claveCarlos", "carlos111@gmail.com");
            facade.registerUser("ana222", "claveAna", "ana222@gmail.com");
            facade.registerUser("david333", "claveDavid", "david333@gmail.com");
            facade.registerUser("laura444", "claveLaura", "laura444@gmail.com");


            System.out.println("AuthServer is ready and waiting for connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
