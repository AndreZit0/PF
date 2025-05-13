package Empresas.Controlador;

import Empresas.ConexionBD.ConnectionDB;
import Empresas.Modelo.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para manejar las operaciones CRUD sobre la entidad Modelo.Empresa
 * en la base de datos.
 */
public class EmpresaDAO {
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Obtiene la lista de todas las empresas registradas en la base de datos.
     *
     * @return una lista de objetos {@link Empresa} con los datos recuperados.
     */
    public List<Empresa> obtenerEmpresa() {
        List<Empresa> empresas = new ArrayList<>();
        String query = "SELECT * FROM empresas";

        try (Connection con = connectionDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Empresa empresa = new Empresa(rs.getInt("ID_empresas"), rs.getInt("ID_usuarios")
                        , rs.getString("nit"),
                        rs.getString("nombre_empresa"), rs.getString("direccion"), rs.getString("area"),
                        rs.getString("contacto"), rs.getString("email"), rs.getString("departamento"), rs.getString("ciudad"));
                empresas.add(empresa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    /**
     * Agrega una nueva empresa a la base de datos.
     *
     * @param empresa el objeto {@link Empresa} que contiene los datos a insertar.
     * @return {@code true} si la operación fue exitosa, de lo contrario {@code false}.
     */
    public boolean agregarEmpresa(Empresa empresa) {
        String query = "INSERT INTO empresas (nit, nombre_empresa, direccion, area, contacto, email, departamento, ciudad, ID_usuarios) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, empresa.getNit());
            pst.setString(2, empresa.getNombre_empresa());
            pst.setString(3, empresa.getDireccion());
            pst.setString(4, empresa.getArea());
            pst.setString(5, empresa.getContacto());
            pst.setString(6, empresa.getEmail());
            pst.setString(7, empresa.getDepartamento());
            pst.setString(8, empresa.getCiudad());
            pst.setInt(9, empresa.getID_usuarios());

            int resultado = pst.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una empresa de la base de datos según su ID.
     *
     * @param id el ID único de la empresa a eliminar.
     * @return {@code true} si la eliminación fue exitosa, de lo contrario {@code false}.
     */
    public boolean eliminarEmpresa(int id) {
        String query = "DELETE FROM empresas WHERE ID_empresas = ?";
        try (Connection con = connectionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            int files = stmt.executeUpdate();
            if (files > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca una empresa en la base de datos según su NIT.
     *
     * @param nit el NIT de la empresa a buscar.
     * @return un objeto {@link Empresa} si se encuentra, o {@code null} si no existe.
     */
    public Empresa buscarEmpresa(String nit) {
        String query = "SELECT * FROM empresas WHERE nit = ?";
        try (Connection con = connectionDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, nit);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Empresa(
                        rs.getInt("ID_empresas"),
                        rs.getInt("ID_usuarios"),
                        rs.getString("nit"),
                        rs.getString("nombre_empresa"),
                        rs.getString("direccion"),
                        rs.getString("area"),
                        rs.getString("contacto"),
                        rs.getString("email"),
                        rs.getString("departamento"),
                        rs.getString("ciudad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza los datos de una empresa existente en la base de datos.
     *
     * @param empresa el objeto {@link Empresa} con los nuevos datos.
     * @return {@code true} si la actualización fue exitosa, de lo contrario {@code false}.
     */
    public boolean actualizarEmpresa(Empresa empresa) {
        String query = "UPDATE empresas SET nit = ?, nombre_empresa = ?, direccion = ?, area = ?, contacto = ?, email = ?, departamento = ?, ciudad = ?, ID_usuarios = ? WHERE ID_empresas = ?";

        try (Connection con = connectionDB.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, empresa.getNit());
            pst.setString(2, empresa.getNombre_empresa());
            pst.setString(3, empresa.getDireccion());
            pst.setString(4, empresa.getArea());
            pst.setString(5, empresa.getContacto());
            pst.setString(6, empresa.getEmail());
            pst.setString(7, empresa.getDepartamento());
            pst.setString(8, empresa.getCiudad());
            pst.setInt(9, empresa.getID_usuarios()); // Aquí se asegura de pasar el ID del coevaluador seleccionado
            pst.setInt(10, empresa.getID_empresas()); // ID de la empresa a actualizar

            // Ejecutar la actualización
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0; // Si actualizó, devuelve true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si hay error, devuelve false
        }
    }
}
