package es.deusto.sd.strava.servicios;

import java.util.HashMap;

import es.deusto.sd.strava.auth.AuthServiceFactory;
import es.deusto.sd.strava.auth.IAuthServiceGateway;

public class ServicioAutentificacion {
    private static ServicioAutentificacion instancia;
    public ServicioAutentificacion() {
        System.out.println("ServicioAutentificacion inicializado.");
    }

    public static ServicioAutentificacion getInstancia() {
        if (instancia == null) {
            instancia = new ServicioAutentificacion();
        }
        return instancia;
    }

    public String autenticar(String username, String password, String proveedor, String plataforma) {
        try {
        	if (proveedor.equals(plataforma)) {
	            IAuthServiceGateway gateway = AuthServiceFactory.getAuthService(proveedor);
	
	            String token = gateway.generarToken();
	            if(gateway.autenticar(username, password, token)) {
	            	return token;
	            }
        	}
        	return null;
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return null;
        }
    }
}
