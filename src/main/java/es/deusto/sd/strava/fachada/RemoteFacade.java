package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.servicios.*;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

    private UsuarioService usuarioService;
    private EntrenamientoService entrenamientoService;
    private RetoService retoService;
    private ServicioAutentificacion servicioAutentificacion;


    public RemoteFacade() throws RemoteException {
        super();
        this.usuarioService = new UsuarioService();  //crear instancia del servicio
        this.entrenamientoService = new EntrenamientoService();
        this.retoService = new RetoService();
    }
    
    
    

    @Override
	public ArrayList<Usuario> getAmigos(Usuario usuario) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Usuario>) usuarioService.getAmigos(usuario);
	}

    



	@Override
	public UsuarioService getUsuarioService() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuarioService;
	}




	@Override
	public HashMap<Integer, Usuario> getUsuarios() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuarioService.getUsuarios();
	}




	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}




	@Override
	public UsuarioDTO registrarUsuario(String username, String password, String email, String nombre) throws RemoteException {
	    Usuario usuario = usuarioService.getUsuarios().values().stream()
	            .filter(u -> u.getUsername().equals(username))
	            .findFirst()
	            .orElse(null);

	    if (usuario != null) {
	        System.out.println("Usuario ya existe: " + username);
	        return UsuarioAssembler.toDTO(usuario);
	    }

	    return usuarioService.registrar(username, password, email, nombre);
	}

    @Override
    public UsuarioDTO login(String username, String contrasena) throws RemoteException {
        
        return usuarioService.login(username, contrasena);
       
    }
    
    @Override
    public UsuarioDTO loginConProveedor(String username, String password, String proveedor) throws RemoteException {
        try {
            Usuario usuario = usuarioService.getUsuarios().values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (usuario == null) {
                UsuarioDTO newUser = usuarioService.registrar(username, password, username + "@" + proveedor.toLowerCase() + ".com", proveedor);
                System.out.println("User registered with provider: " + proveedor);
                return newUser;
            } else if (!password.equals(usuario.getContrasena())) {
                throw new RemoteException("Incorrect password for user: " + username);
            }

            return UsuarioAssembler.toDTO(usuario);
        } catch (Exception e) {
            throw new RemoteException("Error during login with provider: " + proveedor, e);
        }
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
    public void actualizarUsuario(UsuarioDTO usuarioDTO) throws RemoteException {
        usuarioService.actualizarUsuario(usuarioDTO);
    }


	@Override
	public Reto crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia,
			float objetivoTiempo, String deporte, Usuario usuarioCreador, List<Usuario> participantes)
			throws RemoteException {
		 return retoService.crearReto(nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, deporte, usuarioCreador, participantes);
		
	}

	@Override
	public void aceptarReto(Usuario usuario, Reto reto) throws RemoteException {
		retoService.aceptarReto(usuario, reto);
		
	}

	@Override
	public HashMap<Integer,Reto> visualizarReto() throws RemoteException {
		return retoService.visualizarReto();
		
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
	public Entrenamiento crearEntreno(Usuario usuario, String titulo, String deporte, double distancia, LocalDate fechaIni,
			float horaInicio, double duracion) throws RemoteException {
		return entrenamientoService.crearEntreno(usuario, titulo, deporte, distancia, fechaIni, horaInicio, duracion);
		
	}

	@Override
	public void actualizarEntreno(Entrenamiento entrenamiento, double distancia, LocalDate fechaIni,
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