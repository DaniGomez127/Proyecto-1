package logica;

public class InformacionPago {
    private String nombreTitular;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String cvv;
    private double monto;
    private String idTransaccion;
    private comprador comprador;
    private pieza pieza;

    public InformacionPago(String nombreTitular, String numeroTarjeta, String fechaExpiracion, String cvv, double monto, String idTransaccion, comprador comprador, pieza pieza) {
        this.nombreTitular = nombreTitular;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
        this.monto = monto;
        this.idTransaccion = idTransaccion;
        this.comprador = comprador;
        this.pieza = pieza;
    }


    // Getters y setters...

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public comprador getComprador() {
        return comprador;
    }

    public void setComprador(comprador comprador) {
        this.comprador = comprador;
    }

    @Override
    public String toString() {
        return "InformacionPago{" +
                "nombreTitular='" + nombreTitular + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", cvv='" + cvv + '\'' +
                ", monto=" + monto +
                ", idTransaccion='" + idTransaccion + '\'' +
                ", comprador=" + comprador +
                '}';
    }

    // Método para determinar el tipo de tarjeta
    public String getTipoTarjeta() {
        if (numeroTarjeta.startsWith("4")) {
            return "Visa";
        } else if (numeroTarjeta.startsWith("5")) {
            return "Mastercard";
        } else {
            return "Desconocido";
        }
    }
    
    
    

    public pieza getPieza() {
        return pieza;
    }

    public void setPieza(pieza pieza) {
        this.pieza = pieza;
    }
}
    
    









