package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.UsuarioDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteFacade extends Remote {
    UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException;
    UsuarioDTO login(String email, String contrasena) throws RemoteException;
    void logout(String token) throws RemoteException;
    void eliminarUsuario(int userId) throws RemoteException;
    void actualizarUsuario(int userId, String contrasena, String nombre, float peso, float altura, float fecCMax, float frecCReposo) throws RemoteException;
}
