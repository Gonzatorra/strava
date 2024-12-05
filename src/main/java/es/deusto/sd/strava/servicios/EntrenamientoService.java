package es.deusto.sd.strava.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.assembler.EntrenamientoAssembler;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Usuario;

public class EntrenamientoService {

    public EntrenamientoDTO crearEntreno(UsuarioDTO usuario, String titulo, String deporte, double distancia, LocalDate fechaIni,
                             float horaInicio, double duracion) {
        Entrenamiento entrenamiento = new Entrenamiento(0, UsuarioAssembler.toDomain(usuario), titulo, deporte, (float) distancia, fechaIni, horaInicio, duracion);
        usuario.getEntrenamientos().add(entrenamiento);
        System.out.println("Entrenamiento creado: " + entrenamiento.getTitulo());
        return EntrenamientoAssembler.toDTO(entrenamiento);
    }

    public void actualizarEntreno(EntrenamientoDTO entrenamiento, double distancia, LocalDate fechaIni,
                                  float horaInicio, double duracion) {
        entrenamiento.setDistancia((float) distancia);
        entrenamiento.setFecIni(fechaIni);
        entrenamiento.setHoraIni(horaInicio);
        entrenamiento.setDuracion(duracion);
        System.out.println("Entrenamiento actualizado: " + entrenamiento.getTitulo());
    }

    public void eliminarEntreno(EntrenamientoDTO entrenamiento) {
        System.out.println("Entrenamiento eliminado: " + entrenamiento.getTitulo());
    }

    public void visualizarEntreno(EntrenamientoDTO entrenamientoDTO) {
        System.out.println("Visualizando entrenamiento: " + entrenamientoDTO.getTitulo());
    }
}