package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logica.comprador;
import logica.pieza;
import logica.transaccion;
import logica.oferta;
import logica.subasta;
import logica.InformacionPago;
import java.util.UUID;
import java.util.Date;

public class CompradorTest {
    private comprador comprador;
    private pieza pieza;

    @BeforeEach
    public void setUp() {
        comprador = new comprador("testComprador", "password", "email@test.com", "123456789", "Address", "City", "12345", "Country");
        pieza = new pieza("1", 10.0, 10.0, 10.0, 1.0, "Test Piece", true, "ABC123", 2021, "Test Author", "Test Description", null, 100.0, null, false, "Owner", null, "path/to/image");
    }

    @Test
    public void testAgregarHistorialCompra() {
       
        transaccion transaccion = new transaccion(UUID.randomUUID().toString(), 100.0, "Online", new Date(), pieza, comprador);
        comprador.agregarHistorialCompra(transaccion);
        assertTrue(comprador.getHistorialCompras().contains(transaccion));
    }

    @Test
    public void testRealizarOferta() {
        subasta subasta = new subasta("1", pieza, 100.0, 150.0);
        oferta oferta = new oferta(120.0, new Date(), comprador, pieza);
        comprador.realizarOferta(subasta, oferta);
        assertTrue(comprador.getOfertasRealizadas().contains(oferta));
        assertTrue(subasta.getOfertas().contains(oferta));
    }

    
}

