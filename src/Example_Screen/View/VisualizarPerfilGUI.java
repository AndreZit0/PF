package Example_Screen.View;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class VisualizarPerfilGUI {
    public JPanel panel1;
    private JComboBox estado;
    private JTextField conta;
    private JTextField direc;
    private JTextField email;
    private JTextField num_doc;
    private JTextField nombre;
    private JComboBox rol;
    private JComboBox tipo_doc;
    private JTextField apellido;
    private int userID; // Para almacenar el ID del usuario actual


    public VisualizarPerfilGUI() {


        cargarDatosUsuario();
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        tipo_doc.setEnabled(false);
        num_doc.setEnabled(false);
        email.setEnabled(false);
        direc.setEnabled(false);
        conta.setEnabled(false);
        rol.setEnabled(false);
        estado.setEnabled(false);

        nombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        apellido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tipo_doc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        num_doc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        email.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        direc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        conta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        estado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void cargarDatosUsuario()
    {
        String usuarioEmail = obtenerUsuarioActual();
        if (usuarioEmail == null || usuarioEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del usuario actual.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuarioEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Almacenar ID para futuras actualizaciones
                userID = rs.getInt("ID_usuarios");

                // Cargar datos a los campos
                nombre.setText(rs.getString("nombres"));
                apellido.setText(rs.getString("apellidos"));
                num_doc.setText(rs.getString("numero"));
                email.setText(rs.getString("email"));
                direc.setText(rs.getString("direccion"));
                conta.setText(rs.getString("contacto1"));

                // Seleccionar el tipo de documento en el comboBox
                String tipoDocumento = rs.getString("tipo_dc");
                for (int i = 0; i < tipo_doc.getItemCount(); i++) {
                    if (tipo_doc.getItemAt(i).toString().equals(tipoDocumento)) {
                        tipo_doc.setSelectedIndex(i);
                        break;
                    }
                }

                // Seleccionar el rol en el comboBox
                int idRol = rs.getInt("ID_rol");
                for (int i = 0; i < rol.getItemCount(); i++) {
                    // Asumiendo que los items del comboBox tienen el mismo orden que los IDs
                    // Esto podría necesitar ajustes según cómo esté configurado tu comboBox
                    if (i + 1 == idRol) {
                        rol.setSelectedIndex(i);
                        break;
                    }
                }

                // Seleccionar el estado en el comboBox
                String estadoUsuario = rs.getString("estado");
                for (int i = 0; i < estado.getItemCount(); i++) {
                    if (estado.getItemAt(i).toString().equals(estadoUsuario)) {
                        estado.setSelectedIndex(i);
                        break;
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró información del usuario en la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    public String obtenerUsuarioActual() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("config.properties")) {
            props.load(in);
            return props.getProperty("usuario", "");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
