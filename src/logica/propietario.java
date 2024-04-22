package logica;

import java.util.ArrayList;
import java.util.List;

public class propietario {


    private String nombre;
    private String contacto;
    private List<pieza> piezas; 


    public propietario(String nombre, String contacto) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.piezas = new ArrayList<>();
    }


    public void agregarPieza(pieza pieza) {

        piezas.add(pieza);
    }
    
    public void eliminarPieza(pieza pieza) {

        piezas.remove(pieza);
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

    public List<pieza> getPiezas() {
        return piezas;
    }
    


}


