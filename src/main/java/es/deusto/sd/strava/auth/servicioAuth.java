package es.deusto.sd.strava.auth;

public abstract class servicioAuth {
	
	public boolean validarToken(String token) {
		return true;
	}

	public String obtenerInfoUsuario(String token) {
		return "ana";
		
	}
}
