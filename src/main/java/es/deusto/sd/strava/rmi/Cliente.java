package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.fachada.*;
import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Usuario;

import java.rmi.Naming;
import java.time.LocalDateTime;

public class Cliente {

    public static void main(String[] args) {
        try {
            IRemoteFacade facade = (IRemoteFacade) Naming.lookup("rmi://localhost/RemoteFacade");

            // Usamos la fachada a través de RMI
            UsuarioDTO usuario = facade.registrarUsuario("juan", "1234", "juan@mail.com", "Juan Pérez");
            Usuario user = new Usuario(0, "juan", "juan@mail.com", "1234", "Juan Pérez", null, null, null); //creo esto para hacer el entrenamiento
            
            if (usuario != null) {
                System.out.println("Usuario registrado con éxito: " + usuario.getUsername());
            }
            System.out.println("Esperando...\n");
            facade.login("A", "D");
            Entrenamiento entrenamiento = new Entrenamiento(1, user, "Behobia", "Running", 5.0f, LocalDateTime.now(), 6.30f, 30.0);
            EntrenamientoDTO entrenamientoDTO = new EntrenamientoDTO(entrenamiento);
            facade.crearEntreno(entrenamientoDTO);
            System.out.println("Entrenamiento creado con éxito: " + entrenamientoDTO.getTitulo());

            entrenamientoDTO.setTitulo("San Silvestre");
            facade.actualizarEntreno(entrenamientoDTO);
            System.out.println("Entrenamiento actualizado: " + entrenamientoDTO.getTitulo());

            facade.eliminarEntreno(entrenamientoDTO.getId());
            System.out.println("Entrenamiento eliminado con ID: " + entrenamientoDTO.getId());

            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
