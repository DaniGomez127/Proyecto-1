package logica;

public class pago {
    private String id;
    private double monto;
    private String metodo;
    private String detalles;
    private boolean pagoConfirmado;


    public pago(String id, double monto, String metodo, String detalles, boolean pagoConfirmado) {
        this.id = id;
        this.monto = monto;
        this.metodo = metodo;
        this.detalles = detalles;
        this.pagoConfirmado = pagoConfirmado;
    }


    public void confirmar() {
        this.pagoConfirmado = true;
    }

  
    public boolean isPagoConfirmado() {
        return pagoConfirmado;
    }

    // Getters
    public String getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getDetalles() {
        return detalles;
    }


    public String getPago() {
        return "Id: " + id + ", Monto: " + monto + ", Método: " + metodo + ", Detalles: " + detalles + ", Confirmado: " + (pagoConfirmado ? "Sí" : "No");
    }


    public void setPagoConfirmado(boolean confirmado) {
        this.pagoConfirmado = confirmado;
    }
}
