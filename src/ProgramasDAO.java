package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para realizar operaciones de acceso a datos
 * relacionadas con la entidad Programa en la base de datos.
 */
public class ProgramasDAO {
    // Objeto para gestionar la conexión a la base de datos
    private static ConexionBD conexion = new ConexionBD();

    /**
     * Agrega un nuevo programa a la base de datos.
     *
     * @param programa Objeto Programas_getset que contiene los datos del
     * programa a agregar.
     * @return true si el programa se agregó correctamente, false si hubo un error.
     */
    public boolean agregarPrograma(Programas_getset programa) {
        String query = "INSERT INTO programas (nombre_programa, estado) VALUES ( ?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, programa.getNombre_programa());
            preparedStatement.setString(2, programa.getEstado());

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Actualiza un programa existente en la base de datos.
     *
     * @param programa Objeto Programas_getset que contiene los datos
     * actualizados del programa.
     * @return true si el programa se actualizó correctamente, false si hubo un
     * error.
     */
    public boolean actualizarPrograma(Programas_getset programa) {
        String query = "UPDATE programas SET nombre_programa = ?, estado = ? WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, programa.getNombre_programa());
            preparedStatement.setString(2, programa.getEstado());
            preparedStatement.setInt(3, programa.getID_programas());

            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Elimina un programa de la base de datos por su ID.
     *
     * @param idPrograma El ID del programa a eliminar.
     * @return true si el programa se eliminó correctamente, false si hubo un
     * error.
     */
    public boolean eliminarPrograma(int idPrograma) {
        String query = "DELETE FROM programas WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idPrograma);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Obtiene un programa de la base de datos por su ID.
     *
     * @param idProgramas El ID del programa a buscar.
     * @return Un objeto Programas_getset con los datos del programa encontrado,
     * o null si no se encuentra.
     */
    public Programas_getset verPrograma(int idProgramas) {
        String query = "SELECT * FROM programas WHERE ID_programas = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idProgramas);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Programas_getset(
                        resultSet.getString("nombre_programa"),
                        resultSet.getString("estado")
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todos los programas de la base de datos.
     *
     * @return Una lista de objetos Programas_getset, donde cada objeto
     * representa un programa.
     */
    public List<Programas_getset> listarProgramas() {
        List<Programas_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM programas";

        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Programas_getset programa = new Programas_getset(
                        resultSet.getInt("ID_programas"),
                        resultSet.getString("nombre_programa"),
                        resultSet.getString("estado")
                );
                lista.add(programa);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return lista;
    }
}

