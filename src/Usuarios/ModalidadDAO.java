package Usuarios;

import java.sql.*;
import java.util.ArrayList;

public class ModalidadDAO {
    private static ConexionBD conexion = new ConexionBD();

    // Agregar modalidad
    public boolean agregarModalidad(Modalidad_getset modalidad) {
        String query = "INSERT INTO modalidad (ID_modalidad, modalidad) VALUES (?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, modalidad.getID_modalidad());
            pst.setString(2, modalidad.getModalidad());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Actualizar modalidad
    public boolean actualizarModalidad(Modalidad_getset modalidad) {
        String query = "UPDATE modalidad SET modalidad = ? WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, modalidad.getModalidad());
            pst.setInt(2, modalidad.getID_modalidad());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Eliminar modalidad
    public boolean eliminarModalidad(int id_modalidad) {
        String query = "DELETE FROM modalidad WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_modalidad);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // ver modalidad por ID
    public Modalidad_getset verModalidad(int id_modalidad) {
        String query = "SELECT * FROM modalidad WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_modalidad);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Modalidad_getset(
                        rs.getInt("ID_modalidad"),
                        rs.getString("modalidad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Listar todas las modalidades
    public ArrayList<Modalidad_getset> listarModalidades() {
        ArrayList<Modalidad_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM modalidad";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Modalidad_getset modalidad = new Modalidad_getset(
                        rs.getInt("ID_modalidad"),
                        rs.getString("modalidad")
                );
                lista.add(modalidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}
