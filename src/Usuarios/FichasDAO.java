package Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichasDAO {
    private Connection connection;

    public FichasDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para insertar una nueva ficha (resuelve nombres a IDs)
    public boolean insertarFicha(Fichas_setget ficha) {
        String sql = """
            INSERT INTO fichas (ID_programas, ID_sede, codigo, modalidad, jornada, nivel_formacion, estado, fecha_inicio, fecha_fin_lec, fecha_final)
            VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ficha.getNombre_programa());
            stmt.setString(2, ficha.getNombre_sede());
            stmt.setString(3, ficha.getCodigo());
            stmt.setString(4, ficha.getModalidad());
            stmt.setString(5, ficha.getJornada());
            stmt.setString(6, ficha.getNivel_formacion());
            stmt.setString(7, ficha.getEstado());
            stmt.setDate(8, new java.sql.Date(ficha.getFecha_inicio().getTime()));
            stmt.setDate(9, new java.sql.Date(ficha.getFecha_fin_lec().getTime()));
            stmt.setDate(10, new java.sql.Date(ficha.getFecha_final().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener una ficha por ID (usa JOIN para obtener nombres)
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

    // Método para listar todas las fichas (con nombres)
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

    // Método para eliminar una ficha
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

    // Método para actualizar una ficha (resuelve nombres a IDs)
    public boolean actualizarFicha(Fichas_setget ficha) {
        String sql = """
        UPDATE fichas
        SET ID_programas = ?,
            ID_sede = ?,
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
            stmt.setString(7, ficha.getEstado());           // ✅ Posición 7
            stmt.setDate(8, new java.sql.Date(ficha.getFecha_inicio().getTime()));
            stmt.setDate(9, new java.sql.Date(ficha.getFecha_fin_lec().getTime()));
            stmt.setDate(10, new java.sql.Date(ficha.getFecha_final().getTime()));
            stmt.setInt(11, ficha.getID_Fichas());          // ✅ Posición 11
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
