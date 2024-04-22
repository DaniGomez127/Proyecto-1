package logica;



public class cajero {

    private String nombre;
    private String contacto;
    
    // Constructor
    public cajero(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
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

   
}

