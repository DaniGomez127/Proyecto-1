package Tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import logica.usuario;
import logica.galeria;
import logica.pieza;
import logica.subasta;
import logica.cajero;
import logica.comprador;
import logica.oferta;
import logica.operador;
import logica.pago;
import logica.transaccion;

public class TransaccionTest {
    private galeria galeria;
    private pieza pieza;
    private comprador comprador;
    private oferta oferta;
    private transaccion transaccion;

    @Before
    public void setUp() throws Exception {
        galeria = new galeria();
        pieza = new pieza("id1", 1.0, 1.0, 1.0, 10.0, "Test Pieza", true, "COD123", 2020, "Test Artist", "Descripción de prueba", null, 1000.0, "", false, "Owner Test", "2023-12-31", null);
   
        comprador = new comprador("comprador", "securePass", "comprador@example.com", "1234567890", "123 Test Street", "Test City", "00000", "Test Country");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse("2024-01-01");
        oferta = new oferta(500.0, fecha, comprador, pieza);
        transaccion = new transaccion("trans001", fecha, "Test Place", 500.0, comprador, pieza);
    }

    @Test
    public void testConfirmarPago() {
        pago pago = new pago("pago001", 500.0, "Tarjeta", "Detalles del pago", false);
        cajero cajero = new cajero("Cajero Test", "1234567890", null, null, null, null, null, null);
        assertTrue("El pago debe ser confirmado por el cajero", cajero.confirmarPago(pago));
        assertTrue("El pago debe estar confirmado", pago.isPagoConfirmado());
    }

    @Test
    public void testRegistrarOfertaEnSubasta() {
        subasta subasta = new subasta("sub001", pieza, 0, 0);
        operador operador = new operador("Operador Test");
        operador.registrarOfertaEnSubasta(oferta, subasta);
        assertFalse("La subasta debe contener la oferta registrada", subasta.getOfertas().isEmpty());
        assertEquals("La oferta debe ser la misma que la registrada", oferta, subasta.getOfertas().get(0));
    }

    @Test
    public void testRealizarTransaccion() {
        transaccion.realizarOferta(oferta);
        assertFalse("La transacción debe tener ofertas asociadas", transaccion.getOfertas().isEmpty());
        assertEquals("La oferta en la transacción debe ser la registrada", oferta, transaccion.getOfertas().get(0));
    }

    @Test
  
    public void testAgregarPiezaEnConsignacionComoAdmin() {
        usuario admin = new usuario("admin", "adminPass", "admin@example.com", "1234567890", "Admin Street", "Admin City", "00000", "Country", false, false, true);
        pieza testPieza = new pieza("pieza1", 2.0, 1.0, 1.0, 10.0, "Pieza Test", true, "COD123", 2021, "Test Artist", "Descripción test", null, 0.0, "", true, "Original Owner", "2023-12-31", null);
        assertTrue("El administrador debe poder agregar piezas en consignación", galeria.agregarPiezaEnConsignacion(testPieza, admin));
    }

}

