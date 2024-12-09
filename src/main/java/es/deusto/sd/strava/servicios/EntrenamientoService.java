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
		// Crear el nuevo entrenamiento
		EntrenamientoDTO entreno = new EntrenamientoDTO(UsuarioAssembler.toDomain(usuario), titulo, deporte, (float) distancia, fechaIni, horaInicio, duracion);
		return entreno;
	}


	/*public void actualizarEntreno(EntrenamientoDTO entrenamiento, String titulo, String deporte, double distancia, double duracion) {
	    for (Entrenamiento e : entrenamiento.getUsuario().getEntrenamientos()) {
	        if (e.getId() == entrenamiento.getId()) {
	            e.setTitulo(titulo);
	            e.setDeporte(deporte);
	            e.setDistancia((float) distancia);
	            e.setDuracion(duracion);

	            System.out.println("Entrenamiento actualizado: " + titulo);
	            return;  // Sale del método después de actualizar el entrenamiento
	        }
	    }

	    // Mensaje fuera del bucle, solo si no se encuentra el entrenamiento
	    System.out.println("No se encontró el entrenamiento para actualizar.");
	}*/



    public void eliminarEntreno(int index, EntrenamientoDTO entrenamiento) {
        System.out.println("Entrenamiento eliminado: " + entrenamiento.getTitulo());
    }

    public void visualizarEntreno(EntrenamientoDTO entrenamientoDTO) {
        System.out.println("Visualizando entrenamiento: " + entrenamientoDTO.getTitulo());
    }
}