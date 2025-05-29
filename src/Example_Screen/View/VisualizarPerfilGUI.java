package Example_Screen.View;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class VisualizarPerfilGUI {
    public JPanel panel1;
    private JLabel estado;
    private JLabel conta;
    private JLabel direc;
    private JLabel email;
    private JLabel num_doc;
    private JLabel nombre;
    private JLabel rol;
    private JLabel tipo_doc;
    private JLabel apellido;
    private JLabel email_insti;
    private JLabel modalidad;
    private JLabel datoEmpre;
    private JLabel datoProg;
    private JLabel datoFich;
    private JLabel datoModal;
    private JButton irAlPerfilButton;
    private JLabel fich;
    private JLabel empr;
    private JLabel prog;
    private int userID;
    private DBConnection dbConnection = new DBConnection();


    public VisualizarPerfilGUI(int idUsuario, int idRol) {


        //irAlPerfilButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Color colorHover = new Color(0, 120, 50);
        Color colorBase = new Color(57, 169, 0);


        //aplicarEfectoHover(irAlPerfilButton, colorHover, colorBase);

        inicializarVisibilidadElementos();

        cargarDatosUsuario(idUsuario);
        cargarDatosAprendiz(idUsuario, idRol);

        configurarVisibilidadAprendiz(idRol);

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

    private void configurarVisibilidadAprendiz(int idRol) {
        boolean esAprendiz = (idRol == 1);

        // Mostrar/ocultar botón
        //irAlPerfilButton.setVisible(esAprendiz);

        // Mostrar/ocultar datos específicos de aprendiz
        empr.setVisible(esAprendiz);
        fich.setVisible(esAprendiz);
        prog.setVisible(esAprendiz);
        modalidad.setVisible(esAprendiz);


    }

    private void inicializarVisibilidadElementos() {
        // Inicializar elementos como ocultos por defecto
        //irAlPerfilButton.setVisible(false);
        empr.setVisible(false);
        fich.setVisible(false);
        prog.setVisible(false);
        modalidad.setVisible(false);
    }



    public void cargarDatosUsuario(int idUsuario)
    {
        String usuarioEmail = obtenerUsuarioActual();
        if (usuarioEmail == null || usuarioEmail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener la información del usuario actual.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            //String sql = "SELECT * FROM usuarios WHERE email = ?";
            // Cambiar esta consulta:
            String sql = "SELECT u.*, r.rol FROM usuarios u " +
                    "INNER JOIN rol r ON u.ID_rol = r.ID_rol " +
                    "WHERE u.email = ?";
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
                email_insti.setText(rs.getString("email_insti"));
                direc.setText(rs.getString("direccion"));
                conta.setText(rs.getString("contacto1"));
                tipo_doc.setText(rs.getString("tipo_dc"));
                rol.setText(rs.getString("rol"));
                estado.setText(rs.getString("estado"));


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

    // Agregar este método a la clase VisualizarPerfilGUI
    public void cargarDatosUsuarioEspecifico(String numeroDoc, String tipoDoc) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT u.*, r.rol FROM usuarios u " +
                    "INNER JOIN rol r ON u.ID_rol = r.ID_rol " +
                    "WHERE u.numero = ? AND u.tipo_dc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeroDoc);
            stmt.setString(2, tipoDoc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Almacenar ID para futuras actualizaciones
                userID = rs.getInt("ID_usuarios");

                // Cargar datos a los campos
                nombre.setText(rs.getString("nombres"));
                apellido.setText(rs.getString("apellidos"));
                num_doc.setText(rs.getString("numero"));
                email.setText(rs.getString("email"));
                email_insti.setText(rs.getString("email_insti"));
                direc.setText(rs.getString("direccion"));
                conta.setText(rs.getString("contacto1"));
                tipo_doc.setText(rs.getString("tipo_dc"));
                rol.setText(rs.getString("rol"));
                estado.setText(rs.getString("estado"));


            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró información del usuario con documento: " + numeroDoc,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los datos del usuario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosAprendiz(int idUsuario, int idRol) {
        if (idRol != 1) return; // Solo si es Aprendiz

        try (Connection conn = dbConnection.getConnection()) {
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
                datoFich.setText(rs.getString("codigo"));
                datoProg.setText(rs.getString("nombre_programa"));
                datoModal.setText(rs.getString("modalidadContrato"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
