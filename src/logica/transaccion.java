package logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class transaccion {

 
	private String id;
    private Date fecha;
    private String lugar;
    private double monto;
    private usuario comprador;
    private pieza pieza;
    private List<oferta> ofertas;




    public transaccion(String id, Date fechaStr, String lugar, double monto, usuario comprador, pieza pieza) {
    	this.id = id;
        this.fecha = (fechaStr);
        this.lugar = lugar;
        this.monto = monto;
        this.comprador = comprador;
        this.pieza = pieza;
        this.ofertas = new ArrayList<>();


    }
 // Constructor para cargar desde texto con fechas en formato de cadena
    public transaccion(String id, String fechaStr, String lugar, double monto, usuario comprador, pieza pieza) throws ParseException {
        this.id = id;
        this.fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr); // Convertir cadena a fecha
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

    public usuario getComprador() {
        return comprador;
    }

    public void setComprador(usuario comprador) {
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
 // Getters y setters para cada campo
   
   
 
    
    private Date parseFecha(String fechaStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(fechaStr);
        } catch (ParseException e) {
            System.err.println("Error al parsear la fecha: " + fechaStr);
            return null;  
        }
    }

    public void imprimirDetallesDePieza() {
        if (this.pieza != null) {
            System.out.println("Pieza: " + pieza.getNombre());
        
        } else {
            System.out.println("Detalles de pieza no disponibles.");
        }
    }


   

    

}


