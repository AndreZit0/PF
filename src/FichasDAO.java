package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para realizar operaciones de acceso a datos
 * relacionadas con la entidad Ficha en la base de datos.
 */
public class FichasDAO {
    private Connection connection;

    /**
     * Constructor de la clase FichasDAO.
     *
     * @param connection La conexión a la base de datos que se utilizará para
     * realizar las operaciones.
     */
    public FichasDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta una nueva ficha en la base de datos, resolviendo los nombres de
     * programa y sede a sus correspondientes IDs.
     *
     * @param ficha Objeto Fichas_setget que contiene los datos de la ficha a
     * insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public boolean insertarFicha(Fichas_setget ficha) {
        String sql = """
            INSERT INTO fichas (ID_programas, ID_sede, codigo, modalidad, jornada, nivel_formacion, estado, fecha_inicio, fecha_fin_lec, fecha_final)
            VALUES (
                (SELECT ID_programas FROM programas WHERE nombre_programa = ?),
                (SELECT ID_sede FROM sedes WHERE nombre_sede = ?),
                ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ficha.getNombre_programa());
            stmt.setString(2, ficha.getNombre_sede());
            stmt.setString(3, ficha.getCodigo());
            stmt.setString(4, ficha.getModalidad());
            stmt.setString(5, ficha.getJornada());
            stmt.setString(6, ficha.getNivel_formacion());
            stmt.setDate(7, new java.sql.Date(ficha.getFecha_inicio().getTime()));
            stmt.setDate(8, new java.sql.Date(ficha.getFecha_fin_lec().getTime()));
            stmt.setDate(9, new java.sql.Date(ficha.getFecha_final().getTime()));
            stmt.setString(10, ficha.getEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una ficha de la base de datos por su ID, incluyendo los nombres
     * del programa y la sede.
     *
     * @param id El ID de la ficha a obtener.
     * @return Un objeto Fichas_setget con los datos de la ficha, o null si no
     * se encuentra.
     */
    public Fichas_setget obtenerFichaPorID(int id) {
        String sql = """
            SELECT f.ID_Fichas, p.nombre_programa, s.nombre_sede, f.codigo, f.modalidad,
                   f.jornada, f.nivel_formacion, f.estado, f.fecha_inicio, f.fecha_fin_lec, f.fecha_final
            FROM fichas f
            JOIN programas p ON f.ID_programas = p.ID_programas
            JOIN sede s ON f.ID_sede = s.ID_sede
            WHERE f.ID_Fichas = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Fichas_setget ficha = new Fichas_setget(
                        rs.getString("nombre_programa"),
                        rs.getString("nombre_sede"),
                        rs.getString("codigo"),
                        rs.getString("modalidad"),
                        rs.getString("jornada"),
                        rs.getString("nivel_formacion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin_lec"),
                        rs.getDate("fecha_final"),
                        rs.getString("estado")
                );
                ficha.setID_Fichas(rs.getInt("ID_Fichas"));
                return ficha;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todas las fichas de la base de datos, incluyendo los nombres de
     * los programas y las sedes.
     *
     * @return Una lista de objetos Fichas_setget, donde cada objeto representa
     * una ficha.
     */
    public List<Fichas_setget> listarFichas() {
        List<Fichas_setget> lista = new ArrayList<>();
        String sql = """
            SELECT f.ID_Fichas, p.nombre_programa, s.nombre_sede, f.codigo, f.modalidad,
                   f.jornada, f.nivel_formacion, f.estado, f.fecha_inicio, f.fecha_fin_lec, f.fecha_final
            FROM fichas f
            JOIN programas p ON f.ID_programas = p.ID_programas
            JOIN sede s ON f.ID_sede = s.ID_sede
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fichas_setget ficha = new Fichas_setget(
                        rs.getString("nombre_programa"),
                        rs.getString("nombre_sede"),
                        rs.getString("codigo"),
                        rs.getString("modalidad"),
                        rs.getString("jornada"),
                        rs.getString("nivel_formacion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin_lec"),
                        rs.getDate("fecha_final"),
                        rs.getString("estado")
                );
                ficha.setID_Fichas(rs.getInt("ID_Fichas"));
                lista.add(ficha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina una ficha de la base de datos por su ID.
     *
     * @param idFicha El ID de la ficha a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarFicha(int idFicha) {
        String sql = "DELETE FROM fichas WHERE ID_Fichas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFicha);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de una ficha en la base de datos, resolviendo los
     * nombres de programa y sede a sus correspondientes IDs.
     *
     * @param ficha Objeto Fichas_setget que contiene los nuevos datos de la ficha.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarFicha(Fichas_setget ficha) {
        String sql = """
            UPDATE fichas
            SET ID_programas = (SELECT ID_programas FROM programas WHERE nombre_programa = ?),
                ID_sede = (SELECT ID_sede FROM sedes WHERE nombre_sede = ?),
                codigo = ?, modalidad = ?, jornada = ?, nivel_formacion = ?, estado = ?,
                fecha_inicio = ?, fecha_fin_lec = ?, fecha_final = ?
            WHERE ID_Fichas = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ficha.getNombre_programa());
            stmt.setString(2, ficha.getNombre_sede());
            stmt.setString(3, ficha.getCodigo());
            stmt.setString(4, ficha.getModalidad());
            stmt.setString(5, ficha.getJornada());
            stmt.setString(6, ficha.getNivel_formacion());
            stmt.setDate(7, new java.sql.Date(ficha.getFecha_inicio().getTime()));
            stmt.setDate(8, new java.sql.Date(ficha.getFecha_fin_lec().getTime()));
            stmt.setDate(9, new java.sql.Date(ficha.getFecha_final().getTime()));
            stmt.setInt(10, ficha.getID_Fichas());
            stmt.setString(11, ficha.getEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

