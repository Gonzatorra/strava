package es.deusto.sd.strava.servicios;

import java.util.HashMap;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;

public class EntrenamientoService {

    private HashMap<Integer, Entrenamiento> entrenamientos = new HashMap<>();

    public void crearEntreno(EntrenamientoDTO entrenamientoDTO) {
        Entrenamiento entrenamiento = new Entrenamiento(
            entrenamientoDTO.getId(),
            entrenamientoDTO.getUsuario(),
            entrenamientoDTO.getTitulo(),
            entrenamientoDTO.getDeporte(),
            entrenamientoDTO.getDistancia(),
            entrenamientoDTO.getFecIni(),
            entrenamientoDTO.getHoraIni(),
            entrenamientoDTO.getDuracion()
        );
        entrenamientos.put(entrenamiento.getId(), entrenamiento);
    }

    public EntrenamientoDTO getEntreno(int id) {
        Entrenamiento entrenamiento = entrenamientos.get(id);
        return entrenamiento != null ? new EntrenamientoDTO(entrenamiento) : null;
    }

    public void actualizarEntreno(EntrenamientoDTO entrenamientoDTO) {
        Entrenamiento entrenamiento = entrenamientos.get(entrenamientoDTO.getId());
        if (entrenamiento != null) {
            entrenamiento.setTitulo(entrenamientoDTO.getTitulo());
            entrenamiento.setDeporte(entrenamientoDTO.getDeporte());
            entrenamiento.setDistancia(entrenamientoDTO.getDistancia());
            entrenamiento.setFecIni(entrenamientoDTO.getFecIni());
            entrenamiento.setHoraIni(entrenamientoDTO.getHoraIni());
            entrenamiento.setDuracion(entrenamientoDTO.getDuracion());
        }
    }

    public void eliminarEntreno(int id) {
        entrenamientos.remove(id);
    }
}