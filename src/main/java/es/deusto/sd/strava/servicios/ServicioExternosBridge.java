package es.deusto.sd.strava.servicios;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import es.deusto.sd.strava.GAuth.IRemoteAuthFacadeG;
import es.deusto.sd.strava.MAuth.IRemoteAuthFacadeM;

public class ServicioExternosBridge {
	private static IRemoteAuthFacadeG fachadaExternaG;
	private static IRemoteAuthFacadeM fachadaExternaM;

	public ServicioExternosBridge() {
		// Conecta al registro RMI en los puertos correspondientes
		Registry registry;
		try {
		    // Conexión al registro RMI en el puerto 1100
		    registry = LocateRegistry.getRegistry("localhost", 1100);
		    if (fachadaExternaG != null) {
		        // Descartar exportación anterior si la hay
		        UnicastRemoteObject.unexportObject(fachadaExternaG, true);
		        System.out.println("Exportación anterior de fachadaExternaG descartada.");
		    }
		    // Buscar y asignar el objeto remoto
		    fachadaExternaG = (IRemoteAuthFacadeG) registry.lookup("RemoteAuthFacadeG");
		    System.out.println("Conexión exitosa al RemoteAuthFacade en el puerto 1100.");

		    // Conexión al registro RMI en el puerto 1101
		    registry = LocateRegistry.getRegistry("localhost", 1101);
		    if (fachadaExternaM != null) {
		        // Descartar exportación anterior si la hay
		        UnicastRemoteObject.unexportObject(fachadaExternaM, true);
		        System.out.println("Exportación anterior de fachadaExternaM descartada.");
		    }
		    // Buscar y asignar el objeto remoto
		    fachadaExternaM = (IRemoteAuthFacadeM) registry.lookup("RemoteAuthFacadeM");
		    System.out.println("Conexión exitosa al RemoteAuthFacade en el puerto 1101.");

		} catch (RemoteException e) {
		    System.err.println("Error al conectar con el registro RMI: " + e.getMessage());
		    e.printStackTrace();
		} catch (NotBoundException e) {
		    System.err.println("El objeto 'RemoteAuthFacade' no está registrado en el registro RMI: " + e.getMessage());
		    e.printStackTrace();
		}

        System.out.println("ServicioExternosBridge inicializado.");
    }
	
	public String verifyGoogle(String username, String pass){
		try {
			return fachadaExternaG.loginUser(username, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String verifyMeta(String username, String pass){
		try {
			return fachadaExternaM.loginUser(username, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
