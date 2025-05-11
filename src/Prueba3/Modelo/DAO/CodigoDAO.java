package Prueba3.Modelo.DAO;

import Prueba3.Modelo.Codigo;
import Prueba3.Modelo.Conexion.Conexion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CodigoDAO {
    private static final String INSERTAR = "INSERT INTO seguimiento (tipo_formato, fecha, archivo, observaciones, nombre_archivo, ID_usuarios, ID_aprendices) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String LISTAR = "SELECT * FROM seguimiento ORDER BY fecha DESC";
    private static final String ELIMINAR = "DELETE FROM seguimiento WHERE ID_seguimiento = ?";
    private static final String OBTENER_POR_ID = "SELECT * FROM seguimiento WHERE ID_seguimiento = ?";

    public boolean insertar(Codigo archivo) {
        String sql = "INSERT INTO seguimiento (tipo_formato, fecha, archivo, observaciones, nombre_archivo, ID_usuarios, ID_aprendices) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Verificar tamaño del archivo antes de insertar
            if (archivo.getArchivo() != null && archivo.getArchivo().length > 16777215) { // 16MB
                // Opción 1: Guardar solo la ruta
                stmt.setString(3, archivo.getRutaArchivo()); // Guardamos la ruta en lugar del contenido
            } else {
                // Opción 2: Guardar el contenido si es pequeño
                stmt.setBytes(3, archivo.getArchivo());
            }

            stmt.setString(1, archivo.getTipoFormato());
            stmt.setTimestamp(2, new Timestamp(archivo.getFecha().getTime()));
            stmt.setString(4, archivo.getObservaciones());
            stmt.setString(5, archivo.getNombreArchivo());
            stmt.setInt(6, archivo.getIdUsuario());
            stmt.setInt(7, archivo.getIdAprendiz());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        archivo.setIdSeguimiento(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar archivo: " + e.getMessage());
            // Intentar guardar solo la ruta si falla
            return insertarSoloRuta(archivo);
        }
        return false;
    }

    private boolean insertarSoloRuta(Codigo archivo) {
        String sql = "INSERT INTO seguimiento (tipo_formato, fecha, archivo, observaciones, nombre_archivo, ID_usuarios, ID_aprendices) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, archivo.getTipoFormato());
            stmt.setTimestamp(2, new Timestamp(archivo.getFecha().getTime()));
            stmt.setString(3, "RUTA:" + archivo.getRutaArchivo()); // Guardamos solo la ruta
            stmt.setString(4, archivo.getObservaciones());
            stmt.setString(5, archivo.getNombreArchivo());
            stmt.setInt(6, archivo.getIdUsuario());
            stmt.setInt(7, archivo.getIdAprendiz());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar solo ruta: " + e.getMessage());
            return false;
        }
    }

    public List<Codigo> listarTodos() {
        List<Codigo> archivos = new ArrayList<>();
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(LISTAR);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Codigo archivo = mapearResultSetACodigo(rs);
                archivos.add(archivo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar archivos: " + e.getMessage());
        }
        return archivos;
    }

    private Codigo mapearResultSetACodigo(ResultSet rs) throws SQLException {
        Codigo archivo = new Codigo();
        archivo.setIdSeguimiento(rs.getInt("ID_seguimiento"));
        archivo.setTipoFormato(rs.getString("tipo_formato"));
        archivo.setNombreArchivo(rs.getString("nombre_archivo"));

        byte[] archivoBytes = rs.getBytes("archivo");
        if (archivoBytes != null) {
            String contenido = new String(archivoBytes);
            if (contenido.startsWith("RUTA:")) {
                archivo.setRutaArchivo(contenido.substring(5));
            } else {
                archivo.setArchivo(archivoBytes);
                archivo.setRutaArchivo("almacenado_en_bd");
            }
        }

        archivo.setObservaciones(rs.getString("observaciones"));
        archivo.setFecha(rs.getTimestamp("fecha"));
        archivo.setIdAprendiz(rs.getInt("ID_aprendices"));

        Map<String, String> infoAprendiz = obtenerInfoCompletaAprendiz(archivo.getIdAprendiz());
        archivo.setNombreAprendiz(infoAprendiz.get("nombre"));
        archivo.setCedulaAprendiz(infoAprendiz.get("cedula"));

        return archivo;
    }

    public Codigo obtenerPorId(int id) {
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(OBTENER_POR_ID)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACodigo(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener archivo por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminar(int id) {
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(ELIMINAR)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar archivo: " + e.getMessage());
            return false;
        }
    }

    public boolean existeAprendiz(int cedula) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE numero = ? AND ID_rol = 1";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cedula);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error al verificar aprendiz: " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> obtenerInfoCompletaAprendizPorCedula(int cedula) {
        Map<String, String> info = new HashMap<>();
        String sql = "SELECT ID_usuarios, nombres, apellidos, numero FROM usuarios WHERE numero = ? AND ID_rol = 1";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                info.put("id", String.valueOf(rs.getInt("ID_usuarios")));
                info.put("nombre", rs.getString("nombres") + " " + rs.getString("apellidos"));
                info.put("cedula", rs.getString("numero"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener info de aprendiz: " + e.getMessage());
        }
        return info;
    }

    private Map<String, String> obtenerInfoCompletaAprendiz(int idAprendiz) {
        Map<String, String> info = new HashMap<>();
        String sql = "SELECT nombres, apellidos, numero FROM usuarios WHERE ID_usuarios = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAprendiz);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                info.put("nombre", rs.getString("nombres") + " " + rs.getString("apellidos"));
                info.put("cedula", rs.getString("numero"));
            } else {
                info.put("nombre", "Desconocido");
                info.put("cedula", "N/A");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener info de aprendiz por ID: " + e.getMessage());
        }
        return info;
    }

    private byte[] comprimirArchivo(byte[] datos) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
            gzip.write(datos);
        }
        return baos.toByteArray();
    }

    private byte[] descomprimirArchivo(byte[] datosComprimidos) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(datosComprimidos);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gzip = new GZIPInputStream(bais)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
        }
        return baos.toByteArray();
    }
}