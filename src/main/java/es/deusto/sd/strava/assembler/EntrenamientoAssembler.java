package es.deusto.sd.strava.assembler;

import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;


public class EntrenamientoAssembler {
	public static EntrenamientoDTO toDTO(Entrenamiento entrenamiento) {
        EntrenamientoDTO dto = new EntrenamientoDTO();
        dto.setId(entrenamiento.getId());
        dto.setUsuario(entrenamiento.getUsuario());
        dto.setTitulo(entrenamiento.getTitulo());
        dto.setDeporte(entrenamiento.getDeporte());
        dto.setDistancia(entrenamiento.getDistancia());
        dto.setFecIni(entrenamiento.getFecIni());
        dto.setHoraIni(entrenamiento.getHoraIni());
        dto.setDuracion(entrenamiento.getDuracion());
        return dto;

    }
	
	public Entrenamiento toDomain(EntrenamientoDTO entrenamientoDTO) {
        return new Entrenamiento(entrenamientoDTO.getId(),
        		entrenamientoDTO.getUsuario(),
        		entrenamientoDTO.getTitulo(),
        		entrenamientoDTO.getDeporte(),
        		entrenamientoDTO.getDistancia(),
        		entrenamientoDTO.getFecIni(),
        		entrenamientoDTO.getHoraIni(),
        		entrenamientoDTO.getDuracion());
    }
	
}
