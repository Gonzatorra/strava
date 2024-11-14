package es.deusto.sd.strava.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;


public class Usuario implements Serializable{
	private int id;
    private String username;
    private String email;
    private String contrasena;
    private String nombre;
    private float peso;
    private float altura;
    private LocalDate fNacimiento;
    private float frecCMax;
    private float frecCReposo;
    private String token;
    private ArrayList<Entrenamiento> entrenamientos;
    private HashMap<Reto, String> retos;
    
	
	
	//Constructores
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Sin valores opcionales
	public Usuario(int id, String username, String email, String contrasena,
			String nombre, String token, ArrayList<Entrenamiento> entrenamientos,
			HashMap<Reto, String> retos) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.token = token;
		this.entrenamientos = entrenamientos;
		this.retos = retos;
		
	}
	
	//Getters - Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public LocalDate getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(LocalDate fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public float getFecCMax() {
		return frecCMax;
	}

	public void setFecCMax(float fecCMax) {
		this.frecCMax = fecCMax;
	}

	public float getFecCReposo() {
		return frecCReposo;
	}

	public void setFecCReposo(float fecCReposo) {
		this.frecCReposo = fecCReposo;
	}

	public ArrayList<Entrenamiento> getEntrenamientos() {
		return entrenamientos;
	}

	public void setEntrenamientos(ArrayList<Entrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}

	public HashMap<Reto, String> getRetos() {
		return retos;
	}

	public void setRetos(HashMap<Reto, String> retos) {
		this.retos = retos;
	}
	
		
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	
	//Metodos
	// Otros métodos relacionados con el objeto Usuario, pero sin lógica de negocio
    public void agregarEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamientos.add(entrenamiento);
    }

    public void agregarReto(Reto reto, String estado) {
        this.retos.put(reto, estado);
    }

    // Puedes agregar métodos de validación de datos si es necesario	
	

	

}
