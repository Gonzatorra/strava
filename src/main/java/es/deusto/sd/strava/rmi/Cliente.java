package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.fachada.*;
import es.deusto.sd.strava.DTO.UsuarioDTO;

import java.rmi.Naming;

public class Cliente {

    public static void main(String[] args) {
        try {
            IRemoteFacade facade = (IRemoteFacade) Naming.lookup("rmi://localhost/RemoteFacade");

            // Usamos la fachada a través de RMI
            UsuarioDTO usuario = facade.registrarUsuario("juan", "1234", "juan@mail.com", "Juan Pérez");
            if (usuario != null) {
                System.out.println("Usuario registrado con éxito: " + usuario.getUsername());
            }

            // Otros métodos pueden ser llamados de igual forma...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
