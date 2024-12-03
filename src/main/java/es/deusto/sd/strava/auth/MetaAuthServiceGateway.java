package es.deusto.sd.strava.auth;

public class MetaAuthServiceGateway implements IAuthServiceGateway {
    @Override
    public boolean autenticar(String token) {
        // Simulación: verifica que el token contenga "meta_"
        return token != null && token.startsWith("meta_");
    }

    @Override
    public String getProveedor() {
        return "Meta";
    }
}