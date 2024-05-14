package logica;

import java.util.ArrayList;
import java.util.List;

public class artista {
    private String nombre;
    private List<pieza> piezasCreadas;  // Lista de piezas creadas por el artista

    // Constructor
    public artista(String nombre) {
        this.nombre = nombre;
        this.piezasCreadas = new ArrayList<>();  // Inicializar la lista de piezas
    }

    // Método para agregar una pieza a la lista de piezas creadas por el artista
    public void agregarPieza(pieza pieza) {
        this.piezasCreadas.add(pieza);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public List<pieza> getPiezasCreadas() {
        return piezasCreadas;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

