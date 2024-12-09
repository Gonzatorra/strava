package es.deusto.sd.strava.servicios;

import java.rmi.RemoteException;
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

    private static HashMap<Integer, UsuarioDTO> usuarios = new HashMap<>(); 
    private static int idCounter = 1;  //para signar IDs unicos
    private static UsuarioService instancia;
    private UsuarioDTO usuarioAutenticado; // Usuario actualmente autenticado
    
    public UsuarioService() {
        usuarios = new HashMap<>();
        idCounter = 1;
        System.out.println("UsuarioService inicializado.");
    }

    public UsuarioDTO registrar(String username, String contrasena, String email, String nombre) {
        int nuevoId = idCounter++;
        Usuario usuario = new Usuario(nuevoId, username, email, contrasena, nombre, null, new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        usuarios.put(UsuarioAssembler.toDTO(usuario).getId(), UsuarioAssembler.toDTO(usuario));
        System.out.println("Usuario registrado: " + username);
        return UsuarioAssembler.toDTO(usuario);
    }

    public UsuarioDTO login(String username, String contrasena) {
        for (UsuarioDTO usuario : usuarios.values()) {
            if (usuario.getUsername().equals(username) && usuario.getContrasena().equals(contrasena)) {
                System.out.println("Login exitoso para: " + username);
                return usuario;
            }
        }
        System.out.println("Login fallido para: " + username);
        return null;
    }

    public void logout(String token) {
        for (UsuarioDTO usu : usuarios.values()) {
        	Usuario usuario= UsuarioAssembler.toDomain(usu);
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

    public void eliminarUsuario(UsuarioDTO usuario) {
        if (usuario != null) {
            usuarios.remove(usuario.getId());
            System.out.println("Usuario eliminado");
        }
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
    	UsuarioDTO usuario = usuarios.get(usuarioDTO.getId());
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
    
    public UsuarioDTO obtenerUsuarioPorNombre(String username) {
        return usuarios.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


    public void registrarUsuario(UsuarioDTO usuario) {
        if (usuarios.values().stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername()))) {
            throw new IllegalArgumentException("Usuario ya existe: " + usuario.getUsername());
        }

        int newId = usuarios.size() + 1;
        usuario.setId(newId);
        usuarios.put(newId, usuario);
    }

    

    public List<EntrenamientoDTO> getEntrenosUsuario(UsuarioDTO usuario, double fechaIni, double fechaFin) {
        System.out.println("Devuelve entrenamientos del usuario");
        return null;
    }

    public ArrayList<RetoDTO> getRetosUsuario(UsuarioDTO usuario, String estado) {
        System.out.println("Devuelve retos del usuario");
        
        return null;
    }
    
    
    public static HashMap<Integer, UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public static void setUsuarios(HashMap<Integer, UsuarioDTO> usuarios) {
		UsuarioService.usuarios = usuarios;
	}

	public List<UsuarioDTO> getAmigos(UsuarioDTO usuario){
		ArrayList<UsuarioDTO> amigos= new ArrayList<UsuarioDTO>();
		for (UsuarioDTO usu: usuario.getAmigos()) {
			amigos.add(usu);
		}
		
    	return amigos;
    }
	
	public static UsuarioService getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioService();
        }
        return instancia;
    }

    public UsuarioDTO obtenerUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void autenticarUsuario(UsuarioDTO usuario, String token) {
        usuario.setToken(token); // Actualiza el token del usuario
        this.usuarioAutenticado = usuario;
    }

    public boolean esTokenValido(String token) {
        return usuarioAutenticado != null && usuarioAutenticado.getToken().equals(token);
    }
    
    public Map<Integer, Float> calcularProgreso(UsuarioDTO usuarioDTO) throws RemoteException {
        Map<Integer, Float> progresoPorReto = new HashMap<>();

        // Obtener retos y entrenamientos del usuario
        HashMap<RetoDTO, String> retos = usuarioDTO.getRetos();  // Map<Reto, Estado>
        List<EntrenamientoDTO> entrenamientos = usuarioDTO.getEntrenamientos();

        // Iterar sobre los retos
        for (RetoDTO reto : retos.keySet()) {
            double totalDistancia = 0;
            double totalDuracion = 0;

            // Filtrar entrenamientos relevantes basados en las fechas y el deporte del reto
            for (EntrenamientoDTO entrenamiento : entrenamientos) {
                if (entrenamiento.getDeporte().equalsIgnoreCase(reto.getDeporte())
                        && !entrenamiento.getFecIni().isBefore(reto.getFecIni().toLocalDate())
                        && !entrenamiento.getFecIni().isAfter(reto.getFecFin().toLocalDate())) {

                    totalDistancia += entrenamiento.getDistancia();
                    totalDuracion += entrenamiento.getDuracion();
                }
            }

            // Calcular el progreso basado en el objetivo de distancia y tiempo
            float progresoDistancia = (reto.getObjetivoDistancia() > 0)
                    ? (float) (totalDistancia / reto.getObjetivoDistancia()) * 100
                    : 0;

            float progresoTiempo = (reto.getObjetivoTiempo() > 0)
                    ? (float) (totalDuracion / reto.getObjetivoTiempo()) * 100
                    : 0;

            // Combina progreso de distancia y tiempo, eligiendo el valor más realista
            float progreso = Math.min(100, Math.max(progresoDistancia, progresoTiempo));

            // Almacenar progreso en el mapa
            progresoPorReto.put(reto.getId(), progreso);
        }

        return progresoPorReto;
    }



    
}
