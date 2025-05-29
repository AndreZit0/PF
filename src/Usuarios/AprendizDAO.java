package Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AprendizDAO {

    private static ConexionBD conexion = new ConexionBD();

    public boolean crearAprendiz(Aprendiz_getset aprendiz) {
        String query = "INSERT INTO aprendices (ID_numeroAprendices, ID_usuarios, estado, ID_Fichas, ID_empresas, ID_instructor, ID_modalidad) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, aprendiz.getID_numeroAprendices());
            pst.setInt(2, aprendiz.getID_usuarios());
            pst.setString(3, aprendiz.getEstado());
            pst.setInt(4, aprendiz.getID_Fichas());
            pst.setInt(5, aprendiz.getID_empresas());
            pst.setInt(6, aprendiz.getID_instructor());
            pst.setInt(7, aprendiz.getID_modalidad());

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAprendiz(Aprendiz_getset aprendiz) {
        String query = "UPDATE aprendices SET ID_usuarios = ?, estado = ?, ID_Fichas = ?, ID_empresas = ?, ID_instructor = ?, ID_modalidad = ? WHERE ID_numeroAprendices = ?";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, aprendiz.getID_usuarios());
            pst.setString(2, aprendiz.getEstado());
            pst.setInt(3, aprendiz.getID_Fichas());
            pst.setInt(4, aprendiz.getID_empresas());
            pst.setInt(5, aprendiz.getID_instructor());
            pst.setInt(6, aprendiz.getID_modalidad());
            pst.setInt(7, aprendiz.getID_numeroAprendices());

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAprendiz(int id_numeroAprendiz) {
        String query = "DELETE FROM aprendices WHERE ID_numeroAprendices = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_numeroAprendiz);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Aprendiz_getset buscarAprendiz(int id_numeroAprendiz) {
        String query = "SELECT * FROM aprendices WHERE ID_numeroAprendices = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_numeroAprendiz);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Aprendiz_getset(
                            rs.getInt("ID_numeroAprendices"),
                            rs.getInt("ID_usuarios"),
                            rs.getInt("ID_empresas"),
                            rs.getInt("ID_instructor"),
                            rs.getInt("ID_modalidad"),
                            rs.getInt("ID_Fichas"),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Aprendiz_getset obtenerAprendizPorUsuario(int idUsuario) {
        String query = "SELECT * FROM aprendices WHERE ID_usuarios = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Aprendiz_getset(
                            rs.getInt("ID_numeroAprendices"),
                            rs.getInt("ID_usuarios"),
                            rs.getInt("ID_empresas"),
                            rs.getInt("ID_instructor"),
                            rs.getInt("ID_modalidad"),
                            rs.getInt("ID_Fichas"),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}

