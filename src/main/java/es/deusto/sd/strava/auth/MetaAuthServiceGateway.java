package es.deusto.sd.strava.auth;

public class MetaAuthServiceGateway implements IAuthServiceGateway {
    
	@Override
    public boolean autenticar(String username, String password, String token) {
        return token != null && token.startsWith("meta_") && username != null && password != null;
    }

    @Override
    public String getProveedor() {
        return "Meta";
    }

    @Override
    public String generarToken() {
        return "meta_" + System.currentTimeMillis();
    }
}