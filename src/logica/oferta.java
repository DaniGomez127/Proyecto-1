package logica;

import java.util.Date;

public class oferta {


    private double valorOferta;
    private Date fecha;
    private comprador comprador;
    private pieza pieza;


    public oferta(double valorOferta, Date fecha, comprador comprador, pieza pieza) {
        this.valorOferta = valorOferta;
        this.fecha = fecha;
        this.comprador = comprador;
        this.pieza = pieza;
    }


    public double getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(double valorOferta) {
        this.valorOferta = valorOferta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public comprador getComprador() {
        return comprador;
    }

    public void setComprador(comprador comprador) {
        this.comprador = comprador;
    }

    public pieza getPieza() {
        return pieza;
    }

    public void setPieza(pieza pieza) {
        this.pieza = pieza;
    }
    
    
    
   


    
    

}


