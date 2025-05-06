package Example_Screen.View.Login;

import Example_Screen.Connection.DBConnection;
import Example_Screen.Model.Usuario;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import Example_Screen.View.Administrador.*;
import Example_Screen.View.Aprendiz.AprendizGUI;
//import Example_Screen.View.Aprendiz.*;

public class LoginGUI {
    private JPanel main;
    private JTextField TexfUsuario;
    private JPasswordField TexfContraseña;
    private JButton ingresarButton;
    private final String CONFIG_PATH = "config.properties";

    Administrador administrador = new Administrador();

    public LoginGUI() {


        ingresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Border bottom1 = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        TexfUsuario.setBorder(bottom1);
        TexfContraseña.setBorder(bottom1);

        cargarUsuario();

        TexfUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {  // Si se presiona Enter
                    ingresarButton.doClick();
                }
            }
        });

        TexfContraseña.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {  // Si se presiona Enter
                    ingresarButton.doClick();
                }
            }
        });


        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = TexfUsuario.getText().trim();
                String contraseña = new String(TexfContraseña.getPassword());

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "SELECT * FROM usuarios WHERE email = ? AND clave = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, usuario);
                    stmt.setString(2, contraseña);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        Usuario user = new Usuario(
                                rs.getString("nombres"),
                                rs.getString("id_rol")
                        );

                        guardarUsuario(usuario);

                        switch (user.getRol().toLowerCase()) {
                            case "6":
                                Administrador admi = new Administrador();
                                admi.Admin_Screen();
                                break;
                            case "1":
                                new AprendizGUI();
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        SwingUtilities.getWindowAncestor(main).dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void guardarUsuario(String usuario) {
        Properties props = new Properties();
        props.setProperty("usuario", usuario);
        try (FileOutputStream out = new FileOutputStream(CONFIG_PATH)) {
            props.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarUsuario() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_PATH)) {
            props.load(in);
            String usuarioGuardado = props.getProperty("usuario", "");
            TexfUsuario.setText(usuarioGuardado);
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LOGIN");
        LoginGUI loginGUI = new LoginGUI();
        frame.setContentPane(loginGUI.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        URL iconoURL = LoginGUI.class.getClassLoader().getResource("Example_Screen/img/SENA.png");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea salir?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }
}
