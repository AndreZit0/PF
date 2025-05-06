package Example_Screen.Model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import Example_Screen.Model.*;

import Example_Screen.Connection.*;

import static Example_Screen.Connection.DBConnection.getConnection;

public class DatabaseManager {

    // Gestión de permisos
    public static Map<Permiso, Boolean> cargarPermisosRol(Rol rol) {
        Map<Permiso, Boolean> permisos = new HashMap<>();
        String sql = "SELECT p.id_permiso FROM permisos_rol pr " +
                "JOIN permisos p ON pr.id_permiso = p.id_permiso " +
                "WHERE pr.ID_rol = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
        } catch (SQLException e) {
            System.err.println("Error al cargar permisos del rol " + rol + ":");
            e.printStackTrace();
        }
        return permisos;
    }
}

