package Example_Screen.View.Administrador;

import Example_Screen.Connection.DBConnection;
import Example_Screen.Model.Usuarios;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Esta clase es para la pantalla donde el administrador,puede ver y cambiar sus propios datos.
 */
public class Editar_Admin{

    private JPanel panel1;
    private JButton confirmar️Button;
    private JComboBox estado;
    private JComboBox rol;
    private JTextField nombre;
    private JComboBox tipo_doc;
    private JTextField num_doc;
    private JTextField email;
    private JTextField direc;
    private JTextField conta;
    private JButton editarPerfil️Button;
    private JButton cancelar;
    private JTextField apellido;
    private JTextField textField1;

    // Variables para almacenar los datos originales
    private String originalNombre;
    private String originalApellido;
    private String originalTipoDoc;
    private String originalNumDoc;
    private String originalEmail;
    private String originalDireccion;
    private String originalContacto;
    private String originalRol;
    private String originalEstado;
    private int userID; // Para almacenar el ID del usuario actual

    /**
     * Este metodo es para que otras partes del programa puedan agarrar el panel principal
     * de esta pantalla y mostrarlo donde necesiten.
     * @return El panel1 con todos los componentes de la interfaz. Es el que se muestra.
     */
    public JPanel getPanel() {
        return panel1;
    }
    /**
     * El constructor, donde se arma toda la pantalla.
     * Aquí se configuran los botones, se cargan los datos del usuario
     * y se preparan las acciones para cuando el usuario hace clic.
     */
    public Editar_Admin(){

        textField1.setVisible(false);
        confirmar️Button.setEnabled(false);
        cancelar.setEnabled(false);

        nombre.setEnabled(false);
        apellido.setEnabled(false);
        tipo_doc.setEnabled(false);
        num_doc.setEnabled(false);
        email.setEnabled(false);
        direc.setEnabled(false);
        conta.setEnabled(false);
        rol.setEnabled(false);
        estado.setEnabled(false);


        JButton[] botones = {editarPerfil️Button, cancelar, confirmar️Button};

        for (JButton btn : botones) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        Color colorHover = new Color(0, 120, 50);
        Color colorBase = new Color(57, 169, 0);
        aplicarEfectoHover(confirmar️Button, colorHover, colorBase);

        Color colorBaseA = new Color(0,122,255);
        Color colorHoverA = new Color(102, 153, 255);
        aplicarEfectoHover(editarPerfil️Button, colorHoverA, colorBaseA);

        Color colorBaseR = new Color(255,59,48);
        Color colorHoverR = new Color(255, 102, 102);
        aplicarEfectoHover(cancelar, colorHoverR, colorBaseR);


        Border bottom1 = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#39A900"));
        nombre.setBorder(bottom1);
        apellido.setBorder(bottom1);
        tipo_doc.setBorder(bottom1);
        num_doc.setBorder(bottom1);
        email.setBorder(bottom1);
        direc.setBorder(bottom1);
        conta.setBorder(bottom1);


        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));

        cargarDatosUsuario();

        editarPerfil️Button.addActionListener(new ActionListener() {
            /**
             * Cuando le pican al botón de "Editar Perfil".
             * Lo que hace es dejar que el usuario cambie los datos en los campos.
             * @param e El evento del clic, o sea, la acción de haber hecho clic.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setBorder(bottom);
                apellido.setBorder(bottom);
                num_doc.setBorder(bottom);
                email.setBorder(bottom);
                direc.setBorder(bottom);
                conta.setBorder(bottom);

                nombre.setEnabled(true);
                apellido.setEnabled(true);
                tipo_doc.setEnabled(true);
                num_doc.setEnabled(true);
                email.setEnabled(true);
                direc.setEnabled(true);
                conta.setEnabled(true);
                rol.setEnabled(true);
                estado.setEnabled(true);

                editarPerfil️Button.setEnabled(false);
                confirmar️Button.setEnabled(true);
                cancelar.setEnabled(true);

            }
        });
        confirmar️Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                actualizarDatosUsuario();

                nombre.setEnabled(false);
                apellido.setEnabled(false);
                tipo_doc.setEnabled(false);
                num_doc.setEnabled(false);
                email.setEnabled(false);
                direc.setEnabled(false);
                conta.setEnabled(false);
                rol.setEnabled(false);
                estado.setEnabled(false);

                editarPerfil️Button.setEnabled(true);
                cancelar.setEnabled(false);
                confirmar️Button.setEnabled(false);

                nombre.setBorder(bottom1);
                apellido.setBorder(bottom1);
                tipo_doc.setBorder(bottom1);
                num_doc.setBorder(bottom1);
                email.setBorder(bottom1);
                direc.setBorder(bottom1);
                conta.setBorder(bottom1);
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                restaurarDatosOriginales();
                nombre.setEnabled(false);
                apellido.setEnabled(false);
                tipo_doc.setEnabled(false);
                num_doc.setEnabled(false);
                email.setEnabled(false);
                direc.setEnabled(false);
                conta.setEnabled(false);
                rol.setEnabled(false);
                estado.setEnabled(false);

                editarPerfil️Button.setEnabled(true);
                cancelar.setEnabled(false);
                confirmar️Button.setEnabled(false);

                nombre.setBorder(bottom1);
                apellido.setBorder(bottom1);
                tipo_doc.setBorder(bottom1);
                num_doc.setBorder(bottom1);
                email.setBorder(bottom1);
                direc.setBorder(bottom1);
                conta.setBorder(bottom1);
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

                // Guardar valores originales para la función cancelar
                guardarValoresOriginales();
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

    public void guardarValoresOriginales() {
        originalNombre = nombre.getText();
        originalApellido = apellido.getText();
        originalTipoDoc = tipo_doc.getSelectedItem().toString();
        originalNumDoc = num_doc.getText();
        originalEmail = email.getText();
        originalDireccion = direc.getText();
        originalContacto = conta.getText();
        originalRol = rol.getSelectedItem().toString();
        originalEstado = estado.getSelectedItem().toString();
    }

    private void restaurarDatosOriginales() {
        nombre.setText(originalNombre);
        apellido.setText(originalApellido);

        // Restaurar tipo de documento
        for (int i = 0; i < tipo_doc.getItemCount(); i++) {
            if (tipo_doc.getItemAt(i).toString().equals(originalTipoDoc)) {
                tipo_doc.setSelectedIndex(i);
                break;
            }
        }

        num_doc.setText(originalNumDoc);
        email.setText(originalEmail);
        direc.setText(originalDireccion);
        conta.setText(originalContacto);

        // Restaurar rol
        for (int i = 0; i < rol.getItemCount(); i++) {
            if (rol.getItemAt(i).toString().equals(originalRol)) {
                rol.setSelectedIndex(i);
                break;
            }
        }

        // Restaurar estado
        for (int i = 0; i < estado.getItemCount(); i++) {
            if (estado.getItemAt(i).toString().equals(originalEstado)) {
                estado.setSelectedIndex(i);
                break;
            }
        }
    }

    public void actualizarDatosUsuario() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, tipo_dc = ?, numero = ?, " +
                    "email = ?, direccion = ?, contacto1 = ?, ID_rol = ?, estado = ? WHERE ID_usuarios = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre.getText());
            stmt.setString(2, apellido.getText());
            stmt.setString(3, tipo_doc.getSelectedItem().toString());
            stmt.setString(4, num_doc.getText());
            stmt.setString(5, email.getText());
            stmt.setString(6, direc.getText());
            stmt.setString(7, conta.getText());

            // Obtener el ID del rol seleccionado
            // Esto podría necesitar ajustes dependiendo de cómo estén organizados tus comboBox
            int selectedRolIndex = rol.getSelectedIndex() + 1;
            stmt.setInt(8, selectedRolIndex);

            stmt.setString(9, estado.getSelectedItem().toString());
            stmt.setInt(10, userID);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Actualizar los valores originales con los nuevos valores
                guardarValoresOriginales();

                // Actualizar también el archivo de configuración si el email cambió
                actualizarConfigSiEmailCambio(email.getText());
            } else {
                JOptionPane.showMessageDialog(null, "No se pudieron actualizar los datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarConfigSiEmailCambio(String nuevoEmail) {
        String usuarioActual = obtenerUsuarioActual();
        if (!usuarioActual.equals(nuevoEmail)) {
            // Si el email cambió, deberíamos actualizar el archivo de configuración
            // Esta funcionalidad podría implementarse si es necesario
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




    public void aplicarEfectoHover(JButton boton, Color colorHover, Color colorBase) {
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBase);
            }
        });
    }


}
