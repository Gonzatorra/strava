package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;
import es.deusto.sd.strava.fachada.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            //crear el registro RMI en el puerto 1099 si no existe
            Registry registry = null;
            try {
                registry = LocateRegistry.getRegistry(1099);
                registry.list();  //intentar listar servicios, si lanza excepción significa que no está en uso
                System.out.println("El registro RMI ya está en uso.");
            } catch (RemoteException e) {
                //si no existe el registro, se crea
                System.out.println("Creando nuevo registro RMI.");
                registry = LocateRegistry.createRegistry(1099);
            }

            //crear servidor
            Servidor servidor = new Servidor();

            //si el objeto ya ha sido exportado, evitar la exportación de nuevo
            IRemoteFacade stub = null;
            if (servidor.facade != null) {
                //descartar exportación anterior si la hay
                UnicastRemoteObject.unexportObject(servidor.facade, true);
                //exportar nuevo objeto
                stub = (IRemoteFacade) UnicastRemoteObject.exportObject(servidor.facade, 0);
            }

            //registrar stub en registro RMI como "RemoteFacade"
            registry.rebind("RemoteFacade", stub);
            
            
            LocalDate fecha = LocalDate.of(2024, 8, 23);
            LocalDateTime fecha1 = LocalDateTime.now();
            LocalDateTime fecha2 = LocalDateTime.of(2024, 8, 23, 0, 0);
            List<Usuario> challengers = new ArrayList<Usuario>();
            
            
            UsuarioDTO usuario= servidor.facade.registrarUsuario("ana123", "hola", "ana123@gmail.com", "Ana");
            Entrenamiento entreno= servidor.facade.crearEntreno(usuario.toDomain(), "MiPrimerEntrenamiento","running", 10.0, fecha, (float) 14.5, 0.0);
            challengers.add(usuario.toDomain());
            Reto reto= servidor.facade.crearReto("PrimerReto", fecha1, fecha2, 10, 30, "running", usuario.toDomain(), challengers);
            usuario.getEntrenamientos().add(entreno);
            usuario.getRetos().put(reto, "prueba");
            
            System.out.println("Servidor RMI listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
    }
}
