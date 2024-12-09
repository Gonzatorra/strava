package es.deusto.sd.strava.DTO;

import es.deusto.sd.strava.dominio.Usuario;
import java.io.Serializable;
import java.time.LocalDate;

public class EntrenamientoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos
    private static int idCounter = 1;  // Contador estático para incrementar el ID automáticamente
    private int id;
    private Usuario usuario;
    private String titulo;
    private String deporte;
    private double distancia;
    private LocalDate fecIni; 
    private float horaIni;
    private double duracion;

    
    public EntrenamientoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Constructor
    public EntrenamientoDTO(Usuario usuario, String titulo, String deporte, double distancia, LocalDate fecIni,
                            float horaIni, double duracion) {
        this.id = idCounter++;  // Asigna y aumenta el ID automáticamente
        this.usuario = usuario;
        this.titulo = titulo;
        this.deporte = deporte;
        this.distancia = distancia;
        this.fecIni = fecIni;
        this.horaIni = horaIni;
        this.duracion = duracion;
    }

    // Getters y Setters
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

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public LocalDate getFecIni() {
        return fecIni;
    }

    public void setFecIni(LocalDate fecIni) {
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
}
