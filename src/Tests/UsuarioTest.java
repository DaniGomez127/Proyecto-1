package Tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import logica.pieza;
import logica.transaccion;
import logica.usuario;

import java.util.Date;

public class UsuarioTest {
    private usuario usuarioComprador;
    private pieza piezaComprada;
    private transaccion transaccionCompra;

    @Before
    public void setUp() {
        usuarioComprador = new usuario("comprador", "password", "comprador@example.com", "1234567890", "Calle Compra 123", "Ciudad", "00000", "País", false, true, false);
        piezaComprada = new pieza("id1", 2.0, 1.5, 0.5, 20.0, "Pieza Prueba", true, "COD123", 2021, "Artista", "Descripción de prueba", null, 1500.0, "2024-05-20", false, "Propietario Original", "2024-12-31", null);
        transaccionCompra = new transaccion("trans1", new Date(), "Galería", 1500.0, usuarioComprador, piezaComprada);
    }

    @Test
    public void testAgregarPiezaComprada() {
        usuarioComprador.agregarPiezaComprada(piezaComprada);
        assertFalse("La lista de piezas compradas no debe estar vacía", usuarioComprador.getPiezasCompradas().isEmpty());
        assertTrue("La pieza comprada debe estar en la lista", usuarioComprador.getPiezasCompradas().contains(piezaComprada));
    }

    @Test
    public void testAgregarTransaccion() {
        usuarioComprador.agregarTransaccion(transaccionCompra);
        assertFalse("El historial de compras no debe estar vacío", usuarioComprador.getHistorialCompras().isEmpty());
        assertTrue("La transacción debe estar en el historial de compras", usuarioComprador.getHistorialCompras().contains(transaccionCompra));
    }

    @Test
    public void testCalcularValorColeccion() {
        usuarioComprador.agregarPiezaComprada(piezaComprada);
        double expectedTotal = 1500.0;  // Precio de la pieza comprada
        assertEquals("El valor total de la colección debe coincidir con el precio de la pieza", expectedTotal, usuarioComprador.calcularValorColeccion(), 0.0);
    }

    @Test
    public void testRolesUsuario() {
        assertFalse("El usuario no debe ser cajero", usuarioComprador.esCajero());
        assertTrue("El usuario debe ser comprador", usuarioComprador.esComprador());
        assertFalse("El usuario no debe ser administrador", usuarioComprador.esAdministrador());
    }
    
    
}
