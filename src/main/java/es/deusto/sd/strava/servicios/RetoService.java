package es.deusto.sd.strava.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class RetoService {

    public void crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
                          String deporte, Usuario usuarioCreador, List<Usuario> participantes) {
        Reto reto = new Reto(0, deporte, usuarioCreador, nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, participantes);
        
        System.out.println("Reto creado: " + reto.getNombre());
    }

    public void aceptarReto(Usuario usuario, Reto reto) {
        reto.aceptarReto(usuario, reto);
    }

    public void visualizarReto(RetoDTO retoDTO, String estado) {
        System.out.println("Visualizando reto: " + retoDTO.getNombre() + " con estado: " + estado);
    }

    public void actualizarReto(Reto reto, String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
            Usuario usuarioCreador, String deporte, List<Usuario> participantes) {
    	reto.actualizarReto(reto, nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, usuarioCreador, deporte, participantes);
    }

    public void eliminarReto(Usuario usuario, Reto reto) {
        reto.eliminarReto(usuario, reto);
    }

    public List<Usuario> obtenerClasificacion(Reto reto) {
        return reto.obtenerClasificacion(reto);
    }

    public void calcularProgreso(Usuario usuario) {
        System.out.println("Calculando progreso del usuario: " + usuario.getUsername());
    }
}