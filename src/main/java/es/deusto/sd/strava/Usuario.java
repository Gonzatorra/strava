package es.deusto.sd.strava;

import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
	//Atributos
	int id;
	String token;
	String username;
	String email;
	String nombre;
	String contrasena;
	float peso;
	float altura;
	double fNacimiento;
	float fecCMax;
	float fecCReposo;
	ArrayList<Entrenamiento> entrenamientos;
	HashMap<Reto, String> retos;
	
	
	//Constructores
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Sin valores opcionales
	public Usuario(int id, String token, String username, String email, String nombre,
			double fNacimiento, ArrayList<Entrenamiento> entrenamientos,
			HashMap<Reto, String> retos, String contrasena) {
		super();
		this.id = id;
		this.token = token;
		this.username = username;
		this.email = email;
		this.nombre = nombre;
		this.fNacimiento = fNacimiento;
		this.entrenamientos = entrenamientos;
		this.retos = retos;
		this.contrasena = contrasena;
	}
	
	//Con valores opcionales
	public Usuario(int id, String token, String username, String email, String nombre, float peso, float altura,
			double fNacimiento, float fecCMax, float fecCReposo, ArrayList<Entrenamiento> entrenamientos,
			HashMap<Reto, String> retos, String contrasena) {
		super();
		this.id = id;
		this.token = token;
		this.username = username;
		this.email = email;
		this.nombre = nombre;
		this.peso = peso;
		this.altura = altura;
		this.fNacimiento = fNacimiento;
		this.fecCMax = fecCMax;
		this.fecCReposo = fecCReposo;
		this.entrenamientos = entrenamientos;
		this.retos = retos;
		this.contrasena = contrasena;
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

	public double getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(double fNacimiento) {
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
	void registrar(String username, String contrasena, String email) {
		System.out.println("El usuario se registra");
		
	};
	
	void login(String email, String contrasena) {
		System.out.println("El usuario inicia sesion y se le asigna su token");
		
	};
	
	void logout(String token) {
		System.out.println("El usuario cierra sesion y se elimina su token");
	};
	
	void eliminarUsuario(Usuario usuario) {
		System.out.println("Se elimina el usuario");
		
	};
	
	void actualizarUsuario(Usuario usuario, String contrasena, String nombre, float peso, float altura,
			float fecCMax, float frecCReposo) {
		System.out.println("Se ha actualizado el usuario");
		
	};
	
	ArrayList<Entrenamiento> getEntrenosUsuario(Usuario usuario, double fechaIni, double fechaFin){
		System.out.println("Se devuelven los entrenamientos del usuario");
		return null;
		
	};
	
	ArrayList<Reto> getRetosUsuario(Usuario usuario, String estado){
		System.out.println("Se devuelven los retos del usuario");
		return null;
		
		
	};
	
	
	
	

	
	

	

}
