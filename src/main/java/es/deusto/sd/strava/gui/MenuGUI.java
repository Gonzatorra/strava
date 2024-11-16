package es.deusto.sd.strava.gui;

import javax.swing.*;
import es.deusto.sd.strava.*;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.fachada.IRemoteFacade;
import es.deusto.sd.strava.servicios.UsuarioService;

import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuGUI extends JFrame {

    private static final Color ORANGE_ACCENT = new Color(255, 87, 34);
    private IRemoteFacade facade;

    public MenuGUI(IRemoteFacade facade) {
        this.facade = facade;
        setTitle("Strava - Login / Registro");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Login", createLoginPanel());
        tabbedPane.addTab("Registro", createRegisterPanel());

        add(tabbedPane);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE); // Fondo blanco para el login
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = createStyledLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja
        JLabel passwordLabel = createStyledLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja

        JButton loginButton = createStyledButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            try {
                UsuarioDTO usuario = facade.login(usernameField.getText(), new String(passwordField.getPassword()));
                if (usuario != null) {
                    JOptionPane.showMessageDialog(this, "¡Bienvenido a STRAVA, " + usuario.getUsername() + "!");
                    this.dispose();
                    MainAppGUI main = new MainAppGUI(usuario);
                    main.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Parece ser que algo no es correcto, inténtalo otra vez.");
                    usernameField.setText("");
                    passwordField.setText("");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el login: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = createStyledLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja
        JLabel passwordLabel = createStyledLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja
        JLabel emailLabel = createStyledLabel("Email:");
        JTextField emailField = new JTextField(15);
        emailField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja
        JLabel nameLabel = createStyledLabel("Name:");
        JTextField nameField = new JTextField(15);
        nameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  // Borde de color naranja

        JButton registerButton = createStyledButton("Registrar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registerPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String name = nameField.getText();

                UsuarioDTO usuario = facade.registrarUsuario(username, password, email, name);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(this, "Usuario registrado con éxito: " + usuario.getUsername());
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar el usuario.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en el registro: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        return registerPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ORANGE_ACCENT);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ORANGE_ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 2));  // Borde de color naranja
        return button;
    }

    public static void main(String[] args) {
        try {
            IRemoteFacade facade = (IRemoteFacade) Naming.lookup("rmi://localhost/RemoteFacade");
            SwingUtilities.invokeLater(() -> new MenuGUI(facade).setVisible(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Nueva clase para la ventana principal después del login
class MainAppGUI extends JFrame {
    private static final Color ORANGE_ACCENT = new Color(255, 87, 34);
    private UsuarioDTO usuario;
    private IRemoteFacade facade;

    public MainAppGUI(UsuarioDTO usuario) {
        this.usuario = usuario;
        try {
            facade = (IRemoteFacade) Naming.lookup("rmi://localhost/RemoteFacade");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Strava - Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Mi Perfil", createProfilePanel());
        tabbedPane.addTab("Mis Entrenamientos", createTrainPanel());
        tabbedPane.addTab("Retos", createRetoPanel());
        tabbedPane.addTab("Amigos", createTabPanel("Contenido de Amigos"));

        add(tabbedPane);

        // Añadiendo el listener para el cierre de ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                int respuesta = JOptionPane.showConfirmDialog(
                        MainAppGUI.this,
                        "¿Seguro que quieres cerrar la ventana?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    System.out.println("Guardando datos antes de cerrar... NO FUNCIONA NO GUARDA JEEJE");
                    try {
                        facade.actualizarUsuario(usuario);
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                    dispose();
                }
                else {
                	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private JPanel createProfilePanel() {
        JPanel profilePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Atributo", "Valor"};
        Object[][] data = {
                {"Username", usuario.getUsername()},
                {"Email", usuario.getEmail()},
                {"Fecha de Nacimiento", usuario.getfNacimiento()},
                {"Nombre", usuario.getNombre()},
                {"Peso", usuario.getPeso()},
                {"Altura", usuario.getAltura()},
                {"Frecuencia Cardiaca Maxima", usuario.getFecCMax()},
                {"Frecuencia Cardiaca en Reposo", usuario.getFecCReposo()},
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };

        JButton modButton = new JButton("Modificar Usuario");
        modButton.setBackground(ORANGE_ACCENT);
        modButton.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modButton);
        modButton.addActionListener(e -> {
        	System.out.println("POR AHORA NO MODIFICA");
        });
        
        
        JTable profileTable = new JTable(tableModel);
        profileTable.setFocusable(false); // Deshabilitar el foco en las celdas
        profileTable.setRowSelectionAllowed(false); // Deshabilitar selección de filas
 
        profilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        JTableHeader header = profileTable.getTableHeader();
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);

        return profilePanel;
    }

    private JPanel createTrainPanel() {
        JPanel trainPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Fecha", "Duración", "Distancia", "Deporte"};
        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };
        
        
        //DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable trainTable = new JTable(tableModel);
        trainTable.setFocusable(false); // Deshabilitar el foco en las celdas
        JTableHeader header = trainTable.getTableHeader();
        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);
        trainPanel.add(new JScrollPane(trainTable), BorderLayout.CENTER);

        // Botón para agregar entrenamiento
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Añadir Entrenamiento");
        JButton modButton = new JButton("Modificar Entrenamiento");
        JButton delButton = new JButton("Eliminar Entrenamiento");
        addButton.setBackground(ORANGE_ACCENT);
        addButton.setForeground(Color.WHITE);
        buttonPanel.add(addButton);
        modButton.setBackground(ORANGE_ACCENT);
        modButton.setForeground(Color.WHITE);
        buttonPanel.add(modButton);
        delButton.setBackground(ORANGE_ACCENT);
        delButton.setForeground(Color.WHITE);
        buttonPanel.add(delButton);
        trainPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        modButton.addActionListener(e -> {
        	System.out.println("POR AHORA NO MODIFICA");
        });
        
        
        delButton.addActionListener(e -> {
        	System.out.println("POR AHORA NO ELIMINA");
        });
        
        
        // Acción al hacer clic en el botón de añadir
        addButton.addActionListener(e -> {
            // Mostrar un formulario para capturar los datos del nuevo entrenamiento
            JPanel panel = new JPanel(new GridLayout(4, 2));

            JTextField titleField = new JTextField(10);
            JTextField sportField = new JTextField(10);
            JTextField durationField = new JTextField(10);
            JTextField distanceField = new JTextField(10);

            panel.add(new JLabel("Título:"));
            panel.add(titleField);
            panel.add(new JLabel("Deporte:"));
            panel.add(sportField);
            panel.add(new JLabel("Duración (min):"));
            panel.add(durationField);
            panel.add(new JLabel("Distancia (km):"));
            panel.add(distanceField);

            int option = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Nuevo Entrenamiento",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                // Obtener los valores ingresados
                String title = titleField.getText();
                String sport = sportField.getText();
                int duration;
                double distance;

                try {
                    duration = Integer.parseInt(durationField.getText());
                    distance = Double.parseDouble(distanceField.getText());
                    
                    LocalTime time = LocalDateTime.now().toLocalTime();
                    
                    // Obtener la hora, minuto y segundo
                    int hours = time.getHour();
                    int minutes = time.getMinute();
                    int seconds = time.getSecond();
                    
                    // Convertir todo a horas decimales (float)
                    float timeInHours = hours + minutes / 60.0f + seconds / 3600.0f;
                    
                    // Agregar el entrenamiento a la fachada
                    //facade.crearEntreno(usuario, title, sport, distance, LocalDateTime.now().toLocalDate(), timeInHours, duration);

                    // Añadir la fila al modelo de la tabla
                    tableModel.addRow(new Object[]{LocalDateTime.now(), duration, distance, sport});

                    JOptionPane.showMessageDialog(this, "Entrenamiento añadido con éxito. NO SE HA AÑADIDO AL ARRAY SOLO TABLA");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingresa valores válidos para la duración y distancia.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al agregar el entrenamiento: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        return trainPanel;
    }

    
    
    private JPanel createRetoPanel() {
        JPanel retoPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Nombre", "Deporte", "Creador", "Fecha Inicio", "Fecha Fin", "Objetivo Distancia", "Objetivo Tiempo"};
        
        DefaultTableModel retoModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };

        JTable retoTable = new JTable(retoModel);
        retoTable.setFocusable(false); // Deshabilitar el foco en las celdas
        JTableHeader header = retoTable.getTableHeader();
        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);
        retoPanel.add(new JScrollPane(retoTable), BorderLayout.CENTER);

        // Botón para agregar entrenamiento
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Añadir Reto");
        JButton modButton = new JButton("Modificar Reto");
        JButton delButton = new JButton("Eliminar Reto");
        addButton.setBackground(ORANGE_ACCENT);
        addButton.setForeground(Color.WHITE);
        buttonPanel.add(addButton);
        modButton.setBackground(ORANGE_ACCENT);
        modButton.setForeground(Color.WHITE);
        buttonPanel.add(modButton);
        delButton.setBackground(ORANGE_ACCENT);
        delButton.setForeground(Color.WHITE);
        buttonPanel.add(delButton);
        retoPanel.add(buttonPanel, BorderLayout.SOUTH); // Solo se agrega una vez

        modButton.addActionListener(e -> {
            System.out.println("POR AHORA NO MODIFICA");
        });

        delButton.addActionListener(e -> {
            System.out.println("POR AHORA NO ELIMINA");
        });

        // Acción al hacer clic en el botón de añadir
        addButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(7, 2)); // Corregido el tamaño del GridLayout
            
            JTextField titleField = new JTextField(10);
            JTextField sportField = new JTextField(10);
            JTextField creatorField = new JTextField(10);
            JTextField fecIniField = new JTextField(10);
            JTextField fecFinField = new JTextField(10);
            JTextField objDisField = new JTextField(10);
            JTextField objTempField = new JTextField(10);

            panel.add(new JLabel("Nombre:"));
            panel.add(titleField);
            panel.add(new JLabel("Deporte:"));
            panel.add(sportField);
            panel.add(new JLabel("Creador:"));
            panel.add(creatorField);
            panel.add(new JLabel("Fecha Inicio:"));
            panel.add(fecIniField);
            panel.add(new JLabel("Fecha Fin:"));
            panel.add(fecFinField);
            panel.add(new JLabel("Objetivo Distancia:"));
            panel.add(objDisField);
            panel.add(new JLabel("Objetivo Tiempo:"));
            panel.add(objTempField);

            int option = JOptionPane.showConfirmDialog(
                    retoPanel, // Cambiado para usar el panel actual
                    panel,
                    "Nuevo Reto",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String title = titleField.getText();
                String sport = sportField.getText();
                String creator = creatorField.getText();
                String fecIni = fecIniField.getText();
                String fecFin = fecFinField.getText();
                String objDis = objDisField.getText();
                String objTemp = objTempField.getText();

                try {
                    retoModel.addRow(new Object[]{title, sport, creator, fecIni, fecFin, objDis, objTemp});
                    JOptionPane.showMessageDialog(retoPanel, "Reto añadido con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(retoPanel, "Error al agregar el reto: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        return retoPanel;
    }

    
    
    private JPanel createTabPanel(String content) {
        JPanel tabPanel = new JPanel();
        tabPanel.add(new JLabel(content));
        return tabPanel;
    }
}
