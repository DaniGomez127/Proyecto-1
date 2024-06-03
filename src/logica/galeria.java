package logica;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import interfaz.PasarelaPago;

public class galeria {
    private List<pieza> piezas;
    private List<subasta> subastas;
    private HashMap<String, usuario> usuarios;
   
    private HashMap<String, artista> artistas; 
    private List<transaccion> transacciones = new ArrayList<>();
    


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
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoPiezas))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pieza pieza = parsePieza(line);
                if (pieza != null) {
                    piezas.add(pieza);
                    if (!pieza.getPropietarioReal().equals("null")) {
                        comprador comprador = (comprador) usuarios.get(pieza.getPropietarioReal());
                        if (comprador != null) {
                            comprador.agregarHistorialCompra(new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", pieza.getPrecioVenta(), comprador, pieza));
                        }
                    }
                }
            }
        }
    }



    private void cargarSubastas() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoSubastas))) {
            String line;
            subasta currentSubasta = null;
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
                        currentSubasta = new subasta(idSubasta, pieza, valorInicial, valorMinimo);
                        currentSubasta.setEstaActiva(estaActiva);
                        subastas.add(currentSubasta);
                    }
                } else if (parts.length == 4 && currentSubasta != null) {
                    if (currentSubasta.isEstaActiva()) {
                        String idSubasta = parts[0];
                        String nombreComprador = parts[1];
                        double valorOferta = Double.parseDouble(parts[2]);
                        Date fechaOferta = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);

                        comprador comprador = (comprador) usuarios.get(nombreComprador);
                        if (comprador != null && currentSubasta.getPieza() != null) {
                            oferta oferta = new oferta(valorOferta, fechaOferta, comprador, currentSubasta.getPieza());
                            currentSubasta.registrarOferta(oferta);
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
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
                        if (parts.length > 11) {
                            double cupoMaximo = Double.parseDouble(parts[11]);
                            ((comprador) newUser).setCupoMaximo(cupoMaximo);
                            if (parts.length > 12) {
                                String infoPagoStr = parts[12];
                                ((comprador) newUser).informacionPagoFromString(infoPagoStr);
                            }
                            for (int i = 13; i < parts.length; i++) {
                                String[] transParts = parts[i].split(";");
                                if (transParts.length == 6) {
                                    transaccion t = parseTransaccion(parts[i]);
                                    if (t != null) {
                                        ((comprador) newUser).agregarHistorialCompra(t);
                                    }
                                }
                            }
                        }
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

    


    
    
    
 

    public void guardarDatos() {
        guardarPiezas();
        guardarSubastas();
        guardarUsuario();
        guardarTransacciones();
    }


 // ESTO ES LA PARTE DE ARTISTAS

    
    private pieza parsePieza(String data) {
        String[] parts = data.split(",");
        if (parts.length == 17) {
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
                String imagenRuta = parts[16].trim();

                return new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                                 codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta,
                                 fechaVenta, enConsignacion, propietarioReal, fechaFinConsignacion, imagenRuta);
            } catch (NumberFormatException e) {
                System.err.println("Error en el formato de los números: " + e.getMessage());
                return null;
            }
        } else {
            
            return null;
        }
    }





    

    public void guardarSubastas() {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) {
            for (usuario user : usuarios.values()) {
                StringBuilder userInfo = new StringBuilder();
                userInfo.append(user.getNombre()).append(",")
                        .append(user.getContraseña()).append(",")
                        .append(user.getEmail()).append(",")
                        .append(user.getTelefono()).append(",")
                        .append(user.getDireccion()).append(",")
                        .append(user.getCiudad()).append(",")
                        .append(user.getCodigoPostal()).append(",")
                        .append(user.getPais()).append(",")
                        .append(user.esCajero()).append(",")
                        .append(user.esComprador()).append(",")
                        .append(user.esAdministrador());

                if (user instanceof comprador) {
                    comprador c = (comprador) user;
                    userInfo.append(",")
                            .append(c.getCupoMaximo()).append(",")
                            .append(c.informacionPagoToString());
                    for (transaccion t : c.getHistorialCompras()) {
                        userInfo.append(",").append(t.toString());
                    }
                }

                writer.write(userInfo.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar todos los usuarios en el archivo.");
            e.printStackTrace();
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
        try (BufferedReader br = new BufferedReader(new FileReader("data/transacciones.txt"))) {
            String linea;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    try {
                        String id = partes[0];
                        Date fecha = dateFormat.parse(partes[1]);
                        double monto = Double.parseDouble(partes[2]);
                        usuario comprador = encontrarUsuarioPorNombre(partes[3]); 
                        pieza piezaComprada = encontrarPiezaPorId(partes[4]); 
                        
                        if (comprador != null && piezaComprada != null) {
                            transaccion nuevaTransaccion = new transaccion(id, fecha, monto, (comprador) comprador, piezaComprada);
                            transacciones.add(nuevaTransaccion);
                        } else {
                          
                        }
                    } catch (ParseException e) {
                        System.err.println("Error al parsear la fecha para la transacción: " + partes[0]);
                    } catch (NumberFormatException e) {
                    
                    }
                } else {
                 
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar las transacciones: " + e.getMessage());
        }
    }
    
    private usuario encontrarUsuarioPorNombre(String nombre) {

        return null; // Placeholder
    }

    private pieza encontrarPiezaPorId(String id) {
   
        return null; // Placeholder
    }
    
    public boolean agregarPiezaEnConsignacion(pieza nuevaPieza, usuario usuario) {
        if (usuario.esAdministrador()) {
            piezas.add(nuevaPieza);
            guardarPiezas(); 
            return true;
        }
        return false;
    }
    private void guardarPiezas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoPiezas, false))) {
            for (pieza p : piezas) {
                String piezaData = p.getId() + "," +
                                   p.getAltura() + "," +
                                   p.getAnchura() + "," +
                                   p.getProfundidad() + "," +
                                   p.getPeso() + "," +
                                   p.getNombre() + "," +
                                   p.isEstaEnExhibicion() + "," +
                                   p.getCodigoIdentificador() + "," +
                                   p.getAnoCreacion() + "," +
                                   p.getAutor() + "," +
                                   p.getDescripcion() + "," +
                                   p.getPrecioVenta() + "," +
                                   p.getFechaVenta() + "," +
                                   p.isenConsignacion() + "," +
                                   p.getPropietarioReal() + "," +
                                   p.getFechaFinConsignacion() + "," +
                                   p.getImagenRuta(); 
                writer.write(piezaData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar las piezas: " + e.getMessage());
        }
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
                comprador.agregarHistorialCompra(nuevaTransaccion);  // Agrega la transacción al historial del comprador
                guardarTransacciones();
                guardarUsuarios();  // Guarda los usuarios con el historial de compras actualizado
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

    public List<oferta> obtenerOfertasDeSubasta(String idSubasta) {
        subasta s = buscarSubastaPorId(idSubasta);
        if (s != null) {
            return s.getOfertas();
        }
        return new ArrayList<>();
    }

    public void cerrarSubastaManualmente(String idSubasta, oferta ofertaGanadora) {
        subasta s = buscarSubastaPorId(idSubasta);
        if (s != null && s.getOfertas().contains(ofertaGanadora)) {
            s.cerrarSubasta();
            // Procesar el pago antes de completar la transacción
            InformacionPago infoPago = ofertaGanadora.getComprador().getInformacionPago();
            if (infoPago != null) {
                boolean pagoExitoso = procesarPago("PasarelaSimulada", infoPago, ofertaGanadora.getComprador());
                if (pagoExitoso) {
                    transaccion nuevaTransaccion = new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", ofertaGanadora.getValorOferta(), ofertaGanadora.getComprador(), s.getPieza());
                    transacciones.add(nuevaTransaccion);
                    ofertaGanadora.getComprador().agregarHistorialCompra(nuevaTransaccion);
                    s.getPieza().setPropietarioReal(ofertaGanadora.getComprador().getNombre()); // Actualiza el propietario real de la pieza
                    guardarTransacciones();
                    guardarUsuarios();
                    guardarPiezas(); // Guarda los cambios en las piezas
                    System.out.println("Subasta cerrada exitosamente. Ganador: " + ofertaGanadora.getComprador().getNombre());
                } else {
                    System.out.println("Pago fallido. La pieza no puede ser entregada.");
                }
            } else {
                System.out.println("No se encontró información de pago para el comprador.");
            }
            guardarSubastas(); // Guardar después de cerrar la subasta
        } else {
            System.out.println("Oferta ganadora no encontrada en la subasta.");
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
    
   


    public List<pieza> getPiezas() {
        return piezas;
    }
    public List<transaccion> obtenerHistorialTransacciones(String idPieza) {
        List<transaccion> historial = new ArrayList<>();
        for (transaccion trans : transacciones) {
            if (trans.getPieza().getId().equals(idPieza)) {
                historial.add(trans);
            }
        }
        return historial;
    }

    public void actualizarPieza(pieza piezaActualizada) {
        for (int i = 0; i < piezas.size(); i++) {
            if (piezas.get(i).getId().equals(piezaActualizada.getId())) {
                piezas.set(i, piezaActualizada);
                return;
            }
        }
    }

    public void eliminarPieza(pieza piezaAEliminar) {
        piezas.removeIf(p -> p.getId().equals(piezaAEliminar.getId()));
    }

    public List<String> getPasarelasPagoDisponibles() {
        List<String> pasarelas = new ArrayList<>();
        pasarelas.add("PayU");
        pasarelas.add("Paypal");
    
        return pasarelas;
    }
    
    
    
    public boolean procesarPago(String pasarelaSeleccionada, InformacionPago informacionPago, comprador comprador) {
 
        InformacionPago infoPagoRegistrada = comprador.getInformacionPago();
        if (infoPagoRegistrada == null ||
            !informacionPago.getNumeroTarjeta().equals(infoPagoRegistrada.getNumeroTarjeta()) ||
            !informacionPago.getFechaExpiracion().equals(infoPagoRegistrada.getFechaExpiracion()) ||
            !informacionPago.getCvv().equals(infoPagoRegistrada.getCvv())) {
            System.out.println("Pago fallido: los datos de la tarjeta no coinciden con los registrados.");
            return false;
        }

        // Validación del monto
        double monto = informacionPago.getMonto();
        if (monto > comprador.getCupoMaximo()) {
            System.out.println("Pago fallido: el monto excede el cupo máximo del comprador.");
            return false;
        }

        // Procesar el pago utilizando la pasarela seleccionada
        PasarelaPago pasarelaPago = new PasarelaPagoSimulada(); 
        boolean resultado = pasarelaPago.procesarPago(informacionPago);
        System.out.println("Procesando pago con " + pasarelaSeleccionada + ": " + (resultado ? "Exitoso" : "Fallido"));

        if (resultado) {
     
            pieza piezaComprada = informacionPago.getPieza();
            
   
            if (piezaComprada == null) {
                System.out.println("Error: la pieza es nula.");
                return false;
            }

            piezaComprada.setPrecioVenta(monto);
            piezaComprada.setFechaVenta(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            piezaComprada.setPropietarioReal(comprador.getNombre());
            piezaComprada.cambiarEstadoExhibicion(false);
            piezaComprada.setenConsignacion(false);

            // Actualizar el archivo de piezas
            actualizarArchivoPiezas();

            // Crear una nueva instancia de Date para cada transacción
            Date fechaCompra = new Date();

            // Registrar la transacción
            transaccion nuevaTransaccion = new transaccion(
                UUID.randomUUID().toString(),
                fechaCompra,
                monto,
                comprador,
                piezaComprada
            );
            agregarTransaccion(nuevaTransaccion);
            guardarTransacciones();
        }

        return resultado;
    }





    
    


    // Método para agregar una transacción
    private void agregarTransaccion(transaccion nuevaTransaccion) {
        transacciones.add(nuevaTransaccion);
    }
    private void guardarTransacciones() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/transacciones.txt", true))) {
            for (transaccion trans : transacciones) {
                String linea = trans.getId() + "," + 
                               new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) + "," + 
                               trans.getMonto() + "," + 
                               trans.getComprador().getNombre() + "," + 
                               trans.getPieza().getId();
                writer.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las transacciones: " + e.getMessage());
        }
    }


 
    
    





    
    //nuevo
    public void agregarUsuario(usuario newUser) {
        if (!usuarios.containsKey(newUser.getNombre())) {
            usuarios.put(newUser.getNombre(), newUser);
            guardarUsuario();  // Guarda todos los usuarios después de agregar el nuevo usuario
        } else {
            System.out.println("El usuario ya existe.");
        }
    }
    
    private void guardarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) {
            for (usuario user : usuarios.values()) {
                StringBuilder userInfo = new StringBuilder();
                userInfo.append(user.getNombre()).append(",")
                        .append(user.getContraseña()).append(",")
                        .append(user.getEmail()).append(",")
                        .append(user.getTelefono()).append(",")
                        .append(user.getDireccion()).append(",")
                        .append(user.getCiudad()).append(",")
                        .append(user.getCodigoPostal()).append(",")
                        .append(user.getPais()).append(",")
                        .append(user.esCajero()).append(",")
                        .append(user.esComprador()).append(",")
                        .append(user.esAdministrador());

                if (user instanceof comprador) {
                    comprador c = (comprador) user;
                    userInfo.append(",")
                            .append(c.getCupoMaximo()).append(",")
                            .append(c.informacionPagoToString());
                    for (transaccion t : c.getHistorialCompras()) {
                        userInfo.append(",").append(t.toString());
                    }
                }

                writer.write(userInfo.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar todos los usuarios en el archivo.");
            e.printStackTrace();
        }
    }
    

    


    
    private transaccion parseTransaccion(String data) {
        String[] parts = data.split(";");
        if (parts.length >= 6) {
            try {
                String idTransaccion = parts[0];
                Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1]);
                String lugar = parts[2];
                double monto = Double.parseDouble(parts[3]);
                String nombreUsuario = parts[4];
                String idPieza = parts[5];

                usuario comprador = usuarios.get(nombreUsuario);
                pieza pieza = buscarPiezaPorId(idPieza);

                if (comprador != null && pieza != null) {
                    return new transaccion(idTransaccion, fecha, lugar, monto, comprador, pieza);
                } else {
                    System.out.println("Error: Comprador o pieza no encontrada para la transacción " + idTransaccion);
                }
            } catch (ParseException e) {
                System.err.println("Error al parsear la fecha para la transacción: " + parts[0]);
            }
        }
        return null;
    }
    
   

    public void seleccionarGanadorSubasta(String idSubasta, oferta ofertaGanadora) {
        subasta s = buscarSubastaPorId(idSubasta);
        if (s != null && s.getOfertas().contains(ofertaGanadora)) {

            InformacionPago infoPago = ofertaGanadora.getComprador().getInformacionPago();
            if (infoPago != null) {
                boolean pagoExitoso = procesarPago("PasarelaSimulada", infoPago, ofertaGanadora.getComprador());
                if (pagoExitoso) {
                    cerrarSubasta(idSubasta, ofertaGanadora);
                } else {
                    System.out.println("Pago fallido. La pieza no puede ser entregada.");
                }
            } else {
                System.out.println("No se encontró información de pago para el comprador.");
            }
        } else {
            System.out.println("Oferta ganadora no encontrada en la subasta.");
        }
    }

    private void cerrarSubasta(String idSubasta, oferta ofertaGanadora) {
        subasta s = buscarSubastaPorId(idSubasta);
        if (s != null) {
            s.cerrarSubasta();
            transaccion nuevaTransaccion = new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", ofertaGanadora.getValorOferta(), ofertaGanadora.getComprador(), s.getPieza());
            transacciones.add(nuevaTransaccion);
            ofertaGanadora.getComprador().agregarHistorialCompra(nuevaTransaccion);
            s.getPieza().setPropietarioReal(ofertaGanadora.getComprador().getNombre()); // Actualiza el propietario real de la pieza
            guardarTransacciones();
            guardarUsuarios();
            guardarPiezas(); // Guarda los cambios en las piezas
            guardarSubastas(); // Guardar después de cerrar la subasta
            System.out.println("Subasta cerrada exitosamente. Ganador: " + ofertaGanadora.getComprador().getNombre());
        }
    }


    
    public boolean procesarPagoYTransferirPieza(oferta ofertaGanadora) {
        if (ofertaGanadora != null) {
            comprador compradorGanador = ofertaGanadora.getComprador();
            InformacionPago infoPago = compradorGanador.getInformacionPago();
            if (infoPago != null && procesarPago("PasarelaSimulada", infoPago, compradorGanador)) {
                transaccion nuevaTransaccion = new transaccion(UUID.randomUUID().toString(), new Date(), "Galería", ofertaGanadora.getValorOferta(), compradorGanador, ofertaGanadora.getPieza());
                transacciones.add(nuevaTransaccion);
                compradorGanador.agregarHistorialCompra(nuevaTransaccion);
                ofertaGanadora.getPieza().setPropietarioReal(compradorGanador.getNombre()); // Actualiza el propietario real de la pieza
                guardarTransacciones();
                guardarUsuarios();
                guardarPiezas(); // Guarda los cambios en las piezas
                System.out.println("Pago exitoso. Propiedad transferida a: " + ofertaGanadora.getComprador().getNombre());
                return true;
            } else {
                System.out.println("Pago fallido. La pieza no puede ser entregada.");
            }
        }
        return false;
    }
    
    
    private void actualizarArchivoPiezas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/piezas.txt"))) {
            for (pieza p : getPiezas()) {
                String linea = p.getId() + "," + 
                               p.getAltura() + "," + 
                               p.getAnchura() + "," + 
                               p.getProfundidad() + "," + 
                               p.getPeso() + "," + 
                               p.getNombre() + "," + 
                               p.isEstaEnExhibicion() + "," + 
                               p.getCodigoIdentificador() + "," + 
                               p.getAnoCreacion() + "," + 
                               p.getAutor() + "," + 
                               p.getDescripcion() + "," + 
                               p.getPrecioVenta() + "," + 
                               p.getFechaVenta() + "," + 
                               p.isenConsignacion() + "," + 
                               p.getPropietarioReal() + "," + 
                               p.getFechaFinConsignacion() + "," + 
                               p.getImagenRuta();
                writer.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo de piezas: " + e.getMessage());
        }
    }

}
    
    
    
    







