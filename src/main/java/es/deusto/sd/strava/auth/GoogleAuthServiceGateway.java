package es.deusto.sd.strava.auth;

public class GoogleAuthServiceGateway implements IAuthServiceGateway {

    @Override
    public boolean autenticar(String username, String password, String token) {
        return token != null && token.startsWith("google_") && !username.equals("")  && !password.equals("");
    }

    @Override
    public String getProveedor() {
        return "Google";
    }

    @Override
    public String generarToken() {
        return "google_" + System.currentTimeMillis();
    }
}