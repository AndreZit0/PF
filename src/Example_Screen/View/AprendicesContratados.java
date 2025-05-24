package Example_Screen.View;

import Example_Screen.Connection.DBConnection;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;

import static Example_Screen.View.Administrador.Administrador.verUsuarioPorRol;

public class AprendicesContratados {

    // No se crea una nueva instancia de VerUsuariosRegistrados aquí

    public void obtenerAprendicesContratados(int idInstructor, VerUsuariosRegistrados vista) {
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7 ; // Solo la columna del botón "Ver Perfil" es editable (ahora es la columna 6)
            }
        };

        // Columnas de la tabla
        model.addColumn("Tipo de Documento");
        model.addColumn("Número");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Email");
        model.addColumn("Empresa"); // Nueva columna agregada
        model.addColumn("Ver Perfil");
        model.addColumn("Seguimiento");

        try {
            Connection con = DBConnection.getConnection();

            // Consulta modificada: Obtener aprendices asignados a una empresa específica (coevaluador)
            String sqlAprendices = """
                SELECT u.tipo_dc, u.numero, u.nombres, u.apellidos, u.email, e.nombre_empresa, a.ID_usuarios
                FROM aprendices a
                INNER JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios
                INNER JOIN empresas e ON a.ID_empresas = e.ID_empresas
                WHERE a.ID_empresas = ?
            """;

            PreparedStatement psAprendices = con.prepareStatement(sqlAprendices);
            psAprendices.setInt(1, idInstructor); // Nota: aquí idInstructor representa el ID de la empresa
            ResultSet rsAprendices = psAprendices.executeQuery();

            while (rsAprendices.next()) {
                Object[] dato = new Object[8]; // Aumentado a 7 columnas
                dato[0] = rsAprendices.getString("tipo_dc");
                dato[1] = rsAprendices.getString("numero");
                dato[2] = rsAprendices.getString("nombres");
                dato[3] = rsAprendices.getString("apellidos");
                dato[4] = rsAprendices.getString("email");
                dato[5] = rsAprendices.getString("nombre_empresa"); // Nueva columna Empresa
                dato[6] = "Ver Perfil";
                dato[7] = "Seguimiento";
                model.addRow(dato);
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

            tabla.getColumn("Seguimiento").setCellRenderer(vista.getButtonRenderer());
            tabla.getColumn("Seguimiento").setCellEditor(vista.getButtonEditor());

            vista.componentesPersonalizado();

            rsAprendices.close();
            psAprendices.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}