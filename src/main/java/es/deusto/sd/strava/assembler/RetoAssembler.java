package es.deusto.sd.strava.assembler;

import java.util.ArrayList;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.dominio.Reto;

public class RetoAssembler {
	 public static RetoDTO toDTO(Reto reto) {
		 	RetoDTO dto = new RetoDTO();
		 	dto.setId(reto.getId());
		 	dto.setDeporte(reto.getDeporte());
		 	dto.setUsuarioCreador(reto.getUsuarioCreador());
		 	dto.setNombre(reto.getNombre());
		 	dto.setFecIni(reto.getFecIni());
		 	dto.setFecFin(reto.getFecFin());
		 	dto.setObjetivoDistancia(reto.getObjetivoDistancia());
		 	dto.setObjetivoTiempo(reto.getObjetivoTiempo());
		 	if(reto.getParticipantes() != null) {
		 		dto.setParticipantes(new ArrayList<>(reto.getParticipantes()));
		 	}
		 	return dto;
	    }
	 
	 public Reto toDomain(RetoDTO retoDTO) {
	    	Reto reto = new Reto();
	    	reto.setId(retoDTO.getId());
	    	reto.setDeporte(retoDTO.getDeporte());
	    	reto.setUsuarioCreador(retoDTO.getUsuarioCreador());
	    	reto.setNombre(retoDTO.getNombre());
	    	reto.setFecIni(retoDTO.getFecIni());
	    	reto.setFecFin(retoDTO.getFecFin());
	    	reto.setObjetivoDistancia(retoDTO.getObjetivoDistancia());
	    	reto.setObjetivoTiempo(retoDTO.getObjetivoTiempo());
	    	if(retoDTO.getParticipantes() != null) {
		 		reto.setParticipantes(new ArrayList<>(retoDTO.getParticipantes()));
		 	}
	    	return reto;
	    }

}
