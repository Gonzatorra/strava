package es.deusto.sd.strava.MAuth;

public class AuthClient {
    public static void main(String[] args) {
        try {
            //Crear una instancia del Gateway
            AuthGateway authGateway = new AuthGateway("localhost", 1099);

            //Registrar usuario
            System.out.println(authGateway.registerUser("newuser", "newpass", "newuser@example.com"));

            //LogIn y obtener token
            String token = authGateway.login("newuser", "newpass");
            System.out.println("Token: " + token);

            //Validar token
            boolean isValid = authGateway.validateToken(token);
            System.out.println("Token valid: " + isValid);

            //Obtener información del usuario
            String userInfo = authGateway.getUserInfo(token);
            System.out.println("User Info: " + userInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
