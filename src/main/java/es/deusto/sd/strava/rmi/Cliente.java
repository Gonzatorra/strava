package es.deusto.sd.strava.rmi;

import es.deusto.sd.strava.fachada.*;
import es.deusto.sd.strava.DTO.EntrenamientoDTO;
import es.deusto.sd.strava.DTO.RetoDTO;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;

import java.rmi.Naming;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            
            //Entrenamiento
            Entrenamiento entrenamiento = new Entrenamiento(1, user, "Behobia", "Running", 5.0f, LocalDateTime.now(), 6.30f, 30.0);
            EntrenamientoDTO entrenamientoDTO = new EntrenamientoDTO(entrenamiento);
            facade.crearEntreno(entrenamientoDTO);
            System.out.println("Entrenamiento creado con éxito: " + entrenamientoDTO.getTitulo());

            entrenamientoDTO.setTitulo("San Silvestre");
            facade.actualizarEntreno(entrenamientoDTO);
            System.out.println("Entrenamiento actualizado: " + entrenamientoDTO.getTitulo());

            facade.eliminarEntreno(entrenamientoDTO.getId());
            System.out.println("Entrenamiento eliminado con ID: " + entrenamientoDTO.getId());
            
            //Reto
            List<Usuario> participantes = new ArrayList<>();
            participantes.add(user);
            
            Reto reto = new Reto(1, "Running", user, "Marathon", LocalDateTime.now(), LocalDateTime.now(), 42.1f, 68.7f, participantes);

            RetoDTO retoDTO = new RetoDTO(reto);
            facade.crearReto(retoDTO.getNombre(), retoDTO.getFecIni(), retoDTO.getFecFin(),
                             retoDTO.getObjetivoDistancia(), 240, retoDTO.getDeporte(), retoDTO.getUsuarioCreador(),
                             retoDTO.getParticipantes());
            System.out.println("Reto creado con éxito: " + retoDTO.getNombre());

            retoDTO.setNombre("Ultra Marathon");
            facade.actualizarReto(retoDTO.toDomain(), retoDTO.getNombre(), retoDTO.getFecIni(),
                                  retoDTO.getFecFin(), retoDTO.getObjetivoDistancia(), 300, retoDTO.getUsuarioCreador(),
                                  retoDTO.getDeporte(), retoDTO.getParticipantes());
            System.out.println("Reto actualizado con éxito: " + retoDTO.getNombre());

            facade.aceptarReto(user, retoDTO.toDomain());
            System.out.println("Usuario " + user.getUsername() + " ha aceptado el reto.");

            facade.eliminarReto(user, retoDTO.toDomain());
            System.out.println("Reto eliminado con éxito: " + retoDTO.getNombre());
            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
