package interfaz;

import logica.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import logica.*;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.UUID;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class GaleriaGUI extends JFrame {
    private galeria galeria;
    private usuario usuarioLogueado;
    private oferta ofertaGanadoraSeleccionada;
    private String idSubastaSeleccionada;
    private JPanel comprasPanel;
    public GaleriaGUI() {
        galeria = new galeria();
        initialize();
    }

    private void initialize() {
        setTitle("Galería de Arte");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.cyan);
        add(mainPanel);
        comprasPanel = new JPanel();
        comprasPanel.setLayout(new BoxLayout(comprasPanel, BoxLayout.Y_AXIS));
        

        // Imagen redimensionada
        JLabel imageLabel = new JLabel(new ImageIcon(new ImageIcon("data/imagenes/logo.png").getImage().getScaledInstance(300, 330, Image.SCALE_SMOOTH)));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.NORTH);

        // Panel central para los campos de texto y botones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.cyan);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("Iniciar Sesión");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField usuarioField = new JTextField(15);
        centerPanel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passwordField = new JPasswordField(15);
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginButton = new JButton("Entrar");
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = usuarioField.getText();
                String password = new String(passwordField.getPassword());
                usuarioLogueado = galeria.verificarCredenciales(nombre, password);
                if (usuarioLogueado != null) {
                    mostrarMenu();
                } else {
                    JOptionPane.showMessageDialog(GaleriaGUI.this, "Credenciales incorrectas o usuario no encontrado.");
                }
            }
        });
        centerPanel.add(loginButton, gbc);

        gbc.gridy++;
        JButton registerButton = new JButton("Registrarse");
        registerButton.setBackground(Color.DARK_GRAY);
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarRegistroDialog();
            }
        });
        centerPanel.add(registerButton, gbc);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
    }

    private void mostrarRegistroDialog() {
        JTextField nombreField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        JTextField emailField = new JTextField(15);
        JTextField telefonoField = new JTextField(15);
        JTextField direccionField = new JTextField(15);
        JTextField ciudadField = new JTextField(15);
        JTextField codigoPostalField = new JTextField(15);
        JTextField paisField = new JTextField(15);
        JCheckBox cajeroCheckBox = new JCheckBox("Cajero");
        JCheckBox compradorCheckBox = new JCheckBox("Comprador");
        JCheckBox adminCheckBox = new JCheckBox("Administrador");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        panel.add(createLabeledField("Nombre de usuario", nombreField));
        panel.add(createLabeledField("Contraseña", passwordField));
        panel.add(createLabeledField("Email", emailField));
        panel.add(createLabeledField("Teléfono", telefonoField));
        panel.add(createLabeledField("Dirección", direccionField));
        panel.add(createLabeledField("Ciudad", ciudadField));
        panel.add(createLabeledField("Código Postal", codigoPostalField));
        panel.add(createLabeledField("País", paisField));

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.X_AXIS));
        checkBoxPanel.setBackground(Color.WHITE);
        checkBoxPanel.add(cajeroCheckBox);
        checkBoxPanel.add(compradorCheckBox);
        checkBoxPanel.add(adminCheckBox);

        panel.add(checkBoxPanel);

        int option = JOptionPane.showConfirmDialog(this, panel, "Registrarse", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String telefono = telefonoField.getText();
            String direccion = direccionField.getText();
            String ciudad = ciudadField.getText();
            String codigoPostal = codigoPostalField.getText();
            String pais = paisField.getText();
            boolean esCajero = cajeroCheckBox.isSelected();
            boolean esComprador = compradorCheckBox.isSelected();
            boolean esAdministrador = adminCheckBox.isSelected();

            usuario newUser = new usuario(nombre, password, email, telefono, direccion, ciudad, codigoPostal, pais, esCajero, esComprador, esAdministrador);
            galeria.agregarUsuario(newUser);
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
        }
    }

    private JPanel createLabeledField(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void mostrarMenu() {
        if (usuarioLogueado.esCajero()) {
            mostrarMenuCajero();
        } else if (usuarioLogueado.esComprador()) {
            mostrarMenuComprador();
        } else if (usuarioLogueado.esAdministrador()) {
            mostrarMenuAdministrador();
        }
    }
    
    
    
   // MENU COMPRADOR //

    private void mostrarMenuComprador() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // Título y bienvenida
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);
        JLabel welcomeLabel = new JLabel("Hola, "  + usuarioLogueado.getNombre() +" ¿Qué quieres hacer hoy?");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);
        
        JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon("data/imagenes/logo.png").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(iconLabel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);

        // Menú de botones
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 100, 100));
        menuPanel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Menú");
        border.setTitleFont(new Font("Arial", Font.BOLD, 30));
        menuPanel.setBorder(border);
        
        
        menuPanel.add(crearBotonMenu("Ver catálogo de piezas", e -> verCatalogo()));
        menuPanel.add(crearBotonMenu("Ver mis compras", e -> verCompras()));
        menuPanel.add(crearBotonMenu("Mostrar historia de una pieza", e -> mostrarHistoriaPiezaComprador()));
        menuPanel.add(crearBotonMenu("Mostrar historia de un artista", e -> mostrarHistoriaArtistaComprador()));
        menuPanel.add(crearBotonMenu("Participar en una subasta", e -> participarEnSubasta()));
        menuPanel.add(crearBotonMenu("Comprar pieza por valor fijo", e -> comprarPiezaPorValorFijo()));
        menuPanel.add(crearBotonMenu("Agregar datos de pago", e -> agregarDatosPago()));
        
        panel.add(menuPanel, BorderLayout.CENTER);

        // Botón de salida
        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(GaleriaGUI.this, "¿Estás seguro de que deseas cerrar la sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioLogueado = null;
                getContentPane().removeAll();
                initialize();
                validate();
                repaint();
            }
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(exitButton);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);

        validate();
        repaint();
    }

    private void verCatalogo() {
        List<pieza> piezas = galeria.getPiezas();
        if (piezas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay piezas disponibles en el catálogo.");
            return;
        }

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Crear el panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("CATÁLOGO DE PIEZAS");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Crear el panel central con la imagen y los detalles de la pieza
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        centerPanel.add(imageLabel, gbc);

        JLabel nombreLabel = new JLabel();
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(nombreLabel, gbc);

        JLabel autorLabel = new JLabel();
        autorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        centerPanel.add(autorLabel, gbc);

        JLabel anoCreacionLabel = new JLabel();
        anoCreacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 2;
        centerPanel.add(anoCreacionLabel, gbc);

        JLabel descripcionLabel = new JLabel();
        descripcionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 3;
        centerPanel.add(descripcionLabel, gbc);

        JLabel precioVentaLabel = new JLabel();
        precioVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        precioVentaLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        centerPanel.add(precioVentaLabel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel de navegación
        JPanel navPanel = new JPanel(new FlowLayout());
        JButton prevButton = new JButton("ANTERIOR");
        JButton nextButton = new JButton("SIGUIENTE");
        navPanel.add(prevButton);
        navPanel.add(nextButton);
        mainPanel.add(navPanel, BorderLayout.SOUTH);

        // Botón para volver a la página principal
        JButton homeButton = new JButton("VOLVER");
        homeButton.addActionListener(e -> {
            setContentPane(new JPanel());
            mostrarMenuComprador();
        });
        navPanel.add(homeButton);

        // Configurar el JFrame
        setContentPane(mainPanel);
        validate();
        repaint();

        // Controlador para mostrar la información de una pieza
        int[] currentIndex = {0};

        ActionListener updateDetails = e -> {
            if (currentIndex[0] < 0) {
                currentIndex[0] = 0;
            } else if (currentIndex[0] >= piezas.size()) {
                currentIndex[0] = piezas.size() - 1;
            }

            pieza currentPieza = piezas.get(currentIndex[0]);
            nombreLabel.setText("Nombre: " + currentPieza.getNombre());
            autorLabel.setText("Autor: " + currentPieza.getAutor());
            anoCreacionLabel.setText("Año de Creación: " + currentPieza.getAnoCreacion());
            descripcionLabel.setText("<html>Descripción: " + currentPieza.getDescripcion() + "</html>");
            precioVentaLabel.setText("Precio de Venta: $" + currentPieza.getPrecioVenta());

            // Cargar la imagen de la pieza
            String imagePath = currentPieza.getImagenRuta(); // Usar la ruta de la imagen almacenada en la pieza
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            imageLabel.setIcon(imageIcon);
        };

        prevButton.addActionListener(e -> {
            currentIndex[0]--;
            updateDetails.actionPerformed(null);
        });

        nextButton.addActionListener(e -> {
            currentIndex[0]++;
            updateDetails.actionPerformed(null);
        });

        updateDetails.actionPerformed(null); // Mostrar la primera pieza al iniciar
    }

    private void verCompras() {
        List<transaccion> compras = usuarioLogueado.getHistorialCompras();
        if (compras.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes compras registradas.");
            return;
        }

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Crear el panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("MIS COMPRAS");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Crear el panel central con la lista de compras
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245));

        for (transaccion trans : compras) {
            pieza p = trans.getPieza();
            JPanel compraPanel = new JPanel();
            compraPanel.setLayout(new BoxLayout(compraPanel, BoxLayout.Y_AXIS));
            compraPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            compraPanel.setBackground(Color.WHITE);
            compraPanel.setPreferredSize(new Dimension(800, 150));

            JLabel nombreLabel = new JLabel("Nombre: " + p.getNombre());
            nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel autorLabel = new JLabel("Autor: " + p.getAutor());
            autorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            JLabel fechaCompraLabel = new JLabel("Fecha de Compra: " + trans.getFecha());
            fechaCompraLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            JLabel precioLabel = new JLabel("Precio: $" + trans.getMonto());
            precioLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            compraPanel.add(nombreLabel);
            compraPanel.add(autorLabel);
            compraPanel.add(fechaCompraLabel);
            compraPanel.add(precioLabel);

            centerPanel.add(compraPanel);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre compras
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para volver a la página principal
        JButton homeButton = new JButton("VOLVER");
        homeButton.addActionListener(e -> {
            setContentPane(new JPanel());
            mostrarMenuComprador();
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(homeButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Configurar el JFrame
        setContentPane(mainPanel);
        validate();
        repaint();
    }
    private void mostrarHistoriaPiezaComprador() {
    	 String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
         
         if (idPieza == null || idPieza.trim().isEmpty()) {
             JOptionPane.showMessageDialog(this, "ID de pieza no puede estar vacío.");
             return;
         }
         
         pieza pieza = galeria.buscarPiezaPorId(idPieza);
         
         if (pieza == null) {
             JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
             return;
         }
         
         // Crear el panel principal
         JPanel mainPanel = new JPanel(new BorderLayout());
         mainPanel.setBackground(new Color(245, 245, 245));

         // Crear el panel de cabecera
         JPanel headerPanel = new JPanel();
         headerPanel.setBackground(new Color(0, 102, 204));
         JLabel headerLabel = new JLabel("HISTORIA DE LA PIEZA");
         headerLabel.setForeground(Color.WHITE);
         headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
         headerPanel.add(headerLabel);
         mainPanel.add(headerPanel, BorderLayout.NORTH);

         // Crear el panel central con los detalles de la pieza
         JPanel centerPanel = new JPanel(new GridBagLayout());
         centerPanel.setBackground(new Color(245, 245, 245));
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10);
         gbc.anchor = GridBagConstraints.WEST;

         JLabel nombreLabel = new JLabel("Nombre: " + pieza.getNombre());
         nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridx = 0;
         gbc.gridy = 0;
         centerPanel.add(nombreLabel, gbc);

         JLabel autorLabel = new JLabel("Autor: " + pieza.getAutor());
         autorLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(autorLabel, gbc);

         JLabel anoCreacionLabel = new JLabel("Año de Creación: " + pieza.getAnoCreacion());
         anoCreacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(anoCreacionLabel, gbc);

         JLabel descripcionLabel = new JLabel("<html>Descripción: " + pieza.getDescripcion() + "</html>");
         descripcionLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(descripcionLabel, gbc);

         JLabel precioVentaLabel = new JLabel("Precio de Venta: $" + pieza.getPrecioVenta());
         precioVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
         precioVentaLabel.setForeground(Color.RED);
         gbc.gridy++;
         centerPanel.add(precioVentaLabel, gbc);

         JLabel enExhibicionLabel = new JLabel("En Exhibición: " + (pieza.isEstaEnExhibicion() ? "Sí" : "No"));
         enExhibicionLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(enExhibicionLabel, gbc);

         JLabel enConsignacionLabel = new JLabel("En Consignación: " + (pieza.isenConsignacion() ? "Sí" : "No"));
         enConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(enConsignacionLabel, gbc);

         JLabel propietarioRealLabel = new JLabel("Propietario Real: " + pieza.getPropietarioReal());
         propietarioRealLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(propietarioRealLabel, gbc);

         JLabel fechaFinConsignacionLabel = new JLabel("Fecha Fin de Consignación: " + pieza.getFechaFinConsignacion());
         fechaFinConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(fechaFinConsignacionLabel, gbc);

         JLabel fechaVentaLabel = new JLabel("Fecha de Venta: " + pieza.getFechaVenta());
         fechaVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
         gbc.gridy++;
         centerPanel.add(fechaVentaLabel, gbc);

         JLabel imagenLabel = new JLabel();
         imagenLabel.setPreferredSize(new Dimension(300, 300));
         String imagePath = pieza.getImagenRuta();
         ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
         imagenLabel.setIcon(imageIcon);
         gbc.gridx = 1;
         gbc.gridy = 0;
         gbc.gridheight = 10;
         centerPanel.add(imagenLabel, gbc);

         // Panel de historial de transacciones
         JPanel historialPanel = new JPanel(new GridLayout(0, 1));
         historialPanel.setBackground(new Color(245, 245, 245));
         historialPanel.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
         List<transaccion> historialTransacciones = galeria.obtenerHistorialTransacciones(idPieza);
         for (transaccion trans : historialTransacciones) {
             JLabel transLabel = new JLabel("ID Transacción: " + trans.getId() +
                                             ", Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) +
                                             ", Comprador: " + trans.getComprador().getNombre() +
                                             ", Monto: $" + trans.getMonto());
             historialPanel.add(transLabel);
         }
         gbc.gridx = 0;
         gbc.gridy = 10;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.fill = GridBagConstraints.BOTH;
         centerPanel.add(historialPanel, gbc);

         mainPanel.add(centerPanel, BorderLayout.CENTER);

         // Botón para volver a la página principal
         JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         JButton volverButton = new JButton("Volver");
         volverButton.addActionListener(e -> {
             setContentPane(new JPanel());
             mostrarMenuComprador();
         });
         bottomPanel.add(volverButton);
         mainPanel.add(bottomPanel, BorderLayout.SOUTH);

         // Configurar el JFrame
         setContentPane(mainPanel);
         validate();
         repaint();
     }
    
    
    private void mostrarHistoriaArtistaComprador() {
    String nombreArtista = JOptionPane.showInputDialog(this, "Ingrese el nombre del artista:");

    if (nombreArtista == null || nombreArtista.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre del artista no puede estar vacío.");
        return;
    }

    List<pieza> obrasDelArtista = new ArrayList<>();
    for (pieza p : galeria.getPiezas()) {
        if (p.getAutor().equalsIgnoreCase(nombreArtista)) {
            obrasDelArtista.add(p);
        }
    }

    if (obrasDelArtista.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron obras para el artista especificado.");
        return;
    }

    // Crear el panel principal
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(245, 245, 245));

    // Crear el panel de cabecera
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("HISTORIA DEL ARTISTA");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    mainPanel.add(headerPanel, BorderLayout.NORTH);

    // Crear el panel central con los detalles del artista
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(245, 245, 245));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;

    JLabel nombreLabel = new JLabel("Nombre: " + nombreArtista);
    nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(nombreLabel, gbc);

    // Panel de obras del artista
    JPanel obrasPanel = new JPanel(new GridLayout(0, 1));
    obrasPanel.setBackground(new Color(245, 245, 245));
    obrasPanel.setBorder(BorderFactory.createTitledBorder("Obras del Artista"));
    for (pieza obra : obrasDelArtista) {
        JLabel obraLabel = new JLabel("Nombre: " + obra.getNombre() +
                                      ", Año de Creación: " + obra.getAnoCreacion() +
                                      ", Descripción: " + obra.getDescripcion());
        obrasPanel.add(obraLabel);
    }
    gbc.gridy++;
    gbc.fill = GridBagConstraints.BOTH;
    centerPanel.add(obrasPanel, gbc);

    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // Botón para volver a la página principal
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton volverButton = new JButton("Volver");
    volverButton.addActionListener(e -> {
        setContentPane(new JPanel());
        mostrarMenuComprador();
    });
    bottomPanel.add(volverButton);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    // Configurar el JFrame
    setContentPane(mainPanel);
    validate();
    repaint();
}
    
    private void participarEnSubasta() {
        if (usuarioLogueado instanceof comprador) {
            String idSubasta = JOptionPane.showInputDialog("Ingrese el ID de la subasta:");
            String valorOfertaStr = JOptionPane.showInputDialog("Ingrese el valor de la oferta:");
            double valorOferta = Double.parseDouble(valorOfertaStr);
            subasta subasta = galeria.buscarSubastaPorId(idSubasta);
            if (subasta != null && subasta.isEstaActiva()) {
                oferta oferta = new oferta(valorOferta, new Date(), (comprador) usuarioLogueado, subasta.getPieza());
                String nombreCajero = JOptionPane.showInputDialog("Ingrese el nombre del cajero:");
                usuario cajeroUsuario = galeria.getUsuario(nombreCajero);
                if (cajeroUsuario != null && cajeroUsuario.esCajero()) {
                    cajero caj = (cajero) cajeroUsuario;
                    galeria.registrarOfertaEnSubasta(idSubasta, oferta, caj);
                    JOptionPane.showMessageDialog(this, "Oferta registrada en la subasta.");
                } else {
                    JOptionPane.showMessageDialog(this, "Cajero no encontrado o no tiene permisos de cajero.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Subasta no encontrada o no activa.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tiene permisos para participar en subastas.");
        }
    }

    private void comprarPiezaPorValorFijo() {
        if (usuarioLogueado instanceof comprador) {
            String idPieza = JOptionPane.showInputDialog("Ingrese el ID de la pieza a comprar:");
            if (galeria.comprarPiezaPorValorFijo(idPieza, (comprador) usuarioLogueado)) {
                JOptionPane.showMessageDialog(this, "Oferta enviada. La pieza está bloqueada hasta la verificación.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo enviar la oferta. La pieza no está disponible o está bloqueada.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tiene permisos para comprar piezas.");
        }
    }
    
    private void agregarDatosPago() {
        JTextField nombreTitularField = new JTextField(15);
        JTextField numeroTarjetaField = new JTextField(15);
        JTextField fechaExpiracionField = new JTextField(5);
        JTextField cvvField = new JTextField(3);
        JTextField cupoMaximoField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("Nombre del titular", nombreTitularField));
        panel.add(createLabeledField("Número de tarjeta", numeroTarjetaField));
        panel.add(createLabeledField("Fecha de expiración (MM/AA)", fechaExpiracionField));
        panel.add(createLabeledField("CVV", cvvField));
        panel.add(createLabeledField("Cupo máximo", cupoMaximoField));

        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Datos de Pago", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nombreTitular = nombreTitularField.getText();
            String numeroTarjeta = numeroTarjetaField.getText();
            String fechaExpiracion = fechaExpiracionField.getText();
            String cvv = cvvField.getText();
            double cupoMaximo = Double.parseDouble(cupoMaximoField.getText());

            InformacionPago informacionPago = new InformacionPago(nombreTitular, numeroTarjeta, fechaExpiracion, cvv, cupoMaximo, cvv, null, null);
            ((comprador) usuarioLogueado).setInformacionPago(informacionPago);
            ((comprador) usuarioLogueado).setCupoMaximo(cupoMaximo);

            galeria.guardarUsuario();
            JOptionPane.showMessageDialog(this, "Datos de pago agregados con éxito.");
        }
    }



    
    // FIN MENU COMPRADOR //  
    
    
    
    
    
    // INICIO MENU ADMINISTRADOR//    
    private void mostrarMenuAdministrador() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        // Título y bienvenida
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);
        JLabel welcomeLabel = new JLabel("Hola, administrador ¿Qué quieres hacer hoy?");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon("data/imagenes/logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(iconLabel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

        // Menú de botones
        JPanel menuPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        menuPanel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Menú");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16)); // Configurar la fuente del borde
        menuPanel.setBorder(border);

        menuPanel.add(crearBotonMenu("Administrar Piezas", e -> administrarPieza()));
        menuPanel.add(crearBotonMenu("Administrar Subastas", e -> administrarSubasta()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de un	a Pieza", e -> mostrarHistoriaPieza()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de un Artista", e -> mostrarHistoriaArtista()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de un Comprador", e -> mostrarHistoriaComprador()));
        menuPanel.add(crearBotonMenu("Registrar Nueva Pieza en Consignación", e -> registrarPiezaEnConsignacion()));
       
       
  
        menuPanel.add(crearBotonMenu("Crear Subasta", e -> crearSubasta()));
        menuPanel.add(crearBotonMenu("Activar Subasta", e -> activarSubasta()));
        menuPanel.add(crearBotonMenu("Mostrar Gráfico de Ventas", e -> mostrarGraficoDeVentas()));

        panel.add(menuPanel, BorderLayout.CENTER);

        // Botón de salir
        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar la sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioLogueado = null;
                getContentPane().removeAll();
                initialize();
                validate();
                repaint();
            }
        });

        panel.add(exitButton, BorderLayout.SOUTH);

        validate();
        repaint();
    }

    private void administrarPieza() {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza a administrar:");
        pieza piezaSeleccionada = galeria.buscarPiezaPorId(idPieza);

        if (piezaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
            return;
        }

        JTextField idField = new JTextField(piezaSeleccionada.getId(), 15);
        JTextField alturaField = new JTextField(String.valueOf(piezaSeleccionada.getAltura()), 5);
        JTextField anchuraField = new JTextField(String.valueOf(piezaSeleccionada.getAnchura()), 5);
        JTextField profundidadField = new JTextField(String.valueOf(piezaSeleccionada.getProfundidad()), 5);
        JTextField pesoField = new JTextField(String.valueOf(piezaSeleccionada.getPeso()), 5);
        JTextField nombreField = new JTextField(piezaSeleccionada.getNombre(), 15);
        JCheckBox enExhibicionCheck = new JCheckBox("En exhibición", piezaSeleccionada.isEstaEnExhibicion());
        JTextField codigoIdentificadorField = new JTextField(piezaSeleccionada.getCodigoIdentificador(), 15);
        JTextField anoCreacionField = new JTextField(String.valueOf(piezaSeleccionada.getAnoCreacion()), 5);
        JTextField autorField = new JTextField(piezaSeleccionada.getAutor(), 15);
        JTextField descripcionField = new JTextField(piezaSeleccionada.getDescripcion(), 20);
        JTextField precioVentaField = new JTextField(String.valueOf(piezaSeleccionada.getPrecioVenta()), 10);
        JTextField fechaVentaField = new JTextField(piezaSeleccionada.getFechaVenta(), 10);
        JCheckBox enConsignacionCheck = new JCheckBox("En consignación", piezaSeleccionada.isenConsignacion());
        JTextField propietarioRealField = new JTextField(piezaSeleccionada.getPropietarioReal(), 15);
        JTextField fechaFinConsignacionField = new JTextField(piezaSeleccionada.getFechaFinConsignacion(), 10);

        JButton seleccionarImagenButton = new JButton("Seleccionar Imagen");
        JLabel imagenLabel = new JLabel(piezaSeleccionada.getImagenRuta());

        seleccionarImagenButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "gif"));
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imagenLabel.setText(file.getAbsolutePath());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("ID", idField));
        panel.add(createLabeledField("Altura", alturaField));
        panel.add(createLabeledField("Anchura", anchuraField));
        panel.add(createLabeledField("Profundidad", profundidadField));
        panel.add(createLabeledField("Peso", pesoField));
        panel.add(createLabeledField("Nombre", nombreField));
        panel.add(enExhibicionCheck);
        panel.add(createLabeledField("Código Identificador", codigoIdentificadorField));
        panel.add(createLabeledField("Año de Creación", anoCreacionField));
        panel.add(createLabeledField("Autor", autorField));
        panel.add(createLabeledField("Descripción", descripcionField));
        panel.add(createLabeledField("Precio de Venta", precioVentaField));
        panel.add(createLabeledField("Fecha de Venta", fechaVentaField));
        panel.add(enConsignacionCheck);
        panel.add(createLabeledField("Propietario Real", propietarioRealField));
        panel.add(createLabeledField("Fecha Fin de Consignación", fechaFinConsignacionField));
        panel.add(seleccionarImagenButton);
        panel.add(imagenLabel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Pieza", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            double altura = Double.parseDouble(alturaField.getText());
            double anchura = Double.parseDouble(anchuraField.getText());
            double profundidad = Double.parseDouble(profundidadField.getText());
            double peso = Double.parseDouble(pesoField.getText());
            String nombre = nombreField.getText();
            boolean estaEnExhibicion = enExhibicionCheck.isSelected();
            String codigoIdentificador = codigoIdentificadorField.getText();
            int anoCreacion = Integer.parseInt(anoCreacionField.getText());
            String autor = autorField.getText();
            String descripcion = descripcionField.getText();
            double precioVenta = Double.parseDouble(precioVentaField.getText());
            String fechaVenta = fechaVentaField.getText();
            boolean enConsignacion = enConsignacionCheck.isSelected();
            String propietarioReal = propietarioRealField.getText();
            String fechaFinConsignacion = fechaFinConsignacionField.getText();
            String imagenRuta = imagenLabel.getText();

            pieza piezaActualizada = new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                                              codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta,
                                              fechaVenta, enConsignacion, propietarioReal, fechaFinConsignacion, imagenRuta);
            galeria.actualizarPieza(piezaActualizada);
            galeria.guardarDatos();
            JOptionPane.showMessageDialog(this, "Pieza actualizada con éxito.");
        }
    }
    private void administrarSubasta() {
        String idSubasta = JOptionPane.showInputDialog(this, "Ingrese el ID de la subasta:");

        if (idSubasta != null && !idSubasta.isEmpty()) {
            List<oferta> ofertas = galeria.obtenerOfertasDeSubasta(idSubasta);

            if (ofertas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ofertas para esta subasta.");
                return;
            }

            // Mostrar las ofertas en un cuadro de diálogo
            String[] opciones = new String[ofertas.size()];
            for (int i = 0; i < ofertas.size(); i++) {
                oferta o = ofertas.get(i);
                opciones[i] = "Comprador: " + o.getComprador().getNombre() + ", Oferta: " + o.getValorOferta();
            }

            String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona la oferta ganadora:",
                "Ofertas de Subasta",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (seleccion != null) {
                int indiceSeleccionado = -1;
                for (int i = 0; i < opciones.length; i++) {
                    if (opciones[i].equals(seleccion)) {
                        indiceSeleccionado = i;
                        break;
                    }
                }

                if (indiceSeleccionado != -1) {
                    ofertaGanadoraSeleccionada = ofertas.get(indiceSeleccionado);
                    idSubastaSeleccionada = idSubasta;
                    mostrarPanelProcesarPagos();
                } else {
                    JOptionPane.showMessageDialog(this, "Selección inválida.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID de subasta inválido.");
        }
    }

    private void mostrarPanelProcesarPagos() {
        // Verificar que el usuario logueado es un administrador o un cajero
        if (!usuarioLogueado.esAdministrador() && !usuarioLogueado.esCajero()) {
            JOptionPane.showMessageDialog(this, "Solo los administradores y cajeros pueden procesar pagos.");
            return;
        }

        // Obtener el comprador de la oferta ganadora seleccionada
        comprador compradorSeleccionado = ofertaGanadoraSeleccionada.getComprador();
        pieza piezaSeleccionada = ofertaGanadoraSeleccionada.getPieza(); 

        // Verificar si el comprador tiene información de pago registrada
        InformacionPago infoPagoRegistrada = compradorSeleccionado.getInformacionPago();
        if (infoPagoRegistrada == null) {
            JOptionPane.showMessageDialog(this, "No se ha registrado ninguna tarjeta de pago para este comprador.");
            return;
        }

        
        JTextField nombreTitularField = new JTextField(infoPagoRegistrada.getNombreTitular(), 15);
        JTextField numeroTarjetaField = new JTextField(infoPagoRegistrada.getNumeroTarjeta(), 15);
        JTextField fechaExpiracionField = new JTextField(infoPagoRegistrada.getFechaExpiracion(), 5);
        JTextField cvvField = new JTextField(infoPagoRegistrada.getCvv(), 3);
        JTextField montoField = new JTextField(String.valueOf(ofertaGanadoraSeleccionada.getValorOferta()), 10);
        JComboBox<String> pasarelaComboBox = new JComboBox<>(galeria.getPasarelasPagoDisponibles().toArray(new String[0]));
        JLabel tipoTarjetaLabel = new JLabel("Tipo de Tarjeta: Desconocido");

        numeroTarjetaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarTipoTarjeta();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarTipoTarjeta();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarTipoTarjeta();
            }

            private void actualizarTipoTarjeta() {
                String numeroTarjeta = numeroTarjetaField.getText();
                InformacionPago infoPago = new InformacionPago("", numeroTarjeta, "", "", 0, "", null, null);
                tipoTarjetaLabel.setText("Tipo de Tarjeta: " + infoPago.getTipoTarjeta());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("Nombre del titular", nombreTitularField));
        panel.add(createLabeledField("Número de tarjeta", numeroTarjetaField));
        panel.add(tipoTarjetaLabel);
        panel.add(createLabeledField("Fecha de expiración (MM/AA)", fechaExpiracionField));
        panel.add(createLabeledField("CVV", cvvField));
        panel.add(createLabeledField("Monto", montoField));
        panel.add(createLabeledField("Pasarela de Pago", pasarelaComboBox));

        int result = JOptionPane.showConfirmDialog(null, panel, "Procesar Pago", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String nombreTitular = nombreTitularField.getText();
            String numeroTarjeta = numeroTarjetaField.getText();
            String fechaExpiracion = fechaExpiracionField.getText();
            String cvv = cvvField.getText();
            double monto = Double.parseDouble(montoField.getText());
            String pasarelaSeleccionada = (String) pasarelaComboBox.getSelectedItem();
            String idTransaccion = UUID.randomUUID().toString();

            // Crear la instancia de InformacionPago
            InformacionPago informacionPago = new InformacionPago(
                nombreTitular, 
                numeroTarjeta, 
                fechaExpiracion, 
                cvv, 
                monto, 
                idTransaccion, 
                compradorSeleccionado, 
                piezaSeleccionada
            );

            // Procesar el pago
            if (galeria.procesarPago(pasarelaSeleccionada, informacionPago, compradorSeleccionado)) {
                galeria.seleccionarGanadorSubasta(idSubastaSeleccionada, ofertaGanadoraSeleccionada);
                JOptionPane.showMessageDialog(this, "Pago procesado con éxito. La subasta ha sido cerrada y la pieza transferida.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar el pago. Verifique los datos e intente nuevamente.");
            }
        }
    }


    
    
    
    
    
    







    
    
    
    

    private void mostrarHistoriaPieza() {
        String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
        
        if (idPieza == null || idPieza.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID de pieza no puede estar vacío.");
            return;
        }
        
        pieza pieza = galeria.buscarPiezaPorId(idPieza);
        
        if (pieza == null) {
            JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
            return;
        }
        
        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Crear el panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("HISTORIA DE LA PIEZA");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Crear el panel central con los detalles de la pieza
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nombreLabel = new JLabel("Nombre: " + pieza.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nombreLabel, gbc);

        JLabel autorLabel = new JLabel("Autor: " + pieza.getAutor());
        autorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(autorLabel, gbc);

        JLabel anoCreacionLabel = new JLabel("Año de Creación: " + pieza.getAnoCreacion());
        anoCreacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(anoCreacionLabel, gbc);

        JLabel descripcionLabel = new JLabel("<html>Descripción: " + pieza.getDescripcion() + "</html>");
        descripcionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(descripcionLabel, gbc);

        JLabel precioVentaLabel = new JLabel("Precio de Venta: $" + pieza.getPrecioVenta());
        precioVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        precioVentaLabel.setForeground(Color.RED);
        gbc.gridy++;
        centerPanel.add(precioVentaLabel, gbc);

        JLabel enExhibicionLabel = new JLabel("En Exhibición: " + (pieza.isEstaEnExhibicion() ? "Sí" : "No"));
        enExhibicionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(enExhibicionLabel, gbc);

        JLabel enConsignacionLabel = new JLabel("En Consignación: " + (pieza.isenConsignacion() ? "Sí" : "No"));
        enConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(enConsignacionLabel, gbc);

        JLabel propietarioRealLabel = new JLabel("Propietario Real: " + pieza.getPropietarioReal());
        propietarioRealLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(propietarioRealLabel, gbc);

        JLabel fechaFinConsignacionLabel = new JLabel("Fecha Fin de Consignación: " + pieza.getFechaFinConsignacion());
        fechaFinConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(fechaFinConsignacionLabel, gbc);

        JLabel fechaVentaLabel = new JLabel("Fecha de Venta: " + pieza.getFechaVenta());
        fechaVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(fechaVentaLabel, gbc);

        JLabel imagenLabel = new JLabel();
        imagenLabel.setPreferredSize(new Dimension(300, 300));
        String imagePath = pieza.getImagenRuta();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        imagenLabel.setIcon(imageIcon);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 10;
        centerPanel.add(imagenLabel, gbc);

        // Panel de historial de transacciones
        JPanel historialPanel = new JPanel(new GridLayout(0, 1));
        historialPanel.setBackground(new Color(245, 245, 245));
        historialPanel.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
        List<transaccion> historialTransacciones = galeria.obtenerHistorialTransacciones(idPieza);
        for (transaccion trans : historialTransacciones) {
            JLabel transLabel = new JLabel("ID Transacción: " + trans.getId() +
                                            ", Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) +
                                            ", Comprador: " + trans.getComprador().getNombre() +
                                            ", Monto: $" + trans.getMonto());
            historialPanel.add(transLabel);
        }
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(historialPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botón para volver a la página principal
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(e -> {
            setContentPane(new JPanel());
            mostrarMenuAdministrador();
        });
        bottomPanel.add(volverButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Configurar el JFrame
        setContentPane(mainPanel);
        validate();
        repaint();
    }
    private void mostrarHistoriaArtista() {
        String nombreArtista = JOptionPane.showInputDialog(this, "Ingrese el nombre del artista:");

        if (nombreArtista == null || nombreArtista.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del artista no puede estar vacío.");
            return;
        }

        List<pieza> obrasDelArtista = new ArrayList<>();
        for (pieza p : galeria.getPiezas()) {
            if (p.getAutor().equalsIgnoreCase(nombreArtista)) {
                obrasDelArtista.add(p);
            }
        }

        if (obrasDelArtista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron obras para el artista especificado.");
            return;
        }

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Crear el panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("HISTORIA DEL ARTISTA");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Crear el panel central con los detalles del artista
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nombreLabel = new JLabel("Nombre: " + nombreArtista);
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nombreLabel, gbc);

        // Panel de obras del artista
        JPanel obrasPanel = new JPanel(new GridLayout(0, 1));
        obrasPanel.setBackground(new Color(245, 245, 245));
        obrasPanel.setBorder(BorderFactory.createTitledBorder("Obras del Artista"));
        for (pieza obra : obrasDelArtista) {
            JLabel obraLabel = new JLabel("Nombre: " + obra.getNombre() +
                                          ", Año de Creación: " + obra.getAnoCreacion() +
                                          ", Descripción: " + obra.getDescripcion());
            obrasPanel.add(obraLabel);
        }
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(obrasPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botón para volver a la página principal
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(e -> {
            setContentPane(new JPanel());
            mostrarMenuAdministrador();
        });
        bottomPanel.add(volverButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Configurar el JFrame
        setContentPane(mainPanel);
        validate();
        repaint();
    }
    private void mostrarHistoriaComprador() {
        String nombreComprador = JOptionPane.showInputDialog(this, "Ingrese el nombre del comprador:");

        if (nombreComprador == null || nombreComprador.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del comprador no puede estar vacío.");
            return;
        }

        usuario comprador = galeria.getUsuario(nombreComprador);

        if (comprador == null || !comprador.esComprador()) {
            JOptionPane.showMessageDialog(this, "Comprador no encontrado o el usuario no es un comprador.");
            return;
        }

        // Crear el panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Crear el panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel headerLabel = new JLabel("HISTORIA DEL COMPRADOR");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Crear el panel central con los detalles del comprador
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nombreLabel = new JLabel("Nombre: " + comprador.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(nombreLabel, gbc);

        JLabel emailLabel = new JLabel("Email: " + comprador.getEmail());
        emailLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(emailLabel, gbc);

        JLabel telefonoLabel = new JLabel("Teléfono: " + comprador.getTelefono());
        telefonoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(telefonoLabel, gbc);

        JLabel direccionLabel = new JLabel("Dirección: " + comprador.getDireccion());
        direccionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(direccionLabel, gbc);

        JLabel ciudadLabel = new JLabel("Ciudad: " + comprador.getCiudad());
        ciudadLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(ciudadLabel, gbc);

        JLabel codigoPostalLabel = new JLabel("Código Postal: " + comprador.getCodigoPostal());
        codigoPostalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(codigoPostalLabel, gbc);

        JLabel paisLabel = new JLabel("País: " + comprador.getPais());
        paisLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy++;
        centerPanel.add(paisLabel, gbc);

        // Panel de piezas compradas
        JPanel piezasPanel = new JPanel(new GridLayout(0, 1));
        piezasPanel.setBackground(new Color(245, 245, 245));
        piezasPanel.setBorder(BorderFactory.createTitledBorder("Piezas Compradas"));
        for (pieza p : comprador.getPiezasCompradas()) {
            JLabel piezaLabel = new JLabel("Nombre: " + p.getNombre() +
                                          ", Año de Creación: " + p.getAnoCreacion() +
                                          ", Descripción: " + p.getDescripcion() +
                                          ", Precio de Venta: " + p.getPrecioVenta());
            piezasPanel.add(piezaLabel);
        }
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        centerPanel.add(piezasPanel, gbc);

        // Panel de historial de transacciones
        JPanel transaccionesPanel = new JPanel(new GridLayout(0, 1));
        transaccionesPanel.setBackground(new Color(245, 245, 245));
        transaccionesPanel.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
        for (transaccion t : comprador.getHistorialCompras()) {
            JLabel transaccionLabel = new JLabel("ID: " + t.getId() +
                                                 ", Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(t.getFecha()) +
                                                 ", Lugar: " + t.getLugar() +
                                                 ", Monto: " + t.getMonto() +
                                                 ", Pieza: " + t.getPieza().getNombre());
            transaccionesPanel.add(transaccionLabel);
        }
        gbc.gridy++;
        centerPanel.add(transaccionesPanel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Botón para volver a la página principal
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(e -> {
            setContentPane(new JPanel());
            mostrarMenuAdministrador();
        });
        bottomPanel.add(volverButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Configurar el JFrame
        setContentPane(mainPanel);
        validate();
        repaint();
    }
    
    
    
    private void actualizarCompras(comprador comprador) {
       
        comprasPanel.removeAll();

       
        List<transaccion> transacciones = comprador.getTransacciones();

        for (transaccion t : transacciones) {
            JPanel compraPanel = new JPanel();
            compraPanel.setLayout(new BoxLayout(compraPanel, BoxLayout.Y_AXIS));
            compraPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nombrePieza = new JLabel("Nombre: " + t.getPieza().getNombre());
            JLabel autorPieza = new JLabel("Autor: " + t.getPieza().getAutor());
            JLabel fechaCompra = new JLabel("Fecha de Compra: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getFecha()));
            JLabel precioCompra = new JLabel("Precio: $" + t.getMonto());

            compraPanel.add(nombrePieza);
            compraPanel.add(autorPieza);
            compraPanel.add(fechaCompra);
            compraPanel.add(precioCompra);

            comprasPanel.add(compraPanel);
        }


        comprasPanel.revalidate();
        comprasPanel.repaint();
    }

    
    
    private void procesarPagoDesdeGUI(String nombreTitular, String numeroTarjeta, String fechaExpiracion, String cvv, double monto, String pasarelaSeleccionada) {
     
        if (usuarioLogueado instanceof comprador || usuarioLogueado.esAdministrador()) {
            comprador compradorLogueado;
            
           
            if (usuarioLogueado.esAdministrador()) {
                compradorLogueado = ofertaGanadoraSeleccionada.getComprador();
            } else {
                compradorLogueado = (comprador) usuarioLogueado;
            }


            InformacionPago informacionPago = new InformacionPago(
                nombreTitular,
                numeroTarjeta,
                fechaExpiracion,
                cvv,
                monto,
                UUID.randomUUID().toString(),
                compradorLogueado,
                ofertaGanadoraSeleccionada.getPieza() // Obtener la pieza de la oferta ganadora seleccionada
            );

            boolean resultado = galeria.procesarPago(pasarelaSeleccionada, informacionPago, compradorLogueado);

            // Si el pago es exitoso, actualizar las compras
            if (resultado) {
                actualizarCompras(compradorLogueado);
                JOptionPane.showMessageDialog(this, "Pago procesado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar el pago.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El usuario logueado no tiene permisos para procesar pagos.");
        }
    }


  
    
    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void mostrarHistoriaComprador(String nombreComprador) {
        comprador comprador = (comprador) galeria.getUsuario(nombreComprador);
        if (comprador != null) {
            List<transaccion> historialCompras = comprador.getHistorialCompras();
            if (historialCompras != null) {
                for (transaccion trans : historialCompras) {
                  
                }
            } else {
                System.out.println("El comprador no tiene historial de compras.");
            }
        } else {
            System.out.println("Comprador no encontrado.");
        }
    }


    private void registrarPiezaEnConsignacion() {
        JTextField idField = new JTextField(15);
        JTextField alturaField = new JTextField(5);
        JTextField anchuraField = new JTextField(5);
        JTextField profundidadField = new JTextField(5);
        JTextField pesoField = new JTextField(5);
        JTextField nombreField = new JTextField(15);
        JCheckBox enExhibicionCheck = new JCheckBox("En exhibición");
        JTextField codigoIdentificadorField = new JTextField(15);
        JTextField anoCreacionField = new JTextField(5);
        JTextField autorField = new JTextField(15);
        JTextField descripcionField = new JTextField(20);
        JTextField precioVentaField = new JTextField(10);
        JTextField fechaVentaField = new JTextField(10);
        JCheckBox enConsignacionCheck = new JCheckBox("En consignación");
        JTextField propietarioRealField = new JTextField(15);
        JTextField fechaFinConsignacionField = new JTextField(10);

        JButton seleccionarImagenButton = new JButton("Seleccionar Imagen");
        JLabel imagenLabel = new JLabel();

        seleccionarImagenButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg", "gif"));
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imagenLabel.setText(file.getAbsolutePath());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("ID", idField));
        panel.add(createLabeledField("Altura", alturaField));
        panel.add(createLabeledField("Anchura", anchuraField));
        panel.add(createLabeledField("Profundidad", profundidadField));
        panel.add(createLabeledField("Peso", pesoField));
        panel.add(createLabeledField("Nombre", nombreField));
        panel.add(enExhibicionCheck);
        panel.add(createLabeledField("Código Identificador", codigoIdentificadorField));
        panel.add(createLabeledField("Año de Creación", anoCreacionField));
        panel.add(createLabeledField("Autor", autorField));
        panel.add(createLabeledField("Descripción", descripcionField));
        panel.add(createLabeledField("Precio de Venta", precioVentaField));
        panel.add(createLabeledField("Fecha de Venta", fechaVentaField));
        panel.add(enConsignacionCheck);
        panel.add(createLabeledField("Propietario Real", propietarioRealField));
        panel.add(createLabeledField("Fecha Fin de Consignación", fechaFinConsignacionField));
        panel.add(seleccionarImagenButton);
        panel.add(imagenLabel);

        int result = JOptionPane.showConfirmDialog(null, panel, "Registrar Nueva Pieza en Consignación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            double altura = Double.parseDouble(alturaField.getText());
            double anchura = Double.parseDouble(anchuraField.getText());
            double profundidad = Double.parseDouble(profundidadField.getText());
            double peso = Double.parseDouble(pesoField.getText());
            String nombre = nombreField.getText();
            boolean estaEnExhibicion = enExhibicionCheck.isSelected();
            String codigoIdentificador = codigoIdentificadorField.getText();
            int anoCreacion = Integer.parseInt(anoCreacionField.getText());
            String autor = autorField.getText();
            String descripcion = descripcionField.getText();
            double precioVenta = Double.parseDouble(precioVentaField.getText());
            String fechaVenta = fechaVentaField.getText();
            boolean enConsignacion = enConsignacionCheck.isSelected();
            String propietarioReal = propietarioRealField.getText();
            String fechaFinConsignacion = fechaFinConsignacionField.getText();
            String imagenRuta = imagenLabel.getText();

            pieza nuevaPieza = new pieza(id, altura, anchura, profundidad, peso, nombre, estaEnExhibicion,
                                         codigoIdentificador, anoCreacion, autor, descripcion, null, precioVenta,
                                         fechaVenta, enConsignacion, propietarioReal, fechaFinConsignacion, imagenRuta);
            if (galeria.agregarPiezaEnConsignacion(nuevaPieza, usuarioLogueado)) {
                JOptionPane.showMessageDialog(this, "Pieza registrada con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la pieza.");
            }
        }
    }
    private void registrarVentaPieza() {
     
    }



    private void verificarYConfirmarCompra() {
        if (usuarioLogueado.esAdministrador()) {
            String idPieza = JOptionPane.showInputDialog("Ingrese el ID de la pieza a verificar:");
            String nombreComprador = JOptionPane.showInputDialog("Ingrese el nombre del comprador:");
            usuario compradorUsuario = galeria.getUsuario(nombreComprador);
            if (compradorUsuario instanceof comprador) {
                comprador comprador = (comprador) compradorUsuario;
                galeria.verificarYConfirmarCompra(idPieza, usuarioLogueado, comprador);
                JOptionPane.showMessageDialog(this, "Compra verificada y confirmada.");
            } else {
                JOptionPane.showMessageDialog(this, "Comprador no encontrado o no tiene permisos de comprador.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tiene permisos para verificar compras.");
        }
    }
    private void crearSubasta() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea crear una nueva subasta?", "Crear Subasta", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza a subastar:");
            pieza piezaSeleccionada = galeria.buscarPiezaPorId(idPieza);

            if (piezaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
                return;
            }

            double precioVenta = piezaSeleccionada.getPrecioVenta();

            JTextField idSubastaField = new JTextField(15);
            JTextField valorInicialField = new JTextField(String.valueOf(precioVenta), 10);
            JTextField valorMinimoField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(createLabeledField("ID de la Subasta", idSubastaField));
            panel.add(createLabeledField("Valor Inicial", valorInicialField));
            panel.add(createLabeledField("Valor Mínimo", valorMinimoField));

            int result = JOptionPane.showConfirmDialog(null, panel, "Crear Nueva Subasta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String idSubasta = idSubastaField.getText();
                double valorInicial;
                double valorMinimo;

                try {
                    valorInicial = Double.parseDouble(valorInicialField.getText());
                    valorMinimo = Double.parseDouble(valorMinimoField.getText());

                    if (valorInicial < precioVenta) {
                        JOptionPane.showMessageDialog(this, "El valor inicial no puede ser menor que el precio de venta de la pieza.");
                        return;
                    }

                    if (valorMinimo <= valorInicial) {
                        JOptionPane.showMessageDialog(this, "El valor mínimo debe ser mayor que el valor inicial.");
                        return;
                    }

                    subasta nuevaSubasta = new subasta(idSubasta, piezaSeleccionada, valorInicial, valorMinimo);
                    if (galeria.agregarSubasta(nuevaSubasta)) {
                        JOptionPane.showMessageDialog(this, "Subasta creada con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al crear la subasta. Puede que el ID ya exista.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para los precios.");
                }
            }
        }
    }

    

    
    
    
    
    private void activarSubasta() {
        String idSubasta = JOptionPane.showInputDialog(this, "Ingrese el ID de la subasta que desea activar:");
        
        if (idSubasta == null || idSubasta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID de subasta no puede estar vacío.");
            return;
        }
        
        subasta subasta = galeria.buscarSubastaPorId(idSubasta);
        
        if (subasta == null) {
            JOptionPane.showMessageDialog(this, "Subasta no encontrada.");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea activar la subasta?", "Activar Subasta", JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            subasta.setEstaActiva(true);
            galeria.guardarSubastas(); 
            JOptionPane.showMessageDialog(this, "Subasta activada con éxito.");
        }
    }
    
    private void mostrarGraficoDeVentas() {
        Map<String, Integer> ventasPorMes = DataLoader.cargarTransacciones("data/transacciones.txt");

  
        Map<String, Integer> ventasOrdenadas = new TreeMap<>();

       
        ventasOrdenadas.putAll(ventasPorMes);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : ventasOrdenadas.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Ventas Realizadas a lo Largo del Año", 
                "Mes", 
                "Cantidad de Ventas", 
                dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Gráfico de Ventas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }

  
    private JButton crearBotonMenu(String texto, ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(accion);
        return boton;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GaleriaGUI frame = new GaleriaGUI();
            frame.setVisible(true);
        });
    }




 
// FIN MENU ADMINISTRADOR//
  
    
    




    
// INICIO MENU CAJERO//
 // INICIO MENU CAJERO//
    private void mostrarMenuCajero() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        setContentPane(panel);


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);
        JLabel welcomeLabel = new JLabel("Hola, " + usuarioLogueado.getNombre() + " ¿Qué quieres hacer hoy?");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon("data/imagenes/logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(iconLabel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);

   
        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        menuPanel.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder("Menú");
        border.setTitleFont(new Font("Arial", Font.BOLD, 16)); 
        menuPanel.setBorder(border);

        menuPanel.add(crearBotonMenu("Administrar Subasta y Pagos", e -> administrarSubasta()));
        menuPanel.add(crearBotonMenu("Ver Catálogo de Piezas", e -> verCatalogoCajero()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de una Pieza", e -> mostrarHistoriaPiezaCajero()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de un Artista", e -> mostrarHistoriaArtistaCajero()));
        menuPanel.add(crearBotonMenu("Mostrar Historia de un Comprador", e -> mostrarHistoriaCompradorCajero()));

        panel.add(menuPanel, BorderLayout.CENTER);

      
        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar la sesión?", "Confirmar cierre de sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                usuarioLogueado = null;
                getContentPane().removeAll();
                initialize();
                validate();
                repaint();
            }
        });

        panel.add(exitButton, BorderLayout.SOUTH);

        validate();
        repaint();
    }

    // INICIO PROCESAR PAGOS CAJERO//
    





private void verCatalogoCajero() {
    List<pieza> piezas = galeria.getPiezas();
    if (piezas.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay piezas disponibles en el catálogo.");
        return;
    }

   
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(245, 245, 245));

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("CATÁLOGO DE PIEZAS");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    mainPanel.add(headerPanel, BorderLayout.NORTH);

   
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(245, 245, 245));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    JLabel imageLabel = new JLabel();
    imageLabel.setPreferredSize(new Dimension(300, 300));
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridheight = 5;
    centerPanel.add(imageLabel, gbc);

    JLabel nombreLabel = new JLabel();
    nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridheight = 1;
    gbc.anchor = GridBagConstraints.WEST;
    centerPanel.add(nombreLabel, gbc);

    JLabel autorLabel = new JLabel();
    autorLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 1;
    centerPanel.add(autorLabel, gbc);

    JLabel anoCreacionLabel = new JLabel();
    anoCreacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 2;
    centerPanel.add(anoCreacionLabel, gbc);

    JLabel descripcionLabel = new JLabel();
    descripcionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy = 3;
    centerPanel.add(descripcionLabel, gbc);

    JLabel precioVentaLabel = new JLabel();
    precioVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
    precioVentaLabel.setForeground(Color.RED);
    gbc.gridy = 4;
    centerPanel.add(precioVentaLabel, gbc);

    mainPanel.add(centerPanel, BorderLayout.CENTER);


    JPanel navPanel = new JPanel(new FlowLayout());
    JButton prevButton = new JButton("ANTERIOR");
    JButton nextButton = new JButton("SIGUIENTE");
    navPanel.add(prevButton);
    navPanel.add(nextButton);
    mainPanel.add(navPanel, BorderLayout.SOUTH);


    JButton homeButton = new JButton("VOLVER");
    homeButton.addActionListener(e -> {
        setContentPane(new JPanel());
        mostrarMenuCajero();
    });
    navPanel.add(homeButton);


    setContentPane(mainPanel);
    validate();
    repaint();


    int[] currentIndex = {0};

    ActionListener updateDetails = e -> {
        if (currentIndex[0] < 0) {
            currentIndex[0] = 0;
        } else if (currentIndex[0] >= piezas.size()) {
            currentIndex[0] = piezas.size() - 1;
        }

        pieza currentPieza = piezas.get(currentIndex[0]);
        nombreLabel.setText("Nombre: " + currentPieza.getNombre());
        autorLabel.setText("Autor: " + currentPieza.getAutor());
        anoCreacionLabel.setText("Año de Creación: " + currentPieza.getAnoCreacion());
        descripcionLabel.setText("<html>Descripción: " + currentPieza.getDescripcion() + "</html>");
        precioVentaLabel.setText("Precio de Venta: $" + currentPieza.getPrecioVenta());

   
        String imagePath = currentPieza.getImagenRuta(); // Usar la ruta de la imagen almacenada en la pieza
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        imageLabel.setIcon(imageIcon);
    };

    prevButton.addActionListener(e -> {
        currentIndex[0]--;
        updateDetails.actionPerformed(null);
    });

    nextButton.addActionListener(e -> {
        currentIndex[0]++;
        updateDetails.actionPerformed(null);
    });

    updateDetails.actionPerformed(null); 
}

