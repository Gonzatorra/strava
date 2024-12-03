package es.deusto.sd.strava.auth;

public interface IAuthServiceGateway {
    boolean autenticar(String token);
    String getProveedor();
}