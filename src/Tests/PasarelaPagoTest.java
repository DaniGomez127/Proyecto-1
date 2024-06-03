package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import interfaz.PasarelaPago;
import logica.InformacionPago;
import logica.comprador;
import logica.pieza;

public class PasarelaPagoTest {
    private PasarelaPago pasarelaPago;
    private InformacionPago informacionPago;
    private comprador comprador;
    private pieza pieza;

    @BeforeEach
    public void setUp() {
        pasarelaPago = new PasarelaPago() {
            @Override
            public boolean procesarPago(InformacionPago informacionPago) {
                return informacionPago.getNumeroTarjeta().equals("1234567890123456");
            }
        };
        comprador = new comprador("testComprador", "password", "email@test.com", "123456789", "Address", "City", "12345", "Country");
        pieza = new pieza("1", 10.0, 10.0, 10.0, 1.0, "Test Piece", true, "ABC123", 2021, "Test Author", "Test Description", null, 100.0, null, false, "Owner", null, "path/to/image");
        informacionPago = new InformacionPago("Test Titular", "1234567890123456", "12/25", "123", 100.0, "transaccion-1", comprador, pieza);
    }

    @Test
    public void testProcesarPago() {
        assertTrue(pasarelaPago.procesarPago(informacionPago));
    }

    @Test
    public void testProcesarPagoInvalido() {
        informacionPago = new InformacionPago("Test Titular", "1111222233334444", "12/25", "123", 100.0, "transaccion-1", comprador, pieza);
        assertFalse(pasarelaPago.procesarPago(informacionPago));
    }
}
