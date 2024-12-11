package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.GAuth.IRemoteAuthFacade;
import es.deusto.sd.strava.assembler.RetoAssembler;
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
    private ServicioExternosBridge externoService;
    
    /*private static final String MOCK_GOOGLE_USER = "user@google.com";
    private static final String MOCK_GOOGLE_PASSWORD = "google";
    private static final String MOCK_META_USER = "user@meta.com";
    private static final String MOCK_META_PASSWORD = "meta";*/


    public RemoteFacade() throws RemoteException {
        super();
        this.usuarioService = new UsuarioService();  //crear instancia del servicio
        this.entrenamientoService = new EntrenamientoService();
        this.retoService = new RetoService();
        this.externoService= new ServicioExternosBridge();
    }
    
    
    

    @Override
	public ArrayList<Integer> getAmigos(UsuarioDTO usuario) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Integer>) usuarioService.getAmigos(usuario);
	}

    



	@Override
	public UsuarioService getUsuarioService() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuarioService;
	}




	@Override
	public HashMap<Integer, UsuarioDTO> getUsuarios() throws RemoteException {
		// TODO Auto-generated method stub
		return this.usuarioService.getUsuarios();
	}




	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}




	@Override
	public UsuarioDTO registrarUsuario(String username, String password, String email, String nombre) throws RemoteException {
	    UsuarioDTO usuario = usuarioService.getUsuarios().values().stream()
	            .filter(u -> u.getUsername().equals(username))
	            .findFirst()
	            .orElse(null);

	    if (usuario != null) {
	        System.out.println("Usuario ya existe: " + username);
	        return usuario;
	    }

	    return usuarioService.registrar(username, password, email, nombre);
	}

    @Override
    public UsuarioDTO login(String username, String contrasena) throws RemoteException {
        
        return usuarioService.login(username, contrasena);
       
    }
    
    @Override
    public UsuarioDTO loginConProveedor(String username, String password, String proveedor) throws RemoteException {
        // Simula lógica según proveedor
        if ("Google".equals(proveedor)) {
            return autenticacionGoogle(username, password);
        } else if ("Meta".equals(proveedor)) {
            return autenticacionMeta(username, password);
        }
        return null; // Usuario no válido o proveedor no soportado
    }

   

    private UsuarioDTO autenticacionGoogle(String username, String password) {
        // Simulación de lógica para Google (mock)
    	if(externoService.verifyGoogle(username, password)!= null) {
        //if (MOCK_GOOGLE_USER.equals(username) && MOCK_GOOGLE_PASSWORD.equals(password)) {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsername(username);
            usuario.setContrasena(password);
            usuario.setProveedor("Google");
            usuario.setEntrenamientos(new ArrayList<EntrenamientoDTO>());
            usuario.setRetos(new HashMap<RetoDTO, String>());
            usuario.setAmigos(new ArrayList<Integer>());
            
            usuarioService.registrarUsuario(usuario);
            
            return usuario;
        } else {
            System.out.println("Autenticación fallida para el usuario: " + username);
            return null;
        }
    }


    private UsuarioDTO autenticacionMeta(String username, String password) {
        // Simulación de lógica para Meta (mock)
    	if(externoService.verifyMeta(username, password)!= null) {
        //if (MOCK_META_USER.equals(username) && MOCK_META_PASSWORD.equals(password)) {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setUsername(username);
            usuario.setContrasena(password);
            usuario.setProveedor("Meta");
            usuario.setEntrenamientos(new ArrayList<EntrenamientoDTO>());
            usuario.setRetos(new HashMap<RetoDTO, String>());
            usuario.setAmigos(new ArrayList<Integer>());
            
            usuarioService.registrarUsuario(usuario);
            
            return usuario;
        } else {
            System.out.println("Autenticación fallida para el usuario: " + username);
            return null;
        }
    }


    @Override
    public void logout(String token) throws RemoteException {
        
        usuarioService.logout(token);
    }

    @Override
    public void eliminarUsuario(int userId) throws RemoteException {
        UsuarioDTO usu = usuarioService.getUsuarios().get(userId); 

        usuarioService.eliminarUsuario(usu);
    }

    @Override
    public void actualizarUsuario(UsuarioDTO usuarioDTO) throws RemoteException {
        usuarioService.actualizarUsuario(usuarioDTO);
    }


	@Override
	public RetoDTO crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia,
			float objetivoTiempo, String deporte, UsuarioDTO usuarioCreador, List<UsuarioDTO> participantes)
			throws RemoteException {
		 return retoService.crearReto(nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, deporte, usuarioCreador, participantes);
		
	}

	@Override
	public void aceptarReto(UsuarioDTO usuario, RetoDTO reto) throws RemoteException {
		retoService.aceptarReto(usuario, reto);
		
	}

	@Override
	public HashMap<Integer,RetoDTO> visualizarReto() throws RemoteException {
		return retoService.visualizarReto();
		
	}


	@Override
	public void eliminarReto(UsuarioDTO usuario, RetoDTO reto) throws RemoteException {
		retoService.eliminarReto(usuario, reto);
		
	}

	@Override
	public List<Integer> obtenerClasificacion(RetoDTO reto) throws RemoteException {
		return retoService.obtenerClasificacion(reto);
	}

	@Override
	public void calcularProgreso(UsuarioDTO usuario) throws RemoteException {
		retoService.calcularProgreso(usuario);
		
	}

	@Override
	public void actualizarReto(RetoDTO reto, String nombre, LocalDateTime fechaIni, LocalDateTime fechaFin, float distancia,
			float tiempo, UsuarioDTO usuarioCreador, String deporte, ArrayList<Integer> participantes) throws RemoteException {
		retoService.actualizarReto(reto, nombre, fechaIni, fechaFin, distancia, tiempo, usuarioCreador, deporte, participantes);
	}

	@Override
	public EntrenamientoDTO crearEntreno(UsuarioDTO usuario, String titulo, String deporte, double distancia, LocalDate fechaIni,
			float horaInicio, double duracion) throws RemoteException {
		return entrenamientoService.crearEntreno(usuario, titulo, deporte, distancia, fechaIni, horaInicio, duracion);
		
	}

	@Override
	public void actualizarEntreno(EntrenamientoDTO entrenamiento, String titulo, String deporte, double distancia, double duracion) throws RemoteException {
		//entrenamientoService.actualizarEntreno(entrenamiento, titulo, deporte, distancia, duracion);
		
	}

	@Override
	public void eliminarEntreno(int index, EntrenamientoDTO entrenamiento) throws RemoteException {
		entrenamientoService.eliminarEntreno(index, entrenamiento);
		
	}

	@Override
	public void visualizarEntreno(EntrenamientoDTO entrenamiento) throws RemoteException {
		entrenamientoService.visualizarEntreno(entrenamiento);
		
	}



}