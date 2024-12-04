package es.deusto.sd.strava.auth;

public class StravaAuthServiceGateway implements IAuthServiceGateway {
   
	@Override
    public boolean autenticar(String username, String password, String token) {
        return token != null && token.startsWith("strava_") && username != null && password != null;
    }

    @Override
    public String getProveedor() {
        return "Strava";
    }

    @Override
    public String generarToken() {
        return "strava_" + System.currentTimeMillis();
    }
}