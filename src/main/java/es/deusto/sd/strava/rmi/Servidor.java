package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.fachada.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    private RemoteFacade facade;

    public Servidor() {
        try {
            this.facade = new RemoteFacade();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Crear el registro RMI en el puerto 1099 si no existe
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(1099);
                registry.list();  // Intentar listar servicios, si lanza excepción significa que no está en uso
                System.out.println("El registro RMI ya está en uso.");
            } catch (RemoteException e) {
                // Si no existe el registro, se crea uno nuevo
                System.out.println("Creando nuevo registro RMI.");
                registry = LocateRegistry.createRegistry(1099);
            }

            // Crear el servidor
            Servidor servidor = new Servidor();

            // Si el objeto ya ha sido exportado, evitar la exportación de nuevo
            IRemoteFacade stub = null;
            if (servidor.facade != null) {
                // Descartar la exportación anterior si la hay
                UnicastRemoteObject.unexportObject(servidor.facade, true);
                // Exportar el nuevo objeto
                stub = (IRemoteFacade) UnicastRemoteObject.exportObject(servidor.facade, 0);
            }

            // Registrar el stub en el registro RMI bajo el nombre "RemoteFacade"
            registry.rebind("RemoteFacade", stub);

            System.out.println("Servidor RMI listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
