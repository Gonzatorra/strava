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
    private static UsuarioService instancia;
    private Usuario usuarioAutenticado; // Usuario actualmente autenticado
    
    public UsuarioService() {
        usuarios = new HashMap<>();
        idCounter = 1;
        System.out.println("UsuarioService inicializado.");
    }

    public UsuarioDTO registrar(String username, String contrasena, String email, String nombre) {
        int nuevoId = idCounter++;
        Usuario usuario = new Usuario(nuevoId, username, email, contrasena, nombre, null, new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        usuarios.put(usuario.getId(), usuario);
        System.out.println("Usuario registrado: " + username);
        return UsuarioAssembler.toDTO(usuario);
    }

    public UsuarioDTO login(String username, String contrasena) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getUsername().equals(username) && usuario.getContrasena().equals(contrasena)) {
                System.out.println("Login exitoso para: " + username);
                return UsuarioAssembler.toDTO(usuario);
            }
        }
        System.out.println("Login fallido para: " + username);
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
    
    public Usuario obtenerUsuarioPorNombre(String username) {
        return usuarios.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


    public void registrarUsuario(Usuario usuario) {
        if (usuarios.values().stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            throw new IllegalArgumentException("Usuario ya existe: " + usuario.getUsername());
        }

        int newId = usuarios.size() + 1;
        usuario.setId(newId);
        usuarios.put(newId, usuario);
    }

    

    public List<Entrenamiento> getEntrenosUsuario(Usuario usuario, double fechaIni, double fechaFin) {
        System.out.println("Devuelve entrenamientos del usuario");
        return null;
    }

    public ArrayList<Reto> getRetosUsuario(Usuario usuario, String estado) {
        System.out.println("Devuelve retos del usuario");
        
        return null;
    }
    
    
    public static HashMap<Integer, Usuario> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(HashMap<Integer, Usuario> usuarios) {
		UsuarioService.usuarios = usuarios;
	}

	public List<Usuario> getAmigos(Usuario usuario){
    	return usuario.getAmigos();
    }
	
	public static UsuarioService getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioService();
        }
        return instancia;
    }

    public Usuario obtenerUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void autenticarUsuario(Usuario usuario, String token) {
        usuario.setToken(token); // Actualiza el token del usuario
        this.usuarioAutenticado = usuario;
    }

    public boolean esTokenValido(String token) {
        return usuarioAutenticado != null && usuarioAutenticado.getToken().equals(token);
    }
    
}
