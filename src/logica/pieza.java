package logica;

import java.util.ArrayList;
import java.util.List;

public class pieza {

    private String id;
    private double altura;
    private double anchura;
    private double profundidad;
    private double peso;
    private String nombre;
    private boolean estaEnExhibicion;
    private String codigoIdentificador;
    private int anoCreacion;
    private String autor;
    private String descripcion;
    private usuario propietario;
    private List<transaccion> historialTransacciones; // Historial de transacciones
    private double precioVenta;  
    private String fechaVenta;
    private boolean enConsignacion;
    private String propietarioReal;
    private String fechaFinConsignacion;
    private boolean disponibleParaVentaFija;
    private boolean bloqueada;
    private String imagenRuta; // Campo para almacenar la ruta de la imagen

    // Constructor actualizado para incluir la ruta de la imagen
    public pieza(String id, double altura, double anchura, double profundidad,
                 double peso, String nombre, boolean estaEnExhibicion,
                 String codigoIdentificador, int anoCreacion, String autor,
                 String descripcion, usuario propietario, double precioVenta,
                 String fechaVenta, boolean enConsignacion, String propietarioReal,
                 String fechaFinConsignacion, String imagenRuta) {
        this.id = id;
        this.altura = altura;
        this.anchura = anchura;
        this.profundidad = profundidad;
        this.peso = peso;
        this.nombre = nombre;
        this.estaEnExhibicion = estaEnExhibicion;
        this.codigoIdentificador = codigoIdentificador;
        this.anoCreacion = anoCreacion;
        this.autor = autor;
        this.descripcion = descripcion;
        this.propietario = propietario;
        this.historialTransacciones = new ArrayList<>();
        this.precioVenta = precioVenta;
        this.fechaVenta = fechaVenta;
        this.enConsignacion = enConsignacion;
        this.propietarioReal = propietarioReal;
        this.fechaFinConsignacion = fechaFinConsignacion;
        this.imagenRuta = imagenRuta; // Inicializar la ruta de la imagen
    }

    // Getters y setters adicionales
    public String getImagenRuta() {
        return imagenRuta;
    }

    public void setImagenRuta(String imagenRuta) {
        this.imagenRuta = imagenRuta;
    }
    

    public String getId() {
        return id;
    }

    public double getAltura() {
        return altura;
    }

    public double getAnchura() {
        return anchura;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public double getPeso() {
        return peso;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstaEnExhibicion() {
        return estaEnExhibicion;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public int getAnoCreacion() {
        return anoCreacion;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcion() {
        return descripcion;
    }



    public void setPeso(double peso) {
        this.peso = peso;
    }


    public void cambiarEstadoExhibicion(boolean enExhibicion) {
        this.estaEnExhibicion = enExhibicion;
    }
    
    public String toFileString() {
        return id + "," + altura + "," + anchura + "," + profundidad + "," +
               peso + "," + nombre + "," + estaEnExhibicion + "," +
               codigoIdentificador + "," + anoCreacion + "," + autor + "," +
               descripcion + "," + precioVenta + "," + fechaVenta + "," +
               enConsignacion + "," + propietarioReal + "," + fechaFinConsignacion + "," +
               imagenRuta;
    }

    
 // Agregar una transacción al historial
    public void agregarHistorialTransaccion(transaccion transaccion) {
        this.historialTransacciones.add(transaccion);
    }

    // Métodos getters y setters adicionales
    public List<transaccion> getHistorialTransacciones() {
        return historialTransacciones;
    }
    
    //ESTO ES DE ARTISTA
 // Getters y setters para precioVenta y fechaVenta
    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
 // getters y setters
    public boolean isenConsignacion() {
        return enConsignacion;
    }

    public void setenConsignacion(boolean enConsignacion) {
        this.enConsignacion = enConsignacion;
    }
    
    //ESTO ES DE LA PARTE DE ACTUALZIAR PRECIO Y FECHAS
    public String getPropietarioReal() {
        return this.propietarioReal;
    }

    public String getFechaFinConsignacion() {
        return this.fechaFinConsignacion;
    }
    
    
    
    public boolean isDisponibleParaVentaFija() {
        return disponibleParaVentaFija;
    }

    public void setDisponibleParaVentaFija(boolean disponibleParaVentaFija) {
        this.disponibleParaVentaFija = disponibleParaVentaFija;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void bloquear() {
        this.bloqueada = true;
    }

    public void desbloquear() {
        this.bloqueada = false;
    }
    
  

    public void setPropietarioReal(String propietarioReal) {
        this.propietarioReal = propietarioReal;
    }



    

}


