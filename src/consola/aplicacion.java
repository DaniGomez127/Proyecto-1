package consola;

import logica.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class aplicacion {
    private galeria galeria;
    private usuario usuarioLogueado;

    public aplicacion() {
        this.galeria = new galeria();
        this.usuarioLogueado = null;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        do {
            if (usuarioLogueado == null) {
                System.out.println("Bienvenido a la Consola de Gestión de la Galería");
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Crear nuevo usuario");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                int inicioOpcion = Integer.parseInt(scanner.nextLine());

                switch (inicioOpcion) {
                    case 1:
                        usuarioLogueado = iniciarSesion(scanner);
                        break;
                    case 2:
                        crearUsuario(scanner);
                        break;
                    case 0:
                        salir = true;
                        galeria.guardarSubastas();// Guardar subastas al salir
                        break;
                    default:
                        System.out.println("Opción no válida, intente nuevamente.");
                        break;
                }
            }

            if (usuarioLogueado != null) {
                mostrarMenu(scanner);
            }
        } while (!salir);
        scanner.close();
    }

    private usuario iniciarSesion(Scanner scanner) {
        System.out.println("Iniciar Sesión");
        System.out.print("Ingrese nombre de usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine();
        usuario usuario = galeria.verificarCredenciales(nombre, contraseña);
        if (usuario != null) {
            usuarioLogueado = usuario;
            System.out.println("Inicio de sesión exitoso como " + (usuario.esCajero() ? "cajero" : usuario.esComprador() ? "comprador" : "administrador") + ".");
            return usuario;
        }
        System.out.println("Credenciales incorrectas o usuario no encontrado.");
        return null;
    }
    
    private void mostrarMenu(Scanner scanner) {
        System.out.println("\nBienvenido " + usuarioLogueado.getNombre());
        if (usuarioLogueado.esCajero()) {
            // Menú específico para cajeros
            System.out.println("1. Procesar Pagos");
            System.out.println("2. Mostrar Historia de una Pieza");
            System.out.println("3. Mostrar Historia de un Artista");
            System.out.println("0. Salir");
            
        }
          	else if (usuarioLogueado.esComprador()){
          		System.out.println("1. Ver Catálogo de Piezas");
          		System.out.println("2. Ver Mis Compras");
          		System.out.println("3. Mostrar Historia de una Pieza");
                System.out.println("4. Mostrar Historia de un Artista");
                System.out.println("5. Participar en Subasta");
                System.out.println("6. Comprar Pieza por Valor Fijo");
                
                
          		System.out.println("0. Salir");	
        	  
         }
            
            
          	else if (usuarioLogueado.esAdministrador()){
            
          		System.out.println("1. Administrar Piezas");
          		System.out.println("2. Administrar Subastas");
          		System.out.println("3. Procesar Pagos");
          		System.out.println("4. Mostrar Historia de una Pieza");
          		System.out.println("5. Mostrar Historia de un Artista");
          		System.out.println("6. Mostrar Historia de un Comprador");
          		System.out.println("7. Registrar Venta de Pieza ");
                System.out.println("8. Devolver Pieza a Propietario");
                System.out.println("9. Registrar Nueva Pieza en Consignación");
                System.out.println("10. Verificar Compra de Pieza");
                System.out.println("11. Crear Subasta");
                System.out.println("12. Activar Subasta");
          
                
                
          		System.out.println("0. Salir");
        }
        System.out.print("Seleccione una opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        procesarOpcion(opcion, scanner);
    }
    
  

    private void crearUsuario(Scanner scanner) {
        System.out.println("Crear Nuevo Usuario");
        System.out.print("Ingrese nombre de usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Ingrese código postal: ");
        String codigoPostal = scanner.nextLine();
        System.out.print("Ingrese país: ");
        String pais = scanner.nextLine();
        System.out.print("Es cajero? (si/no): ");
        boolean esCajero = scanner.nextLine().trim().equalsIgnoreCase("si");
        System.out.print("Es comprador? (si/no): ");
        boolean esComprador = scanner.nextLine().equalsIgnoreCase("si");
        System.out.print("Es administrador? (si/no): ");
        boolean esAdministrador = scanner.nextLine().trim().equalsIgnoreCase("si");
        
        usuario newUser = new usuario(nombre, contraseña, email, telefono, direccion, ciudad, codigoPostal, pais, esCajero, esComprador,esAdministrador);
        galeria.agregarUsuario(newUser);  // Esta llamada ahora pasa el objeto completo
    }

  

    private void procesarOpcion(int opcion, Scanner scanner) {
        if (usuarioLogueado.esCajero()) {
            // Opciones para cajeros
            switch (opcion) {
                case 1:
                    procesarPagos(scanner);
                    break;
                case 2:
                	mostrarHistoriaPieza(scanner);
                    break;
                case 3:
                	mostrarHistoriaArtista(scanner);
                    break;
                    
                case 0:
                    System.out.println("Saliendo del sistema...");
                    usuarioLogueado = null;
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
                    break;
                    
          }
        }  
            
          else if(usuarioLogueado.esComprador()){
        	  switch (opcion) {
              case 1:
                  verCatalogo(scanner);
                  break;
              case 2:
                  verCompras(scanner);
                  break;
              case 3:
                  mostrarHistoriaPieza(scanner);
                  break;
              case 4:
                  mostrarHistoriaArtista(scanner);
                  break;
              case 5:participarEnSubasta(scanner);
                  break;
              case 6:
            	  comprarPiezaPorValorFijo(scanner);
                  break;
                  
                  
              case 0:
                  System.out.println("Saliendo...");
                  System.exit(0);
                  break;
              default:
                  System.out.println("Opción no válida, intente nuevamente.");
                  break;
          }
        	  
        	  
        	  
          }
          else if (usuarioLogueado.esAdministrador()) {
            // Opciones para otros empleados
            switch (opcion) {
                case 1:
                    gestionarPiezas(scanner);
                    break;
                case 2:
                    gestionarSubastas(scanner);
                    break;
                case 3:
                    procesarPagos(scanner);
                    break;
                case 4:
                    mostrarHistoriaPieza(scanner);
                    break;
                case 5:
                    mostrarHistoriaArtista(scanner);
                    break;
                case 6:
                    mostrarHistoriaComprador(scanner);
                    break;
                case 7:
                	registrarVentaPieza(scanner);
                    break;
                case 8:
                	devolverPieza(scanner);
                    break;
                case 9:
                	agregarPiezaEnConsignacion(scanner);
                    break;
                    
                case 10:
                    verificarCompraPieza(scanner);
                    break;
                case 11:
                	crearSubasta(scanner);
                    break;
                case 12:
                	activarSubasta(scanner);
                    break;
                    
                    
                    
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
                    break;
            }
        }
    }
    



    private void devolverPieza(Scanner scanner) {
        System.out.print("Ingrese el ID de la pieza a devolver: ");
        String idPieza = scanner.nextLine();
        if (galeria.devolverPieza(idPieza, usuarioLogueado)) {
            System.out.println("Pieza devuelta con éxito.");
        } else {
            System.out.println("No se pudo devolver la pieza. Verifique que el ID de la pieza sea correcto y que la pieza esté en consignación.");
        }
    }



  



    private void gestionarPiezas(Scanner scanner) {
        System.out.println("Gestionar Piezas de la Galería");
        
    }

    private void gestionarSubastas(Scanner scanner) {
        System.out.println("Gestionar Subastas");
 
    }

    private void procesarPagos(Scanner scanner) {
        System.out.println("Procesar Pagos");
      
    }

    public static void main(String[] args) {
        aplicacion app = new aplicacion();
        app.iniciar();
    }
    
    private void mostrarHistoriaPieza(Scanner scanner) {
        System.out.print("Ingrese el ID de la pieza: ");
        String idPieza = scanner.nextLine();
        pieza pieza = galeria.buscarPiezaPorId(idPieza);

        if (pieza != null) {
            // Mostrar detalles completos de la pieza
            System.out.println("Detalles de la Pieza:");
            System.out.println("ID: " + pieza.getId());
            System.out.println("Nombre: " + pieza.getNombre());
            System.out.println("Autor: " + pieza.getAutor());
            System.out.println("Año de Creación: " + pieza.getAnoCreacion());
            System.out.println("Descripción: " + pieza.getDescripcion());
            System.out.println("Dimensiones (Altura x Anchura x Profundidad): " + 
                               pieza.getAltura() + " x " + 
                               pieza.getAnchura() + " x " + 
                               pieza.getProfundidad() + " cm");
            System.out.println("Peso: " + pieza.getPeso() + " kg");
            System.out.println("Está en Exhibición: " + (pieza.isEstaEnExhibicion() ? "Sí" : "No"));
            System.out.println("Código Identificador: " + pieza.getCodigoIdentificador());
            
            // Mostrar historial de transacciones si está disponible
            if (!pieza.getHistorialTransacciones().isEmpty()) {
                System.out.println("\nHistorial de Transacciones:");
                for (transaccion trans : pieza.getHistorialTransacciones()) {
                    System.out.println("Fecha: " + trans.getFecha() +
                                       ", Comprador: " + trans.getComprador().getNombre() +
                                       ", Monto: " + trans.getMonto() +
                                       ", Lugar: " + trans.getLugar());
                }
            } else {
                System.out.println("No hay transacciones registradas para esta pieza.");
            }
        } else {
            System.out.println("Pieza no encontrada.");
        }
    }
    
    
    
    private void mostrarHistoriaArtista(Scanner scanner) {
        System.out.print("Ingrese el nombre del artista: ");
        String nombreArtista = scanner.nextLine();
        artista artista = galeria.getArtista(nombreArtista);

        if (artista != null) {
            System.out.println("Historial de obras de " + artista.getNombre() + ":");
            for (pieza pieza : artista.getPiezasCreadas()) {
                System.out.println("  Pieza: " + pieza.getNombre() +
                                   ", Creada en: " + pieza.getAnoCreacion() +
                                   ", Vendida por: " + pieza.getPrecioVenta() +
                                   ", Fecha de venta: " + pieza.getFechaVenta());
            }
        } else {
            System.out.println("Artista no encontrado.");
        }
    }
    
    
    private void mostrarHistoriaComprador(Scanner scanner) {
        System.out.print("Ingrese el nombre del comprador: ");
        String nombreComprador = scanner.nextLine();
        usuario comprador = galeria.getUsuario(nombreComprador);
        if (comprador != null && !comprador.getTransacciones().isEmpty()) {
            System.out.println("Historia del comprador " + nombreComprador + ":");
            for (transaccion trans : comprador.getTransacciones()) {
                System.out.println("Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) +
                                   ", Pieza: " + trans.getPieza().getNombre() +
                                   ", Monto: " + trans.getMonto());
            }
        } else {
            System.out.println("No hay transacciones registradas para este comprador o el comprador no existe.");
        }
    }
    
    
    
    
    
    private void verCatalogo(Scanner scanner) {
        System.out.println("Catálogo de Piezas:");
        
    }

    private void verCompras(Scanner scanner) {
        System.out.println("Mis Compras:");
     
    }
    
    
    
    private void agregarPiezaEnConsignacion(Scanner scanner) {
        System.out.println("Agregar nueva pieza en consignación:");
        System.out.print("Ingrese ID: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese altura: ");
        double altura = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese anchura: ");
        double anchura = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese profundidad: ");
        double profundidad = Double.parseDouble(scanner.nextLine());
        System.out.print("Ingrese peso: ");
        double peso = Double.parseDouble(scanner.nextLine());
        System.out.print("¿Está en exhibición? (si/no): ");
        boolean estaEnExhibicion = scanner.nextLine().trim().equalsIgnoreCase("si");
        System.out.print("Código identificador: ");
        String codigoIdentificador = scanner.nextLine();
        System.out.print("Año de creación: ");
        int anoCreacion = Integer.parseInt(scanner.nextLine());
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio de venta (estimado): ");
        double precioVenta = Double.parseDouble(scanner.nextLine());
        System.out.print("Fecha de venta (si ya se vendió, de lo contrario dejar en blanco): ");
        String fechaVenta = scanner.nextLine();
        System.out.print("¿Está en consignación? (si/no): ");
        boolean enConsignacion = scanner.nextLine().trim().equalsIgnoreCase("si");
        System.out.print("Nombre del propietario real: ");
        String propietarioReal = scanner.nextLine();
        System.out.print("Fecha fin de consignación (formato YYYY-MM-DD): ");
        String fechaFinConsignacion = scanner.nextLine();

        pieza nuevaPieza = new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                                     codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta,
                                     fechaVenta, enConsignacion, propietarioReal, fechaFinConsignacion, fechaFinConsignacion);

        if (galeria.agregarPiezaEnConsignacion(nuevaPieza, usuarioLogueado)) {
            System.out.println("Pieza agregada correctamente en consignación.");
        } else {
            System.out.println("Error al agregar la pieza, verifique sus permisos.");
        }
    }
    
    
    private void registrarVentaPieza(Scanner scanner) {
        System.out.print("Ingrese el ID de la pieza que se vendió: ");
        String idPieza = scanner.nextLine();
        System.out.print("Ingrese el precio de venta: ");
        double precioVenta;
        try {
            precioVenta = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido para el precio.");
            return;
        }
        System.out.print("Ingrese la fecha de venta (formato YYYY-MM-DD): ");
        String fechaVenta = scanner.nextLine();

        if (galeria.registrarVentaPieza(idPieza, precioVenta, fechaVenta, usuarioLogueado)) {
            System.out.println("La venta de la pieza ha sido registrada con éxito.");
        } else {
            System.out.println("No se pudo registrar la venta. Verifique que el ID de la pieza sea correcto y que aún esté disponible para la venta.");
        }
    }
    
    
    private void comprarPiezaPorValorFijo(Scanner scanner) {
        if (usuarioLogueado instanceof comprador) {
            System.out.print("Ingrese el ID de la pieza a comprar: ");
            String idPieza = scanner.nextLine();
            if (galeria.comprarPiezaPorValorFijo(idPieza, (comprador) usuarioLogueado)) {
                System.out.println("Oferta enviada. La pieza está bloqueada hasta la verificación.");
            } else {
                System.out.println("No se pudo enviar la oferta. La pieza no está disponible o está bloqueada.");
            }
        } else {
            System.out.println("No tiene permisos para comprar piezas.");
        }
    }

    private void verificarCompraPieza(Scanner scanner) {
        if (usuarioLogueado.esAdministrador()) {
            System.out.print("Ingrese el ID de la pieza a verificar: ");
            String idPieza = scanner.nextLine();
            System.out.print("Ingrese el nombre del comprador: ");
            String nombreComprador = scanner.nextLine();
            usuario compradorUsuario = galeria.getUsuario(nombreComprador);
            if (compradorUsuario instanceof comprador) {
                comprador comprador = (comprador) compradorUsuario;
                galeria.verificarYConfirmarCompra(idPieza, usuarioLogueado, comprador);
                System.out.println("Compra verificada y confirmada.");
            } else {
                System.out.println("Comprador no encontrado o no tiene permisos de comprador.");
            }
        } else {
            System.out.println("No tiene permisos para verificar compras.");
        }
    }
    


    private void participarEnSubasta(Scanner scanner) {
        if (usuarioLogueado instanceof comprador) {
            System.out.print("Ingrese el ID de la subasta: ");
            String idSubasta = scanner.nextLine();
            System.out.print("Ingrese el valor de la oferta: ");
            double valorOferta = Double.parseDouble(scanner.nextLine());
            subasta subasta = galeria.buscarSubastaPorId(idSubasta);
            if (subasta != null && subasta.isEstaActiva()) {
                oferta oferta = new oferta(valorOferta, new Date(), (comprador) usuarioLogueado, subasta.getPieza());
                System.out.print("Ingrese el nombre del cajero: ");
                String nombreCajero = scanner.nextLine();
                usuario cajeroUsuario = galeria.getUsuario(nombreCajero);
                if (cajeroUsuario != null && cajeroUsuario.esCajero()) {
                    cajero caj = (cajero) cajeroUsuario;
                    galeria.registrarOfertaEnSubasta(idSubasta, oferta, caj);
                    System.out.println("Oferta registrada en la subasta.");
                } else {
                    System.out.println("Cajero no encontrado o no tiene permisos de cajero.");
                }
            } else {
                System.out.println("Subasta no encontrada o no activa.");
            }
        } else {
            System.out.println("No tiene permisos para participar en subastas.");
        }
    }

    
    
    
    
    
    
    private void crearSubasta(Scanner scanner) {
        System.out.print("Ingrese el ID de la subasta: ");
        String idSubasta = scanner.nextLine();
        System.out.print("Ingrese el ID de la pieza a subastar: ");
        String idPieza = scanner.nextLine();
        pieza pieza = galeria.buscarPiezaPorId(idPieza);
        if (pieza != null) {
            System.out.print("Ingrese el valor inicial de la subasta: ");
            double valorInicial = Double.parseDouble(scanner.nextLine());
            System.out.print("Ingrese el valor mínimo de la subasta: ");
            double valorMinimo = Double.parseDouble(scanner.nextLine());

            subasta nuevaSubasta = new subasta(idSubasta, pieza, valorInicial, valorMinimo);
            if (galeria.agregarSubasta(nuevaSubasta)) {
                System.out.println("Subasta creada correctamente.");
            } else {
                System.out.println("No se pudo crear la subasta. ID de subasta duplicado.");
            }
        } else {
            System.out.println("Pieza no encontrada.");
        }
    }
    
    private void activarSubasta(Scanner scanner) {
        System.out.print("Ingrese el ID de la subasta: ");
        String idSubasta = scanner.nextLine();
        galeria.activarSubasta(idSubasta);
    }
    
 
    
    
}    

    
   



