package logica;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class galeria {
    private List<pieza> piezas;
    private List<subasta> subastas;
    private HashMap<String, usuario> usuarios;
    private List<transaccion> transacciones;
    private HashMap<String, artista> artistas; 
    


    private File archivoPiezas = new File("data/piezas.txt");
    private File archivoSubastas = new File("data/subastas.txt");
    private File archivoUsuarios = new File("data/usuarios.txt");
    private File archivoTransacciones = new File("data/transacciones.txt");
    private File archivoArtistas = new File("data/artistas.txt"); 

    public galeria() {
        piezas = new ArrayList<>();
        subastas = new ArrayList<>();
        usuarios = new HashMap<>();
        transacciones = new ArrayList<>();
        artistas = new HashMap<>(); // Inicializar el HashMap de artistas

        try {
            cargarUsuarios();
            cargarDatos();
          
        } catch (IOException e) {
            System.err.println("Error al inicializar la galería: " + e.getMessage());
        }
    }

    private void cargarDatos() {
        try {
            cargarUsuarios();
            cargarPiezas();
            cargarSubastas();
            cargarTransacciones();
            cargarArtistas();
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarPiezas() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivoPiezas));
        String line;
        while ((line = reader.readLine()) != null) {
            pieza pieza = parsePieza(line);
            if (pieza != null) {
                piezas.add(pieza);
            }
        }
        reader.close();
    }

    private pieza parsePieza(String data) {
        String[] parts = data.split(",");
        if (parts.length == 16) {
            try {
                String id = parts[0];
                double altura = Double.parseDouble(parts[1]);
                double anchura = Double.parseDouble(parts[2]);
                double profundidad = Double.parseDouble(parts[3]);
                double peso = Double.parseDouble(parts[4]);
                String nombre = parts[5];
                boolean estaEnExhibicion = Boolean.parseBoolean(parts[6]);
                String codigoIdentificador = parts[7];
                int anoCreacion = Integer.parseInt(parts[8]);
                String autor = parts[9];
                String descripcion = parts[10];
                double precioVenta = Double.parseDouble(parts[11].trim()); 
                String fechaVenta = parts[12].trim(); 
                boolean enConsignacion = Boolean.parseBoolean(parts[13]);
                String propietarioReal = parts[14].trim(); 
                String fechaFinConsignacion = parts[15].trim(); 

              
                return new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                                 codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta, fechaVenta,enConsignacion,propietarioReal,fechaFinConsignacion);
            } catch (NumberFormatException e) {
                System.err.println("Error en el formato de los números: " + e.getMessage());
                return null;
            }
        } else {
            System.err.println("Línea en formato incorrecto: " + data);
            return null;
        }
    }


    private void cargarSubastas() throws IOException {
        File archivoSubastas = new File("data/subastas.txt");
        if (!archivoSubastas.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoSubastas))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String idSubasta = parts[0];
                    String idPieza = parts[1];
                    boolean estaActiva = Boolean.parseBoolean(parts[2]);
                    double valorInicial = Double.parseDouble(parts[3]);
                    double valorMinimo = Double.parseDouble(parts[4]);

                    pieza pieza = buscarPiezaPorId(idPieza);
                    if (pieza != null) {
                        subasta nuevaSubasta = new subasta(idSubasta, pieza, valorInicial, valorMinimo);
                        nuevaSubasta.setEstaActiva(estaActiva);
                        subastas.add(nuevaSubasta);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar las subastas: " + e.getMessage());
        }
    }


    private void cargarUsuarios() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoUsuarios))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 11) {
                    String nombre = parts[0];
                    String contraseña = parts[1];
                    String email = parts[2];
                    String telefono = parts[3];
                    String direccion = parts[4];
                    String ciudad = parts[5];
                    String codigoPostal = parts[6];
                    String pais = parts[7];
                    boolean esCajero = Boolean.parseBoolean(parts[8]);
                    boolean esComprador = Boolean.parseBoolean(parts[9]);
                    boolean esAdministrador = Boolean.parseBoolean(parts[10]);

                    usuario newUser;
                    if (esCajero) {
                        newUser = new cajero(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais);
                    } else if (esComprador) {
                        newUser = new comprador(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais);
                    } else if (esAdministrador) {
                        newUser = new usuario(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais, false, false, true);
                    } else {
                        newUser = new usuario(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais, false, false, false);
                    }

                    usuarios.put(nombre, newUser);
                }
            }
        }
    }
    
    public void agregarUsuario(usuario newUser) {
        if (!usuarios.containsKey(newUser.getNombre())) {
            usuarios.put(newUser.getNombre(), newUser);
            guardarUsuario();
        } else {
            System.out.println("El usuario ya existe.");
        }
    }
    
    
    
 

    public void guardarDatos() {
        guardarPiezas();
        guardarSubastas();
        guardarUsuario();
        guardarTransacciones();
    }

    private void guardarPiezas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoPiezas))) {
            for (pieza pieza : piezas) {
                String piezaData = pieza.getId() + "," +
                                   pieza.getAltura() + "," +
                                   pieza.getAnchura() + "," +
                                   pieza.getProfundidad() + "," +
                                   pieza.getPeso() + "," +
                                   pieza.getNombre() + "," +
                                   pieza.isEstaEnExhibicion() + "," +
                                   pieza.getCodigoIdentificador() + "," +
                                   pieza.getAnoCreacion() + "," +
                                   pieza.getAutor() + "," +
                                   pieza.getDescripcion() + "," +
                                   pieza.getPrecioVenta() + "," +
                                   pieza.getFechaVenta() + "," +
                                   pieza.isenConsignacion() + "," +
                                   pieza.getPropietarioReal() + "," +
                                   pieza.getFechaFinConsignacion();
                writer.write(piezaData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de piezas.");
            e.printStackTrace();
        }
    }
 // ESTO ES LA PARTE DE ARTISTAS

    
    private pieza parsePieza1(String data) {
        String[] parts = data.split(",");
        if (parts.length < 16) {
            System.err.println("Línea en formato incorrecto o incompleta: " + data);
            return null;
        }
        try {
            String id = parts[0].trim();
            double altura = Double.parseDouble(parts[1].trim());
            double anchura = Double.parseDouble(parts[2].trim());
            double profundidad = Double.parseDouble(parts[3].trim());
            double peso = Double.parseDouble(parts[4].trim());
            String nombre = parts[5].trim();
            boolean estaEnExhibicion = Boolean.parseBoolean(parts[6].trim());
            String codigoIdentificador = parts[7].trim();
            int anoCreacion = Integer.parseInt(parts[8].trim());
            String autor = parts[9];
            String descripcion = parts[10].trim(); 
            double precioVenta = Double.parseDouble(parts[11].trim()); 
            String fechaVenta = parts[12].trim(); 
            boolean enConsignacion = Boolean.parseBoolean(parts[13]);
            String propietarioReal = parts[14].trim();
            String fechaFinConsignacion = parts[15].trim(); 

            return new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                    codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta, fechaVenta,enConsignacion,propietarioReal,fechaFinConsignacion);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el número: " + e.getMessage() + " en la línea: " + data);
            return null;
        }
    }



    

    public void guardarSubastas() {
        File archivoSubastas = new File("data/subastas.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoSubastas))) {
            for (subasta sub : subastas) {
                writer.write(sub.getId() + "," + sub.getPieza().getId() + "," + sub.isEstaActiva() + "," + sub.getValorInicial() + "," + sub.getValorMinimo());
                writer.newLine();
                for (oferta of : sub.getOfertas()) {
                    writer.write(sub.getId() + "," + of.getComprador().getNombre() + "," + of.getValorOferta() + "," + new SimpleDateFormat("yyyy-MM-dd").format(of.getFecha()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las subastas: " + e.getMessage());
        }
    }

    public void guardarUsuario() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) {  // false para sobreescribir el archivo
            for (usuario user : usuarios.values()) {
                String userInfo = user.getNombre() + "," +
                                  user.getContraseña() + "," +
                                  user.getEmail() + "," +
                                  user.getTelefono() + "," +
                                  user.getDireccion() + "," +
                                  user.getCiudad() + "," +
                                  user.getCodigoPostal() + "," +
                                  user.getPais() + "," +
                                  user.esCajero() + "," +
                                  user.esComprador() + "," +
                				  user.esAdministrador();
                					
                writer.write(userInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar todos los usuarios en el archivo.");
            e.printStackTrace();
        }
    }

    private void guardarTransacciones() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTransacciones))) {
            for (transaccion trans : transacciones) {
                writer.write(trans.getId() + "," +
                        new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) + "," +
                        trans.getComprador().getNombre() + "," +
                        trans.getPieza().getId() + "," +
                        trans.getMonto());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las transacciones: " + e.getMessage());
        }
    }


    public usuario verificarCredenciales(String nombre, String contraseña) {
        usuario user = usuarios.get(nombre);
        if (user != null && user.getContraseña().equals(contraseña)) {
            return user;
        }
        return null;
    }
    
    
    
 // ESTO HACE PARTE DE PIEZA
    public pieza buscarPiezaPorId(String id) {
        for (pieza p : piezas) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    // ESTO HACE PARTE DE ARTISTA
    private void cargarArtistas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoArtistas))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String nombreArtista = parts[0].trim();
                artista art = artistas.getOrDefault(nombreArtista, new artista(nombreArtista));
                for (int i = 1; i < parts.length; i++) {
                    pieza p = parsePieza(parts[i]);
                    if (p != null) art.agregarPieza(p);
                }
                artistas.put(nombreArtista, art);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de artistas: " + e.getMessage());
        }
    }

    
 // Método para obtener un artista por nombre
    public artista getArtista(String nombre) {
        return artistas.get(nombre);
    }
    
    //PARTE DE HISTORIAL DE COMRPADOR
    
    public usuario getUsuario(String nombre) {
        return usuarios.get(nombre);
    }
    
    private Date parseFecha(String fechaStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(fechaStr);
    }
    
    
    private void cargarTransacciones() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoTransacciones))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    try {
                        String idTransaccion = parts[0];
                        Date fecha = parseFecha(parts[1]);
                        String lugar = parts[2];
                        double monto = Double.parseDouble(parts[4]);
                        String nombreUsuario = parts[3];
                        String idPieza = parts[5];

                        usuario comprador = usuarios.get(nombreUsuario);
                        pieza pieza = buscarPiezaPorId(idPieza);

                        if (comprador != null && pieza != null) {
                            transaccion nuevaTransaccion = new transaccion(idTransaccion, fecha, lugar, monto, comprador, pieza);
                            transacciones.add(nuevaTransaccion);
                            comprador.agregarTransaccion(nuevaTransaccion);
                            System.out.println("Transacción cargada: " + idTransaccion);
                        } else {
                            System.out.println("Error: Comprador o pieza no encontrada para la transacción " + idTransaccion);
                        }
                    } catch (ParseException e) {
                        System.err.println("Error al parsear la fecha para la transacción: " + parts[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de transacciones: " + e.getMessage());
        }
    }
    
    
    public boolean agregarPiezaEnConsignacion(pieza nuevaPieza, usuario usuario) {
        if (usuario.esAdministrador()) {
            piezas.add(nuevaPieza);
            guardarPiezas();  // Llama al método que guarda las piezas en el archivo
            return true;
        }
        return false;
    }


    public boolean registrarVentaPieza(String id, double precioVenta, String fechaVenta, usuario usuarioLogueado) {
        pieza pieza = buscarPiezaPorId(id);
        if (pieza != null && usuarioLogueado.esAdministrador()) {
            pieza.setPrecioVenta(precioVenta);
            pieza.setFechaVenta(fechaVenta);
            guardarPiezas();  // Guardar los cambios en el archivo
            return true;  // Venta registrada
        }
        return false;  // Pieza no encontrada o acceso denegado
    }



    public boolean devolverPieza(String idPieza, usuario admin) {
        if (admin.esAdministrador()) {
            pieza p = buscarPiezaPorId(idPieza);
            if (p != null && p.isenConsignacion()) {
                piezas.remove(p);
                guardarPiezas();
                return true;
            }
        }
        return false;
    }


 // Métodos existentes para cargar y guardar datos

    public boolean comprarPiezaPorValorFijo(String idPieza, comprador c) {
        pieza p = buscarPiezaPorId(idPieza);
        if (p != null && p.isDisponibleParaVentaFija() && !p.isBloqueada()) {
            p.bloquear();
           
            return true;
        }
        return false;
    }

    public void verificarYConfirmarCompra(String idPieza, usuario admin, comprador comprador) {
        if (admin.esAdministrador()) {
            pieza pieza = buscarPiezaPorId(idPieza);
            if (pieza != null && pieza.isDisponibleParaVentaFija()) {
                pieza.setDisponibleParaVentaFija(false);
                transaccion nuevaTransaccion = new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", pieza.getPrecioVenta(), comprador, pieza);
                transacciones.add(nuevaTransaccion);
                comprador.agregarTransaccion(nuevaTransaccion);
                guardarTransacciones();
                System.out.println("Compra verificada y confirmada.");
            } else {
                System.out.println("Pieza no encontrada o no disponible para venta fija.");
            }
        } else {
            System.out.println("No tiene permisos para verificar compras.");
        }
    }

    public void registrarOfertaEnSubasta(String idSubasta, oferta oferta, cajero cajero) {
        subasta subasta = buscarSubastaPorId(idSubasta);
        if (subasta != null && subasta.isEstaActiva()) {
            cajero.registrarOfertaEnSubasta(oferta, subasta);
            guardarSubastas(); // Guardar después de registrar la oferta
        } else {
            System.out.println("Subasta no encontrada o no activa.");
        }
    }

    public void cerrarSubasta(String idSubasta) {
        subasta s = buscarSubastaPorId(idSubasta);
        if (s != null) {
            s.cerrarSubasta();
            if (s.verificarValorMinimo()) {
                oferta ofertaGanadora = s.obtenerOfertaGanadora().orElse(null);
                if (ofertaGanadora != null) {
                    transaccion nuevaTransaccion = new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", ofertaGanadora.getValorOferta(), ofertaGanadora.getComprador(), s.getPieza());
                    transacciones.add(nuevaTransaccion);
                    ofertaGanadora.getComprador().agregarTransaccion(nuevaTransaccion);
                    System.out.println("Subasta cerrada exitosamente. Ganador: " + ofertaGanadora.getComprador().getNombre());
                }
            } else {
                System.out.println("Subasta cerrada sin alcanzar el valor mínimo.");
            }
            guardarSubastas(); // Guardar después de cerrar la subasta
        }
    }

    // Métodos para buscar piezas y subastas por ID


    public subasta buscarSubastaPorId(String idSubasta) {
        for (subasta subasta : subastas) {
            if (subasta.getId().equals(idSubasta)) {
                return subasta;
            }
        }
        return null;
    }
    
    public boolean agregarSubasta(subasta nuevaSubasta) {
        if (buscarSubastaPorId(nuevaSubasta.getId()) == null) {
            subastas.add(nuevaSubasta);
            guardarSubastas(); // Guardar después de agregar la subasta
            return true;
        }
        return false;
    }
    
    


    public void activarSubasta(String idSubasta) {
        subasta subasta = buscarSubastaPorId(idSubasta);
        if (subasta != null) {
            subasta.setEstaActiva(true);
            guardarSubastas(); // Guardar después de activar la subasta
            System.out.println("Subasta activada.");
        } else {
            System.out.println("Subasta no encontrada.");
        }
    }
    


}



