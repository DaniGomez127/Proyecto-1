package logica;

import java.util.ArrayList;
import java.util.List;

public class comprador extends usuario {

    private List<oferta> ofertasRealizadas;
    private List<transaccion> historialCompras;

    public comprador(String nombre, String contraseña, String email, String telefono,
                     String direccion, String ciudad, String codigoPostal, String pais) {
        super(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais);
        this.ofertasRealizadas = new ArrayList<>();
        this.historialCompras = new ArrayList<>();
    }

    public void realizarOferta(subasta subasta, oferta oferta) {
        subasta.registrarOferta(oferta);
        ofertasRealizadas.add(oferta);

    }

    public void agregarHistorialCompra(transaccion transaccion) {
        historialCompras.add(transaccion);

    }


    public List<oferta> getOfertasRealizadas() {
        return ofertasRealizadas;
    }

    public void setOfertasRealizadas(List<oferta> ofertasRealizadas) {
        this.ofertasRealizadas = ofertasRealizadas;
    }

    public List<transaccion> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<transaccion> historialCompras) {
        this.historialCompras = historialCompras;
    }

}


