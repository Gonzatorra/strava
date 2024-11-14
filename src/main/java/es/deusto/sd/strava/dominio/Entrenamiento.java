package es.deusto.sd.strava.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Entrenamiento implements Serializable{
    // Atributos
    private int id;
    private Usuario usuario;
    private String titulo;
    private String deporte;
    private float distancia;
    private LocalDateTime fecIni;
    private float horaIni;
    private double duracion;

    // Constructores
    public Entrenamiento(int id, Usuario usuario, String titulo, String deporte, float distancia, 
                          LocalDateTime fecIni, float horaIni, double duracion) {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.deporte = deporte;
        this.distancia = distancia;
        this.fecIni = fecIni;
        this.horaIni = horaIni;
        this.duracion = duracion;
    }
    
    

    // Getters - Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public LocalDateTime getFecIni() {
        return fecIni;
    }

    public void setFecIni(LocalDateTime fecIni) {
        this.fecIni = fecIni;
    }

    public float getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(float horaIni) {
        this.horaIni = horaIni;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    // Métodos
    public void actualizarEntreno(Entrenamiento entreno, double distancia, double fechaIni,
                                  float horaInicio, double duracion) {
        System.out.println("Se actualiza el entrenamiento");
        entreno.setDistancia((float) distancia);
        entreno.setFecIni(LocalDateTime.ofEpochSecond((long) fechaIni, 0, java.time.ZoneOffset.UTC));
        entreno.setHoraIni(horaInicio);
        entreno.setDuracion(duracion);
    }

    public void eliminarEntreno(Entrenamiento entreno) {
        System.out.println("Se elimina el entrenamiento");
        // Si estuviera en una lista o colección, eliminaría el objeto aquí.
    }
}
