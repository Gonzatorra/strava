package es.deusto.sd.strava.servicios;

import es.deusto.sd.strava.auth.AuthServiceFactory;
import es.deusto.sd.strava.auth.IAuthServiceGateway;

public class ServicioAutentificacion {
    private static ServicioAutentificacion instancia;

    private ServicioAutentificacion() {}

    public static ServicioAutentificacion getInstancia() {
        if (instancia == null) {
            instancia = new ServicioAutentificacion();
        }
        return instancia;
    }

    public boolean autenticar(String username, String password, String proveedor) {
        try {
            IAuthServiceGateway gateway = AuthServiceFactory.getAuthService(proveedor);
            
            String token = gateway.generarToken();
            
            boolean autenticado = gateway.autenticar(username, password, token);
            
            GestionEstadoSingleton.getInstancia().setUsuarioAutenticado(autenticado);
            
            return autenticado;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
