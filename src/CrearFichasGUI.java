package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que representa la interfaz gráfica para la creación de fichas.
 * Permite ingresar y guardar la información de una nueva ficha en la base de datos.
 */
public class CrearFichasGUI {
    private JPanel main;
    private JComboBox estado;
    private JButton confirmarButton;
    private JButton cancelar;
    private JComboBox idsede;
    private JTextField codigo;
    private JTextField fechainicio;
    private JTextField fechafinlectiva;
    private JTextField fechafin;
    private JComboBox idprograma;
    private JComboBox modalidad;
    private JComboBox jornada;
    private JComboBox nivelformacion;
    private java.util.List<String> listaIDSede = new java.util.ArrayList<>();
    private java.util.List<String> listaIDPrograma = new java.util.ArrayList<>();


    /**
     * Constructor de la clase CrearFichasGUI.
     * Inicializa y configura los componentes de la interfaz gráfica,
     * incluyendo la carga de datos desde la base de datos para los ComboBox.
     */
    public CrearFichasGUI() {

        cargarSedes();
        cargarPrograma();

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Formato de fecha para el análisis
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                    // Reemplazar barras diagonales por guiones para el formato de fecha MySQL
                    String inicioTexto = fechainicio.getText().replace("/", "-");
                    String finLectivaTexto = fechafinlectiva.getText().replace("/", "-");
                    String finTexto = fechafin.getText().replace("/", "-");

                    // Convertir las cadenas a objetos Date
                    Date fechaInicio = formatoFecha.parse(inicioTexto);
                    Date fechaFinLectiva = formatoFecha.parse(finLectivaTexto);
                    Date fechaFinal = formatoFecha.parse(finTexto);

                    // Crear objeto Fichas_setget con los datos ingresados
                    Fichas_setget fichas = new Fichas_setget(
                            listaIDPrograma.get(idprograma.getSelectedIndex()),
                            listaIDSede.get(idsede.getSelectedIndex()),
                            codigo.getText(),
                            modalidad.getSelectedItem().toString(),
                            jornada.getSelectedItem().toString(),
                            nivelformacion.getSelectedItem().toString(),
                            fechaInicio,
                            fechaFinLectiva,
                            fechaFinal,
                            estado.getSelectedItem().toString()
                    );

                    // Configuración de la conexión a la base de datos
                    String url = "jdbc:mysql://localhost:3306/saep";
                    String user = "root";
                    String password = "";

                    // Establecer la conexión y realizar la operación de inserción
                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        FichasDAO dao = new FichasDAO(conn);
                        boolean exito = dao.insertarFicha(fichas); // Llama al método insertarFicha de FichasDAO

                        if (exito) {
                            JOptionPane.showMessageDialog(null, "Ficha creada correctamente");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al guardar la ficha en la base de datos.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error de conexión: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear ficha: " + ex.getMessage());
                }
            }
        });

        // Configuración del listener para el botón Cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opcional: limpiar campos o cerrar ventana
            }
        });
    }

    /**
     * Carga las sedes desde la base de datos y las agrega al ComboBox correspondiente.
     * Utiliza una conexión JDBC para realizar la consulta.
     */
    private void cargarSedes() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_sede, nombre_sede FROM sede")) {

            DefaultComboBoxModel<String> modeloSede = new DefaultComboBoxModel<>();
            listaIDSede.clear(); // Limpia la lista antes de cargar los nuevos IDs

            while (rs.next()) {
                listaIDSede.add(rs.getString("ID_sede"));
                modeloSede.addElement(rs.getString("nombre_sede"));
            }

            idsede.setModel(modeloSede); // Asigna el modelo al ComboBox

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar sedes");
            ex.printStackTrace();
        }
    }

    /**
     * Carga los programas desde la base de datos y los agrega al ComboBox correspondiente.
     * Utiliza una conexión JDBC para realizar la consulta.
     */
    private void cargarPrograma() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_programas, nombre_programa FROM programas")) {

            DefaultComboBoxModel<String> modeloPrograma = new DefaultComboBoxModel<>();
            listaIDPrograma.clear();  // Limpia la lista antes de cargar los nuevos IDs

            while (rs.next()) {
                listaIDPrograma.add(rs.getString("ID_programas"));
                modeloPrograma.addElement(rs.getString("nombre_programa"));
            }

            idprograma.setModel(modeloPrograma); // Asigna el modelo al ComboBox

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar programas");
            ex.printStackTrace();
        }
    }

    /**
     * Método principal para iniciar la aplicación de creación de fichas.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Fichas");
        frame.setContentPane(new CrearFichasGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}

