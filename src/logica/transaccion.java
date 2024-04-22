package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class transaccion {

 
    private String id;
    private Date fecha;
    private String lugar;
    private double monto;
    private comprador comprador;
    private pieza pieza;
    private List<oferta> ofertas;


    public transaccion(String id, Date fecha, String lugar, double monto, comprador comprador, pieza pieza) {
        this.id = id;
        this.fecha = fecha;
        this.lugar = lugar;
        this.monto = monto;
        this.comprador = comprador;
        this.pieza = pieza;
        this.ofertas = new ArrayList<>();
    }

  
    public void realizarOferta(oferta oferta) {
        ofertas.add(oferta);
    
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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

    public List<oferta> getOfertas() {
        return ofertas;
    }
    

}


