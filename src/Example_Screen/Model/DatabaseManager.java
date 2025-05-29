package Example_Screen.Model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import Example_Screen.Model.*;

import Example_Screen.Connection.*;

import static Example_Screen.Connection.DBConnection.getConnection;

/**
 * Esta es la Clase que maneja operaciones relacionadas con la base de datos, como cargar permisos de roles.
 * Es como el "intermediario".
 */
public class DatabaseManager {

    /**
     * Aqui se obtiene todos los permisos que tiene un rol específico desde la base de datos.
     *
     * @param rol El rol del que queremos saber sus permisos (ej: admin, aprendiz,evaluador, etc.).
     * @return Un mapa donde cada permiso está asociado a 'true' si el rol lo tiene.
     *         Ejemplo: Si el rol es admin, podría devolver {Permiso.CREAR_USUARIO=true, Permiso.ELIMINAR=true}.
     */
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

