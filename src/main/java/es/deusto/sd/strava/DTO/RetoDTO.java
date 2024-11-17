package es.deusto.sd.strava.DTO;

import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RetoDTO implements Serializable{
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

    //constructor
    public RetoDTO(Reto reto) {
        this.id = reto.getId();
        this.deporte = reto.getDeporte();
        this.usuarioCreador = reto.getUsuarioCreador();
        this.nombre = reto.getNombre();
        this.fecIni = reto.getFecIni();
        this.fecFin = reto.getFecFin();
        this.objetivoDistancia = reto.getObjetivoDistancia();
        this.objetivoTiempo = reto.getObjetivoTiempo();
        this.participantes = reto.getParticipantes();
    }

    //getters
    public int getId() {
        return id;
    }

    public String getDeporte() {
        return deporte;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDateTime getFecIni() {
        return fecIni;
    }

    public LocalDateTime getFecFin() {
        return fecFin;
    }

    public float getObjetivoDistancia() {
        return objetivoDistancia;
    }

    public float getObjetivoTiempo() {
        return objetivoTiempo;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecIni(LocalDateTime fecIni) {
        this.fecIni = fecIni;
    }

    public void setFecFin(LocalDateTime fecFin) {
        this.fecFin = fecFin;
    }

    public void setObjetivoDistancia(float objetivoDistancia) {
        this.objetivoDistancia = objetivoDistancia;
    }

    public void setObjetivoTiempo(float objetivoTiempo) {
        this.objetivoTiempo = objetivoTiempo;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }
    
    public Reto toDomain() {
    	Reto reto = new Reto(id, deporte, usuarioCreador, nombre, fecIni, fecFin, (float) objetivoDistancia, 0.0f, participantes);
    	return reto;
    }

}
