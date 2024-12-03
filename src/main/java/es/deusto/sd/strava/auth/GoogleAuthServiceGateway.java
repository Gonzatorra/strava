package es.deusto.sd.strava.auth;

public class GoogleAuthServiceGateway implements IAuthServiceGateway {
    @Override
    public boolean autenticar(String token) {
        // Simulación: verifica que el token contenga "google_"
        return token != null && token.startsWith("google_");
    }

    @Override
    public String getProveedor() {
        return "Google";
    }
}