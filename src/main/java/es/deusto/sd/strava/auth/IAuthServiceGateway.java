package es.deusto.sd.strava.auth;

public interface IAuthServiceGateway {
    boolean autenticar(String username, String password, String token);
    String getProveedor();
    String generarToken();
}
