package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRemoteFacade extends Remote {
    UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException;
    UsuarioDTO login(String email, String contrasena) throws RemoteException;
    void logout(String token) throws RemoteException;
    void eliminarUsuario(int userId) throws RemoteException;
    void actualizarUsuario(int userId, String contrasena, String nombre, float peso, float altura, float fecCMax, float frecCReposo) throws RemoteException;
    
    //Para entrenamiento
    void crearEntreno(EntrenamientoDTO entrenamiento) throws java.rmi.RemoteException;
    EntrenamientoDTO getEntreno(int idEntreno) throws java.rmi.RemoteException;
    void actualizarEntreno(EntrenamientoDTO entrenamiento) throws java.rmi.RemoteException;
    void eliminarEntreno(int idEntreno) throws java.rmi.RemoteException;

}

