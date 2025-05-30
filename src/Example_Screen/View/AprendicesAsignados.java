package Example_Screen.View;

import Example_Screen.Connection.DBConnection;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

import static Example_Screen.View.Administrador.Administrador.verUsuarioPorRol;

public class AprendicesAsignados {

    // No se crea una nueva instancia de VerUsuariosRegistrados aquí

    public void obtenerAprendicesAsignados(int idInstructor, VerUsuariosRegistrados vista) {
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return column == 5;// Solo la columna del botón es editable
            }
        };

        // Columnas de la tabla
        model.addColumn("Tipo de Documento");
        model.addColumn("Número");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Email");
        model.addColumn("Ver Perfil");
//        model.addColumn("Bitácoras");
//        model.addColumn("Seguimiento");

        try {
            Connection con = DBConnection.getConnection();

            // Paso 1: Obtener los IDs de los aprendices asignados al instructor
            String sqlAprendices = "SELECT ID_usuarios FROM aprendices WHERE ID_instructor = ?";
            PreparedStatement psAprendices = con.prepareStatement(sqlAprendices);
            psAprendices.setInt(1, idInstructor);
            ResultSet rsAprendices = psAprendices.executeQuery();

            // Paso 2: Por cada aprendiz, traer datos de la tabla usuarios
            while (rsAprendices.next()) {
                int idAprendiz = rsAprendices.getInt("ID_usuarios");

                String sqlUsuario = "SELECT tipo_dc, numero, nombres, apellidos, email FROM usuarios WHERE ID_usuarios = ?";
                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                psUsuario.setInt(1, idAprendiz);
                ResultSet rsUsuario = psUsuario.executeQuery();

                if (rsUsuario.next()) {
                    Object[] dato = new Object[6];
                    dato[0] = rsUsuario.getString("tipo_dc");
                    dato[1] = rsUsuario.getString("numero");
                    dato[2] = rsUsuario.getString("nombres");
                    dato[3] = rsUsuario.getString("apellidos");
                    dato[4] = rsUsuario.getString("email");
                    dato[5] = "Ver Perfil";
//                    dato[6] = "Bitácoras";
//                    dato[7] = "Seguimiento";
                    model.addRow(dato);
                }

                psUsuario.close();
            }


            // Mostrar los datos en la tabla de VerUsuariosRegistrados
            JTable tabla = vista.getTable();
            tabla.setModel(model);

// Filtro de búsqueda
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tabla.setRowSorter(sorter);

            vista.setSorter(sorter);


// Aplicar el filtro al campo de búsqueda
            vista.inicializarFiltro(vista.getBusqueda(), tabla);

// Renderizador y editor para el botón "Ver Perfil"
            tabla.getColumn("Ver Perfil").setCellRenderer(vista.getButtonRenderer());
            tabla.getColumn("Ver Perfil").setCellEditor(vista.getButtonEditor());

//            tabla.getColumn("Bitácoras").setCellRenderer(vista.getButtonRenderer());
//            tabla.getColumn("Bitácoras").setCellEditor(vista.getButtonEditor());
//
//            tabla.getColumn("Seguimiento").setCellRenderer(vista.getButtonRenderer());
//            tabla.getColumn("Seguimiento").setCellEditor(vista.getButtonEditor());

            vista.componentesPersonalizado();

            rsAprendices.close();
            psAprendices.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
