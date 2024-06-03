package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class comprador extends usuario {

    private List<oferta> ofertasRealizadas;
    private List<transaccion> historialCompras;
    private InformacionPago informacionPago;
    private double cupoMaximo;

    public comprador(String nombre, String contraseña, String email, String telefono, String direccion, String ciudad, String codigoPostal, String pais) {
        super(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais, false, true, false);
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

    public boolean ofrecerComprarPieza(pieza p) {
        if (p.isDisponibleParaVentaFija() && !p.isBloqueada()) {
            p.bloquear();
            return true;
        }
        return false;
    }

    public InformacionPago getInformacionPago() {
        return informacionPago;
    }

    public void setInformacionPago(InformacionPago informacionPago) {
        this.informacionPago = informacionPago;
    }

    public double getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(double cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public void agregarDatosPago(String nombreTitular, String numeroTarjeta, String fechaExpiracion, String cvv, double monto, pieza pieza) {
        String idTransaccion = UUID.randomUUID().toString();
        InformacionPago infoPago = new InformacionPago(nombreTitular, numeroTarjeta, fechaExpiracion, cvv, monto, idTransaccion, this, pieza);

        this.informacionPago = infoPago;
    }

    public String informacionPagoToString() {
        if (informacionPago == null) {
            return "null";
        }
        return informacionPago.getNombreTitular() + ";" +
               informacionPago.getNumeroTarjeta() + ";" +
               informacionPago.getFechaExpiracion() + ";" +
               informacionPago.getCvv() + ";" +
               informacionPago.getMonto() + ";" +
               informacionPago.getIdTransaccion();
    }

    public void informacionPagoFromString(String data) {
        if (data == null || data.equals("null")) {
            this.informacionPago = null;
        } else {
            String[] parts = data.split(";");
            this.informacionPago = new InformacionPago(
                parts[0], // nombreTitular
                parts[1], // numeroTarjeta
                parts[2], // fechaExpiracion
                parts[3], // cvv
                Double.parseDouble(parts[4]), // monto
                parts[5],  // idTransaccion
                this,
                null 
            );
        }
    }
}
