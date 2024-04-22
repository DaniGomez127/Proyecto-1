package logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class subasta {

    private String id;
    private pieza pieza;
    private List<oferta> ofertas;
    private boolean estaActiva; 

    public subasta(String id, pieza pieza) {
        this.id = id;
        this.pieza = pieza;
        this.ofertas = new ArrayList<>();
        this.estaActiva = true;
    }


    public void registrarOferta(oferta oferta) {
        if (estaActiva) {
            ofertas.add(oferta);
        } else {
            throw new IllegalStateException("La subasta no está activa.");
        }
    }

 
    public void cerrarSubasta() {
        estaActiva = false;
    }


    public Optional<oferta> obtenerOfertaGanadora() {
        return ofertas.stream().max(Comparator.comparing(oferta::getValorOferta));
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isEstaActiva() {
        return estaActiva;
    }

    public void setEstaActiva(boolean estaActiva) {
        this.estaActiva = estaActiva;
    }


}
