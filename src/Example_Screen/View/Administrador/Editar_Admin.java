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
import Example_Screen.View.Login.LoginGUI;
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
    private JTextField email_insti;
    private JLabel modal;
    private JLabel ficha;
    private JLabel progra;
    private JLabel empre;
    private JTextField datoEmpre;
    private JTextField datoProgra;
    private JTextField datoFicha;
    private JTextField datoModal;

    // Variables para almacenar los datos originales
    private String originalNombre;
    private String originalApellido;
    private String originalTipoDoc;
    private String originalNumDoc;
    private String originalEmail;
    private String originalEmail_Insti;
    private String originalDireccion;
    private String originalContacto;
    private String originalRol;
    private String originalEstado;
    private int userID; // Para almacenar el ID del usuario actual
    private int rolUsuario;
    private int idUsuario;
    private int idRol;

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
    public Editar_Admin(int idUsuario, int idRol){


        this.idUsuario = idUsuario;
        this.idRol = idRol;
        cargarDatosAprendiz(this.idUsuario, this.idRol);
        cargarDatosUsuario();
        configurarVisibilidadCampos();



//        // Ocultar campos si el usuario NO es rol 1 (Aprendiz)
//        // Ocultar campos si el usuario NO es rol 1 (Aprendiz)
//        if (this.idRol != 1) { // ← AQUÍ ESTÁ EL CAMBIO
//            modal.setVisible(false);
//            datoModal.setVisible(false);
//            empre.setVisible(false);
//            datoEmpre.setVisible(false);
//            progra.setVisible(false);
//            datoProgra.setVisible(false);
//            ficha.setVisible(false);
//            datoFicha.setVisible(false);
//        }else {
//            // Solo mostrar si es rol 1 (Aprendiz)
//            modal.setVisible(true);
//            datoModal.setVisible(true);
//            empre.setVisible(true);
//            datoEmpre.setVisible(true);
//            progra.setVisible(true);
//            datoProgra.setVisible(true);
//            ficha.setVisible(true);
//            datoFicha.setVisible(true);
//        }


        textField1.setVisible(false);
        confirmar️Button.setEnabled(false);
        cancelar.setEnabled(false);

        nombre.setEnabled(false);
        apellido.setEnabled(false);
        tipo_doc.setEnabled(false);
        num_doc.setEnabled(false);
        email.setEnabled(false);
        email_insti.setEnabled(false);
        datoModal.setEnabled(false);
        datoFicha.setEnabled(false);
        datoProgra.setEnabled(false);
        datoEmpre.setEnabled(false);
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
        email_insti.setBorder(bottom1);
        datoFicha.setBorder(bottom1);
        datoProgra.setBorder(bottom1);
        datoEmpre.setBorder(bottom1);
        datoModal.setBorder(bottom1);
        direc.setBorder(bottom1);
        conta.setBorder(bottom1);


        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));


        editarPerfil️Button.addActionListener(new ActionListener() {
            /**
             * Cuando le pican al botón de "Editar Perfil".
             * Lo que hace es dejar que el usuario cambie los datos en los campos.
             * @param e El evento del clic, o sea, la acción de haber hecho clic.
             */
            @Override
            public void actionPerformed(ActionEvent e) {


                configurarPermisosPorRol();

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
                email_insti.setEnabled(false);
                datoModal.setEnabled(false);
                datoFicha.setEnabled(false);
                datoProgra.setEnabled(false);
                datoEmpre.setEnabled(false);
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
                email_insti.setBorder(bottom1);
                datoModal.setBorder(bottom1);
                datoFicha.setBorder(bottom1);
                datoProgra.setBorder(bottom1);
                datoEmpre.setBorder(bottom1);
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
                email_insti.setEnabled(false);
                datoModal.setEnabled(false);
                datoFicha.setEnabled(false);
                datoProgra.setEnabled(false);
                datoEmpre.setEnabled(false);
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
                email_insti.setBorder(bottom1);
                datoModal.setBorder(bottom1);
                datoFicha.setBorder(bottom1);
                datoProgra.setBorder(bottom1);
                datoEmpre.setBorder(bottom1);
                direc.setBorder(bottom1);
                conta.setBorder(bottom1);
            }
        });
    }

    private void configurarPermisosPorRol() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        Border bottomDisabled = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#39A900"));

        switch(rolUsuario) {
            case 1: // APRENDIZ - Solo puede editar datos básicos
                // Permitir editar
                email.setEnabled(true);
                email.setBorder(bottom);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);

                // NO permitir editar (mantener deshabilitados)
                nombre.setEnabled(false);
                nombre.setBorder(bottom);
                apellido.setEnabled(false);
                apellido.setBorder(bottom);
                email_insti.setEnabled(false);
                email_insti.setBorder(bottom);
                datoModal.setEnabled(false);
                datoModal.setBorder(bottom);
                datoFicha.setEnabled(false);
                datoFicha.setBorder(bottom);
                datoProgra.setEnabled(false);
                datoProgra.setBorder(bottom);
                datoEmpre.setEnabled(false);
                datoEmpre.setBorder(bottom);
                tipo_doc.setEnabled(false);
                tipo_doc.setBorder(bottomDisabled);
                num_doc.setEnabled(false);
                num_doc.setBorder(bottomDisabled);
                rol.setEnabled(false);
                estado.setEnabled(false);
                break;

            case 2: // EVALUADOR - Puede editar más campos que aprendiz
                email.setEnabled(true);
                email.setBorder(bottom);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);

                // NO permitir editar (mantener deshabilitados)
                nombre.setEnabled(false);
                nombre.setBorder(bottom);
                apellido.setEnabled(false);
                apellido.setBorder(bottom);
                email_insti.setEnabled(false);
                email_insti.setBorder(bottom);
                modal.setVisible(false);
                ficha.setVisible(false);
                progra.setVisible(false);
                empre.setVisible(false);
                datoModal.setVisible(false);
                datoFicha.setVisible(false);
                datoProgra.setVisible(false);
                datoEmpre.setVisible(false);
                tipo_doc.setEnabled(false);
                tipo_doc.setBorder(bottomDisabled);
                num_doc.setEnabled(false);
                num_doc.setBorder(bottomDisabled);
                rol.setEnabled(false);
                estado.setEnabled(false);

            case 3: // COEVALUADOR - Mismo nivel que evaluador
                // Permitir editar
                email.setEnabled(true);
                email.setBorder(bottom);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);


                // NO permitir editar
                nombre.setEnabled(false);
                nombre.setBorder(bottom);
                apellido.setEnabled(false);
                apellido.setBorder(bottom);
                email_insti.setEnabled(false);
                email_insti.setBorder(bottom);
                modal.setVisible(false);
                ficha.setVisible(false);
                progra.setVisible(false);
                empre.setVisible(false);
                datoModal.setVisible(false);
                datoFicha.setVisible(false);
                datoProgra.setVisible(false);
                datoEmpre.setVisible(false);
                tipo_doc.setEnabled(false);
                num_doc.setEnabled(false);
                num_doc.setBorder(bottom);
                rol.setEnabled(false);
                estado.setEnabled(false);
                break;

            case 4: // AUXILIAR - Similar a evaluador
            case 5: // ADMINISTRADOR - Más permisos
                // Permitir editar
                nombre.setEnabled(true);
                nombre.setBorder(bottom);
                apellido.setEnabled(true);
                apellido.setBorder(bottom);
                tipo_doc.setEnabled(true);
                num_doc.setEnabled(true);
                num_doc.setBorder(bottom);
                email.setEnabled(true);
                email.setBorder(bottom);
                email_insti.setEnabled(true);
                email_insti.setBorder(bottom);
                modal.setVisible(false);
                ficha.setVisible(false);
                progra.setVisible(false);
                empre.setVisible(false);
                datoModal.setVisible(false);
                datoFicha.setVisible(false);
                datoProgra.setVisible(false);
                datoEmpre.setVisible(false);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);

                // Administrador puede cambiar estado pero no rol
                if(rolUsuario == 5) {
                    estado.setEnabled(true);
                } else {
                    estado.setEnabled(false);
                }
                rol.setEnabled(false);
                break;

            case 6: // ADMINISTRADOR DEL SISTEMA - Todos los permisos
                // Permitir editar TODO
                nombre.setEnabled(true);
                nombre.setBorder(bottom);
                apellido.setEnabled(true);
                apellido.setBorder(bottom);
                tipo_doc.setEnabled(true);
                num_doc.setEnabled(true);
                num_doc.setBorder(bottom);
                email.setEnabled(true);
                email.setBorder(bottom);
                email_insti.setEnabled(true);
                email_insti.setBorder(bottom);
                modal.setVisible(false);
                ficha.setVisible(false);
                progra.setVisible(false);
                empre.setVisible(false);
                datoModal.setVisible(false);
                datoFicha.setVisible(false);
                datoProgra.setVisible(false);
                datoEmpre.setVisible(false);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);
                rol.setEnabled(true);
                estado.setEnabled(true);
                break;

            default:
                // Por seguridad, si no reconoce el rol, no permitir editar nada crítico
                nombre.setEnabled(true);
                nombre.setBorder(bottom);
                apellido.setEnabled(true);
                apellido.setBorder(bottom);
                email.setEnabled(true);
                email.setBorder(bottom);
                email_insti.setEnabled(true);
                email_insti.setBorder(bottom);
                direc.setEnabled(true);
                direc.setBorder(bottom);
                conta.setEnabled(true);
                conta.setBorder(bottom);

                tipo_doc.setEnabled(false);
                tipo_doc.setBorder(bottomDisabled);
                num_doc.setEnabled(false);
                num_doc.setBorder(bottomDisabled);
                rol.setEnabled(false);
                estado.setEnabled(false);
                break;
        }
    }

    private void configurarVisibilidadCampos() {
        if (this.idRol != 1) { // NO es Aprendiz
            modal.setVisible(false);
            datoModal.setVisible(false);
            empre.setVisible(false);
            datoEmpre.setVisible(false);
            progra.setVisible(false);
            datoProgra.setVisible(false);
            ficha.setVisible(false);
            datoFicha.setVisible(false);
        } else { // ES Aprendiz (idRol = 1)
            modal.setVisible(true);
            datoModal.setVisible(true);
            empre.setVisible(true);
            datoEmpre.setVisible(true);
            progra.setVisible(true);
            datoProgra.setVisible(true);
            ficha.setVisible(true);
            datoFicha.setVisible(true);
        }
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
                //NUEVO: Almacenar el rol del Usuario
                rolUsuario=rs.getInt("ID_rol");

                // Cargar datos a los campos
                nombre.setText(rs.getString("nombres"));
                apellido.setText(rs.getString("apellidos"));
                num_doc.setText(rs.getString("numero"));
                email.setText(rs.getString("email"));
                email_insti.setText(rs.getString("email_insti"));
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
        originalEmail_Insti = email_insti.getText();
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
        email_insti.setText(originalEmail_Insti);
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
                    "email = ?, email_insti = ?,direccion = ?, contacto1 = ?, ID_rol = ?, estado = ? WHERE ID_usuarios = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre.getText());
            stmt.setString(2, apellido.getText());
            stmt.setString(3, tipo_doc.getSelectedItem().toString());
            stmt.setString(4, num_doc.getText());
            stmt.setString(5, email.getText());
            stmt.setString(6, email_insti.getText());
            stmt.setString(7, direc.getText());
            stmt.setString(8, conta.getText());

            // Obtener el ID del rol seleccionado
            // Esto podría necesitar ajustes dependiendo de cómo estén organizados tus comboBox
            int selectedRolIndex = rol.getSelectedIndex() + 1;
            stmt.setInt(9, selectedRolIndex);

            stmt.setString(10, estado.getSelectedItem().toString());
            stmt.setInt(11, userID);

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

    private void cargarDatosAprendiz(int idUsuario, int idRol) {
        if (idRol != 1) return; // Solo si es Aprendiz

        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
        SELECT e.nombre_empresa, f.codigo, 
               p.nombre_programa, m.modalidad AS modalidadContrato
        FROM aprendices a
        LEFT JOIN empresas e ON a.ID_empresas = e.ID_empresas
        LEFT JOIN fichas f ON a.ID_Fichas = f.ID_Fichas
        LEFT JOIN programas p ON f.ID_programas = p.ID_programas
        LEFT JOIN modalidad m ON a.ID_modalidad = m.ID_modalidad
        WHERE a.ID_usuarios = ?
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datoEmpre.setText(rs.getString("nombre_empresa"));
                datoFicha.setText(rs.getString("codigo"));
                datoProgra.setText(rs.getString("nombre_programa"));
                datoModal.setText(rs.getString("modalidadContrato"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
