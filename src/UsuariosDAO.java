package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para realizar operaciones de acceso a datos
 * relacionadas con la entidad Usuario en la base de datos.
 */
public class UsuariosDAO {

    // Objeto para gestionar la conexión a la base de datos
    private static ConexionBD conexion = new ConexionBD();

    /**
     * Agrega un nuevo usuario a la base de datos.  El ID del usuario se genera
     * automáticamente por la base de datos.
     *
     * @param usuario Objeto Usuarios_getset que contiene los datos del usuario a
     * agregar.
     * @return true si el usuario se agregó correctamente, false si hubo un error.
     */
    public boolean agregarUsuario(Usuarios_getset usuario) {
        String query = "INSERT INTO usuarios (ID_rol, tipo_dc, documento, nombres, apellidos, email, direccion, contacto1, contacto2, clave, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, usuario.getID_rol());
            preparedStatement.setString(2, usuario.getTipo_dc());
            preparedStatement.setString(3, usuario.getDocumento());
            preparedStatement.setString(4, usuario.getNombres());
            preparedStatement.setString(5, usuario.getApellidos());
            preparedStatement.setString(6, usuario.getEmail());
            preparedStatement.setString(7, usuario.getDireccion());
            preparedStatement.setString(8, usuario.getContacto1());
            preparedStatement.setString(9, usuario.getContacto2());
            preparedStatement.setString(10, usuario.getClave());
            preparedStatement.setString(11, usuario.getEstado());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     *
     * @param usuario Objeto Usuarios_getset que contiene los datos actualizados del
     * usuario.
     * @return true si el usuario se actualizó correctamente, false si hubo un
     * error.
     */
    public boolean actualizarUsuario(Usuarios_getset usuario) {
        String query = "UPDATE usuarios SET ID_rol = ?, tipo_dc = ?, documento = ?, nombres = ?, apellidos = ?, email = ?, direccion = ?, contacto1 = ?, contacto2 = ?, clave = ?, estado = ? WHERE ID_usuarios = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, usuario.getID_rol());
            preparedStatement.setString(2, usuario.getTipo_dc());
            preparedStatement.setString(3, usuario.getDocumento());
            preparedStatement.setString(4, usuario.getNombres());
            preparedStatement.setString(5, usuario.getApellidos());
            preparedStatement.setString(6, usuario.getEmail());
            preparedStatement.setString(7, usuario.getDireccion());
            preparedStatement.setString(8, usuario.getContacto1());
            preparedStatement.setString(9, usuario.getContacto2());
            preparedStatement.setString(10, usuario.getClave());
            preparedStatement.setString(11, usuario.getEstado());
            preparedStatement.setInt(12, usuario.getID_usuarios());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false si hubo un error.
     */
    public boolean eliminarUsuario(int idUsuario) {
        String query = "DELETE FROM usuarios WHERE ID_usuarios = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un usuario en la base de datos por su ID.
     *
     * @param idUsuario El ID del usuario a buscar.
     * @return Un objeto Usuarios_getset con los datos del usuario encontrado, o
     * null si no se encuentra.
     */
    public Usuarios_getset buscarUsuario(int idUsuario) {
        String query = "SELECT * FROM usuarios WHERE ID_usuarios = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setInt(1, idUsuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Usuarios_getset(
                            resultSet.getInt("ID_usuarios"),
                            resultSet.getInt("ID_rol"),
                            resultSet.getString("tipo_dc"),
                            resultSet.getString("documento"),
                            resultSet.getString("nombres"),
                            resultSet.getString("apellidos"),
                            resultSet.getString("email"),
                            resultSet.getString("direccion"),
                            resultSet.getString("contacto1"),
                            resultSet.getString("contacto2"),
                            resultSet.getString("clave"),
                            resultSet.getString("estado")
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todos los usuarios de la base de datos que tienen un rol
     * específico.
     *
     * @param rol El rol de los usuarios a listar.
     * @return Una lista de objetos Usuarios_getset, donde cada objeto representa
     * un usuario.
     */
    public List<Usuarios_getset> listarUsuariosPorRol(String rol) {
        List<Usuarios_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE ID_rol = (SELECT ID_rol FROM rol WHERE rol =?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {

            preparedStatement.setString(1, rol);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Usuarios_getset usuario = new Usuarios_getset(
                            resultSet.getInt("ID_usuarios"),
                            resultSet.getInt("ID_rol"),
                            resultSet.getString("tipo_dc"),
                            resultSet.getString("documento"),
                            resultSet.getString("nombres"),
                            resultSet.getString("apellidos"),
                            resultSet.getString("email"),
                            resultSet.getString("direccion"),
                            resultSet.getString("contacto1"),
                            resultSet.getString("contacto2"),
                            resultSet.getString("clave"),
                            resultSet.getString("estado")
                    );
                    lista.add(usuario);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return lista;
    }
}

