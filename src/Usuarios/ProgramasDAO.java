package Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProgramasDAO {
    private static ConexionBD conexion = new ConexionBD();

    // Agregar programa
    public boolean agregarPrograma(Programas_getset programa) {
        String query = "INSERT INTO programas (nombre_programa, estado) VALUES (?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, programa.getNombre_programa());
            pst.setString(2, programa.getEstado());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Actualizar programa
    public boolean actualizarPrograma(Programas_getset programa) {
        String query = "UPDATE programas SET nombre_programa = ?, estado = ? WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, programa.getNombre_programa());
            pst.setString(2, programa.getEstado());
            pst.setInt(3, programa.getID_programas());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Eliminar programa
    public boolean eliminarPrograma(int id_programa) {
        String query = "DELETE FROM programas WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_programa);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Ver programa por ID
    public Programas_getset verPrograma(int id_programas) {
        String query = "SELECT * FROM programas WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_programas);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Programas_getset(
                        rs.getString("nombre_programa"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todos los programas
    public ArrayList<Programas_getset> listarProgramas() {
        ArrayList<Programas_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM programas";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Programas_getset programa = new Programas_getset(
                        rs.getInt("ID_programas"),
                        rs.getString("nombre_programa"),
                        rs.getString("estado")
                );
                lista.add(programa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //Mapa ID_programas → nombre_programa
    public HashMap<Integer, String> obtenerMapaProgramas() {
        HashMap<Integer, String> mapa = new HashMap<>();
        String query = "SELECT ID_programas, nombre_programa FROM programas";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_programas");
                String nombre = rs.getString("nombre_programa");
                mapa.put(id, nombre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapa;
    }

    //Mapa nombre_programa → ID_programas
    public HashMap<String, Integer> obtenerMapaNombreAId() {
        HashMap<String, Integer> mapa = new HashMap<>();
        String query = "SELECT ID_programas, nombre_programa FROM programas";

        try (Connection con = conexion.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                mapa.put(rs.getString("nombre_programa"), rs.getInt("ID_programas"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapa;
    }
}
