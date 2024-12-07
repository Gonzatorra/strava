package es.deusto.sd.strava.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.assembler.RetoAssembler;
import es.deusto.sd.strava.dominio.Usuario;

public class RetoService {
	private static HashMap<Integer,RetoDTO> retos = new HashMap<>(); 
    private static int idCounter = 1;  //para signar IDs unicos

    public RetoDTO crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
                          String deporte, UsuarioDTO usuarioCreador, List<UsuarioDTO> participantes) {
    	List<Usuario> particips= new ArrayList<Usuario>();
    	for (UsuarioDTO usu: participantes) {
    		particips.add(UsuarioAssembler.toDomain(usu));
    	}
    	
    	int nuevoId = idCounter++;
    	Usuario usu= UsuarioAssembler.toDomain(usuarioCreador);
    	particips.add(usu);
        RetoDTO reto = RetoAssembler.toDTO(new Reto(nuevoId, deporte, usu, nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, particips));
        UsuarioAssembler.toDomain(usuarioCreador).getRetos().put(RetoAssembler.toDomain(reto), "prueba");
        retos.put(nuevoId,reto);
        System.out.println("Reto creado: " + reto.getNombre());
        return reto;
    }

    public void aceptarReto(UsuarioDTO usuario, RetoDTO reto) {
    	RetoAssembler.toDomain(reto).aceptarReto(UsuarioAssembler.toDomain(usuario));
    }

    public HashMap<Integer,RetoDTO> visualizarReto() {
        return retos;
    }

    public void actualizarReto(RetoDTO reto, String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
            UsuarioDTO usuarioCreador, String deporte, List<UsuarioDTO> participantes) {
    	List<Usuario> particips= new ArrayList<Usuario>();
    	for (UsuarioDTO usu: participantes) {
    		particips.add(UsuarioAssembler.toDomain(usu));
    	}
    	Reto retoNuevo= RetoAssembler.toDomain(reto).actualizarReto(nombre, fecIni, fecFin, objetivoDistancia, objetivoTiempo, UsuarioAssembler.toDomain(usuarioCreador), deporte, particips);
    	retos.put((Integer)reto.getId(),RetoAssembler.toDTO(retoNuevo));
    }

    public void eliminarReto(UsuarioDTO usuario, RetoDTO reto) {
    	RetoAssembler.toDomain(reto).eliminarReto(UsuarioAssembler.toDomain(usuario));
    	if(usuario.getId()==reto.getUsuarioCreador().getId()) {
    		retos.remove(reto.getId());
    	}
    }

    public List<UsuarioDTO> obtenerClasificacion(RetoDTO reto) {
    	List<UsuarioDTO> particips= new ArrayList<UsuarioDTO>();
        for (Usuario usu: RetoAssembler.toDomain(reto).obtenerClasificacion()) {
    		particips.add(UsuarioAssembler.toDTO(usu));
    	}
        return particips;
    }

    public void calcularProgreso(UsuarioDTO usuario) {
        System.out.println("Calculando progreso del usuario: " + usuario.getUsername());
    }
}