package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.GAuth.IRemoteAuthFacadeG;
import es.deusto.sd.strava.GAuth.RemoteAuthFacadeG;
import es.deusto.sd.strava.MAuth.MetaAuthClient;
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
import java.util.HashMap;
import java.util.List;

public class Servidor {

    private static RemoteFacade facade;
    protected static HashMap<UsuarioDTO, String> tokenActivos;
    static IRemoteAuthFacadeG facadeG;
    static MetaAuthClient metaAuthClient; 

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
            
            // Buscar la instancia de RemoteAuthFacadeG en el registro del puerto 1100
            Registry authRegistryG = LocateRegistry.getRegistry("localhost", 1100);
            facadeG = (IRemoteAuthFacadeG) authRegistryG.lookup("RemoteAuthFacadeG"); // Usa la interfaz aquí

            System.out.println("RemoteAuthFacadeG vinculado correctamente desde AuthServerGoogle.");

            
            // Buscar la instancia de RemoteAuthFacadeM en el registro del puerto 1101
            metaAuthClient = new MetaAuthClient("localhost", 1101);
            System.out.println("MetaAuthClient inicializado correctamente.");

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
            
            //Usuario 1
            UsuarioDTO usuario= servidor.facade.registrarUsuario("ana123", "hola", "ana123@strava.com", "Ana", "Strava");
            EntrenamientoDTO entreno = servidor.facade.crearEntreno(usuario, "MiPrimerEntrenamiento","running", 10.0, fecha, (float) 14.5, 0.0);
            List<UsuarioDTO> challengers = new ArrayList<UsuarioDTO>();
            challengers.add(usuario);
            facade.actualizarUsuario(usuario);
            RetoDTO reto= servidor.facade.crearReto("PrimerReto", fecha2, fecha1, 10, 30, "running", usuario, challengers);
            usuario.getEntrenamientos().add(entreno);
            facade.actualizarUsuario(usuario);
            usuario.getRetos().put(reto, "superado");
            facade.actualizarUsuario(usuario);
            
            
            // Usuario 2
            UsuarioDTO usuario2 = facade.registrarUsuario("juan456", "pass123", "juan456@strava.com", "Juan", "Strava");
            EntrenamientoDTO entreno2 = facade.crearEntreno(usuario2, "EntrenoAvanzado", "cycling", 20.0, fecha, 18.0f, 5.0);
            List<UsuarioDTO> challengers2 = new ArrayList<UsuarioDTO>();
            challengers2.add(usuario2);
            RetoDTO reto2 = facade.crearReto("RetoCiclismo", fecha1, fecha2, 20, 50, "cycling", usuario2, challengers2);
            usuario2.getEntrenamientos().add(entreno2);
            usuario2.getRetos().put(reto2, "superado");
            facade.actualizarUsuario(usuario2);

            // Usuario 3
            UsuarioDTO usuario3 = facade.registrarUsuario("lucia789", "luciaPass", "lucia789@strava.com", "Lucía", "Strava");
            EntrenamientoDTO entreno3 = facade.crearEntreno(usuario3, "EntrenoMatutino", "swimming", 5.0, fecha, 12.0f, 1.0);
            List<UsuarioDTO> challengers3 = new ArrayList<UsuarioDTO>();
            challengers3.add(usuario3);
            RetoDTO reto3 = facade.crearReto("RetoNatacion", fecha1, fecha2, 5, 15, "swimming", usuario3, challengers3);
            usuario3.getEntrenamientos().add(entreno3);
            usuario3.getRetos().put(reto3, "pendiente");
            facade.actualizarUsuario(usuario3);

