package Example_Screen.Model;

import Example_Screen.Connection.DBConnection;

import java.sql.*;
import java.time.LocalDate;

public class AprendizDAO {

    public Aprendiz obtenerAprendiz() {
        String query = "SELECT * FROM progreso_aprendiz LIMIT 1";
        try (Statement stmt = DBConnection.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                LocalDate inicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate fin = rs.getDate("fecha_fin").toLocalDate();
                return new Aprendiz(nombre, inicio, fin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
