package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para realizar operaciones de acceso a datos
 * relacionadas con la entidad Modalidad en la base de datos.
 */
public class ModalidadDAO {
    // Objeto para gestionar la conexión a la base de datos
    private static ConexionBD conexion = new ConexionBD();

    /**
     * Agrega una nueva modalidad a la base de datos.
     *
     * @param modalidad Objeto Modalidad_getset que contiene los datos de la
     * modalidad a agregar.
     * @return true si la modalidad se agregó correctamente, false si hubo un
     * error.
     */
    public boolean agregarModalidad(Modalidad_getset modalidad) {
        String query = "INSERT INTO modalidad (ID_modalidad, modalidad) VALUES (?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, modalidad.getID_modalidad());
            preparedStatement.setString(2, modalidad.getModalidad());

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Actualiza una modalidad existente en la base de datos.
     *
     * @param modalidad Objeto Modalidad_getset que contiene los datos
     * actualizados de la modalidad.
     * @return true si la modalidad se actualizó correctamente, false si hubo un
     * error.
     */
    public boolean actualizarModalidad(Modalidad_getset modalidad) {
        String query = "UPDATE modalidad SET modalidad = ? WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, modalidad.getModalidad());
            preparedStatement.setInt(2, modalidad.getID_modalidad());

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Elimina una modalidad de la base de datos por su ID.
     *
     * @param idModalidad El ID de la modalidad a eliminar.
     * @return true si la modalidad se eliminó correctamente, false si hubo un
     * error.
     */
    public boolean eliminarModalidad(int idModalidad) {
        String query = "DELETE FROM modalidad WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idModalidad);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Obtiene una modalidad de la base de datos por su ID.
     *
     * @param idModalidad El ID de la modalidad a buscar.
     * @return Un objeto Modalidad_getset con los datos de la modalidad
     * encontrada, o null si no se encuentra.
     */
    public Modalidad_getset verModalidad(int idModalidad) {
        String query = "SELECT * FROM modalidad WHERE ID_modalidad = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idModalidad);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Modalidad_getset(
                        resultSet.getInt("ID_modalidad"),
                        resultSet.getString("modalidad")
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas las modalidades de la base de datos.
     *
     * @return Una lista de objetos Modalidad_getset, donde cada objeto
     * representa una modalidad.
     */
    public List<Modalidad_getset> listarModalidades() {
        List<Modalidad_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM modalidad";

        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Modalidad_getset modalidad = new Modalidad_getset(
                        resultSet.getInt("ID_modalidad"),
                        resultSet.getString("modalidad")
                );
                lista.add(modalidad);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return lista;
    }
}