            // Usuarios 4 al 10
            UsuarioDTO usuario4 = facade.registrarUsuario("mario001", "marioKey", "mario001@strava.com", "Mario", "Strava");
            UsuarioDTO usuario5 = facade.registrarUsuario("elena345", "elenaKey", "elena345@strava.com", "Elena", "Strava");
            UsuarioDTO usuario6 = facade.registrarUsuario("pedro654", "pedroKey", "pedro654@strava.com", "Pedro", "Strava");
            UsuarioDTO usuario7 = facade.registrarUsuario("laura999", "lauraKey", "laura999@strava.com", "Laura", "Strava");
            UsuarioDTO usuario8 = facade.registrarUsuario("david111", "davidKey", "david111@strava.com", "David", "Strava");
            UsuarioDTO usuario9 = facade.registrarUsuario("sofia777", "sofiaKey", "sofia777@strava.com", "Sofía", "Strava");
            UsuarioDTO usuario10 = facade.registrarUsuario("carlos888", "carlosKey", "carlos888@strava.com", "Carlos", "Strava");

            
            
            facadeG.registerUser("daniel333", "claveDaniel", "daniel333@gmail.com");
            facadeG.registerUser("susana555", "claveSusana", "susana555@gmail.com");
            facadeG.registerUser("manuel111", "claveManuel", "manuel111@gmail.com");
            facadeG.registerUser("isabel999", "claveIsabel", "isabel999@gmail.com");
            facadeG.registerUser("andres444", "claveAndres", "andres444@gmail.com");
            facadeG.registerUser("clara777", "claveClara", "clara777@gmail.com");
            facadeG.registerUser("pablo888", "clavePablo", "pablo888@gmail.com");
            
            
            metaAuthClient.registerUser("maria123", "claveMaria", "maria123@meta.com");
            metaAuthClient.registerUser("jose456", "claveJose", "jose456@meta.com");
            metaAuthClient.registerUser("lucia789", "claveLucia", "lucia789@meta.com");
            metaAuthClient.registerUser("carlos111", "claveCarlos", "carlos111@meta.com");
            metaAuthClient.registerUser("ana222", "claveAna", "ana222@meta.com");
            metaAuthClient.registerUser("david333", "claveDavid", "david333@meta.com");
            metaAuthClient.registerUser("laura444", "claveLaura", "laura444@meta.com");


            UsuarioDTO usuario11 = facade.registrarUsuario("daniel333", "claveDaniel", "daniel333@gmail.com", "Daniel", "Google");
            UsuarioDTO usuario12 = facade.registrarUsuario("susana555", "claveSusana", "susana555@gmail.com", "Susana", "Google");
            UsuarioDTO usuario13 = facade.registrarUsuario("manuel111", "claveManuel", "manuel111@gmail.com", "Manuel", "Google");
            UsuarioDTO usuario14 = facade.registrarUsuario("isabel999", "claveIsabel", "isabel999@gmail.com", "Isabel", "Google");
            UsuarioDTO usuario15 = facade.registrarUsuario("andres444", "claveAndres", "andres444@gmail.com", "Andres", "Google");
            UsuarioDTO usuario16 = facade.registrarUsuario("clara777", "claveClara", "clara777@gmail.com", "Clara", "Google");
            UsuarioDTO usuario17 = facade.registrarUsuario("pablo888", "clavePablo", "pablo888@gmail.com", "Pablo", "Google");

            // Registrar más usuarios
            UsuarioDTO usuario18 = facade.registrarUsuario("maria123", "claveMaria", "maria123@meta.com", "Maria", "Meta");
            UsuarioDTO usuario19 = facade.registrarUsuario("jose456", "claveJose", "jose456@meta.com", "Jose", "Meta");
            UsuarioDTO usuario20 = facade.registrarUsuario("lucia789", "claveLucia", "lucia789@meta.com", "Lucia", "Meta");
            UsuarioDTO usuario21 = facade.registrarUsuario("carlos111", "claveCarlos", "carlos111@meta.com", "Carlos", "Meta");
            UsuarioDTO usuario22 = facade.registrarUsuario("ana222", "claveAna", "ana222@meta.com", "Ana", "Meta");
            UsuarioDTO usuario23 = facade.registrarUsuario("david333", "claveDavid", "david333@meta.com", "David", "Meta");
            UsuarioDTO usuario24 = facade.registrarUsuario("laura444", "claveLaura", "laura444@meta.com", "Laura", "Meta");
            
            
            
            
            
            System.out.println("Servidor RMI listo y esperando conexiones...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
