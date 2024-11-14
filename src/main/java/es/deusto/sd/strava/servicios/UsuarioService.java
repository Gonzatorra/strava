package es.deusto.sd.strava.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.deusto.sd.strava.DTO.*;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class UsuarioService {

    private static Map<Integer, Usuario> usuarios = new HashMap<>(); // Uso de HashMap para acceso más eficiente
    private static int idCounter = 1;  // Para asignar IDs únicos

    public UsuarioDTO registrar(String username, String contrasena, String email, String nombre) {
        // Generamos un nuevo id para el usuario
        int nuevoId = idCounter++;  // Asegúrate de que idCounter está inicializado y es accesible
        
        // Crear el objeto Usuario
        Usuario usuario = new Usuario(nuevoId, username, email, contrasena, nombre, 0, 0, null, 0, 0, generateToken(), new ArrayList<>(), new HashMap<>());
        
        // Guardar el usuario
        usuarios.put(usuario.getId(), usuario);
        System.out.println("Usuario registrado");
        
        // Convertimos a UsuarioDTO para devolverlo
        return new UsuarioDTO(usuario);  // Retornamos el DTO del usuario recién registrado
    }

    public UsuarioDTO login(String email, String contrasena) {
    	System.out.println("Login usuario");
        /*for (Usuario usuario : usuarios.values()) {
            if (usuario.getEmail().equals(email) && usuario.getContrasena().equals(contrasena)) {
                usuario.setToken(generateToken());
                System.out.println("Login exitoso");

                // Convertimos a UsuarioDTO para devolverlo
                return new UsuarioDTO(usuario);
            }
        }
        System.out.println("Login fallido");*/
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

    public void actualizarUsuario(Usuario usuario, String contrasena, String nombre, float peso, float altura,
                                   float fecCMax, float frecCReposo) {
        if (usuarios.containsKey(usuario.getId())) {
            usuario.setContrasena(contrasena);
            usuario.setNombre(nombre);
            usuario.setPeso(peso);
            usuario.setAltura(altura);
            usuario.setFecCMax(fecCMax);
            usuario.setFecCReposo(frecCReposo);
            System.out.println("Se ha actualizado el usuario");
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
