package logica;


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
    private propietario propietario;
 


    public pieza(String id, double altura, double anchura, double profundidad,
                 double peso, String nombre, boolean estaEnExhibicion,
                 String codigoIdentificador, int anoCreacion, String autor,
                 String descripcion, propietario propietario) {
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

    public propietario getPropietario() {
        return propietario;
    }


    public void setPeso(double peso) {
        this.peso = peso;
    }


    public void cambiarEstadoExhibicion(boolean enExhibicion) {
        this.estaEnExhibicion = enExhibicion;
    }


    public void cambiarPropietario(propietario nuevoPropietario) {
        this.propietario = nuevoPropietario;
    }


}

