package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos MySQL.
 */
public class ConexionBD {

    /**
     * Establece y devuelve una conexión a la base de datos MySQL.
     *
     * @return Un objeto Connection que representa la conexión a la base de datos.
     * Si la conexión es exitosa, se devuelve una instancia de Connection;
     * de lo contrario, se devuelve null.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Establecer la conexión a la base de datos MySQL
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saep", "root", "");
        } catch (SQLException e) {
            // Capturar cualquier excepción de SQL que ocurra durante el proceso de conexión
            e.printStackTrace(); // Imprimir la traza de la excepción para facilitar la depuración
        }
        return connection; // Devolver la conexión establecida (o null si hubo un error)
    }
}
