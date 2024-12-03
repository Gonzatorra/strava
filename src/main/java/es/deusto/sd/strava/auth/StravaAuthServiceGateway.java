package es.deusto.sd.strava.auth;

public class StravaAuthServiceGateway implements IAuthServiceGateway {
    @Override
    public boolean autenticar(String token) {
        // Simulación: verifica que el token contenga "strava_"
        return token != null && token.startsWith("strava_");
    }

    @Override
    public String getProveedor() {
        return "Strava";
    }
}