package logica;

import java.util.List;
import java.util.ArrayList;

public class inventario {


    private List<pieza> piezasDisponibles;
    private List<pieza> piezasVendidas;


    public inventario() {
        this.piezasDisponibles = new ArrayList<>();
        this.piezasVendidas = new ArrayList<>();
    }


    public void agregarPieza(pieza pieza) {

        piezasDisponibles.add(pieza);
    }

    public void venderPieza(pieza pieza) {

        if (piezasDisponibles.remove(pieza)) {
            piezasVendidas.add(pieza);
        }
    }



    public List<pieza> getPiezasDisponibles() {
        return piezasDisponibles;
    }

    public List<pieza> getPiezasVendidas() {
        return piezasVendidas;
    }


	public static pieza buscarPiezaPorId(String pieceId) {
		// TODO Auto-generated method stub
		return null;
	}
    


}


