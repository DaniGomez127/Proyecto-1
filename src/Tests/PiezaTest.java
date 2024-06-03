package Tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import logica.pieza;
import logica.transaccion;

public class PiezaTest {
    private pieza testPieza;

    @Before
    public void setUp() {
        testPieza = new pieza("id1", 2.0, 1.5, 0.5, 20.0, "Pieza Test", true, "COD123", 2021, "Test Artist", "Descripción test", null, 1500.0, "2024-05-15", true, "Original Owner", "2024-12-31", null);
    }

    @Test
    public void testCambioEstadoExhibicion() {
     
        assertTrue("Inicialmente debería estar en exhibición", testPieza.isEstaEnExhibicion());
        
      
        testPieza.cambiarEstadoExhibicion(false);
        assertFalse("Debería poder cambiar el estado de exhibición", testPieza.isEstaEnExhibicion());
    }

    @Test
    public void testAgregarHistorialTransaccion() {
        // Crear una transacción y agregarla
        transaccion nuevaTransaccion = new transaccion("trans001", new Date(), "Galería", 2000.0, null, testPieza);
        testPieza.agregarHistorialTransaccion(nuevaTransaccion);
        
        // Verificar que se agregó correctamente
        assertEquals("El historial de transacciones debería contener 1 elemento", 1, testPieza.getHistorialTransacciones().size());
        assertEquals("La transacción agregada debe ser la misma que la creada", nuevaTransaccion, testPieza.getHistorialTransacciones().get(0));
    }

    @Test
    public void testActualizarPrecioVenta() {
        // Establecer y verificar nuevo precio de venta
        testPieza.setPrecioVenta(2000.0);
        assertEquals("El precio de venta debería haberse actualizado", 2000.0, testPieza.getPrecioVenta(), 0.0);
    }

    @Test
    public void testActualizarFechaVenta() {
        // Establecer y verificar nueva fecha de venta
        String nuevaFecha = "2024-06-01";
        testPieza.setFechaVenta(nuevaFecha);
        assertEquals("La fecha de venta debería haberse actualizado", nuevaFecha, testPieza.getFechaVenta());
    }

    @Test
    public void testConsignacion() {
        // Verificar estado inicial de consignación
        assertTrue("Inicialmente debería estar en consignación", testPieza.isenConsignacion());
        
        // Cambiar estado de consignación y verificar
        testPieza.setenConsignacion(false);
        assertFalse("El estado de consignación debería haber cambiado", testPieza.isenConsignacion());
    }
    
    
}

