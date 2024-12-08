package es.deusto.sd.strava.gui;

import javax.swing.*;
import es.deusto.sd.strava.*;
import es.deusto.sd.strava.DTO.*;
import es.deusto.sd.strava.assembler.EntrenamientoAssembler;
import es.deusto.sd.strava.assembler.RetoAssembler;
import es.deusto.sd.strava.assembler.UsuarioAssembler;
import es.deusto.sd.strava.auth.AuthServiceFactory;
import es.deusto.sd.strava.auth.IAuthServiceGateway;
import es.deusto.sd.strava.dominio.Entrenamiento;
import es.deusto.sd.strava.dominio.Reto;
import es.deusto.sd.strava.dominio.Usuario;
import es.deusto.sd.strava.fachada.IRemoteFacade;
import es.deusto.sd.strava.servicios.UsuarioService;

import java.awt.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.TableCellRenderer;

public class MenuGUI extends JFrame {

    private static final Color ORANGE_ACCENT = new Color(255, 87, 34);
    private IRemoteFacade facade;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    
    private void handleLogin(String provider, String username, String password) {
        try {
            UsuarioDTO[] usuarioWrapper = new UsuarioDTO[1];

            if ("Strava".equals(provider)) {
                usuarioWrapper[0] = facade.login(username, password);
            } else if ("Google".equals(provider)) {
                usuarioWrapper[0] = facade.loginConProveedor(username, password, "Google");
            } else if ("Meta".equals(provider)) {
                usuarioWrapper[0] = facade.loginConProveedor(username, password, "Meta");
            }

            if (usuarioWrapper[0] != null) {
                JOptionPane.showMessageDialog(this, "¡Inicio de sesión exitoso con " + provider + "!");
                SwingUtilities.invokeLater(() -> new MainAppGUI(usuarioWrapper[0]).setVisible(true));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            }
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión con el servidor. Por favor, intente nuevamente.");
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = createStyledLabel("Username:");
        JTextField usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  
        JLabel passwordLabel = createStyledLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1)); 

        JButton appLoginButton = createStyledButton("Login with App");
        JButton googleLoginButton = createStyledButton("Login with Google");
        JButton metaLoginButton = createStyledButton("Login with Meta");

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.add(appLoginButton);
        buttonPanel.add(googleLoginButton);
        buttonPanel.add(metaLoginButton);
        loginPanel.add(buttonPanel, gbc);

        appLoginButton.addActionListener(e -> handleLogin("Strava", usernameField.getText(), new String(passwordField.getPassword())));
        googleLoginButton.addActionListener(e -> handleLogin("Google", usernameField.getText(), new String(passwordField.getPassword())));
        metaLoginButton.addActionListener(e -> handleLogin("Meta", usernameField.getText(), new String(passwordField.getPassword())));

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
        usernameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  
        JLabel passwordLabel = createStyledLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1)); 
        JLabel emailLabel = createStyledLabel("Email:");
        JTextField emailField = new JTextField(15);
        emailField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  
        JLabel nameLabel = createStyledLabel("Name:");
        JTextField nameField = new JTextField(15);
        nameField.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 1));  

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
        button.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 2));
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

