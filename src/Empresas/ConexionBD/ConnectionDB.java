package Empresas.ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase responsable de gestionar la conexión a la base de datos MySQL.
 */
public class ConnectionDB {

    /**
     * Establece y devuelve una conexión a la base de datos especificada.
     *
     * @return un objeto {@link Connection} si la conexión es exitosa, o {@code null} si ocurre un error.
     */
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saep", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
