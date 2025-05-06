package Example_Screen.View.Usuarios_Registrados;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Example_Screen.View.Administrador.Administrador.verUsuarioPorRol;

public class VerUsuariosRegistrados {
    private JTable table1;
    private JPanel panelVerUsuario;
    private JPanel panelFiltro;
    private JTextField busqueda;
    private JLabel tipoUsuario;
    private JPanel panelTable;
    private JScrollPane scroll;

    public JTable getTable() {
        return table1;
    }

    public JTextField getBusqueda() {
        return busqueda;
    }


    public JPanel getPanel() {
        return panelVerUsuario;
    }


    // Declarar sorter como atributo de clase
    private TableRowSorter<TableModel> sorter;

    public void inicializarFiltro(JTextField busqueda, JTable table1) {
        busqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            private void aplicarFiltro() {
                String texto = busqueda.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    public void obtenerDatosUsuario() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tipo de Documento");
        model.addColumn("Número");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Email");

        String[] dato = new String[5];

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT tipo_dc, numero, nombres, apellidos, email FROM usuarios WHERE id_rol = " + verUsuarioPorRol);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                model.addRow(dato);
            }

            // Establecer el modelo en la tabla
            table1.setModel(model);

            // Asignar el sorter al nuevo modelo
            sorter = new TableRowSorter<>(model);
            table1.setRowSorter(sorter);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String rol = null;

    public void tipoDeUsuarioRegistrado(){
        if (verUsuarioPorRol == 1){
            rol = "Aprendices Registrados";
        }
        if (verUsuarioPorRol == 2){
            rol = "Evaluadores Registrados";
        }
        if (verUsuarioPorRol == 3){
            rol = "Empresas Registradas";
        }
        else if (verUsuarioPorRol == 4) {
            rol = "Auxiliares Registrados";
        }

        tipoUsuario.setText(rol);

    }

    public void componentesPersonalizado() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        busqueda.setBorder(bottom);
        table1.getTableHeader().setForeground(Color.decode("#ffffff")); // Color del texto
        table1.getTableHeader().setBackground(Color.decode("#39A900"));

        table1.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Cuerpo de la tabla
        table1.setRowHeight(25);

        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Encabezado



        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
    }
    public void tipoDeUsuarioRegistrado1() {

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rol FROM rol WHERE id_rol = " + verUsuarioPorRol);

            if (rs.next()) {
                rol = rs.getString("rol");
                tipoUsuario.setText(rol);  // mostrar rol en el JLabel
            } else {
                tipoUsuario.setText("Rol no encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tipoUsuario.setText("Error de conexión");
        }
        tipoUsuario.setText(rol + " Registrado");
    }
}
