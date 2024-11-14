package es.deusto.sd.strava;

public class Entrenamiento {
	//Atributos
	int id;
	Usuario usuario;
	String titulo;
	String deporte;
	float distancia;
	double fecIni;
	float horaIni;
	double duracion;
	
	
	//Constructores
	public Entrenamiento() {
		// TODO Auto-generated constructor stub
	}


	public Entrenamiento(int id, Usuario usuario, String titulo, String deporte, float distancia, double fecIni,
			float horaIni, double duracion) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.titulo = titulo;
		this.deporte = deporte;
		this.distancia = distancia;
		this.fecIni = fecIni;
		this.horaIni = horaIni;
		this.duracion = duracion;
	}


	//Getters - Setters
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


	public double getFecIni() {
		return fecIni;
	}


	public void setFecIni(double fecIni) {
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
	
	
	//Metodos
	void actualizarEntreno(Entrenamiento entreno, double distancia, double fechaIni,
			float horaInicio, double duracion) {
		System.out.println("Se actualiza el entrenamiento");
		
	};
	
	void eliminarEntreno(Entrenamiento entreno) {
		System.out.println("Se elimina el entrenamiento");
		
	};
	

}
