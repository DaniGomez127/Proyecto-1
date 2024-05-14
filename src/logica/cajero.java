package logica;



public class cajero extends usuario{

    private String nombre;
    private String contacto;
    
    // Constructor
    public cajero(String nombre, String contraseña, String email, String telefono, String direccion, String ciudad, String codigoPostal, String pais) {
        super(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais, true, false, false);
    }

 
    public boolean confirmarPago(pago pago) {
        if (pago != null && !pago.isPagoConfirmado()) {
            pago.confirmar();
            return true;
        }
        return false;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public void registrarOfertaEnSubasta(oferta oferta, subasta subasta) {
        subasta.registrarOferta(oferta);
    }

   
}

