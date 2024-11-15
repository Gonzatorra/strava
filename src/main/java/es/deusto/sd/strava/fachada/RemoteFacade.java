package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.servicios.EntrenamientoService;
import es.deusto.sd.strava.servicios.UsuarioService;
import es.deusto.sd.strava.dominio.Usuario;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

    private UsuarioService usuarioService;  // La fachada interactúa con el servicio
    private EntrenamientoService entrenamientoService;


    public RemoteFacade() throws RemoteException {
        super();
        this.usuarioService = new UsuarioService();  // Crear una instancia del servicio
        this.entrenamientoService = new EntrenamientoService();
    }

    @Override
    public UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException {
        // Llamada al servicio para registrar un usuario y devolver un DTO
    	
        return usuarioService.registrar(username, contrasena, email, nombre);
    }

    @Override
    public UsuarioDTO login(String email, String contrasena) throws RemoteException {
        // Llamada al servicio para login y retorno del DTO
        return usuarioService.login(email, contrasena);
       
    }

    @Override
    public void logout(String token) throws RemoteException {
        // Llamada al servicio para hacer logout
        usuarioService.logout(token);
    }

    @Override
    public void eliminarUsuario(int userId) throws RemoteException {
        // Llamada al servicio para eliminar un usuario
        Usuario usuario = new Usuario();  // Crear un objeto de usuario (si es necesario)
        usuario.setId(userId);
        usuarioService.eliminarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(int userId, String contrasena, String nombre, float peso, float altura, float fecCMax, float frecCReposo) throws RemoteException {
        // Llamada al servicio para actualizar el usuario
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuarioService.actualizarUsuario(usuario, contrasena, nombre, peso, altura, fecCMax, frecCReposo);
    }

	@Override
	public void crearEntreno(EntrenamientoDTO entrenamientoDTO) throws RemoteException {
		entrenamientoService.crearEntreno(entrenamientoDTO);
		
	}

	@Override
	public EntrenamientoDTO getEntreno(int idEntreno) throws RemoteException {
		return entrenamientoService.getEntreno(idEntreno);
	}


	@Override
	public void actualizarEntreno(EntrenamientoDTO entrenamientoDTO) throws RemoteException {
		entrenamientoService.actualizarEntreno(entrenamientoDTO);
		
	}

	@Override
	public void eliminarEntreno(int idEntreno) throws RemoteException {
		entrenamientoService.eliminarEntreno(idEntreno);
		
	}
}
