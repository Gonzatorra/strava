package es.deusto.sd.strava.server;

import java.rmi.RemoteException;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.fachada.*;

public class Servidor {

    private IRemoteFacade facade;

    public Servidor() throws RemoteException {
        this.facade = new RemoteFacade();  // Creamos la fachada
    }

    public void procesarRegistro(String username, String contrasena, String email, String nombre) throws RemoteException {
        // Lógica para procesar el registro en el servidor
        facade.registrarUsuario(username, contrasena, email, nombre);
    }

    public void procesarLogin(String email, String contrasena) throws RemoteException {
        // Lógica para procesar el login en el servidor
        facade.login(email, contrasena);
    }
    
    //Entrenamientos
    public void procesarCrearEntrenamiento(EntrenamientoDTO entrenamientoDTO) throws RemoteException {
        facade.crearEntreno(entrenamientoDTO);
    }

    public EntrenamientoDTO procesarObtenerEntrenamiento(int id) throws RemoteException {
        return facade.getEntreno(id);
    }

    public void procesarActualizarEntrenamiento(EntrenamientoDTO entrenamientoDTO) throws RemoteException {
        facade.actualizarEntreno(entrenamientoDTO);
    }

    public void procesarEliminarEntrenamiento(int id) throws RemoteException {
        facade.eliminarEntreno(id);
    }
}
