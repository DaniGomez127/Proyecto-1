package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logica.comprador;
import logica.pieza;
import logica.oferta;
import logica.subasta;
import java.util.Date;

public class SubastaTest {
    private subasta subasta;
    private pieza pieza;
    private comprador comprador;

    @BeforeEach
    public void setUp() {
        comprador = new comprador("testComprador", "password", "email@test.com", "123456789", "Address", "City", "12345", "Country");
        pieza = new pieza("1", 10.0, 10.0, 10.0, 1.0, "Test Piece", true, "ABC123", 2021, "Test Author", "Test Description", null, 100.0, null, false, "Owner", null, "path/to/image");
        subasta = new subasta("1", pieza, 100.0, 150.0);
    }

    @Test
    public void testRegistrarOferta() {
        oferta oferta = new oferta(120.0, new Date(), comprador, pieza);
        subasta.registrarOferta(oferta);
        assertTrue(subasta.getOfertas().contains(oferta));
    }

    @Test
    public void testActivarSubasta() {
        subasta.setEstaActiva(true);
        assertTrue(subasta.isEstaActiva());
    }

    @Test
    public void testObtenerOfertas() {
        oferta oferta1 = new oferta(120.0, new Date(), comprador, pieza);
        oferta oferta2 = new oferta(130.0, new Date(), comprador, pieza);
        subasta.registrarOferta(oferta1);
        subasta.registrarOferta(oferta2);
        assertEquals(2, subasta.getOfertas().size());
    }
}