//nueva clase para la ventana principal después del login
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
        tabbedPane.addTab("Entrenamientos", createTrainPanel());
        tabbedPane.addTab("Retos", createRetoPanel());
        tabbedPane.addTab("Amigos", createTabPanel("Contenido de Amigos"));

        add(tabbedPane);

        //listener para el cierre de ventana
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
        
        
        String contrasena = "*".repeat(usuario.getContrasena().length());
        
        Object[][] data = {
            {"Username", usuario.getUsername()},
            {"Email", usuario.getEmail()},
            {"Contrasena", contrasena}, 
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
                return false; 
            }
        };

        JButton modButton = new JButton("Modificar Usuario");
        modButton.setBackground(ORANGE_ACCENT);
        modButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(modButton);        
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(ORANGE_ACCENT);
        logoutButton.setForeground(Color.WHITE);

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                profilePanel,
                "¿Estás seguro de que quieres cerrar sesión?",
                "Confirmar Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(profilePanel, "Sesión cerrada correctamente.");
                dispose();
                new MenuGUI(facade).setVisible(true);
                
            }
        });
        buttonPanel.add(logoutButton);
        

        modButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(7, 2));
            JTextField usernameField = new JTextField(usuario.getUsername());
            JTextField emailField = new JTextField(usuario.getEmail());
            JPasswordField contraField = new JPasswordField(usuario.getContrasena());
            JTextField nameField = new JTextField(usuario.getNombre());
            JTextField weightField = new JTextField(String.valueOf(usuario.getPeso()));
            JTextField heightField = new JTextField(String.valueOf(usuario.getAltura()));

            JLabel dobLabel = new JLabel("Fecha de Nacimiento:");
            com.toedter.calendar.JDateChooser dateChooser = new com.toedter.calendar.JDateChooser();
            dateChooser.setDate(usuario.getfNacimiento());
            dateChooser.setDateFormatString("yyyy-MM-dd");

            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Contrasena:"));
            panel.add(contraField);
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(dobLabel);
            panel.add(dateChooser);
            panel.add(new JLabel("Weight (kg):"));
            panel.add(weightField);
            panel.add(new JLabel("Height (cm):"));
            panel.add(heightField);

            int option = JOptionPane.showConfirmDialog(
                profilePanel,
                panel,
                "Modificar Usuario",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                try {
                    usuario.setUsername(usernameField.getText());
                    usuario.setEmail(emailField.getText());
                    usuario.setNombre(nameField.getText());
                    usuario.setPeso(Float.parseFloat(weightField.getText()));
                    usuario.setAltura(Float.parseFloat(heightField.getText()));

                    char[] passwordChars = contraField.getPassword();
                    usuario.setContrasena(new String(passwordChars));

                    
                    java.util.Date selectedDate = dateChooser.getDate();
                    if (selectedDate != null) {
                        usuario.setfNacimiento(new java.sql.Date(selectedDate.getTime()));
                    }

                    facade.actualizarUsuario(usuario);

                    
                    tableModel.setValueAt(usuario.getUsername(), 0, 1);
                    tableModel.setValueAt(usuario.getEmail(), 1, 1);
                    tableModel.setValueAt("*".repeat(usuario.getContrasena().length()), 2, 1);
                    tableModel.setValueAt(usuario.getfNacimiento(), 3, 1);
                    tableModel.setValueAt(usuario.getNombre(), 4, 1);
                    tableModel.setValueAt(usuario.getPeso(), 5, 1);
                    tableModel.setValueAt(usuario.getAltura(), 6, 1);

                    JOptionPane.showMessageDialog(profilePanel, "Usuario actualizado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(profilePanel, "Error al actualizar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
                
            }
            
        });

        JTable profileTable = new JTable(tableModel);
        profileTable.setFocusable(false);
        profileTable.setRowSelectionAllowed(false);

        profilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        JTableHeader header = profileTable.getTableHeader();
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);

        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);

        return profilePanel;
    }


    private JPanel createTrainPanel() {
        JPanel trainPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Fecha", "ID", "Título", "Duración", "Distancia", "Deporte"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        
        
        
        //cargar entrenamientos
        ArrayList<Entrenamiento> entrenos= usuario.getEntrenamientos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Entrenamiento e: entrenos) {
        	int horas = (int) e.getHoraIni(); // Parte entera (horas)
            int minutos = (int) ((e.getHoraIni() - horas) * 60); // Fracción convertida a minutos

            // Crear un LocalTime
            LocalTime hora = LocalTime.of(horas, minutos);
        	tableModel.addRow(new Object[]{
                    e.getFecIni().toString() + hora.toString(),
                    e.getId(),
                    e.getTitulo(),
                    e.getDuracion(),
                    e.getDistancia(),
                    e.getDeporte()
                });
        }
       
        JTable trainTable = new JTable(tableModel);
        trainTable.setFocusable(false); 
        JTableHeader header = trainTable.getTableHeader();
        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);
        trainPanel.add(new JScrollPane(trainTable), BorderLayout.CENTER);

       
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
            int selectedRow = trainTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un entrenamiento para modificar.");
                return;
            }

            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField titleField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
            JTextField sportField = new JTextField((String) tableModel.getValueAt(selectedRow, 4));
            JTextField durationField = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            JTextField distanceField = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 3)));

            panel.add(new JLabel("Título:"));
            panel.add(titleField);
            panel.add(new JLabel("Deporte:"));
            panel.add(sportField);
            panel.add(new JLabel("Duración (min):"));
            panel.add(durationField);
            panel.add(new JLabel("Distancia (km):"));
            panel.add(distanceField);

            int option = JOptionPane.showConfirmDialog(this, panel, "Modificar Entrenamiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    LocalDateTime dateTime = LocalDateTime.parse((String) tableModel.getValueAt(selectedRow, 0), formatter);
                    
                    LocalDate fecha = dateTime.toLocalDate();
                    LocalTime horaLT = dateTime.toLocalTime();
                    int horas = horaLT.getHour();
                    int minutos = horaLT.getMinute();
                    float hora = horas + minutos / 60.0f;
                    
                    
                    Entrenamiento entrenamiento = new Entrenamiento(
                        selectedRow,
                        UsuarioAssembler.toDomain(usuario),
                        titleField.getText(),
                        sportField.getText(),
                        Float.parseFloat(distanceField.getText()),
                        fecha,
                        hora,
                        Double.parseDouble(durationField.getText())
                    );

                    
                    facade.actualizarEntreno(
                        EntrenamientoAssembler.toDTO(entrenamiento),
                        Float.parseFloat(distanceField.getText()),
                        fecha,
                        hora,
                        Double.parseDouble(durationField.getText())
                    );

                    tableModel.setValueAt(titleField.getText(), selectedRow, 1);
                    tableModel.setValueAt(sportField.getText(), selectedRow, 4);
                    tableModel.setValueAt(Integer.parseInt(durationField.getText()), selectedRow, 2);
                    tableModel.setValueAt(Double.parseDouble(distanceField.getText()), selectedRow, 3);

                    JOptionPane.showMessageDialog(this, "Entrenamiento modificado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al modificar entrenamiento: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }); 
        
        delButton.addActionListener(e -> {
            int selectedRow = trainTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un entrenamiento para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este entrenamiento?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    LocalDateTime dateTime = LocalDateTime.parse((String) tableModel.getValueAt(selectedRow, 0), formatter);
                    
                    LocalDate fecha = dateTime.toLocalDate();
                    LocalTime horaLT = dateTime.toLocalTime();
                    int horas = horaLT.getHour();
                    int minutos = horaLT.getMinute();
                    float hora = horas + minutos / 60.0f;
                    
                    int id= (Integer) tableModel.getValueAt(selectedRow, 1);
                    String titulo = (String) tableModel.getValueAt(selectedRow, 2);
                    int duracion = ((Number) tableModel.getValueAt(selectedRow, 3)).intValue();
                    float distancia = ((Number) tableModel.getValueAt(selectedRow, 4)).floatValue();
                    String deporte = (String) tableModel.getValueAt(selectedRow, 5);

                   
                    ArrayList<Entrenamiento> entrenos1 = UsuarioAssembler.toDomain(usuario).getEntrenamientos();
                    for (Entrenamiento ent: entrenos1) {
                    	if(ent.getId()==id){
                    		 facade.eliminarEntreno(EntrenamientoAssembler.toDTO(ent));
                    	}
                    }
                   

                    tableModel.removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this, "Entrenamiento eliminado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar entrenamiento: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        
        addButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(5, 2));

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
                try {
                    String title = titleField.getText();
                    String sport = sportField.getText();
                    int duration = Integer.parseInt(durationField.getText());
                    float distance = Float.parseFloat(distanceField.getText());
                    LocalDateTime now = LocalDateTime.now();
                    LocalDate fecha = now.toLocalDate();
                    LocalTime horaLT = now.toLocalTime();
                    int horas = horaLT.getHour();
                    int minutos = horaLT.getMinute();
                    float hora = horas + minutos / 60.0f;

                    facade.crearEntreno(
                    	usuario,
                        title,
                        sport,
                        distance,
                        fecha,
                        hora,
                        duration
                    );

                    tableModel.addRow(new Object[]{
                        now.format(formatter),
                        title,
                        duration,
                        distance,
                        sport
                    });

                    JOptionPane.showMessageDialog(this, "Entrenamiento añadido con éxito.");
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
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        

        // Nombres de las columnas con la nueva columna "ID"
        String[] columnNames = {"ID", "Nombre", "Deporte", "Creador", "Fecha Inicio", "Fecha Fin", "Objetivo Distancia", "Objetivo Tiempo", "Progreso"};

        // **Pestaña de retos aceptados**
        JPanel acceptedPanel = new JPanel(new BorderLayout());
        DefaultTableModel acceptedModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 8 ? Integer.class : String.class;
            }
        };
        
        
        
     // Crear JComboBox con las opciones de filtrado
        JComboBox<String> searchCriteria2 = new JComboBox<>(new String[]{"Todos", "Superado", "En Progreso", "No Superado"});

        // Agregar el JComboBox al panel superior
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(searchCriteria2);
        acceptedPanel.add(filterPanel, BorderLayout.NORTH);

        // Llenar el modelo inicial basado en el criterio seleccionado
        HashMap<Reto, String> retosAceptados = usuario.getRetos(); // Asumiendo que usuario.getRetos() devuelve un HashMap<Reto, String>
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Método para llenar la tabla basado en el criterio seleccionado
        Runnable updateTable = () -> {
        	
        	String criteria = (String) searchCriteria2.getSelectedItem();

            // Limpiar todas las filas del modelo
            acceptedModel.setRowCount(0);

            // Filtrar retos basados en el criterio seleccionado
            for (Reto r : retosAceptados.keySet()) {
                String estado = retosAceptados.get(r);
                if ("Todos".equals(criteria) || criteria.equals(estado)) {

                    List<Entrenamiento> entrenamientos = usuario.getEntrenamientos().stream()
                        .filter(e -> e.getDeporte().equalsIgnoreCase(r.getDeporte())) 
                        .collect(Collectors.toList());

                    double totalDistance = entrenamientos.stream()
                        .mapToDouble(Entrenamiento::getDistancia)
                        .sum();

                    int progress = (int) Math.min((totalDistance / r.getObjetivoDistancia()) * 100, 100); 

                    acceptedModel.addRow(new Object[]{
                        r.getId(),
                        r.getNombre(),
                        r.getDeporte(),
                        r.getUsuarioCreador().getUsername(),
                        r.getFecIni().format(formatter),
                        r.getFecFin().format(formatter),
                        r.getObjetivoDistancia(),
                        r.getObjetivoTiempo(),
                        progress
                    });
                }
            }
        };
        

        // Agregar listener al JComboBox para actualizar la tabla al cambiar el filtro
        searchCriteria2.addActionListener(e -> updateTable.run());

        // Llenar la tabla inicialmente con el criterio por defecto ("Todos")
        updateTable.run();

        // Agregar tabla al panel principal
        JTable acceptedTable = new JTable(acceptedModel);
        acceptedTable.setFocusable(false);
        JTableHeader acceptedHeader = acceptedTable.getTableHeader();
        acceptedHeader.setBackground(ORANGE_ACCENT);
        acceptedHeader.setForeground(Color.WHITE);
        
        acceptedTable.getColumnModel().getColumn(8).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JProgressBar progressBar = new JProgressBar(0, 100);
                if (value != null && value instanceof Integer) {
                    progressBar.setValue((int) value);
                    progressBar.setString(value + "%");
                    progressBar.setStringPainted(true);
                }
                return progressBar;
            }
        });


        acceptedPanel.add(new JScrollPane(acceptedTable), BorderLayout.CENTER);
        


        // Botones de acción para modificar y eliminar retos
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
        acceptedPanel.add(buttonPanel, BorderLayout.SOUTH);
     
        // Lógica para añadir un reto
        addButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Margen entre elementos
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            
            

            // Campos del formulario
            JTextField titleField = new JTextField();
            JTextField sportField = new JTextField();
            JTextField creatorField = new JTextField(usuario.getNombre());
            creatorField.setEditable(false); // Solo lectura para el creador
            com.toedter.calendar.JDateChooser startDateField = new com.toedter.calendar.JDateChooser();
            JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
            startTimeSpinner.setEditor(startTimeEditor);
            com.toedter.calendar.JDateChooser finishDateField = new com.toedter.calendar.JDateChooser();
            JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
            endTimeSpinner.setEditor(endTimeEditor);
            JTextField distanceField = new JTextField();
            JTextField timeField = new JTextField();

            // Agregar elementos al panel
            int row = 0;

            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            panel.add(titleField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Deporte:"), gbc);
            gbc.gridx = 1;
            panel.add(sportField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Creador:"), gbc);
            gbc.gridx = 1;
            panel.add(creatorField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Fecha Inicio:"), gbc);
            gbc.gridx = 1;
            panel.add(startDateField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Hora de Inicio:"), gbc);
            gbc.gridx = 1;
            panel.add(startTimeSpinner, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Fecha Fin:"), gbc);
            gbc.gridx = 1;
            panel.add(finishDateField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Hora de Fin:"), gbc);
            gbc.gridx = 1;
            panel.add(endTimeSpinner, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Objetivo Distancia (km):"), gbc);
            gbc.gridx = 1;
            panel.add(distanceField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Objetivo Tiempo (horas):"), gbc);
            gbc.gridx = 1;
            panel.add(timeField, gbc);

            // Mostrar el formulario en un JOptionPane
            int option = JOptionPane.showConfirmDialog(
                null, 
                panel, 
                "Añadir Reto", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Fechas
                    Date startDate = startDateField.getDate();
                    Date endDate = finishDateField.getDate();

                    // Horas
                    Date startTime = (Date) startTimeSpinner.getValue();
                    Date endTime = (Date) endTimeSpinner.getValue();

                    // Combinar fecha y hora en LocalDateTime
                    LocalDateTime fecIni = LocalDateTime.ofInstant(
                        startDate.toInstant(), ZoneId.systemDefault()
                    ).with(LocalTime.of(startTime.getHours(), startTime.getMinutes(), startTime.getSeconds()));

                    LocalDateTime fecFin = LocalDateTime.ofInstant(
                        endDate.toInstant(), ZoneId.systemDefault()
                    ).with(LocalTime.of(endTime.getHours(), endTime.getMinutes(), endTime.getSeconds()));

                    // Validar que la fecha de inicio sea anterior a la fecha de fin
                    if (fecIni.isAfter(fecFin)) {
                        throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
                    }

                    Reto reto = new Reto(
                        0, // ID se generará
                        sportField.getText(),
                        UsuarioAssembler.toDomain(usuario),
                        titleField.getText(),
                        fecIni,
                        fecFin,
                        Float.parseFloat(distanceField.getText()),
                        Float.parseFloat(timeField.getText()),
                        new ArrayList<>()
                    );

                    // Llamar al método del facade para guardar el reto
                    facade.crearReto(
                        titleField.getText(),
                        fecIni,
                        fecFin,
                        Float.parseFloat(distanceField.getText()),
                        Float.parseFloat(timeField.getText()),
                        sportField.getText(),
                        usuario,
                        new ArrayList<>()
                    );

                    // Añadir el reto al modelo de la tabla
                    acceptedModel.addRow(new Object[]{
                        reto.getId(),
                        reto.getNombre(),
                        reto.getDeporte(),
                        reto.getUsuarioCreador().getUsername(),
                        reto.getFecIni().format(formatter),
                        reto.getFecFin().format(formatter),
                        reto.getObjetivoDistancia(),
                        reto.getObjetivoTiempo()
                    });
                    
                    usuario.getRetos().put(reto, "prueba");
                    facade.actualizarUsuario(usuario);

                    JOptionPane.showMessageDialog(null, "Reto añadido con éxito.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Distancia y tiempo deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

     // Lógica para modificar un reto
        modButton.addActionListener(e -> {
            int selectedRow = acceptedTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un reto para modificar.");
                return;
            }

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Margen entre elementos
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;

            // Extraer datos actuales
            String currentTitle = (String) acceptedModel.getValueAt(selectedRow, 1);
            String currentSport = (String) acceptedModel.getValueAt(selectedRow, 2);
            String currentStartDateTime = (String) acceptedModel.getValueAt(selectedRow, 4);
            String currentEndDateTime = (String) acceptedModel.getValueAt(selectedRow, 5);
            float currentDistance = 0;
            float currentTime = 0;

            try {
                // Convertir el valor a String y luego a float
                currentDistance = Float.parseFloat(acceptedModel.getValueAt(selectedRow, 6).toString());
                currentTime = Float.parseFloat(acceptedModel.getValueAt(selectedRow, 7).toString());
            } catch (NumberFormatException a) {
                // Manejar el caso cuando la conversión falla
                System.out.println("Error al convertir el valor a float: " + a.getMessage());
            }

            // Campos del formulario
            JTextField titleField = new JTextField(currentTitle);
            JTextField sportField = new JTextField(currentSport);
            JTextField creatorField = new JTextField(usuario.getNombre());
            creatorField.setEditable(false); // Solo lectura para el creador

            com.toedter.calendar.JDateChooser startDateField = new com.toedter.calendar.JDateChooser();
            com.toedter.calendar.JDateChooser finishDateField = new com.toedter.calendar.JDateChooser();

            // Parsear las fechas actuales
            LocalDateTime currentStart = LocalDateTime.parse(currentStartDateTime, formatter);
            LocalDateTime currentEnd = LocalDateTime.parse(currentEndDateTime, formatter);

            startDateField.setDate(java.sql.Date.valueOf(currentStart.toLocalDate()));
            finishDateField.setDate(java.sql.Date.valueOf(currentEnd.toLocalDate()));

            // Hora de inicio
            JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
            startTimeSpinner.setEditor(startTimeEditor);
            startTimeSpinner.setValue(java.sql.Time.valueOf(currentStart.toLocalTime()));

            // Hora de fin
            JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
            endTimeSpinner.setEditor(endTimeEditor);
            endTimeSpinner.setValue(java.sql.Time.valueOf(currentEnd.toLocalTime()));

            JTextField distanceField = new JTextField(String.valueOf(currentDistance));
            JTextField timeField = new JTextField(String.valueOf(currentTime));

            // Agregar elementos al panel
            int row = 0;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            panel.add(titleField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Deporte:"), gbc);
            gbc.gridx = 1;
            panel.add(sportField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Creador:"), gbc);
            gbc.gridx = 1;
            panel.add(creatorField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Fecha Inicio:"), gbc);
            gbc.gridx = 1;
            panel.add(startDateField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Hora de Inicio:"), gbc);
            gbc.gridx = 1;
            panel.add(startTimeSpinner, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Fecha Fin:"), gbc);
            gbc.gridx = 1;
            panel.add(finishDateField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Hora de Fin:"), gbc);
            gbc.gridx = 1;
            panel.add(endTimeSpinner, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Objetivo Distancia:"), gbc);
            gbc.gridx = 1;
            panel.add(distanceField, gbc);

            row++;
            gbc.gridx = 0; gbc.gridy = row;
            panel.add(new JLabel("Objetivo Tiempo:"), gbc);
            gbc.gridx = 1;
            panel.add(timeField, gbc);

            int option = JOptionPane.showConfirmDialog(this, panel, "Modificar Reto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    // Fechas
                    Date startDate = startDateField.getDate();
                    Date endDate = finishDateField.getDate();

                    // Horas
                    Date startTime = (Date) startTimeSpinner.getValue();
                    Date endTime = (Date) endTimeSpinner.getValue();

                    // Combinar fecha y hora en LocalDateTime
                    LocalDateTime fecIni = LocalDateTime.ofInstant(
                        startDate.toInstant(), ZoneId.systemDefault()
                    ).with(LocalTime.of(startTime.getHours(), startTime.getMinutes(), startTime.getSeconds()));

                    LocalDateTime fecFin = LocalDateTime.ofInstant(
                        endDate.toInstant(), ZoneId.systemDefault()
                    ).with(LocalTime.of(endTime.getHours(), endTime.getMinutes(), endTime.getSeconds()));

                    // Validar que la fecha de inicio sea anterior a la fecha de fin
                    if (fecIni.isAfter(fecFin)) {
                        throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");
                    }

                    // Obtener el reto actual por ID (clave)
                    Reto retoActual = usuario.getRetos().keySet()
                        .stream()
                        .filter(r -> r.getId() == selectedRow) // Asumimos que el ID coincide con la fila seleccionada
                        .findFirst()
                        .orElse(null);

                    if (retoActual != null) {
                        // Eliminar el reto anterior del Map
                        usuario.getRetos().remove(retoActual);
                    }

                    // Crear el reto actualizado
                    Reto retoActualizado = new Reto(
                        selectedRow, // ID del reto
                        sportField.getText(),
                        UsuarioAssembler.toDomain(usuario),
                        titleField.getText(),
                        fecIni,
                        fecFin,
                        Float.parseFloat(distanceField.getText()),
                        Float.parseFloat(timeField.getText()),
                        new ArrayList<>()
                    );

                    // Actualizar la información en el backend
                    facade.actualizarReto(
                        RetoAssembler.toDTO(retoActualizado),
                        titleField.getText(),
                        fecIni,
                        fecFin,
                        Float.parseFloat(distanceField.getText()),
                        Float.parseFloat(timeField.getText()),
                        usuario,
                        sportField.getText(),
                        new ArrayList<>()
                    );

                    // Agregar el nuevo reto al Map
                    usuario.getRetos().put(retoActualizado, "prueba");

                    // Actualizar la tabla
                    acceptedModel.setValueAt(titleField.getText(), selectedRow, 1);
                    acceptedModel.setValueAt(sportField.getText(), selectedRow, 2);
                    acceptedModel.setValueAt(formatter.format(fecIni), selectedRow, 4);
                    acceptedModel.setValueAt(formatter.format(fecFin), selectedRow, 5);
                    acceptedModel.setValueAt(distanceField.getText(), selectedRow, 6);
                    acceptedModel.setValueAt(timeField.getText(), selectedRow, 7);

                    // Actualizar el usuario en el backend
                    facade.actualizarUsuario(usuario);
                    JOptionPane.showMessageDialog(this, "Reto actualizado con éxito.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al modificar el reto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        // Lógica para eliminar un reto
        delButton.addActionListener(e -> {
            int selectedRow = acceptedTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un reto para eliminar.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este reto?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int retoId = (int) acceptedModel.getValueAt(selectedRow, 0);
                    HashMap<Integer,RetoDTO> retosD= facade.visualizarReto();
                    
                    if (usuario.equals(retosD.get(retoId).getUsuarioCreador())) {
                        //eliminar reto completo
                        System.out.println("El creador elimina el reto.");
                        for(Usuario participante: retosD.get(retoId).getParticipantes()) {
                        	participante.getRetos().remove(retosD.get(retoId).getId());
                        	facade.actualizarUsuario(UsuarioAssembler.toDTO(participante));
                     
                        }
                        facade.eliminarReto(usuario, retosD.get(retoId));
                        
                    } else {
                        //eliminar al participante de la lista
                    	
                    	
                    	ArrayList<UsuarioDTO> participantesDTO= new ArrayList<>();
                        for (Usuario u: retosD.get(retoId).getParticipantes()) {
                        	if(u.getId()==usuario.getId()) {
                            	usuario.getRetos().remove(retosD.get(retoId));
                        	}
                        	else {
                        		participantesDTO.add(UsuarioAssembler.toDTO(u));
                        	}
                        }
                        
                    	facade.actualizarReto(retosD.get(retoId),retosD.get(retoId).getNombre(),
                    			retosD.get(retoId).getFecIni(), 
                    			retosD.get(retoId).getFecFin(), retosD.get(retoId).getObjetivoDistancia(), 
                    			retosD.get(retoId).getObjetivoTiempo(), 
                    			UsuarioAssembler.toDTO(retosD.get(retoId).getUsuarioCreador()),
                    			retosD.get(retoId).getDeporte(), participantesDTO);
                    	
                    	facade.actualizarUsuario(usuario);
                    	
                    	facade.eliminarReto(usuario, retosD.get(retoId));
                    	
                        System.out.println("El usuario se elimina del reto.");
                    }
                    

                    acceptedModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Reto eliminado con éxito.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el reto: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // **Pestaña de añadir reto**
        
        
        JPanel addPanel = new JPanel(new BorderLayout());

        // Definir las columnas para la tabla
        String[] columnNames1 = {"ID", "Nombre", "Deporte", "Creador", "Fecha Inicio", "Fecha Fin", "Objetivo Distancia", "Objetivo Tiempo"};

        // Crear el modelo de la tabla para mostrar los retos disponibles para aceptar
        DefaultTableModel acceptModel = new DefaultTableModel(columnNames1, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que las celdas sean editables
            }
        };

        // Crear la tabla para mostrar los retos disponibles
        JTable acceptTable = new JTable(acceptModel);
        JTableHeader acceptHeader = acceptTable.getTableHeader();
        acceptHeader.setBackground(ORANGE_ACCENT);
        acceptHeader.setForeground(Color.WHITE);
        addPanel.add(new JScrollPane(acceptTable), BorderLayout.CENTER);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Buscar por:");
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Nombre", "Deporte"});
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        addPanel.add(searchPanel, BorderLayout.NORTH);

        // Funcionalidad de búsqueda en los retos disponibles
        searchButton.addActionListener(e -> {
            String criteria = (String) searchCriteria.getSelectedItem();
            String searchTerm = searchField.getText().toLowerCase();
            acceptModel.setRowCount(0); // Limpiar la tabla antes de buscar

            HashMap<Integer, RetoDTO> retosDisponibles= null;
            try {
                retosDisponibles = facade.visualizarReto();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            if (retosDisponibles != null) {
                for (RetoDTO r : retosDisponibles.values()) {
                    if ((criteria.equals("Nombre") && r.getNombre().toLowerCase().contains(searchTerm)) ||
                        (criteria.equals("Deporte") && r.getDeporte().toLowerCase().contains(searchTerm))) {
                        acceptModel.addRow(new Object[]{
                            r.getId(),
                            r.getNombre(),
                            r.getDeporte(),
                            r.getUsuarioCreador().getUsername(),
                            r.getFecIni().toString(),
                            r.getFecFin().toString(),
                            r.getObjetivoDistancia(),
                            r.getObjetivoTiempo()
                        });
                    }
                }
            }
        });

        // Botón para aceptar el reto seleccionado
        JButton acceptSelectedButton = new JButton("Aceptar Reto");
        acceptSelectedButton.setBackground(ORANGE_ACCENT);
        acceptSelectedButton.setForeground(Color.WHITE);
        searchPanel.add(acceptSelectedButton);

        // Acción para aceptar el reto seleccionado
        acceptSelectedButton.addActionListener(e -> {
            int selectedRow = acceptTable.getSelectedRow();
            if (selectedRow != -1) {
                int idReto = (int) acceptModel.getValueAt(selectedRow, 0);
                HashMap<Integer, RetoDTO> retosDisponibles = null;

                try {
                    retosDisponibles = facade.visualizarReto();  // Recuperar retos desde el servidor
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

                if (retosDisponibles != null && retosDisponibles.containsKey(idReto)) {
                    RetoDTO retoSeleccionado = retosDisponibles.get(idReto);

                    // Verificar si el usuario ya ha aceptado el reto
                    if (!retoSeleccionado.getParticipantes().stream().anyMatch(participante -> participante.getUsername().equals(usuario.getUsername()))) {
                    	
                    	
                  
                        retoSeleccionado.getParticipantes().add(UsuarioAssembler.toDomain(usuario));
                    	
                        
                        ArrayList<UsuarioDTO> participantesDTO= new ArrayList<>();
                        for (Usuario u: retoSeleccionado.getParticipantes()) {
                        	participantesDTO.add(UsuarioAssembler.toDTO(u));
                        }
                        
                        
                        try {
							facade.actualizarReto(retoSeleccionado,retoSeleccionado.getNombre(),
									retoSeleccionado.getFecIni(), retoSeleccionado.getFecFin(), retoSeleccionado.getObjetivoDistancia(),
									retoSeleccionado.getObjetivoTiempo(), UsuarioAssembler.toDTO(retoSeleccionado.getUsuarioCreador()), 
									retoSeleccionado.getDeporte(),participantesDTO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                        usuario.getRetos().put(RetoAssembler.toDomain(retoSeleccionado), "En Progreso");
                        // Agregar el reto a la lista de retos aceptados
                        acceptedModel.addRow(new Object[]{
                            retoSeleccionado.getId(),
                            retoSeleccionado.getNombre(),
                            retoSeleccionado.getDeporte(),
                            retoSeleccionado.getUsuarioCreador().getUsername(),
                            retoSeleccionado.getFecIni().toString(),
                            retoSeleccionado.getFecFin().toString(),
                            retoSeleccionado.getObjetivoDistancia(),
                            retoSeleccionado.getObjetivoTiempo()
                        });
                        JOptionPane.showMessageDialog(addPanel, "Reto aceptado con éxito.");
                    }
                    	
                    	
                    	
                    	else {
                        JOptionPane.showMessageDialog(addPanel, "Ya has aceptado este reto.");
                    
                    }
                }
            } else {
                JOptionPane.showMessageDialog(addPanel, "Selecciona un reto válido.");
            }
        });
        tabbedPane.addTab("Mis Retos", acceptedPanel);
        tabbedPane.addTab("Añadir Reto", addPanel);
        
        
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        return mainPanel;
    }
    
    
    
    
   

    private JPanel createTabPanel(String content) {

        // Crear el panel principal y los subpaneles
        JTabbedPane tabbedPanel2 = new JTabbedPane();
        JPanel mainPanel2 = new JPanel(new BorderLayout());
        JPanel misAmigos = new JPanel(new BorderLayout());   
        JPanel addAmigos = new JPanel(new BorderLayout());

     // **Mis Amigos Tab Implementation**
        // Definir columnas para la tabla de amigos
        String[] amigoColumnNames = {"ID", "Username", "Email"};

        // Crear el modelo de la tabla para mostrar los amigos actuales
        DefaultTableModel amigoModel = new DefaultTableModel(amigoColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que las celdas sean editables
            }
        };

        // Crear la tabla para mostrar los amigos actuales
        JTable amigoTable = new JTable(amigoModel);
        JTableHeader amigoHeader = amigoTable.getTableHeader();
        amigoHeader.setBackground(ORANGE_ACCENT);
        amigoHeader.setForeground(Color.WHITE);

        misAmigos.add(new JScrollPane(amigoTable), BorderLayout.CENTER);

        // Cargar amigos actuales al inicializar la tabla
        try {
            amigoModel.setRowCount(0); // Limpiar cualquier dato previo
            ArrayList<UsuarioDTO> amigos = facade.getAmigos(usuario);

            for (UsuarioDTO amigo : amigos) {
                amigoModel.addRow(new Object[]{amigo.getId(), amigo.getUsername(), amigo.getEmail()});
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(misAmigos, "Error al cargar los amigos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
		
    	

        // **Añadir Amigos Tab Implementation**
        // Definir las columnas para la tabla
        String[] columnNames = {"ID", "Username", "Email"};

        // Crear el modelo de la tabla para mostrar los usuarios disponibles para añadir como amigos
        DefaultTableModel userModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar que las celdas sean editables
            }
        };

        // Crear la tabla para mostrar los usuarios disponibles
        JTable userTable = new JTable(userModel);
        JTableHeader userHeader = userTable.getTableHeader();
        userHeader.setBackground(ORANGE_ACCENT);
        userHeader.setForeground(Color.WHITE);
        addAmigos.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Buscar por:");
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"ID", "Username", "Email"});
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");
        searchPanel.add(searchLabel);
        searchPanel.add(searchCriteria);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        addAmigos.add(searchPanel, BorderLayout.NORTH);

        // Cargar todos los usuarios que no son amigos al inicializar la tabla
        try {
            userModel.setRowCount(0); // Limpiar la tabla por si hay datos previos
            HashMap<Integer, UsuarioDTO> usuarios = facade.getUsuarios();
            ArrayList<UsuarioDTO> amigos = facade.getAmigos(usuario);

            for (UsuarioDTO user : usuarios.values()) {
                if (!amigos.contains(user) && !user.getUsername().equals(usuario.getUsername())) {
                    userModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getEmail()});
                }
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(addAmigos, "Error al cargar los usuarios iniciales.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Funcionalidad de búsqueda en la tabla
        searchButton.addActionListener(e -> {
            String criteria = (String) searchCriteria.getSelectedItem();
            String searchTerm = searchField.getText().trim().toLowerCase();

            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(addAmigos, "Por favor, introduzca un término de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                userModel.setRowCount(0); // Limpiar la tabla antes de buscar
                HashMap<Integer, UsuarioDTO> usuarios = facade.getUsuarios();
                ArrayList<UsuarioDTO> amigos = facade.getAmigos(usuario);

                for (UsuarioDTO user : usuarios.values()) {
                    if (amigos.contains(user)) continue; // Excluir amigos

                    boolean matches = switch (criteria) {
                        case "ID" -> String.valueOf(user.getId()).contains(searchTerm);
                        case "Username" -> user.getUsername().toLowerCase().contains(searchTerm);
                        case "Email" -> user.getEmail().toLowerCase().contains(searchTerm);
                        default -> false;
                    };

                    if (matches) {
                        userModel.addRow(new Object[]{user.getId(), user.getUsername(), user.getEmail()});
                    }
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(addAmigos, "Error al buscar usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón para añadir amigo seleccionado
        JButton addFriendButton = new JButton("Añadir amigo");
        addFriendButton.setBackground(ORANGE_ACCENT);
        addFriendButton.setForeground(Color.WHITE);
        addAmigos.add(addFriendButton, BorderLayout.SOUTH);

        // Acción para añadir amigo seleccionado
        addFriendButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                int userId = (int) userModel.getValueAt(selectedRow, 0);

                try {
                    UsuarioDTO currentUser = usuario;
                    UsuarioDTO selectedUser = facade.getUsuarios().get(userId);

                    // Verificar si el usuario ya es amigo
                    ArrayList<UsuarioDTO> amigos = facade.getAmigos(currentUser);
                    if (amigos.contains(selectedUser)) {
                        JOptionPane.showMessageDialog(addAmigos, "Este usuario ya es tu amigo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Añadir el amigo
                    currentUser.getAmigos().add(UsuarioAssembler.toDomain(selectedUser));
                    facade.actualizarUsuario(currentUser);
                    
                    
                    JOptionPane.showMessageDialog(addAmigos, "Amigo añadido con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Eliminar el usuario añadido de la tabla
                    userModel.removeRow(selectedRow);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addAmigos, "Error al intentar añadir al amigo. Por favor, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
             // **Mis Amigos Tab Implementation**
                

               
                // Cargar amigos actuales al inicializar la tabla
                try {
                    amigoModel.setRowCount(0); // Limpiar cualquier dato previo
                    ArrayList<UsuarioDTO> amigos = facade.getAmigos(usuario);

                    for (UsuarioDTO amigo : amigos) {
                        amigoModel.addRow(new Object[]{amigo.getId(), amigo.getUsername(), amigo.getEmail()});
                    }
                    amigoTable.setModel(amigoModel);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(misAmigos, "Error al cargar los amigos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
        		
            	
                
            } else {
                JOptionPane.showMessageDialog(addAmigos, "Seleccione un usuario para añadir como amigo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Agregar las pestañas al panel
        tabbedPanel2.addTab("Mis Amigos", misAmigos);
        tabbedPanel2.addTab("Añadir Amigo", addAmigos);

        mainPanel2.add(tabbedPanel2, BorderLayout.CENTER);
        return mainPanel2;
    }
}
