package es.deusto.sd.strava.DTO;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

public class UsuarioDTO implements Serializable{
    // Atributos
	private int id;
    private String username;
    private String email;
    private String contrasena;
    private String nombre;
    private float peso;
    private float altura;
    private LocalDate fNacimiento;
    private float fecCMax;
    private float fecCReposo;
    private String token;
    private ArrayList<Entrenamiento> entrenamientos;
    private HashMap<Reto, String> retos;

    
    // Constructores
        public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        this.contrasena = usuario.getContrasena();
        this.nombre = usuario.getNombre();
        this.peso = usuario.getPeso();
        this.altura = usuario.getAltura();
        this.fNacimiento = usuario.getfNacimiento();
        this.fecCMax = usuario.getFecCMax();
        this.fecCReposo = usuario.getFecCReposo();
        this.token = usuario.getToken();
        this.entrenamientos = usuario.getEntrenamientos();
        this.retos = usuario.getRetos();
    }

	
	//Getters - Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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
		return fecCMax;
	}

	public void setFecCMax(float fecCMax) {
		this.fecCMax = fecCMax;
	}

	public float getFecCReposo() {
		return fecCReposo;
	}

	public void setFecCReposo(float fecCReposo) {
		this.fecCReposo = fecCReposo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
	
	
	
}
