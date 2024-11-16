package es.deusto.sd.strava.DTO;

import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Usuario;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EntrenamientoDTO implements Serializable{
    // Atributos
    private int id;
    private Usuario usuario;
    private String titulo;
    private String deporte;
    private double distancia;
    private LocalDateTime fecIni; 
    private float horaIni;
    private double duracion;

    // Constructor
    public EntrenamientoDTO(Entrenamiento entenamiento) {
        this.id = entenamiento.getId();
        this.usuario = entenamiento.getUsuario();
        this.titulo = entenamiento.getTitulo();
        this.deporte = entenamiento.getDeporte();
        this.distancia = entenamiento.getDistancia();
        this.fecIni = entenamiento.getFecIni();
        this.horaIni = entenamiento.getHoraIni();
        this.duracion = entenamiento.getDuracion();
        
    }

    // Getters
    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDeporte() {
        return deporte;
    }

    public double getDistancia() {
        return distancia;
    }

    public LocalDateTime getFecIni() {
        return fecIni;
    }

    public float getHoraIni() {
        return horaIni;
    }

    public double getDuracion() {
        return duracion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setFecIni(LocalDateTime fecIni) {
        this.fecIni = fecIni;
    }

    public void setHoraIni(float horaIni) {
    }
    
    public Entrenamiento toDomain() {
        return new Entrenamiento(id, usuario, titulo, deporte, distancia, fecIni, horaIni, duracion);
    }


}
