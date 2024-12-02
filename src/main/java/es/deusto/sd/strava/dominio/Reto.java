package es.deusto.sd.strava.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.deusto.sd.strava.DTO.RetoDTO;

public class Reto implements Serializable{
	//atributos
    private int id;
    private String deporte;
    private Usuario usuarioCreador;
    private String nombre;
    private LocalDateTime fecIni;
    private LocalDateTime fecFin;
    private float objetivoDistancia;
    private float objetivoTiempo;
    private List<Usuario> participantes;

    //constructores
    public Reto() {
        //constructor vacío
    }

    public Reto(int id, String deporte, Usuario usuarioCreador, String nombre, LocalDateTime fecIni, LocalDateTime fecFin,
                float objetivoDistancia, float objetivoTiempo, List<Usuario> participantes) {
        this.id = id;
        this.deporte = deporte;
        this.usuarioCreador = usuarioCreador;
        this.nombre = nombre;
        this.fecIni = fecIni;
        this.fecFin = fecFin;
        this.objetivoDistancia = objetivoDistancia;
        this.objetivoTiempo = objetivoTiempo;
        this.participantes = participantes;
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFecIni() {
        return fecIni;
    }

    public void setFecIni(LocalDateTime fecIni) {
        this.fecIni = fecIni;
    }

    public LocalDateTime getFecFin() {
        return fecFin;
    }

    public void setFecFin(LocalDateTime fecFin) {
        this.fecFin = fecFin;
    }

    public float getObjetivoDistancia() {
        return objetivoDistancia;
    }

    public void setObjetivoDistancia(float objetivoDistancia) {
        this.objetivoDistancia = objetivoDistancia;
    }

    public float getObjetivoTiempo() {
        return objetivoTiempo;
    }

    public void setObjetivoTiempo(float objetivoTiempo) {
        this.objetivoTiempo = objetivoTiempo;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    //metodos
    public void crearReto(String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia, float objetivoTiempo,
            String deporte, Usuario usuarioCreador, List<Usuario> participantes) {
    	this.nombre = nombre;
    	this.fecIni = fecIni;
    	this.fecFin = fecFin;
    	this.objetivoDistancia = objetivoDistancia;
    	this.objetivoTiempo = objetivoTiempo;
    	this.deporte = deporte;
    	this.usuarioCreador = usuarioCreador;
    	this.participantes = participantes;
}
    
    
    public Reto actualizarReto(Reto reto, String nombre, LocalDateTime fecIni, LocalDateTime fecFin, float objetivoDistancia,
                               float objetivoTiempo, Usuario usuarioCreador, String deporte, List<Usuario> participantes) {
        System.out.println("Se actualiza el reto");

        reto.setNombre(nombre);
        reto.setFecIni(fecIni);
        reto.setFecFin(fecFin);
        reto.setObjetivoDistancia(objetivoDistancia);
        reto.setObjetivoTiempo(objetivoTiempo);
        reto.setUsuarioCreador(usuarioCreador);
        reto.setDeporte(deporte);
        reto.setParticipantes(participantes);
        return reto;
    }

    public void eliminarReto(Usuario usuario, Reto reto) {
        System.out.println("El usuario elimina el reto y se saca de la lista en caso de que sea"
                + "participante. Si es creador, se elimina todo el reto");
        
        if (usuario.equals(reto.getUsuarioCreador())) {
            //eliminar reto completo
            System.out.println("El creador elimina el reto.");
        } else {
            //eliminar al participante de la lista
            reto.getParticipantes().remove(usuario);
            System.out.println("El usuario se elimina del reto.");
        }
    }

    public void aceptarReto(Usuario usuario, Reto reto) {
        System.out.println("El usuario acepta el reto y se añade a la lista");
        reto.getParticipantes().add(usuario);
    }

    public void calcularProgreso(Usuario usuario) {
        System.out.println("Se calcula el progreso del usuario");
    }

    public List<Usuario> obtenerClasificacion(Reto reto) {
        System.out.println("Se devuelve la clasificación de todos los usuarios del reto");
        return reto.getParticipantes();
    }
}