private void mostrarHistoriaPiezaCajero() {
    String idPieza = JOptionPane.showInputDialog(this, "Ingrese el ID de la pieza:");
    
    if (idPieza == null || idPieza.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID de pieza no puede estar vacío.");
        return;
    }
    
    pieza pieza = galeria.buscarPiezaPorId(idPieza);
    
    if (pieza == null) {
        JOptionPane.showMessageDialog(this, "Pieza no encontrada.");
        return;
    }
    
  
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(245, 245, 245));


    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("HISTORIA DE LA PIEZA");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    mainPanel.add(headerPanel, BorderLayout.NORTH);


    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(245, 245, 245));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;

    JLabel nombreLabel = new JLabel("Nombre: " + pieza.getNombre());
    nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(nombreLabel, gbc);

    JLabel autorLabel = new JLabel("Autor: " + pieza.getAutor());
    autorLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(autorLabel, gbc);

    JLabel anoCreacionLabel = new JLabel("Año de Creación: " + pieza.getAnoCreacion());
    anoCreacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(anoCreacionLabel, gbc);

    JLabel descripcionLabel = new JLabel("<html>Descripción: " + pieza.getDescripcion() + "</html>");
    descripcionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(descripcionLabel, gbc);

    JLabel precioVentaLabel = new JLabel("Precio de Venta: $" + pieza.getPrecioVenta());
    precioVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
    precioVentaLabel.setForeground(Color.RED);
    gbc.gridy++;
    centerPanel.add(precioVentaLabel, gbc);

    JLabel enExhibicionLabel = new JLabel("En Exhibición: " + (pieza.isEstaEnExhibicion() ? "Sí" : "No"));
    enExhibicionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(enExhibicionLabel, gbc);

    JLabel enConsignacionLabel = new JLabel("En Consignación: " + (pieza.isenConsignacion() ? "Sí" : "No"));
    enConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(enConsignacionLabel, gbc);

    JLabel propietarioRealLabel = new JLabel("Propietario Real: " + pieza.getPropietarioReal());
    propietarioRealLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(propietarioRealLabel, gbc);

    JLabel fechaFinConsignacionLabel = new JLabel("Fecha Fin de Consignación: " + pieza.getFechaFinConsignacion());
    fechaFinConsignacionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(fechaFinConsignacionLabel, gbc);

    JLabel fechaVentaLabel = new JLabel("Fecha de Venta: " + pieza.getFechaVenta());
    fechaVentaLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(fechaVentaLabel, gbc);

    JLabel imagenLabel = new JLabel();
    imagenLabel.setPreferredSize(new Dimension(300, 300));
    String imagePath = pieza.getImagenRuta();
    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
    imagenLabel.setIcon(imageIcon);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridheight = 10;
    centerPanel.add(imagenLabel, gbc);

    // Panel de historial de transacciones
    JPanel historialPanel = new JPanel(new GridLayout(0, 1));
    historialPanel.setBackground(new Color(245, 245, 245));
    historialPanel.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
    List<transaccion> historialTransacciones = galeria.obtenerHistorialTransacciones(idPieza);
    for (transaccion trans : historialTransacciones) {
        JLabel transLabel = new JLabel("ID Transacción: " + trans.getId() +
                                        ", Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(trans.getFecha()) +
                                        ", Comprador: " + trans.getComprador().getNombre() +
                                        ", Monto: $" + trans.getMonto());
        historialPanel.add(transLabel);
    }
    gbc.gridx = 0;
    gbc.gridy = 10;
    gbc.gridwidth = 2;
    gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.BOTH;
    centerPanel.add(historialPanel, gbc);

    mainPanel.add(centerPanel, BorderLayout.CENTER);

 
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton volverButton = new JButton("Volver");
    volverButton.addActionListener(e -> {
        setContentPane(new JPanel());
        mostrarMenuCajero();
    });
    bottomPanel.add(volverButton);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

   
    setContentPane(mainPanel);
    validate();
    repaint();
}
private void mostrarHistoriaArtistaCajero() {
    String nombreArtista = JOptionPane.showInputDialog(this, "Ingrese el nombre del artista:");

    if (nombreArtista == null || nombreArtista.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre del artista no puede estar vacío.");
        return;
    }

    List<pieza> obrasDelArtista = new ArrayList<>();
    for (pieza p : galeria.getPiezas()) {
        if (p.getAutor().equalsIgnoreCase(nombreArtista)) {
            obrasDelArtista.add(p);
        }
    }

    if (obrasDelArtista.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron obras para el artista especificado.");
        return;
    }

    // Crear el panel principal
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(245, 245, 245));

  
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("HISTORIA DEL ARTISTA");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    mainPanel.add(headerPanel, BorderLayout.NORTH);

  
    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(245, 245, 245));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;

    JLabel nombreLabel = new JLabel("Nombre: " + nombreArtista);
    nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(nombreLabel, gbc);

    JPanel obrasPanel = new JPanel(new GridLayout(0, 1));
    obrasPanel.setBackground(new Color(245, 245, 245));
    obrasPanel.setBorder(BorderFactory.createTitledBorder("Obras del Artista"));
    for (pieza obra : obrasDelArtista) {
        JLabel obraLabel = new JLabel("Nombre: " + obra.getNombre() +
                                      ", Año de Creación: " + obra.getAnoCreacion() +
                                      ", Descripción: " + obra.getDescripcion());
        obrasPanel.add(obraLabel);
    }
    gbc.gridy++;
    gbc.fill = GridBagConstraints.BOTH;
    centerPanel.add(obrasPanel, gbc);

    mainPanel.add(centerPanel, BorderLayout.CENTER);

   
    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton volverButton = new JButton("Volver");
    volverButton.addActionListener(e -> {
        setContentPane(new JPanel());
        mostrarMenuCajero();
    });
    bottomPanel.add(volverButton);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

   
    setContentPane(mainPanel);
    validate();
    repaint();
}
private void mostrarHistoriaCompradorCajero() {
    String nombreComprador = JOptionPane.showInputDialog(this, "Ingrese el nombre del comprador:");

    if (nombreComprador == null || nombreComprador.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre del comprador no puede estar vacío.");
        return;
    }

    usuario comprador = galeria.getUsuario(nombreComprador);

    if (comprador == null || !comprador.esComprador()) {
        JOptionPane.showMessageDialog(this, "Comprador no encontrado o el usuario no es un comprador.");
        return;
    }

    
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(245, 245, 245));

    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("HISTORIA DEL COMPRADOR");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerPanel.add(headerLabel);
    mainPanel.add(headerPanel, BorderLayout.NORTH);


    JPanel centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(245, 245, 245));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;

    JLabel nombreLabel = new JLabel("Nombre: " + comprador.getNombre());
    nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridx = 0;
    gbc.gridy = 0;
    centerPanel.add(nombreLabel, gbc);

    JLabel emailLabel = new JLabel("Email: " + comprador.getEmail());
    emailLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(emailLabel, gbc);

    JLabel telefonoLabel = new JLabel("Teléfono: " + comprador.getTelefono());
    telefonoLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(telefonoLabel, gbc);

    JLabel direccionLabel = new JLabel("Dirección: " + comprador.getDireccion());
    direccionLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(direccionLabel, gbc);

    JLabel ciudadLabel = new JLabel("Ciudad: " + comprador.getCiudad());
    ciudadLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(ciudadLabel, gbc);

    JLabel codigoPostalLabel = new JLabel("Código Postal: " + comprador.getCodigoPostal());
    codigoPostalLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(codigoPostalLabel, gbc);

    JLabel paisLabel = new JLabel("País: " + comprador.getPais());
    paisLabel.setFont(new Font("Arial", Font.BOLD, 18));
    gbc.gridy++;
    centerPanel.add(paisLabel, gbc);

    // Panel de piezas compradas
    JPanel piezasPanel = new JPanel(new GridLayout(0, 1));
    piezasPanel.setBackground(new Color(245, 245, 245));
    piezasPanel.setBorder(BorderFactory.createTitledBorder("Piezas Compradas"));
    for (pieza p : comprador.getPiezasCompradas()) {
        JLabel piezaLabel = new JLabel("Nombre: " + p.getNombre() +
                                      ", Año de Creación: " + p.getAnoCreacion() +
                                      ", Descripción: " + p.getDescripcion() +
                                      ", Precio de Venta: " + p.getPrecioVenta());
        piezasPanel.add(piezaLabel);
    }
    gbc.gridy++;
    gbc.fill = GridBagConstraints.BOTH;
    centerPanel.add(piezasPanel, gbc);

   
    JPanel transaccionesPanel = new JPanel(new GridLayout(0, 1));
    transaccionesPanel.setBackground(new Color(245, 245, 245));
    transaccionesPanel.setBorder(BorderFactory.createTitledBorder("Historial de Transacciones"));
    for (transaccion t : comprador.getHistorialCompras()) {
        JLabel transaccionLabel = new JLabel("ID: " + t.getId() +
                                             ", Fecha: " + new SimpleDateFormat("yyyy-MM-dd").format(t.getFecha()) +
                                             ", Lugar: " + t.getLugar() +
                                             ", Monto: " + t.getMonto() +
                                             ", Pieza: " + t.getPieza().getNombre());
        transaccionesPanel.add(transaccionLabel);
    }
    gbc.gridy++;
    centerPanel.add(transaccionesPanel, gbc);

    mainPanel.add(centerPanel, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton volverButton = new JButton("Volver");
    volverButton.addActionListener(e -> {
        setContentPane(new JPanel());
        mostrarMenuCajero();
    });
    bottomPanel.add(volverButton);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    
    setContentPane(mainPanel);
    validate();
    repaint();
}



