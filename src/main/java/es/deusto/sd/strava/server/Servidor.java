package es.deusto.sd.strava.server;

import java.rmi.RemoteException;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.dominio.Reto;
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
    
    // Retos
    public void procesarCrearReto(RetoDTO reto) throws RemoteException {
        facade.crearReto(
            reto.getNombre(),
            reto.getFecIni(),
            reto.getFecFin(),
            reto.getObjetivoDistancia(),
            reto.getObjetivoTiempo(),
            reto.getDeporte(),
            reto.getUsuarioCreador(),
            reto.getParticipantes()
        );
    }
    
    public void procesarAceptarReto(int usuarioId, int retoId) throws RemoteException {
        facade.aceptarReto(null, null);  //habría que crear los objetos
    }
    
    public void procesarEliminarReto(int retoId, int usuarioId) throws RemoteException {
        facade.eliminarReto(null, null); //habría que crear los objetos
    }
   
    public void procesarActualizarReto(RetoDTO reto) throws RemoteException {
        facade.actualizarReto(
        	reto.toDomain(),
            reto.getNombre(),
            reto.getFecIni(),
            reto.getFecFin(),
            reto.getObjetivoDistancia(),
            reto.getObjetivoTiempo(),
            reto.getUsuarioCreador(),
            reto.getDeporte(),
            reto.getParticipantes()
        );
    }
    
}
