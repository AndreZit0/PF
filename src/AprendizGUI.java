package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que representa la interfaz gráfica para la creación de un nuevo aprendiz.
 * Permite seleccionar y asignar los datos del aprendiz, como usuario, ficha, empresa,
 * evaluador, modalidad y estado.
 */
public class AprendizGUI {

    private JComboBox<String> estado;
    private JButton confirmarButton;
    private JComboBox<String> aprendiz;    // ID_usuarios
    private JComboBox<String> ficha;       // ID_Fichas
    private JComboBox<String> modalidad;   // ID_modalidad
    private JComboBox<String> evaluador;   // ID_instructor
    private JComboBox<String> empresa;     // ID_empresas
    private JButton cancelar;
    private ArrayList<Usuarios_getset> listaAprendices;
    private ArrayList<Usuarios_getset> listaInstructores;
    private ArrayList<Integer> listaIDEmpresas = new ArrayList<>(); // ID reales de empresas

    private JPanel main;

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Constructor de la clase AprendizGUI.
     * Inicializa y configura los componentes de la interfaz gráfica,
     * incluyendo la carga de datos desde la base de datos para los ComboBox.
     */
    public AprendizGUI() {
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        // Lista de aprendices
        listaAprendices = usuariosDAO.listarUsuariosPorRol("aprendiz");
        DefaultComboBoxModel<String> modeloAprendiz = new DefaultComboBoxModel<>();
        for (Usuarios_getset u : listaAprendices) {
            modeloAprendiz.addElement(u.getNombres() + " " + u.getApellidos());
        }
        aprendiz.setModel(modeloAprendiz);

        // Lista de instructores
        listaInstructores = usuariosDAO.listarUsuariosPorRol("evaluador");
        DefaultComboBoxModel<String> modeloEvaluador = new DefaultComboBoxModel<>();
        for (Usuarios_getset u : listaInstructores) {
            modeloEvaluador.addElement(u.getNombres() + " " + u.getApellidos());
        }
        evaluador.setModel(modeloEvaluador);

        // Cargar empresas, fichas y modalidades
        cargarEmpresas();
        cargarFichas();
        cargarModalidad();

        // Configuración de listeners para los botones
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndexAprendiz = aprendiz.getSelectedIndex();
                    if (selectedIndexAprendiz == -1 || listaAprendices.isEmpty()) {
                        JOptionPane.showMessageDialog(main, "Por favor, seleccione un aprendiz.");
                        return;
                    }
                    int idUsuario = listaAprendices.get(selectedIndexAprendiz).getID_usuarios();

                    int selectedIndexEvaluador = evaluador.getSelectedIndex();
                    if (selectedIndexEvaluador == -1 || listaInstructores.isEmpty()) {
                        JOptionPane.showMessageDialog(main, "Por favor, seleccione un evaluador.");
                        return;
                    }
                    int idInstructor = listaInstructores.get(selectedIndexEvaluador).getID_usuarios();

                    int idFicha = ficha.getSelectedIndex() + 1;
                    int idEmpresa = listaIDEmpresas.get(empresa.getSelectedIndex()); // Obtener el ID real
                    int idModalidad = modalidad.getSelectedIndex() + 1;
                    String estadoSeleccionado = (String) estado.getSelectedItem();

                    Aprendiz_getset aprend = new Aprendiz_getset(
                            0,
                            idUsuario,
                            idFicha,
                            idEmpresa,
                            idInstructor,
                            idModalidad,
                            estadoSeleccionado
                    );

                    AprendizDAO dao = new AprendizDAO();
                    if (dao.crearAprendiz(aprend)) {
                        JOptionPane.showMessageDialog(main, "Aprendiz agregado correctamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar aprendiz");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    /**
     * Limpia los campos de selección de la interfaz, reseteando los ComboBox
     * a su primer elemento seleccionado (si existen elementos).
     */
    private void limpiarCampos() {
        if (aprendiz.getItemCount() > 0) aprendiz.setSelectedIndex(0);
        if (empresa.getItemCount() > 0) empresa.setSelectedIndex(0);
        if (evaluador.getItemCount() > 0) evaluador.setSelectedIndex(0);
        if (modalidad.getItemCount() > 0) modalidad.setSelectedIndex(0);
        if (ficha.getItemCount() > 0) ficha.setSelectedIndex(0);
        if (estado.getItemCount() > 0) estado.setSelectedIndex(0);
    }

    /**
     * Carga la lista de empresas desde la base de datos y las agrega al ComboBox de empresas.
     * Utiliza una conexión JDBC para realizar la consulta.
     */
    private void cargarEmpresas() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_empresas, nombre_empresa FROM empresas")) {

            DefaultComboBoxModel<String> modeloEmpresa = new DefaultComboBoxModel<>();
            listaIDEmpresas.clear(); // Limpia la lista de IDs antes de cargarla

            while (rs.next()) {
                listaIDEmpresas.add(rs.getInt("ID_empresas"));
                modeloEmpresa.addElement(rs.getString("nombre_empresa"));
            }

            empresa.setModel(modeloEmpresa);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar empresas");
            ex.printStackTrace();
        }
    }

    /**
     * Carga la lista de fichas desde la base de datos y las agrega al ComboBox de fichas.
     * Utiliza una conexión JDBC para realizar la consulta.
     */
    private void cargarFichas() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT codigo FROM fichas")) {

            DefaultComboBoxModel<String> modeloFichas = new DefaultComboBoxModel<>();
            while (rs.next()) {
                modeloFichas.addElement(rs.getString("codigo"));
            }

            ficha.setModel(modeloFichas);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar fichas");
            ex.printStackTrace();
        }
    }

    /**
     * Carga la lista de modalidades desde la base de datos y las agrega al ComboBox de modalidades.
     * Utiliza una conexión JDBC para realizar la consulta.
     */
    private void cargarModalidad() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT modalidad FROM modalidad")) {

            DefaultComboBoxModel<String> modeloModalidad = new DefaultComboBoxModel<>();
            while (rs.next()) {
                modeloModalidad.addElement(rs.getString("modalidad"));
            }

            modalidad.setModel(modeloModalidad);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar modalidad");
            ex.printStackTrace();
        }
    }

    /**
     * Método principal para iniciar la aplicación de creación de aprendices.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Crear Aprendiz");
        frame.setContentPane(new AprendizGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

