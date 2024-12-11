package es.deusto.sd.strava.GAuth;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;

public class AuthServerGoogle {

    private static RemoteAuthFacadeG facade;

    public AuthServerGoogle() {
        try {
            this.facade = new RemoteAuthFacadeG();
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

            AuthServerGoogle servidor = new AuthServerGoogle();

            IRemoteAuthFacadeG stub = (IRemoteAuthFacadeG) UnicastRemoteObject.exportObject(servidor.facade, 0);
            registry.rebind("RemoteAuthFacadeG", stub);

            System.out.println("AuthServer is ready and waiting for connections...");
            
            
            facade.registerUser("daniel333", "claveDaniel", "daniel333@gmail.com");
            facade.registerUser("susana555", "claveSusana", "susana555@gmail.com");
            facade.registerUser("manuel111", "claveManuel", "manuel111@gmail.com");
            facade.registerUser("isabel999", "claveIsabel", "isabel999@gmail.com");
            facade.registerUser("andres444", "claveAndres", "andres444@gmail.com");
            facade.registerUser("clara777", "claveClara", "clara777@gmail.com");
            facade.registerUser("pablo888", "clavePablo", "pablo888@gmail.com");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
