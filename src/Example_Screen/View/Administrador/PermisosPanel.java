package Example_Screen.View.Administrador;

import Example_Screen.Connection.*;
import Example_Screen.Model.Permiso;
import Example_Screen.Model.Rol;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PermisosPanel extends JPanel {
    private JPanel mainPanel;
    private JComboBox<Rol> roleComboBox;
    private JCheckBox crearUsuarioCheckBox;
    private JCheckBox editarUsuarioCheckBox;
    private JCheckBox verUsuarioCheckBox;
    private JCheckBox inhabilitarUsuarioCheckBox;
    private JCheckBox crearFichaCheckBox;
    private JCheckBox editarFichaCheckBox;
    private JCheckBox verFichaCheckBox;
    private JCheckBox inhabilitarFichaCheckBox;
    private JCheckBox crearSeguimientoCheckBox;
    private JCheckBox editarSeguimientoCheckBox;
    private JCheckBox eliminarSeguimientoCheckBox;
    private JCheckBox firmarSeguimientoCheckBox;
    private JCheckBox verSeguimientoCheckBox;
    private JButton guardarButton;
    private JPanel rolePanel;
    private JScrollPane scrollPane;
    private JPanel permissionsPanel;
    private JPanel buttonPanel;

    public PermisosPanel() {
        // Primero verificar la conexión a la base de datos
        if (!verificarConexionBD()) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo establecer conexión con la base de datos. La aplicación no funcionará correctamente.",
                    "Error de conexión", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Configurar el combo box con los roles
        roleComboBox.setModel(new DefaultComboBoxModel<>(Rol.values()));


        guardarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        // Cargar permisos cuando se selecciona un rol
        roleComboBox.addActionListener(e -> cargarPermisos((Rol) roleComboBox.getSelectedItem()));

        // Configurar botón guardar
        guardarButton.addActionListener(e -> guardarPermisos());

        // Cargar permisos iniciales
        if (roleComboBox.getItemCount() > 0) {
            cargarPermisos((Rol) roleComboBox.getSelectedItem());
        }
    }

    public boolean verificarConexionBD() {
        try (Connection conn = DBConnection.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void cargarPermisos(Rol rol) {
        // Consultar permisos desde la base de datos
        Map<Permiso, Boolean> permisos = consultarPermisos(rol);

        // Establecer los checkboxes según los permisos
        crearUsuarioCheckBox.setSelected(permisos.getOrDefault(Permiso.CREAR_USUARIO, false));
        editarUsuarioCheckBox.setSelected(permisos.getOrDefault(Permiso.EDITAR_USUARIO, false));
        verUsuarioCheckBox.setSelected(permisos.getOrDefault(Permiso.VER_USUARIO, false));
        inhabilitarUsuarioCheckBox.setSelected(permisos.getOrDefault(Permiso.INHABILITAR_USUARIO, false));

        crearFichaCheckBox.setSelected(permisos.getOrDefault(Permiso.CREAR_FICHA, false));
        editarFichaCheckBox.setSelected(permisos.getOrDefault(Permiso.EDITAR_FICHA, false));
        verFichaCheckBox.setSelected(permisos.getOrDefault(Permiso.VER_FICHA, false));
        inhabilitarFichaCheckBox.setSelected(permisos.getOrDefault(Permiso.INHABILITAR_FICHA, false));

        crearSeguimientoCheckBox.setSelected(permisos.getOrDefault(Permiso.CREAR_SEGUIMIENTO, false));
        editarSeguimientoCheckBox.setSelected(permisos.getOrDefault(Permiso.EDITAR_SEGUIMIENTO, false));
        eliminarSeguimientoCheckBox.setSelected(permisos.getOrDefault(Permiso.ELIMINAR_SEGUIMIENTO, false));
        firmarSeguimientoCheckBox.setSelected(permisos.getOrDefault(Permiso.FIRMAR_SEGUIMIENTO, false));
        verSeguimientoCheckBox.setSelected(permisos.getOrDefault(Permiso.VER_SEGUIMIENTO, false));
    }

    public Map<Permiso, Boolean> consultarPermisos(Rol rol) {
        Map<Permiso, Boolean> permisos = new HashMap<>();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                JOptionPane.showMessageDialog(this,
                        "No hay conexión a la base de datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return permisos;
            }

            String sql = "SELECT p.id_permiso FROM permisos_rol pr " +
                    "JOIN permisos p ON pr.id_permiso = p.id_permiso " +
                    "WHERE pr.ID_rol = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, rol.getId());

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int idPermiso = rs.getInt("id_permiso");
                        for (Permiso permiso : Permiso.values()) {
                            if (permiso.getId() == idPermiso) {
                                permisos.put(permiso, true);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al consultar permisos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return permisos;
    }

    public void guardarPermisos() {
        if (!verificarConexionBD()) {
            JOptionPane.showMessageDialog(this,
                    "No hay conexión a la base de datos. No se pueden guardar los cambios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Rol rol = (Rol) roleComboBox.getSelectedItem();
        Map<Permiso, Boolean> nuevosPermisos = new HashMap<>();

        // Recoger los permisos de los checkboxes
        nuevosPermisos.put(Permiso.CREAR_USUARIO, crearUsuarioCheckBox.isSelected());
        nuevosPermisos.put(Permiso.EDITAR_USUARIO, editarUsuarioCheckBox.isSelected());
        nuevosPermisos.put(Permiso.VER_USUARIO, verUsuarioCheckBox.isSelected());
        nuevosPermisos.put(Permiso.INHABILITAR_USUARIO, inhabilitarUsuarioCheckBox.isSelected());

        nuevosPermisos.put(Permiso.CREAR_FICHA, crearFichaCheckBox.isSelected());
        nuevosPermisos.put(Permiso.EDITAR_FICHA, editarFichaCheckBox.isSelected());
        nuevosPermisos.put(Permiso.VER_FICHA, verFichaCheckBox.isSelected());
        nuevosPermisos.put(Permiso.INHABILITAR_FICHA, inhabilitarFichaCheckBox.isSelected());

        nuevosPermisos.put(Permiso.CREAR_SEGUIMIENTO, crearSeguimientoCheckBox.isSelected());
        nuevosPermisos.put(Permiso.EDITAR_SEGUIMIENTO, editarSeguimientoCheckBox.isSelected());
        nuevosPermisos.put(Permiso.ELIMINAR_SEGUIMIENTO, eliminarSeguimientoCheckBox.isSelected());
        nuevosPermisos.put(Permiso.FIRMAR_SEGUIMIENTO, firmarSeguimientoCheckBox.isSelected());
        nuevosPermisos.put(Permiso.VER_SEGUIMIENTO, verSeguimientoCheckBox.isSelected());

        // Actualizar en la base de datos
        if (actualizarPermisosEnDB(rol, nuevosPermisos)) {
            JOptionPane.showMessageDialog(this, "Permisos actualizados correctamente!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar permisos. Verifica la conexión a la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean actualizarPermisosEnDB(Rol rol, Map<Permiso, Boolean> nuevosPermisos) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null || conn.isClosed()) {
                return false;
            }

            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Eliminar todos los permisos existentes para este rol
            String deleteSql = "DELETE FROM permisos_rol WHERE ID_rol = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, rol.getId());
                int deletedRows = deleteStmt.executeUpdate();
                System.out.println("Eliminados " + deletedRows + " permisos antiguos para el rol " + rol.name());
            }

            // 2. Insertar los nuevos permisos seleccionados
            String insertSql = "INSERT INTO permisos_rol (ID_rol, id_permiso) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                for (Map.Entry<Permiso, Boolean> entry : nuevosPermisos.entrySet()) {
                    if (entry.getValue()) {
                        insertStmt.setInt(1, rol.getId());
                        insertStmt.setInt(2, entry.getKey().getId());
                        insertStmt.addBatch();
                    }
                }
                int[] resultados = insertStmt.executeBatch();
                System.out.println("Insertados " + resultados.length + " nuevos permisos para el rol " + rol.name());
            }

            conn.commit(); // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir cambios en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar autocommit
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}