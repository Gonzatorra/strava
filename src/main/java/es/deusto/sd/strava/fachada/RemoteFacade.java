package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.servicios.EntrenamientoService;
import es.deusto.sd.strava.servicios.RetoService;
import es.deusto.sd.strava.servicios.UsuarioService;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

    private UsuarioService usuarioService;
    private EntrenamientoService entrenamientoService;
    private RetoService retoService;


    public RemoteFacade() throws RemoteException {
        super();
        this.usuarioService = new UsuarioService();  //crear instancia del servicio
        this.entrenamientoService = new EntrenamientoService();
        this.retoService = new RetoService();
    }

    @Override
    public UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException {
        
        return usuarioService.registrar(username, contrasena, email, nombre);
    }

    @Override
    public UsuarioDTO login(String username, String contrasena) throws RemoteException {
        
        return usuarioService.login(username, contrasena);
       
    }

    @Override
    public void logout(String token) throws RemoteException {
        
        usuarioService.logout(token);
    }

    @Override
    public void eliminarUsuario(int userId) throws RemoteException {
        
        Usuario usuario = new Usuario();  
        usuario.setId(userId);
        usuarioService.eliminarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(UsuarioDTO usuarioDTO) throws RemoteException{
        usuarioService.actualizarUsuario(usuarioDTO);
    }


	@Override
	public void crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia,
			float objetivoTiempo, String deporte, Usuario usuarioCreador, List<Usuario> participantes)
			throws RemoteException {
		retoService.crearReto(nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, deporte, usuarioCreador, participantes);
		
	}

	@Override
	public void aceptarReto(Usuario usuario, Reto reto) throws RemoteException {
		retoService.aceptarReto(usuario, reto);
		
	}

	@Override
	public void visualizarReto(RetoDTO retoDTO, String estado) throws RemoteException {
		retoService.visualizarReto(retoDTO, estado);
		
	}


	@Override
	public void eliminarReto(Usuario usuario, Reto reto) throws RemoteException {
		retoService.eliminarReto(usuario, reto);
		
	}

	@Override
	public List<Usuario> obtenerClasificacion(Reto reto) throws RemoteException {
		return retoService.obtenerClasificacion(reto);
	}

	@Override
	public void calcularProgreso(Usuario usuario) throws RemoteException {
		retoService.calcularProgreso(usuario);
		
	}

	@Override
	public void actualizarReto(Reto reto, String nombre, LocalDateTime fechaIni, LocalDateTime fechaFin, float distancia,
			float tiempo, Usuario usuarioCreador, String deporte, List<Usuario> participantes) throws RemoteException {
		retoService.actualizarReto(reto, nombre, fechaIni, fechaFin, distancia, tiempo, usuarioCreador, deporte, participantes);
	}

	@Override
	public void crearEntreno(Usuario usuario, String titulo, String deporte, double distancia, LocalDateTime fechaIni,
			float horaInicio, double duracion) throws RemoteException {
		entrenamientoService.crearEntreno(usuario, titulo, deporte, distancia, fechaIni, horaInicio, duracion);
		
	}

	@Override
	public void actualizarEntreno(Entrenamiento entrenamiento, double distancia, LocalDateTime fechaIni,
			float horaInicio, double duracion) throws RemoteException {
		entrenamientoService.actualizarEntreno(entrenamiento, distancia, fechaIni, horaInicio, duracion);
		
	}

	@Override
	public void eliminarEntreno(Entrenamiento entrenamiento) throws RemoteException {
		entrenamientoService.eliminarEntreno(entrenamiento);
		
	}

	@Override
	public void visualizarEntreno(EntrenamientoDTO entrenamiento) throws RemoteException {
		entrenamientoService.visualizarEntreno(entrenamiento);
		
	}
}