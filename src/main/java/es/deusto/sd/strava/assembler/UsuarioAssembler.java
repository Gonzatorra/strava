package es.deusto.sd.strava.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class UsuarioAssembler {

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setContrasena(usuario.getContrasena());
        dto.setNombre(usuario.getNombre());
        dto.setPeso(usuario.getPeso());
        dto.setAltura(usuario.getAltura());
        dto.setfNacimiento(usuario.getfNacimiento());
        dto.setFecCMax(usuario.getFecCMax());
        dto.setFecCReposo(usuario.getFecCReposo());
        dto.setAmigos(usuario.getAmigos());
        dto.setToken(usuario.getToken());

        if (usuario.getEntrenamientos() != null) {
            dto.setEntrenamientos(new ArrayList<>(usuario.getEntrenamientos()));
        }
        if (usuario.getAmigos() != null) {
            dto.setAmigos(new ArrayList<>(usuario.getAmigos()));
        }

        if (usuario.getRetos() != null) {
            HashMap<Reto, String> retosMap = new HashMap<>();
            for (Map.Entry<Reto, String> entry : usuario.getRetos().entrySet()) {
                retosMap.put(entry.getKey(), entry.getValue());
            }
            dto.setRetos(retosMap);
        }

        return dto;
    }

    public static Usuario toDomain(UsuarioDTO usuarioDTO) {
	    Usuario usuario = new Usuario();
	    usuario.setId(usuarioDTO.getId());
	    usuario.setUsername(usuarioDTO.getUsername());
	    usuario.setEmail(usuarioDTO.getEmail());
	    usuario.setContrasena(usuarioDTO.getContrasena());
	    usuario.setNombre(usuarioDTO.getNombre());
	    usuario.setPeso(usuarioDTO.getPeso());
	    usuario.setAltura(usuarioDTO.getAltura());
	    usuario.setfNacimiento(usuarioDTO.getfNacimiento());
	    usuario.setFecCMax(usuarioDTO.getFecCMax());
	    usuario.setFecCReposo(usuarioDTO.getFecCReposo());
	    usuario.setToken(usuarioDTO.getToken());
	    usuario.setAmigos(usuarioDTO.getAmigos());

	    if (usuarioDTO.getEntrenamientos() != null) {
	        usuario.setEntrenamientos(new ArrayList<>(usuarioDTO.getEntrenamientos()));
	    }
	    
	    if (usuarioDTO.getAmigos() != null) {
	        usuario.setAmigos(new ArrayList<>(usuarioDTO.getAmigos()));
	    }

	    if (usuarioDTO.getRetos() != null) {
	        HashMap<Reto, String> retosMap = new HashMap<>();
	        for (Map.Entry<Reto, String> entry : usuarioDTO.getRetos().entrySet()) {
	            retosMap.put(entry.getKey(), entry.getValue());
	        }
	        usuario.setRetos(retosMap);
	    }

	    return usuario;
	}
}
