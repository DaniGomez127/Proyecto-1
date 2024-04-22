package logica;

import java.util.ArrayList;
import java.util.List;

public class operador {

    private String nombre;
    private List<subasta> subastasAsignadas;

    public operador(String nombre) {
        this.nombre = nombre;
        this.subastasAsignadas = new ArrayList<>();
    }


    public void agregarSubasta(subasta subasta) {
        subastasAsignadas.add(subasta);
    }

    public void removerSubasta(subasta subasta) {
        subastasAsignadas.remove(subasta);
    }

    public void registrarOfertaEnSubasta(oferta oferta, subasta subasta) {

        subasta.registrarOferta(oferta);
    }

    public subasta buscarSubastaPorPieza(pieza pieza) {

        for (subasta subasta : subastasAsignadas) {
            if (subasta.getPieza().equals(pieza)) {
                return subasta;
            }
        }
        return null;
    }

    public List<oferta> obtenerOfertasDeSubasta(subasta subasta) {

        return subasta.getOfertas();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<subasta> getSubastasAsignadas() {
        return subastasAsignadas;
    }

    public void setSubastasAsignadas(List<subasta> subastasAsignadas) {
        this.subastasAsignadas = subastasAsignadas;
    }


}