private void administrarSubastaCajero() {
    String idSubasta = JOptionPane.showInputDialog(this, "Ingrese el ID de la subasta:");

    if (idSubasta != null && !idSubasta.isEmpty()) {
        List<oferta> ofertas = galeria.obtenerOfertasDeSubasta(idSubasta);

        if (ofertas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ofertas para esta subasta.");
            return;
        }

       
        String[] opciones = new String[ofertas.size()];
        for (int i = 0; i < ofertas.size(); i++) {
            oferta o = ofertas.get(i);
            opciones[i] = "Comprador: " + o.getComprador().getNombre() + ", Oferta: " + o.getValorOferta();
        }

        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona la oferta ganadora:",
            "Ofertas de Subasta",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion != null) {
            int indiceSeleccionado = -1;
            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(seleccion)) {
                    indiceSeleccionado = i;
                    break;
                }
            }

            if (indiceSeleccionado != -1) {
                ofertaGanadoraSeleccionada = ofertas.get(indiceSeleccionado);
                idSubastaSeleccionada = idSubasta;
                mostrarPanelProcesarPagosCajero();
            } else {
                JOptionPane.showMessageDialog(this, "Selección inválida.");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "ID de subasta inválido.");
    }
}

private void mostrarPanelProcesarPagosCajero() {
    
    if (!usuarioLogueado.esAdministrador() && !usuarioLogueado.esCajero()) {
        JOptionPane.showMessageDialog(this, "Solo los administradores y cajeros pueden procesar pagos.");
        return;
    }

    JTextField nombreTitularField = new JTextField(15);
    JTextField numeroTarjetaField = new JTextField(15);
    JTextField fechaExpiracionField = new JTextField(5);
    JTextField cvvField = new JTextField(3);
    JTextField montoField = new JTextField(10);
    JComboBox<String> pasarelaComboBox = new JComboBox<>(galeria.getPasarelasPagoDisponibles().toArray(new String[0]));
    JLabel tipoTarjetaLabel = new JLabel("Tipo de Tarjeta: Desconocido");

    numeroTarjetaField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            actualizarTipoTarjeta();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            actualizarTipoTarjeta();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            actualizarTipoTarjeta();
        }

        private void actualizarTipoTarjeta() {
            String numeroTarjeta = numeroTarjetaField.getText();
            InformacionPago infoPago = new InformacionPago("", numeroTarjeta, "", "", 0, "", null, null);
            tipoTarjetaLabel.setText("Tipo de Tarjeta: " + infoPago.getTipoTarjeta());
        }
    });

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(createLabeledField("Nombre del titular", nombreTitularField));
    panel.add(createLabeledField("Número de tarjeta", numeroTarjetaField));
    panel.add(tipoTarjetaLabel);
    panel.add(createLabeledField("Fecha de expiración (MM/AA)", fechaExpiracionField));
    panel.add(createLabeledField("CVV", cvvField));
    panel.add(createLabeledField("Monto", montoField));
    panel.add(createLabeledField("Pasarela de Pago", pasarelaComboBox));

    int result = JOptionPane.showConfirmDialog(null, panel, "Procesar Pago", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        String nombreTitular = nombreTitularField.getText();
        String numeroTarjeta = numeroTarjetaField.getText();
        String fechaExpiracion = fechaExpiracionField.getText();
        String cvv = cvvField.getText();
        double monto = Double.parseDouble(montoField.getText());
        String pasarelaSeleccionada = (String) pasarelaComboBox.getSelectedItem();
        String idTransaccion = UUID.randomUUID().toString();

       
        if (usuarioLogueado instanceof comprador) {
            comprador compradorLogueado = (comprador) usuarioLogueado;

            InformacionPago informacionPago = new InformacionPago(
                nombreTitular, 
                numeroTarjeta, 
                fechaExpiracion, 
                cvv, 
                monto, 
                idTransaccion, 
                compradorLogueado, 
                ofertaGanadoraSeleccionada.getPieza()
            );

            if (galeria.procesarPago(pasarelaSeleccionada, informacionPago, compradorLogueado)) {
                JOptionPane.showMessageDialog(this, "Pago procesado con éxito.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar el pago.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El usuario logueado no es un comprador.");
        }
    }
}





    }

