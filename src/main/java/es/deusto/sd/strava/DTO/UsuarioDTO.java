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
	private static final long serialVersionUID = 1L;

    // Atributos
	private int id;
    private String username;
    private String email;
    private String contrasena;
    private String nombre;
    private float peso;
    private float altura;
    private java.util.Date fNacimiento;
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
    
    
    

	public UsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public java.util.Date getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(java.util.Date fNacimiento) {
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
	
	public Usuario toDomain() {
	    Usuario usuario = new Usuario();
	    usuario.setId(this.id);
	    usuario.setUsername(this.username);
	    usuario.setEmail(this.email);
	    usuario.setContrasena(this.contrasena);
	    usuario.setNombre(this.nombre);
	    usuario.setPeso(this.peso);
	    usuario.setAltura(this.altura);
	    usuario.setfNacimiento(this.fNacimiento);
	    usuario.setFecCMax(this.fecCMax);
	    usuario.setFecCReposo(this.fecCReposo);
	    usuario.setToken(this.token);

	    if (this.entrenamientos != null) {
	        usuario.setEntrenamientos(new ArrayList<>(this.entrenamientos));
	    }

	    if (this.retos != null) {
	        HashMap<Reto, String> retosMap = new HashMap<>();
	        for (Map.Entry<Reto, String> entry : this.retos.entrySet()) {
	            retosMap.put(entry.getKey(), entry.getValue());
	        }
	        usuario.setRetos(retosMap);
	    }

	    return usuario;
	}
	
	
	
}
