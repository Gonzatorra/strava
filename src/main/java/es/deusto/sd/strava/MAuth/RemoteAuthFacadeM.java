package es.deusto.sd.strava.MAuth;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.servicios.ServicioAutentificacion;
import es.deusto.sd.strava.servicios.UsuarioService;

public class RemoteAuthFacadeM implements IRemoteAuthFacadeM {

    private final Map<String, String> userStore = new HashMap<>();
    private final Map<String, String> tokenStore = new HashMap<>();
    private final Map<String, String> userInfoStore = new HashMap<>();
    private ServicioAutentificacion servicioAutenticacion;
    UsuarioService servicioUsu;

    public RemoteAuthFacadeM() throws RemoteException {}

    @Override
    public String registerUser(String username, String password, String email) throws RemoteException {
        if (userStore.containsKey(username)) {
            return "User already exists";
        }
        userStore.put(username, password);
        userInfoStore.put(username, username + ", " + email);
        return "User registered successfully";
    }

    @Override
    public String loginUser(String username, String password, String proveedor) throws RemoteException {
    	if (userStore.containsKey(username) && userStore.get(username).equals(password)) {
        	if(tokenStore.get(username)!= null) 
            {
        		String token= servicioAutenticacion.autenticar(username, password, "Meta", proveedor);
        		tokenStore.put(token, username);
        		UsuarioDTO usuario= servicioUsu.obtenerUsuarioPorNombre(username);
        		servicioUsu.actualizarUsuario(usuario);
        		
        		return token;
            }
        	else {
        		return tokenStore.get(username);
        	}
        	
            
            
        }
        return null;
    }
    
    @Override
    public void logout(String username) throws RemoteException {
        if (tokenStore.containsKey(username)) {
            tokenStore.remove(username);
            System.out.println("Token eliminado para usuario de Meta: " + username);
        } else {
            System.out.println("No se encontró token para el usuario: " + username);
        }
    }

    
    @Override
    public String getUserInfo(String token) throws RemoteException {
        String username = tokenStore.get(token);
        if (username != null && userInfoStore.containsKey(username)) {
            return userInfoStore.get(username);
        }
        return "Invalid token";
    }
}
