package es.deusto.sd.strava.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Usuario;

public class EntrenamientoService {

    public Entrenamiento crearEntreno(Usuario usuario, String titulo, String deporte, double distancia, LocalDate fechaIni,
                             float horaInicio, double duracion) {
        Entrenamiento entrenamiento = new Entrenamiento(0, usuario, titulo, deporte, (float) distancia, fechaIni, horaInicio, duracion);
        usuario.getEntrenamientos().add(entrenamiento);
        System.out.println("Entrenamiento creado: " + entrenamiento.getTitulo());
        return entrenamiento;
    }

    public void actualizarEntreno(Entrenamiento entrenamiento, double distancia, LocalDate fechaIni,
                                  float horaInicio, double duracion) {
        entrenamiento.setDistancia((float) distancia);
        entrenamiento.setFecIni(fechaIni);
        entrenamiento.setHoraIni(horaInicio);
        entrenamiento.setDuracion(duracion);
        System.out.println("Entrenamiento actualizado: " + entrenamiento.getTitulo());
    }

    public void eliminarEntreno(Entrenamiento entrenamiento) {
        System.out.println("Entrenamiento eliminado: " + entrenamiento.getTitulo());
    }

    public void visualizarEntreno(EntrenamientoDTO entrenamientoDTO) {
        System.out.println("Visualizando entrenamiento: " + entrenamientoDTO.getTitulo());
    }
}