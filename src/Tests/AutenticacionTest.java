package Tests;

import org.junit.*;
import static org.junit.Assert.*;
import logica.galeria;
import logica.usuario;

public class AutenticacionTest {
    private galeria galeria;

    @Before
    public void setUp() {
        galeria = new galeria();
        // Añadir el usuario manualmente para las pruebas
        galeria.agregarUsuario(new usuario("admin", "adminpass", "admin@example.com", "123456789", "Admin Street", "Admin City", "00000", "Country", false, false, true));
    }

    @Test
    public void testVerificacionCredencialesExitosa() {
        usuario usuarioValido = galeria.verificarCredenciales("admin", "adminpass");
        assertNotNull("Debería devolver un objeto usuario cuando las credenciales son correctas", usuarioValido);
    }

    @Test
    public void testVerificacionCredencialesFallo() {
        usuario usuarioInvalido = galeria.verificarCredenciales("fakeuser", "fakepassword");
        assertNull("No debería devolver un objeto usuario cuando las credenciales son incorrectas", usuarioInvalido);
    }
}

