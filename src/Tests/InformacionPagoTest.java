package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logica.oferta;
import logica.subasta;
import logica.comprador;
import java.util.Date;
import logica.pieza;
import logica.InformacionPago;

public class InformacionPagoTest {
    private InformacionPago informacionPago;
    private comprador comprador;
    private pieza pieza;

    @BeforeEach
    public void setUp() {
        comprador = new comprador("testComprador", "password", "email@test.com", "123456789", "Address", "City", "12345", "Country");
        pieza = new pieza("1", 10.0, 10.0, 10.0, 1.0, "Test Piece", true, "ABC123", 2021, "Test Author", "Test Description", null, 100.0, null, false, "Owner", null, "path/to/image");
        informacionPago = new InformacionPago("Nombre Titular", "4111111111111111", "12/23", "123", 100.0, "txn123", comprador, pieza);
    }

    @Test
    public void testGetNombreTitular() {
        assertEquals("Nombre Titular", informacionPago.getNombreTitular());
    }

    @Test
    public void testSetNombreTitular() {
        informacionPago.setNombreTitular("Nuevo Titular");
        assertEquals("Nuevo Titular", informacionPago.getNombreTitular());
    }

    @Test
    public void testGetTipoTarjeta() {
        assertEquals("Visa", informacionPago.getTipoTarjeta());
        informacionPago.setNumeroTarjeta("5111111111111111");
        assertEquals("Mastercard", informacionPago.getTipoTarjeta());
    }
}

