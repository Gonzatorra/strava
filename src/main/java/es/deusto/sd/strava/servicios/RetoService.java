package es.deusto.sd.strava.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class RetoService {
	private static HashMap<Integer,Reto> retos = new HashMap<>(); 
    private static int idCounter = 1;  //para signar IDs unicos

    public Reto crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
                          String deporte, Usuario usuarioCreador, List<Usuario> participantes) {
    	
    	int nuevoId = idCounter++;
    	participantes.add(usuarioCreador);
        Reto reto = new Reto(nuevoId, deporte, usuarioCreador, nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, participantes);
        usuarioCreador.getRetos().put(reto, "prueba");
        retos.put(nuevoId,reto);
        System.out.println("Reto creado: " + reto.getNombre());
        return reto;
    }

    public void aceptarReto(Usuario usuario, Reto reto) {
        reto.aceptarReto(usuario, reto);
    }

    public HashMap<Integer,Reto> visualizarReto() {
        return retos;
    }

    public void actualizarReto(Reto reto, String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
            Usuario usuarioCreador, String deporte, List<Usuario> participantes) {
    	Reto retoNuevo= reto.actualizarReto(reto, nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, usuarioCreador, deporte, participantes);
    	retos.put(reto.getId(),retoNuevo);
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