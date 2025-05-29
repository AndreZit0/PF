package Example_Screen.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{

    /**
     * Establece y devuelve una conexión con la base de datos.
     *
     * @return un objeto {@link Connection} si la conexión es exitosa, o `null` si falla.
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Establece la conexión con la base de datos MySQL
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saep", "root", "");
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en la consola si la conexión falla
        }

        return connection;
    }


}
