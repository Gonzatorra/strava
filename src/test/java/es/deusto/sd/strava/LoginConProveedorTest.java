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
        // Configurar un usuario con token y proveedor Google
        String tokenGoogle = "tokenGoogleTest";
        String proveedorGoogle = "Google";

        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1);
        usuarioEsperado.setUsername("usuarioGoogle");
        usuarioEsperado.setEmail("usuario@google.com");
        usuarioEsperado.setToken(tokenGoogle);
        usuarioEsperado.setProveedor(proveedorGoogle);

        // Crear el DTO esperado
        UsuarioDTO usuarioDTOEsperado = UsuarioAssembler.toDTO(usuarioEsperado);

        // Simular el comportamiento de ServicioAutentificacion (mock)
        Mockito.when(servicioAutentificacion.autenticar(tokenGoogle, proveedorGoogle)).thenReturn(true);

        // Simular el login en la fachada
        Mockito.when(remoteFacade.loginConProveedor(tokenGoogle, proveedorGoogle)).thenReturn(usuarioDTOEsperado);

        // Ejecutar la autenticación
        UsuarioDTO usuarioDTO = remoteFacade.loginConProveedor(tokenGoogle, proveedorGoogle);

        // Comprobar que el login fue exitoso y los datos coinciden
        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTOEsperado.getId(), usuarioDTO.getId());
        assertEquals(usuarioDTOEsperado.getUsername(), usuarioDTO.getUsername());
        assertEquals(usuarioDTOEsperado.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioDTOEsperado.getToken(), usuarioDTO.getToken());
        assertEquals(usuarioDTOEsperado.getProveedor(), usuarioDTO.getProveedor());
    }

    @Test
    public void testLoginMetaProveedor() throws Exception {
        // Configurar un usuario con token y proveedor Meta
        String tokenMeta = "tokenMetaTest";
        String proveedorMeta = "Meta";

        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(2);
        usuarioEsperado.setUsername("usuarioMeta");
        usuarioEsperado.setEmail("usuario@meta.com");
        usuarioEsperado.setToken(tokenMeta);
        usuarioEsperado.setProveedor(proveedorMeta);

        // Crear el DTO esperado
        UsuarioDTO usuarioDTOEsperado = UsuarioAssembler.toDTO(usuarioEsperado);

        // Simular el comportamiento de ServicioAutentificacion (mock)
        Mockito.when(servicioAutentificacion.autenticar(tokenMeta, proveedorMeta)).thenReturn(true);

        // Simular el login en la fachada
        Mockito.when(remoteFacade.loginConProveedor(tokenMeta, proveedorMeta)).thenReturn(usuarioDTOEsperado);

        // Ejecutar la autenticación
        UsuarioDTO usuarioDTO = remoteFacade.loginConProveedor(tokenMeta, proveedorMeta);

        // Comprobar que el login fue exitoso y los datos coinciden
        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTOEsperado.getId(), usuarioDTO.getId());
        assertEquals(usuarioDTOEsperado.getUsername(), usuarioDTO.getUsername());
        assertEquals(usuarioDTOEsperado.getEmail(), usuarioDTO.getEmail());
        assertEquals(usuarioDTOEsperado.getToken(), usuarioDTO.getToken());
        assertEquals(usuarioDTOEsperado.getProveedor(), usuarioDTO.getProveedor());
    }
}
