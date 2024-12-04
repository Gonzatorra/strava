package es.deusto.sd.strava;

import es.deusto.sd.strava.fachada.IRemoteFacade;
import es.deusto.sd.strava.servicios.ServicioAutentificacion;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.dominio.Usuario;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

public class LoginConProveedorTest {

    @Mock
    ServicioAutentificacion servicioAutentificacion;

    @Mock
    IRemoteFacade remoteFacade;

    @Test
    public void testLoginGoogleProveedor() throws Exception {
        // Mock input for Google login
        String username = "usuarioGoogle";
        String password = "passwordGoogle";
        String proveedorGoogle = "Google";

        // Expected user
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1);
        usuarioEsperado.setUsername(username);
        usuarioEsperado.setEmail("usuario@google.com");
        usuarioEsperado.setProveedor(proveedorGoogle);

        // Expected DTO
        UsuarioDTO usuarioDTOEsperado = UsuarioAssembler.toDTO(usuarioEsperado);

        // Mock behavior for ServicioAutentificacion
        Mockito.when(servicioAutentificacion.autenticar(username, password, proveedorGoogle)).thenReturn(true);

        // Mock behavior for RemoteFacade
        Mockito.when(remoteFacade.loginConProveedor(username, password, proveedorGoogle)).thenReturn(usuarioDTOEsperado);

        // Execute login
        UsuarioDTO usuarioDTO = remoteFacade.loginConProveedor(username, password, proveedorGoogle);

        // Validate the results
        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTOEsperado.getId(), usuarioDTO.getId());
        assertEquals(usuarioDTOEsperado.getUsername(), usuarioDTO.getUsername());
        assertEquals(usuarioDTOEsperado.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioDTOEsperado.getProveedor(), usuarioDTO.getProveedor());
    }

    @Test
    public void testLoginMetaProveedor() throws Exception {
        // Mock input for Meta login
        String username = "usuarioMeta";
        String password = "passwordMeta";
        String proveedorMeta = "Meta";

        // Expected user
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(2);
        usuarioEsperado.setUsername(username);
        usuarioEsperado.setEmail("usuario@meta.com");
        usuarioEsperado.setProveedor(proveedorMeta);

        // Expected DTO
        UsuarioDTO usuarioDTOEsperado = UsuarioAssembler.toDTO(usuarioEsperado);

        // Mock behavior for ServicioAutentificacion
        Mockito.when(servicioAutentificacion.autenticar(username, password, proveedorMeta)).thenReturn(true);

        // Mock behavior for RemoteFacade
        Mockito.when(remoteFacade.loginConProveedor(username, password, proveedorMeta)).thenReturn(usuarioDTOEsperado);

        // Execute login
        UsuarioDTO usuarioDTO = remoteFacade.loginConProveedor(username, password, proveedorMeta);

        // Validate the results
        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTOEsperado.getId(), usuarioDTO.getId());
        assertEquals(usuarioDTOEsperado.getUsername(), usuarioDTO.getUsername());
        assertEquals(usuarioDTOEsperado.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioDTOEsperado.getProveedor(), usuarioDTO.getProveedor());
    }
}

