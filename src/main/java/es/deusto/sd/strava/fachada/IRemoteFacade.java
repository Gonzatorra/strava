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

public interface IRemoteFacade extends Remote {
	//usuario
    UsuarioDTO registrarUsuario(String username, String contrasena, String email, String nombre) throws RemoteException;
    UsuarioDTO login(String email, String contrasena) throws RemoteException;
    UsuarioDTO loginConProveedor(String username, String password, String proveedor) throws RemoteException;
    void logout(String token) throws RemoteException;
    void eliminarUsuario(int userId) throws RemoteException;
    void actualizarUsuario(UsuarioDTO usuarioDTO) throws RemoteException;
    ArrayList<Usuario> getAmigos(Usuario usuario) throws RemoteException;
    UsuarioService getUsuarioService() throws RemoteException;
    HashMap<Integer, Usuario> getUsuarios() throws RemoteException;
    
    //entrenamiento
    Entrenamiento crearEntreno(Usuario usuario, String titulo, String deporte, double distancia, LocalDate fechaIni, float horaInicio, double duracion) throws java.rmi.RemoteException;
    void actualizarEntreno(Entrenamiento entrenamiento, double distancia, LocalDate fechaIni, float horaInicio, double duracion) throws java.rmi.RemoteException;
    void eliminarEntreno(Entrenamiento entrenamiento) throws java.rmi.RemoteException;
    void visualizarEntreno(EntrenamientoDTO entrenamiento) throws java.rmi.RemoteException;
    
    //reto
    Reto crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo, String deporte, Usuario usuarioCreador, List<Usuario> participantes) throws RemoteException;
    void aceptarReto(Usuario usuario, Reto reto) throws RemoteException;
    HashMap<Integer,Reto> visualizarReto() throws RemoteException;
    void actualizarReto(Reto reto, String nombre, LocalDateTime fechaIni, LocalDateTime fechaFin, float distancia, float tiempo, Usuario usuarioCreador, String deporte, List<Usuario> participantes) throws RemoteException;
    void eliminarReto(Usuario usuario, Reto reto) throws RemoteException;
	List<Usuario> obtenerClasificacion(Reto reto) throws RemoteException;
	void calcularProgreso(Usuario usuario) throws RemoteException;    

}

