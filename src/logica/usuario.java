package logica;

import java.util.ArrayList;
import java.util.List;

public class usuario {


    private String nombre;
    private String contraseña;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private String codigoPostal;
    private String pais;
    private List<pieza> piezasCompradas;  // Piezas que ha comprado el usuario
    private List<transaccion> historialCompras; // Historial de compras
    private boolean esCajero;
    private boolean esComprador;
    private boolean esAdministrador;


    public usuario(String nombre, String contraseña, String email, String telefono, 
                   String direccion, String ciudad, String codigoPostal, String pais,boolean esCajero,boolean esComprador, boolean esAdministrador ) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.piezasCompradas = new ArrayList<>();
        this.historialCompras = new ArrayList<>();
        this.esCajero = esCajero;
        this.esComprador = esComprador;
        this.esAdministrador = esAdministrador;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    
    public boolean esCajero() {
        return esCajero;
    }
    
    //aca es la parte de historial  comprador
 // Método para agregar pieza comprada
    public void agregarPiezaComprada(pieza pieza) {
        this.piezasCompradas.add(pieza);
    }

    // Método para agregar una transacción al historial de compras
    public void agregarTransaccion(transaccion trans) {
        this.historialCompras.add(trans);
        agregarPiezaComprada(trans.getPieza());
    }

    // Getters y Setters

    public List<pieza> getPiezasCompradas() {
        return piezasCompradas;
    }

    public List<transaccion> getHistorialCompras() {
        return historialCompras;
    }

    // Método para calcular el valor total de las piezas compradas
    public double calcularValorColeccion() {
        double total = 0.0;
        for (pieza p : piezasCompradas) {
            total += p.getPrecioVenta(); 
        }
        return total;
    }
    private List<transaccion> transacciones = new ArrayList<>();

    public List<transaccion> getTransacciones() {
        return transacciones;
    }
    //ESTA ES LA PARTE DE ROL COMPRADOR
    
    

    public boolean esComprador() {
        return esComprador;
    }
    
    
    public boolean esAdministrador() {
        return esAdministrador;
    }




}


