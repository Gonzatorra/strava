package es.deusto.sd.strava.assembler;

import java.util.ArrayList;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class RetoAssembler {
	 public static RetoDTO toDTO(Reto reto) {
		 	RetoDTO dto = new RetoDTO();
		 	dto.setId(reto.getId());
		 	dto.setDeporte(reto.getDeporte());
		 	dto.setUsuarioCreador(UsuarioAssembler.toDTO(reto.getUsuarioCreador()));
		 	dto.setNombre(reto.getNombre());
		 	dto.setFecIni(reto.getFecIni());
		 	dto.setFecFin(reto.getFecFin());
		 	dto.setObjetivoDistancia(reto.getObjetivoDistancia());
		 	dto.setObjetivoTiempo(reto.getObjetivoTiempo());
		 	dto.setParticipantes(reto.getParticipantes());
		 	return dto;
	    }
	 
	 public static Reto toDomain(RetoDTO retoDTO) {
	    	Reto reto = new Reto();
	    	reto.setId(retoDTO.getId());
	    	reto.setDeporte(retoDTO.getDeporte());
	    	reto.setUsuarioCreador(UsuarioAssembler.toDomain(retoDTO.getUsuarioCreador()));
	    	reto.setNombre(retoDTO.getNombre());
	    	reto.setFecIni(retoDTO.getFecIni());
	    	reto.setFecFin(retoDTO.getFecFin());
	    	reto.setObjetivoDistancia(retoDTO.getObjetivoDistancia());
	    	reto.setObjetivoTiempo(retoDTO.getObjetivoTiempo());
	    	reto.setParticipantes(retoDTO.getParticipantes());
		 	return reto;
	    }

}
