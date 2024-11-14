package es.deusto.sd.strava.controlador;

import es.deusto.sd.strava.fachada.*;

import java.rmi.RemoteException;

import es.deusto.sd.strava.DTO.UsuarioDTO;

public class GestionAutentificacion {

    private IRemoteFacade facade;

    public GestionAutentificacion() throws RemoteException {
        this.facade = new RemoteFacade();  // Creamos la fachada
    }

    public void registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException {
        UsuarioDTO usuarioDTO = facade.registrarUsuario(username, contrasena, email, nombre);
        if (usuarioDTO != null) {
            System.out.println("Usuario registrado con éxito: " + usuarioDTO.getUsername());
        } else {
            System.out.println("Error al registrar usuario.");
        }
    }

    public void loginUsuario(String email, String contrasena) throws RemoteException {
        UsuarioDTO usuarioDTO = facade.login(email, contrasena);
        if (usuarioDTO != null) {
            System.out.println("Inicio de sesión exitoso: " + usuarioDTO.getUsername());
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
}
