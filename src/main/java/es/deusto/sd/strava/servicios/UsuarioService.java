package es.deusto.sd.strava.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import es.deusto.sd.strava.DTO.*;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class UsuarioService {

    private static HashMap<Integer, Usuario> usuarios = new HashMap<>(); // Uso de HashMap para acceso mas eficiente
    private static int idCounter = 1;  // Para asignar IDs unicos

    public UsuarioDTO registrar(String username, String contrasena, String email, String nombre) {
        // Generamos un nuevo id para el usuario
        int nuevoId = idCounter++;
        
        // Crear el objeto Usuario
        Usuario usuario = new Usuario(nuevoId, username, email, contrasena, nombre, null, new ArrayList<>(), new HashMap<>());
           
        
        // Guardar el usuario
        usuarios.put(usuario.getId(), usuario);
        System.out.println("Usuario registrado");

        // Convertimos a UsuarioDTO para devolverlo
        return new UsuarioDTO(usuario);  // Retornamos el DTO del usuario recién registrado
    }

    public UsuarioDTO login(String email, String contrasena) {
    	try {
	    	
	        for (Usuario usuario : usuarios.values()) {
	      
	            if (usuario.getEmail().equals(email) && usuario.getContrasena().equals(contrasena)) {
	                //usuario.setToken(generateToken());
	                System.out.println("Login exitoso");
	
	                // Convertimos a UsuarioDTO para devolverlo
	                return new UsuarioDTO(usuario);
	            }
	        }
    	}
    	catch (Exception ex) {
	        System.out.println("Login fallido");
	        ex.printStackTrace();
	        return null;
        }
    	return null;
    }

    public void logout(String token) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getToken().equals(token)) {
                usuario.setToken(null);
                System.out.println("Usuario desconectado");
                return;
            }
        }
        System.out.println("Token no encontrado");
    }

    private String generateToken() {
        return "token-" + System.currentTimeMillis();  // Generación más dinámica de un token
    }

    public void eliminarUsuario(Usuario usuario) {
        if (usuario != null) {
            usuarios.remove(usuario.getId());
            System.out.println("Usuario eliminado");
        }
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
    	Usuario usuario = usuarios.get(usuarioDTO.getId());
        if (usuario != null) {
        	usuario.setUsername(usuarioDTO.getUsername());
        	usuario.setEmail(usuarioDTO.getEmail());
        	usuario.setContrasena(usuario.getContrasena());
        	usuario.setNombre(usuarioDTO.getNombre());
        	usuario.setPeso(usuarioDTO.getPeso());
        	usuario.setAltura(usuarioDTO.getAltura());
        }
        
    }

    public List<Entrenamiento> getEntrenosUsuario(Usuario usuario, double fechaIni, double fechaFin) {
        System.out.println("Devuelve entrenamientos del usuario");
        return null;
    }

    public List<Reto> getRetosUsuario(Usuario usuario, String estado) {
        System.out.println("Devuelve retos del usuario");
        
        return null;
    }
}
