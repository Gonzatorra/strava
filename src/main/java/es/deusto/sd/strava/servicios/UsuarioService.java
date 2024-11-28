package es.deusto.sd.strava.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import es.deusto.sd.strava.DTO.*;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class UsuarioService {

    private static HashMap<Integer, Usuario> usuarios = new HashMap<>(); 
    private static int idCounter = 1;  //para signar IDs unicos

    public UsuarioDTO registrar(String username, String contrasena, String email, String nombre) {
        
        int nuevoId = idCounter++;
        
        Usuario usuario = new Usuario(nuevoId, username, email, contrasena, nombre, null, new ArrayList<>(), new HashMap<>());
           
        usuarios.put(usuario.getId(), usuario);
        System.out.println("Usuario registrado");

        return UsuarioAssembler.toDTO(usuario);
    }

    public UsuarioDTO login(String username, String contrasena) {
    	try {
	    	
	        for (Usuario usuario : usuarios.values()) {
	      
	            if (usuario.getUsername().equals(username) && usuario.getContrasena().equals(contrasena)) {
	                //usuario.setToken(generateToken());
	                System.out.println("Login exitoso");
	
	                return UsuarioAssembler.toDTO(usuario);
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
        return "token-" + System.currentTimeMillis(); 
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
        	usuario.setContrasena(usuarioDTO.getContrasena());
        	usuario.setNombre(usuarioDTO.getNombre());
        	usuario.setPeso(usuarioDTO.getPeso());
        	usuario.setAltura(usuarioDTO.getAltura());
        	usuario.setfNacimiento(usuarioDTO.getfNacimiento());
        	
        	
        	usuario.setEntrenamientos(usuarioDTO.getEntrenamientos());
        	usuario.setRetos(usuarioDTO.getRetos());
        	usuarios.put(usuarioDTO.getId(), usuario);
        	System.out.println(usuario.getUsername()+usuario.getContrasena()+usuario.getEntrenamientos());
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
