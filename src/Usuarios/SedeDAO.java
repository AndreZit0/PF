package Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SedeDAO {
    private static ConexionBD conexion = new ConexionBD();

    // Agregar nueva sede
    public boolean agregarSede(Sede_getset sede) {
        String query = "INSERT INTO sede (nombre_sede, direccion, estado) VALUES (?, ?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, sede.getNombre_sede());
            pst.setString(2, sede.getDireccion());
            pst.setString(3, sede.getEstado());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar datos de una sede existente
    public boolean actualizarSede(Sede_getset sede) {
        String query = "UPDATE sede SET nombre_sede = ?, direccion = ?, estado = ? WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, sede.getNombre_sede());
            pst.setString(2, sede.getDireccion());
            pst.setString(3, sede.getEstado());
            pst.setInt(4, sede.getID_sede());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar sede por ID
    public boolean eliminarSede(int id_sede) {
        String query = "DELETE FROM sede WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_sede);
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ver datos de una sede específica por ID (sin incluir ID en el objeto)
    public Sede_getset verSede(int id_sede) {
        String query = "SELECT * FROM sede WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_sede);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Sede_getset(
                        rs.getString("nombre_sede"),
                        rs.getString("direccion"),
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todas las sedes
    public ArrayList<Sede_getset> listarSedes() {
        ArrayList<Sede_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM sede";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Sede_getset sede = new Sede_getset(
                        rs.getInt("ID_sede"),
                        rs.getString("nombre_sede"),
                        rs.getString("direccion"),
                        rs.getString("estado")
                );
                lista.add(sede);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ✅ NUEVO: Obtener HashMap con nombres e IDs de sedes
    public HashMap<String, Integer> obtenerMapaSedes() {
        HashMap<String, Integer> mapaSedes = new HashMap<>();
        String query = "SELECT ID_sede, nombre_sede FROM sede";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_sede");
                String nombre = rs.getString("nombre_sede");
                mapaSedes.put(nombre, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapaSedes;
    }
}
