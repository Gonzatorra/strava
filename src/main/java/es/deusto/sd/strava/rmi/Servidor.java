package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.assembler.EntrenamientoAssembler;
import es.deusto.sd.strava.assembler.RetoAssembler;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
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

    private static RemoteFacade facade;

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
            List<UsuarioDTO> challengers = new ArrayList<UsuarioDTO>();
            
            //Usuario 1
            UsuarioDTO usuario= servidor.facade.registrarUsuario("ana123", "hola", "ana123@gmail.com", "Ana");
            EntrenamientoDTO entreno = servidor.facade.crearEntreno(usuario, "MiPrimerEntrenamiento","running", 10.0, fecha, (float) 14.5, 0.0);
            challengers.add(usuario);
            RetoDTO reto= servidor.facade.crearReto("PrimerReto", fecha2, fecha1, 10, 30, "running", usuario, challengers);
            usuario.getEntrenamientos().add(entreno);
            usuario.getRetos().put(reto, "prueba");
            facade.actualizarUsuario(usuario);
            
            
            // Usuario 2
            UsuarioDTO usuario2 = facade.registrarUsuario("juan456", "pass123", "juan456@gmail.com", "Juan");
            EntrenamientoDTO entreno2 = facade.crearEntreno(usuario2, "EntrenoAvanzado", "cycling", 20.0, fecha, 18.0f, 5.0);
            List<UsuarioDTO> challengers2 = new ArrayList<UsuarioDTO>();
            challengers2.add(usuario2);
            RetoDTO reto2 = facade.crearReto("RetoCiclismo", fecha1, fecha2, 20, 50, "cycling", usuario2, challengers2);
            usuario2.getEntrenamientos().add(entreno2);
            usuario2.getRetos().put(reto2, "superado");
            facade.actualizarUsuario(usuario2);

            // Usuario 3
            UsuarioDTO usuario3 = facade.registrarUsuario("lucia789", "luciaPass", "lucia789@gmail.com", "Lucía");
            EntrenamientoDTO entreno3 = facade.crearEntreno(usuario3, "EntrenoMatutino", "swimming", 5.0, fecha, 12.0f, 1.0);
            List<UsuarioDTO> challengers3 = new ArrayList<UsuarioDTO>();
            challengers3.add(usuario3);
            RetoDTO reto3 = facade.crearReto("RetoNatacion", fecha1, fecha2, 5, 15, "swimming", usuario3, challengers3);
            usuario3.getEntrenamientos().add(entreno3);
            usuario3.getRetos().put(reto3, "pendiente");
            facade.actualizarUsuario(usuario3);

            // Usuarios 4 al 10
            UsuarioDTO usuario4 = facade.registrarUsuario("mario001", "marioKey", "mario001@gmail.com", "Mario");
            UsuarioDTO usuario5 = facade.registrarUsuario("elena345", "elenaKey", "elena345@gmail.com", "Elena");
            UsuarioDTO usuario6 = facade.registrarUsuario("pedro654", "pedroKey", "pedro654@gmail.com", "Pedro");
            UsuarioDTO usuario7 = facade.registrarUsuario("laura999", "lauraKey", "laura999@gmail.com", "Laura");
            UsuarioDTO usuario8 = facade.registrarUsuario("david111", "davidKey", "david111@gmail.com", "David");
            UsuarioDTO usuario9 = facade.registrarUsuario("sofia777", "sofiaKey", "sofia777@gmail.com", "Sofía");
            UsuarioDTO usuario10 = facade.registrarUsuario("carlos888", "carlosKey", "carlos888@gmail.com", "Carlos");

            
            System.out.println("Servidor RMI listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
