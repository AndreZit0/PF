package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para realizar operaciones de acceso a datos
 * relacionadas con la entidad Sede en la base de datos.
 */
public class SedeDAO {
    // Objeto para gestionar la conexión a la base de datos
    private static ConexionBD conexion = new ConexionBD();

    /**
     * Agrega una nueva sede a la base de datos.
     *
     * @param sede Objeto Sede_getset que contiene los datos de la sede a agregar.
     * @return true si la sede se agregó correctamente, false si hubo un error.
     */
    public boolean agregarSede(Sede_getset sede) {
        String query = "INSERT INTO sede (nombre_sede, direccion, estado) VALUES (?, ?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, sede.getNombre_sede());
            preparedStatement.setString(2, sede.getDireccion());
            preparedStatement.setString(3, sede.getEstado());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de una sede existente en la base de datos.
     *
     * @param sede Objeto Sede_getset que contiene los datos actualizados de la
     * sede.
     * @return true si la sede se actualizó correctamente, false si hubo un error.
     */
    public boolean actualizarSede(Sede_getset sede) {
        String query = "UPDATE sede SET nombre_sede = ?, direccion = ?, estado = ? WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, sede.getNombre_sede());
            preparedStatement.setString(2, sede.getDireccion());
            preparedStatement.setString(3, sede.getEstado());
            preparedStatement.setInt(4, sede.getID_sede());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una sede de la base de datos por su ID.
     *
     * @param idSede El ID de la sede a eliminar.
     * @return true si la sede se eliminó correctamente, false si hubo un error.
     */
    public boolean eliminarSede(int idSede) {
        String query = "DELETE FROM sede WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idSede);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene los datos de una sede específica por su ID.
     *
     * @param idSede El ID de la sede a buscar.
     * @return Un objeto Sede_getset con los datos de la sede encontrada, o null si
     * no se encuentra.  El ID de la sede no se incluye en el objeto retornado.
     */
    public Sede_getset verSede(int idSede) {
        String query = "SELECT * FROM sede WHERE ID_sede = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idSede);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Sede_getset(
                        resultSet.getString("nombre_sede"),
                        resultSet.getString("direccion"),
                        resultSet.getString("estado")
                );
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas las sedes de la base de datos.
     *
     * @return Una lista de objetos Sede_getset, donde cada objeto representa una
     * sede.
     */
    public List<Sede_getset> listarSedes() {
        List<Sede_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM sede";

        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Sede_getset sede = new Sede_getset(
                        resultSet.getInt("ID_sede"),
                        resultSet.getString("nombre_sede"),
                        resultSet.getString("direccion"),
                        resultSet.getString("estado")
                );
                lista.add(sede);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return lista;
    }
}

