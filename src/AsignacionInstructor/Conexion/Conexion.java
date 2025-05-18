package AsignacionInstructor.Conexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection getConnection()
    {
        Connection con = null;

        try {
            // Intenta establecer la conexión con la base de datos MySQL
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saep", "root", "");
        } catch (SQLException e) {
            // Muestra un mensaje de error si la conexión falla
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos" + e.toString());
        }

        return con;
    }
}
