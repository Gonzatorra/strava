package es.deusto.sd.strava.gui;

import javax.swing.*;
import es.deusto.sd.strava.*;
import es.deusto.sd.strava.DTO.UsuarioDTO;
import es.deusto.sd.strava.fachada.IRemoteFacade;
import java.awt.*;
import java.rmi.Naming;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.io.Serializable;


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
                    new MainAppGUI(usuario).setVisible(true);
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
        tabbedPane.addTab("Mis Entrenamientos", createTabPanel("Contenido de Mis Entrenamientos"));
        tabbedPane.addTab("Retos", createTabPanel("Contenido de Retos"));
        tabbedPane.addTab("Amigos", createTabPanel("Contenido de Amigos"));

        add(tabbedPane);
    }

    private JPanel createProfilePanel() {
    	System.out.println(usuario.getUsername());
        System.out.println(usuario.getEmail());
        System.out.println(usuario.getContrasena());
        System.out.println(usuario.getNombre());
        System.out.println(usuario.getPeso());
        System.out.println(usuario.getAltura());
        JPanel profilePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Atributo", "Valor"};
        Object[][] data = {
            {"Username", usuario.getUsername()},
            {"Email", usuario.getEmail()},
            {"Contraseña", usuario.getContrasena()},
            {"Nombre", usuario.getNombre()},
            {"Peso", usuario.getPeso()},
            {"Altura", usuario.getAltura()},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Solo permite editar la columna "Valor"
            }
        };

        JTable profileTable = new JTable(model);
        profileTable.setRowHeight(30); // Altura de las filas
        JTableHeader header = profileTable.getTableHeader();
        header.setBackground(ORANGE_ACCENT);
        header.setForeground(Color.WHITE);

        profileTable.setFont(new Font("Arial", Font.PLAIN, 12));
        profileTable.setGridColor(ORANGE_ACCENT);

        // Boton para guardar los cambios
        JButton updateButton = new JButton("Guardar Cambios");
        updateButton.setBackground(ORANGE_ACCENT);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 12));
        updateButton.setFocusPainted(false);
        updateButton.setBorder(BorderFactory.createLineBorder(ORANGE_ACCENT, 2));

        updateButton.addActionListener(e -> {
        	try {
        	    // Recuperar datos editados
        	    String updatedUsername = (String) model.getValueAt(0, 1);
        	    String updatedEmail = (String) model.getValueAt(1, 1);
        	    String updatedPassword = (String) model.getValueAt(2, 1);
        	    String updatedName = (String) model.getValueAt(3, 1);
        	    float updatedWeight = Float.parseFloat(model.getValueAt(4, 1).toString());
        	    float updatedHeight = Float.parseFloat(model.getValueAt(5, 1).toString());

        	    // Crear un nuevo objeto UsuarioDTO con los nuevos valores (no modificar directamente el original)
        	    UsuarioDTO nuevoUsuario = new UsuarioDTO();  // Crear un nuevo objeto para evitar modificar el original
        	    nuevoUsuario.setUsername(updatedUsername);
        	    nuevoUsuario.setEmail(updatedEmail);
        	    nuevoUsuario.setContrasena(updatedPassword);
        	    nuevoUsuario.setNombre(updatedName);
        	    nuevoUsuario.setPeso(updatedWeight);
        	    nuevoUsuario.setAltura(updatedHeight);

        	    // Llamar al metodo remoto para actualizar
        	    facade.actualizarUsuario(nuevoUsuario);
        	    
        	    JOptionPane.showMessageDialog(this, "¡Usuario actualizado correctamente!");
        	    System.out.println("Nuevo usuario:");
        	    System.out.println(nuevoUsuario.getUsername());
        	    System.out.println(nuevoUsuario.getEmail());
        	    System.out.println(nuevoUsuario.getContrasena());
        	    System.out.println(nuevoUsuario.getNombre());
        	    System.out.println(nuevoUsuario.getPeso());
        	    System.out.println(nuevoUsuario.getAltura());
        	} catch (Exception a) {
        	    a.printStackTrace();
        	}

        });
        
        


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);

        profilePanel.add(new JScrollPane(profileTable), BorderLayout.CENTER);
        profilePanel.add(buttonPanel, BorderLayout.SOUTH);

        return profilePanel;
    }


    private JPanel createTabPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label);
        return panel;
    }
    
    
}
