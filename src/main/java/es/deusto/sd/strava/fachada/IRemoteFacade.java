package es.deusto.sd.strava.fachada;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;
import es.deusto.sd.strava.servicios.UsuarioService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IRemoteFacade extends Remote {
	//usuario
    UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException;
    UsuarioDTO login(String email, String contrasena) throws RemoteException;
    UsuarioDTO loginConProveedor(String username, String password, String proveedor) throws RemoteException;
    void logout(String token) throws RemoteException;
    void eliminarUsuario(int userId) throws RemoteException;
    void actualizarUsuario(UsuarioDTO usuarioDTO) throws RemoteException;
    ArrayList<UsuarioDTO> getAmigos(UsuarioDTO usuario) throws RemoteException;
    UsuarioService getUsuarioService() throws RemoteException;
    HashMap<Integer, UsuarioDTO> getUsuarios() throws RemoteException;
    
    //entrenamiento
    EntrenamientoDTO crearEntreno(int index, UsuarioDTO usuario, String titulo, String deporte, double distancia, LocalDate fechaIni, float horaInicio, double duracion) throws java.rmi.RemoteException;
    void actualizarEntreno(UsuarioDTO usuario, EntrenamientoDTO entrenamiento, String titulo, String deporte, double distancia, double duracion) throws RemoteException;
    void eliminarEntreno(EntrenamientoDTO entrenamiento) throws java.rmi.RemoteException;
    void visualizarEntreno(EntrenamientoDTO entrenamiento) throws java.rmi.RemoteException;
    
    //reto
    RetoDTO crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo, String deporte, UsuarioDTO usuarioCreador, List<UsuarioDTO> participantes) throws RemoteException;
    void aceptarReto(UsuarioDTO usuario, RetoDTO reto) throws RemoteException;
    HashMap<Integer,RetoDTO> visualizarReto() throws RemoteException;
    void actualizarReto(RetoDTO reto, String nombre, LocalDateTime fechaIni, LocalDateTime fechaFin, float distancia, float tiempo, UsuarioDTO usuarioCreador, String deporte, List<UsuarioDTO> participantes) throws RemoteException;
    void eliminarReto(UsuarioDTO usuario, RetoDTO reto) throws RemoteException;
	List<UsuarioDTO> obtenerClasificacion(RetoDTO reto) throws RemoteException;
	void calcularProgreso(UsuarioDTO usuario) throws RemoteException;    

}

