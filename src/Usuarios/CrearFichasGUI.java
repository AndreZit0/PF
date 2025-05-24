package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CrearFichasGUI {
    private JPanel pnlCrearFichas;
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

    // Usamos HashMap para mapear nombre -> ID
    private HashMap<String, String> mapaSedes = new HashMap<>();
    private HashMap<String, String> mapaProgramas = new HashMap<>();

    public JPanel getPanel() {
        return pnlCrearFichas;
    }

    public CrearFichasGUI() {

        cargarSedes();
        cargarPrograma();

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

                    Date fechaInicio = formatoFecha.parse(fechainicio.getText().replace("/", "-"));
                    Date fechaFinLectiva = formatoFecha.parse(fechafinlectiva.getText().replace("/", "-"));
                    Date fechaFinal = formatoFecha.parse(fechafin.getText().replace("/", "-"));

                    // Obtener los nombres seleccionados
                    String nombrePrograma = idprograma.getSelectedItem().toString();
                    String nombreSede = idsede.getSelectedItem().toString();

                    // Obtener los IDs correspondientes desde el HashMap
                    String idPrograma = mapaProgramas.get(nombrePrograma);
                    String idSede = mapaSedes.get(nombreSede);

                    Fichas_setget ficha = new Fichas_setget(
                            idPrograma,
                            idSede,
                            codigo.getText(),
                            modalidad.getSelectedItem().toString(),
                            jornada.getSelectedItem().toString(),
                            nivelformacion.getSelectedItem().toString(),
                            fechaInicio,
                            fechaFinLectiva,
                            fechaFinal,
                            estado.getSelectedItem().toString()
                    );

                    String url = "jdbc:mysql://localhost:3306/saep";
                    String user = "root";
                    String password = "";

                    try (Connection conn = DriverManager.getConnection(url, user, password)) {
                        FichasDAO dao = new FichasDAO(conn);
                        boolean exito = dao.insertarFicha(ficha);

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

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes limpiar los campos si deseas
            }
        });
    }

    private void cargarSedes() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_sede, nombre_sede FROM sede")) {

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            mapaSedes.clear();

            while (rs.next()) {
                String id = rs.getString("ID_sede");
                String nombre = rs.getString("nombre_sede");
                mapaSedes.put(nombre, id);
                modelo.addElement(nombre);
            }

            idsede.setModel(modelo);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pnlCrearFichas, "Error al cargar sedes");
            ex.printStackTrace();
        }
    }

    private void cargarPrograma() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_programas, nombre_programa FROM programas")) {

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            mapaProgramas.clear();

            while (rs.next()) {
                String id = rs.getString("ID_programas");
                String nombre = rs.getString("nombre_programa");
                mapaProgramas.put(nombre, id);
                modelo.addElement(nombre);
            }

            idprograma.setModel(modelo);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pnlCrearFichas, "Error al cargar programas");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Fichas");
        frame.setContentPane(new CrearFichasGUI().pnlCrearFichas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
