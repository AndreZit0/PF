package Usuarios;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class UsuariosDAO {
    private static DBConnection dbConnection = new DBConnection();

    // Agregar usuario (con validaciones)
    public boolean agregarUsuario(Usuarios_getset usuario) {
        if (!validarUsuario(usuario, false)) return false;

        String query = "INSERT INTO usuarios (ID_rol, tipo_dc, numero, nombres, apellidos, email, email_insti, direccion, contacto1, contacto2, clave, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, usuario.getID_rol());
            pst.setString(2, usuario.getTipo_dc());
            pst.setString(3, usuario.getDocumento());
            pst.setString(4, usuario.getNombres());
            pst.setString(5, usuario.getApellidos());
            pst.setString(6, usuario.getEmail());
            pst.setString(7, usuario.getEmail_insti());
            pst.setString(8, usuario.getDireccion());
            pst.setString(9, usuario.getContacto1());
            pst.setString(10, usuario.getContacto2());
            pst.setString(11, usuario.getClave());
            pst.setString(12, usuario.getEstado());

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerCorreoPorDocumento(String numeroDoc, String tipoDoc) {
        String email = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT email FROM usuarios WHERE numero = ? AND tipo_dc = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, numeroDoc);
            ps.setString(2, tipoDoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }


    // Actualizar usuario por ID (con validaciones)
    public boolean actualizarUsuario(Usuarios_getset usuario) {
        if (!validarUsuario(usuario, true)) return false;

        String query = "UPDATE usuarios SET ID_rol = ?, tipo_dc = ?, numero = ?, nombres = ?, apellidos = ?, email = ?, email_insti = ?, direccion = ?, contacto1 = ?, contacto2 = ?, clave = ?, estado = ? WHERE ID_usuarios = ?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, usuario.getID_rol());
            pst.setString(2, usuario.getTipo_dc());
            pst.setString(3, usuario.getDocumento());
            pst.setString(4, usuario.getNombres());
            pst.setString(5, usuario.getApellidos());
            pst.setString(6, usuario.getEmail());
            pst.setString(7, usuario.getEmail_insti());
            pst.setString(8, usuario.getDireccion());
            pst.setString(9, usuario.getContacto1());
            pst.setString(10, usuario.getContacto2());
            pst.setString(11, usuario.getClave());
            pst.setString(12, usuario.getEstado());
            pst.setInt(13, usuario.getID_usuarios());

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar usuario por ID
    public boolean eliminarUsuario(int id_usuario) {
        String query = "DELETE FROM usuarios WHERE ID_usuarios = ?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_usuario);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar un usuario por ID
    public Usuarios_getset buscarUsuario(int id_usuario) {
        String query = "SELECT * FROM usuarios WHERE ID_usuarios = ?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id_usuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuarios_getset(
                            rs.getInt("ID_usuarios"),
                            rs.getInt("ID_rol"),
                            rs.getString("tipo_dc"),
                            rs.getString("numero"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getString("email_insti"),
                            rs.getString("direccion"),
                            rs.getString("contacto1"),
                            rs.getString("contacto2"),
                            rs.getString("clave"),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todos los usuarios por rol
    public ArrayList<Usuarios_getset> listarUsuariosPorRol(String rol) {
        ArrayList<Usuarios_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE ID_rol = (SELECT ID_rol FROM rol WHERE rol =?)";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, rol);
            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Usuarios_getset usuario = new Usuarios_getset(
                            rs.getInt("ID_usuarios"),
                            rs.getInt("ID_rol"),
                            rs.getString("tipo_dc"),
                            rs.getString("numero"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getString("email_insti"),
                            rs.getString("direccion"),
                            rs.getString("contacto1"),
                            rs.getString("contacto2"),
                            rs.getString("clave"),
                            rs.getString("estado")
                    );
                    lista.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Usuarios_getset> obtenerUltimoAprendiz(String rol) {
        ArrayList<Usuarios_getset> lista = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE ID_rol = (SELECT ID_rol FROM rol WHERE rol =?) ORDER BY ID_usuarios DESC LIMIT 1";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, rol);
            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Usuarios_getset usuario = new Usuarios_getset(
                            rs.getInt("ID_usuarios"),
                            rs.getInt("ID_rol"),
                            rs.getString("tipo_dc"),
                            rs.getString("numero"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("email"),
                            rs.getString("email_insti"),
                            rs.getString("direccion"),
                            rs.getString("contacto1"),
                            rs.getString("contacto2"),
                            rs.getString("clave"),
                            rs.getString("estado")
                    );
                    lista.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuarios_getset obtenerUsuarioPorDocumento(String tipoDoc, String numeroDoc) {
        Usuarios_getset usuario = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuarios WHERE tipo_dc = ? AND numero = ?")) {

            stmt.setString(1, tipoDoc);
            stmt.setString(2, numeroDoc);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuarios_getset(
                        rs.getInt("id_usuarios"),
                        rs.getInt("id_rol"),
                        rs.getString("tipo_dc"),
                        rs.getString("numero"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("email_insti"),
                        rs.getString("direccion"),
                        rs.getString("contacto1"),
                        rs.getString("contacto2"),
                        rs.getString("clave"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario: " + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return usuario;
    }

    // Validaciones
    private boolean validarUsuario(Usuarios_getset usuario, boolean esActualizacion) {
        if (usuario.getDocumento().isEmpty() || usuario.getNombres().isEmpty() || usuario.getApellidos().isEmpty()
                || usuario.getEmail().isEmpty() || usuario.getEmail_insti().isEmpty() || usuario.getDireccion().isEmpty()
                || usuario.getContacto1().isEmpty() || usuario.getClave().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos obligatorios deben estar llenos.");
            return false;
        }

        if (!usuario.getDocumento().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "El número de documento debe tener exactamente 10 dígitos numéricos.");
            return false;
        }

        if (!usuario.getContacto1().matches("\\d+") || (!usuario.getContacto2().isEmpty() && !usuario.getContacto2().matches("\\d+"))) {
            JOptionPane.showMessageDialog(null, "Los contactos deben contener solo números.");
            return false;
        }

        if (!usuario.getEmail().contains("@") || !usuario.getEmail_insti().contains("@")) {
            JOptionPane.showMessageDialog(null, "Los correos deben contener el carácter '@'.");
            return false;
        }

        if (existeDocumento(usuario.getDocumento(), esActualizacion ? usuario.getID_usuarios() : -1)) {
            JOptionPane.showMessageDialog(null, "El número de documento ya está registrado.");
            return false;
        }

        if (existeCorreo(usuario.getEmail(), "email", esActualizacion ? usuario.getID_usuarios() : -1)) {
            JOptionPane.showMessageDialog(null, "El correo personal ya está registrado.");
            return false;
        }

        if (existeCorreo(usuario.getEmail_insti(), "email_insti", esActualizacion ? usuario.getID_usuarios() : -1)) {
            JOptionPane.showMessageDialog(null, "El correo institucional ya está registrado.");
            return false;
        }

        return true;
    }

    private boolean existeDocumento(String numero, int idExcluido) {
        String query = "SELECT 1 FROM usuarios WHERE numero = ? AND ID_usuarios <> ?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, numero);
            pst.setInt(2, idExcluido);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean existeCorreo(String correo, String campo, int idExcluido) {
        String query = "SELECT 1 FROM usuarios WHERE " + campo + " = ? AND ID_usuarios <> ?";
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, correo);
            pst.setInt(2, idExcluido);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}
