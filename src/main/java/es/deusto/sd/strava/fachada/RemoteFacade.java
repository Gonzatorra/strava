package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.GAuth.IRemoteAuthFacadeG;
import es.deusto.sd.strava.MAuth.MetaAuthClient;
import es.deusto.sd.strava.servicios.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

    public UsuarioService usuarioService;
    private EntrenamientoService entrenamientoService;
    private RetoService retoService;
    private ServicioAutentificacion servicioAutentificacion;
    //private ServicioExternosBridge externoService;
    private IRemoteAuthFacadeG facadeG;
    //private IRemoteAuthFacadeM facadeM;
    
    private static HashMap<String, String> tokensActivos = new HashMap<>();
    
    /*private static final String MOCK_GOOGLE_USER = "user@google.com";
    private static final String MOCK_GOOGLE_PASSWORD = "google";
    private static final String MOCK_META_USER = "user@meta.com";
    private static final String MOCK_META_PASSWORD = "meta";*/


    public RemoteFacade() throws RemoteException {
        super();
        this.usuarioService = new UsuarioService();  //crear instancia del servicio
        this.entrenamientoService = new EntrenamientoService();
        this.retoService = new RetoService();
        //this.externoService= new ServicioExternosBridge();
        this.servicioAutentificacion= new ServicioAutentificacion();
        
        try {
            Registry registryG = LocateRegistry.getRegistry("localhost", 1100);
            this.facadeG = (IRemoteAuthFacadeG) registryG.lookup("RemoteAuthFacadeG");
            System.out.println("RemoteAuthFacadeG vinculado correctamente.");

            MetaAuthClient metaAuthClient = new MetaAuthClient("localhost", 1101);
            System.out.println("MetaAuthClient inicializado correctamente para el servidor Meta.");

        } catch (Exception e) {
            System.err.println("Error al inicializar facades para Google y Meta: " + e.getMessage());
            e.printStackTrace();
        }
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
	public UsuarioDTO registrarUsuario(String username, String password, String email, String nombre, String proveedor) throws RemoteException {
	    UsuarioDTO usuario = usuarioService.getUsuarios().values().stream()
	            .filter(u -> u.getUsername().equals(username))
	            .findFirst()
	            .orElse(null);

	    if (usuario != null) {
	        System.out.println("Usuario ya existe: " + username);
	        return usuario;
	    }

	    return usuarioService.registrar(username, password, email, nombre, proveedor);
	}

    @Override
    public UsuarioDTO login(String username, String contrasena) throws RemoteException {
    	for (UsuarioDTO u: usuarioService.getUsuarios().values()) {
    		if (u.getUsername().equals(username) && u.getProveedor().equals("Strava")) {
    			System.out.println(u.getUsername()+u.getProveedor());
    			if(tokensActivos.get(username)!=null) {
    	    		return u;
    	    	
    	    	}
    			break;
    		}
    	}
    	UsuarioDTO usu= usuarioService.obtenerUsuarioPorNombre(username);
    	if(usu.getProveedor().equals("Strava")) {
	    	UsuarioDTO u= usuarioService.login(username, contrasena);
	    	u.setToken(servicioAutentificacion.autenticar(username, contrasena, "Strava", u.getProveedor()));
		    usuarioService.actualizarUsuario(u);
		    UsuarioDTO usu2= UsuarioService.getUsuarios().get(u.getId());
		    tokensActivos.put(usu2.getUsername(), usu2.getToken());
		    return usu;
    	}
		return null;
    	
       
    }
    
    @Override
    public UsuarioDTO loginConProveedor(String username, String password, String plataforma) throws RemoteException {
        String token = null;
        String proveedor = usuarioService.obtenerUsuarioPorNombre(username).getProveedor();
        if(plataforma.equals(proveedor)) {
	        if ("Google".equals(plataforma)) {
	        	//token = externoService.verifyGoogle(username, password, proveedor);
	        } else if ("Meta".equals(plataforma)) {
	        	try {
	                MetaAuthClient metaAuthClient = new MetaAuthClient("localhost", 1101);
	                token = metaAuthClient.login(username, password);
	                System.out.println("Login realizado correctamente en Meta.");
	            } catch (IOException e) {
	                System.err.println("Error durante el login en Meta: " + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	        
	        UsuarioDTO usu= usuarioService.obtenerUsuarioPorNombre(username);
	        usu.setToken(token);
	        usuarioService.actualizarUsuario(usu);
	        UsuarioDTO u= usuarioService.getUsuarios().get(usu.getId());
	        
	        return u;
	        }
        else {
            System.out.println("Login fallido con plataforma: " + plataforma + " para usuario con proveedor: " + proveedor);
        }
        return null;
       /* if (token != null) {
            UsuarioDTO usuario = new UsuarioDTO();
            usuario.setToken(token);
            usuario.setUsername(username);
            usuario.setContrasena(password);
            usuario.setProveedor(proveedor);
            usuario.setEntrenamientos(new ArrayList<>());
            usuario.setRetos(new HashMap<>());
            usuario.setAmigos(new ArrayList<>());
            boolean encontrado= false;
            for (UsuarioDTO u: usuarioService.getUsuarios().values()) {
            	if(u.getUsername().equals(usuario.getUsername())) encontrado=true;
            }
            if (!encontrado) {
            	usuarioService.registrarUsuario(usuario);
            }
            else {
            	for (UsuarioDTO u: usuarioService.getUsuarios().values()) {
            		if (u.getUsername().equals(usuario.getUsername())) return u;
            	}
            }
            return usuario;
        } else {
            System.out.println("Autenticacion fallida para usuario: " + username + " con proveedor: " + proveedor);
            return null;
        }*/
    }

   
    /*
    private UsuarioDTO autenticacionGoogle(String username, String password) {
        try {
            String result = externoService.verifyGoogle(username, password);
            if (result != null) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setUsername(username);
                usuario.setContrasena(password);
                usuario.setProveedor("Google");
                usuario.setEntrenamientos(new ArrayList<>());
                usuario.setRetos(new HashMap<>());
                usuario.setAmigos(new ArrayList<>());
                usuarioService.registrarUsuario(usuario);
                return usuario;
            } else {
                System.out.println("Autenticación fallida para el usuario: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private UsuarioDTO autenticacionMeta(String username, String password) {
        try {
            String result = externoService.verifyMeta(username, password);
            if (result != null) {
                UsuarioDTO usuario = new UsuarioDTO();
                usuario.setUsername(username);
                usuario.setContrasena(password);
                usuario.setProveedor("Meta");
                usuario.setEntrenamientos(new ArrayList<>());
                usuario.setRetos(new HashMap<>());
                usuario.setAmigos(new ArrayList<>());
                usuarioService.registrarUsuario(usuario);
                return usuario;
            } else {
                System.out.println("Autenticación fallida para el usuario: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*
    @Override
    public void logout(String token) throws RemoteException {
        
        usuarioService.logout(token);
    }
	*/
    
    @Override
    public void logout(String username) throws RemoteException {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorNombre(username);
        if (usuario != null) {
        	String token= usuario.getToken();
            String proveedor = usuario.getProveedor();
            if ("Google".equals(proveedor)) {
                facadeG.logout(username);
            } else if ("Meta".equals(proveedor)) {
            	 try {
            	        MetaAuthClient metaAuthClient = new MetaAuthClient("localhost", 1101);
            	        metaAuthClient.sendRequest("LOGOUT;" + username);
            	        System.out.println("Logout realizado correctamente en Meta.");
            	    } catch (IOException e) {
            	        System.err.println("Error durante el logout en Meta: " + e.getMessage());
            	        e.printStackTrace();
            	    }
            }
            else {
            	usuarioService.logout(token);
            	tokensActivos.remove(username);
            }
            
            System.out.println("Logout completo para el usuario: " + username);
        } else {
            System.out.println("Usuario no encontrado para logout: " + username);
        }
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
	public void actualizarEntreno(EntrenamientoDTO entrenamiento, UsuarioDTO usu, String titulo, String deporte, double distancia, double duracion) throws RemoteException {
		entrenamientoService.actualizarEntreno(entrenamiento, usu, titulo, deporte, distancia, duracion);
		
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