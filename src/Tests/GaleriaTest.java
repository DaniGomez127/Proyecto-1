package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import logica.usuario;
import logica.galeria;
import logica.pieza;


public class GaleriaTest {
    private galeria galeriaTest;
    private usuario adminUser;
    private usuario nonAdminUser;
    private pieza testPieza;

    @Before
    public void setUp() {
        galeriaTest = new galeria();
        adminUser = new usuario("admin", "password", "admin@example.com", "123456789", "1234 Street", "City", "00000", "Country", false, false, true);
        nonAdminUser = new usuario("user", "password", "user@example.com", "123456789", "1234 Avenue", "City", "00000", "Country", false, true, false);
        testPieza = new pieza("id1", 2.0, 1.5, 0.5, 20.0, "Pieza Test", true, "COD123", 2021, "Test Artist", "Descripción test", null, 0.0, "", true, "Original Owner", "2023-12-31", null);
    }

    @Test
    public void testAgregarPiezaEnConsignacionComoAdmin() {
        assertTrue("Admin debería poder agregar una pieza en consignación", galeriaTest.agregarPiezaEnConsignacion(testPieza, adminUser));
    }

    @Test
    public void testAgregarPiezaEnConsignacionComoNoAdmin() {
        assertFalse("No admin no debería poder agregar una pieza en consignación", galeriaTest.agregarPiezaEnConsignacion(testPieza, nonAdminUser));
    }

    @Test
    public void testRegistrarVentaPiezaComoAdmin() {
        galeriaTest.agregarPiezaEnConsignacion(testPieza, adminUser); // Asegurarse que la pieza está en el inventario
        assertTrue("Admin debería poder registrar la venta de una pieza", galeriaTest.registrarVentaPieza("id1", 1500.0, "2024-05-15", adminUser));
    }

    @Test
    public void testRegistrarVentaPiezaComoNoAdmin() {
        galeriaTest.agregarPiezaEnConsignacion(testPieza, adminUser); // Asegurarse que la pieza está en el inventario
        assertFalse("No admin no debería poder registrar la venta de una pieza", galeriaTest.registrarVentaPieza("id1", 1500.0, "2024-05-15", nonAdminUser));
    }

    @Test
    public void testDevolverPiezaComoAdmin() {
        galeriaTest.agregarPiezaEnConsignacion(testPieza, adminUser);
        assertTrue("Admin debería poder devolver una pieza", galeriaTest.devolverPieza("id1", adminUser));
    }

    @Test
    public void testDevolverPiezaComoNoAdmin() {
        galeriaTest.agregarPiezaEnConsignacion(testPieza, adminUser);
        assertFalse("No admin no debería poder devolver una pieza", galeriaTest.devolverPieza("id1", nonAdminUser));
    }
}

