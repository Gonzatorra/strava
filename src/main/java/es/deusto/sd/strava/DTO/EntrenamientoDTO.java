package es.deusto.sd.strava.DTO;

import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EntrenamientoDTO implements Serializable{
    //atributos
    private int id;
    private Usuario usuario;
    private String titulo;
    private String deporte;
    private double distancia;
    private LocalDate fecIni; 
    private float horaIni;
    private double duracion;

    //constructor
   public EntrenamientoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	//getters
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

    public LocalDate getFecIni() {
        return fecIni;
    }

    public float getHoraIni() {
        return horaIni;
    }

    public double getDuracion() {
        return duracion;
    }

    //setters
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

    public void setFecIni(LocalDate fecIni) {
        this.fecIni = fecIni;
    }

    public void setHoraIni(float horaIni) {
    }



	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
    
    

}
