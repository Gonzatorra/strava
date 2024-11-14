package es.deusto.sd.strava;

import java.util.ArrayList;

public class Reto {
	//Atributos
	int id;
	String deporte;
	Usuario usuarioCreador;
	String nombre;
	double fecIni;
	double fecFin;
	float objetivoDistancia;
	double objetivoTiempo;
	ArrayList<Usuario> participantes;

	//Constructores
	public Reto() {
		// TODO Auto-generated constructor stub
	}
	
	public Reto(int id, String deporte, Usuario usuarioCreador, String nombre, double fecIni, double fecFin,
			float objetivoDistancia, double objetivoTiempo, ArrayList<Usuario> participantes) {
		super();
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

	//Getters - Setters
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

	public double getFecIni() {
		return fecIni;
	}

	public void setFecIni(double fecIni) {
		this.fecIni = fecIni;
	}

	public double getFecFin() {
		return fecFin;
	}

	public void setFecFin(double fecFin) {
		this.fecFin = fecFin;
	}

	public float getObjetivoDistancia() {
		return objetivoDistancia;
	}

	public void setObjetivoDistancia(float objetivoDistancia) {
		this.objetivoDistancia = objetivoDistancia;
	}

	public double getObjetivoTiempo() {
		return objetivoTiempo;
	}

	public void setObjetivoTiempo(double objetivoTiempo) {
		this.objetivoTiempo = objetivoTiempo;
	}

	public ArrayList<Usuario> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(ArrayList<Usuario> participantes) {
		this.participantes = participantes;
	}
	
	
	//Metodos
	void actualizarReto(Reto reto, String nombre, double fecIni, double fecFin, float objetivoDistancia,
	double objetivoTiempo, Usuario usuarioCreador, String deporte, ArrayList<Usuario> participantes){
		System.out.println("Se actualiza el reto");
		
	};
	
	void eliminarReto(Usuario usuario, Reto reto) {
		System.out.println("El usuario elimina el reto y se saca de la lista en caso de que sea"
				+ "participante. Si es creador, se elimina todo el reto");
	};
	
	void aceptarReto(Usuario usuario, Reto reto) {
		System.out.println("El usuario acepta el reto y se añade a la lista");
		
	};
	
	void calcularProgreso(Usuario usuario) {
		System.out.println("Se calcula el proceso del usuario");
		
	};
	
	ArrayList<Usuario> obtenerClasificacion(Reto reto){
		System.out.println("Se devuelve la clasificacion de todos los usuarios del reto");
		return null;
		
	};
	
	
	

}
