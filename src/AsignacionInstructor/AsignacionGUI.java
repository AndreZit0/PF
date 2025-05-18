package AsignacionInstructor;

import AsignacionInstructor.Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que representa la interfaz gráfica para la asignación de instructores a aprendices.
 */
public class AsignacionGUI {

    private JPanel panelAsignarInstructor;
    private JTable table1;
    private JTextField campoBusqueda;
    private static JFrame frame;
    private JFrame parentFrame;
    private Conexion conexion = new Conexion();
    private TableRowSorter<DefaultTableModel> sorter;
    private  NonEditableTableModel modelo;

    public JPanel getPanel(){return panelAsignarInstructor;}


    /**
     * Constructor de la clase AsignacionGUI.
     *
     * @param frame Ventana principal donde se cargará el panel de asignación.
     */
    public AsignacionGUI(JFrame frame) {

        this.frame = frame;
        this.parentFrame = parentFrame;

        modelo = new NonEditableTableModel();
        sorter = new TableRowSorter<>(modelo);
        table1.setModel(modelo);
        table1.setRowSorter(sorter);

        panelAsignarInstructor.setBackground(Color.decode("#F6F6F6"));

        JTableHeader header = table1.getTableHeader();
        header.setBackground(Color.decode("#39A900"));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 15));




        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = campoBusqueda.getText().trim().toLowerCase();
                try {
                    List<Asignacion> asignaciones = buscarGeneral(searchText);
                    actualizarTabla(asignaciones);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar: " + ex.getMessage());
                }
            }
        });

        // Cargar datos al iniciar
        try {
            List<Asignacion> asignaciones = buscarGeneral(""); // Búsqueda sin filtro
            actualizarTabla(asignaciones);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }




    }

    /**
     * Realiza una búsqueda general de aprendices en la base de datos utilizando un término de búsqueda
     * que puede coincidir con el documento, ficha o nombre completo del aprendiz.
     */

    public List<Asignacion> buscarGeneral(String terminoBusqueda) throws SQLException {
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        List<Asignacion> asignacion = new ArrayList<>();

        try {
            Connection con = conexion.getConnection();

            String sql = "SELECT a.ID_numeroAprendices, ua.numero, " +
                    "CONCAT(ua.nombres, ' ', ua.apellidos) AS nombre_aprendiz, " +
                    "f.codigo AS ficha, p.nombre_programa, " +
                    "CONCAT(ui.nombres, ' ', ui.apellidos) AS nombre_instructor " +
                    "FROM aprendices a " +
                    "JOIN usuarios ua ON a.ID_usuarios = ua.ID_usuarios " +
                    "LEFT JOIN usuarios ui ON a.ID_instructor = ui.ID_usuarios " +
                    "JOIN fichas f ON a.ID_Fichas = f.ID_Fichas " +
                    "JOIN programas p ON f.ID_programas = p.ID_programas " +
                    "WHERE ua.ID_rol = 1 AND (" +
                    "LOWER(ua.numero) LIKE ? OR " +
                    "LOWER(f.codigo) LIKE ? OR " +
                    "LOWER(CONCAT(ua.nombres, ' ', ua.apellidos)) LIKE ?)";

            consulta = con.prepareStatement(sql);
            for (int i = 1; i <= 3; i++) {
                consulta.setString(i, "%" + terminoBusqueda + "%");
            }
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Asignacion asg = new Asignacion();
                asg.setID_numeroAprendices(resultado.getInt("ID_numeroAprendices"));
                asg.setDocumento(resultado.getString("numero"));
                asg.setNombre(resultado.getString("nombre_aprendiz"));
                asg.setFicha(resultado.getString("ficha"));
                asg.setPrograma(resultado.getString("nombre_programa"));
                asg.setNombre_instructor(resultado.getString("nombre_instructor"));

                asignacion.add(asg);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultado != null) resultado.close();
            if (consulta != null) consulta.close();
        }
        return asignacion;
    }



    /**
     * Clase interna que define el renderizador de botones para la tabla.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setBorderPainted(true);
            setContentAreaFilled(true);
            setBackground(new Color(0x39A900));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "" : value.toString());

            // Establecer color del texto (foreground)
            setForeground(Color.WHITE);  // Texto blanco

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(new Color(0x39A900));  // Fondo verde original
            }

            return this;
        }
    }

    /**
     * Clase interna que define el editor de celdas tipo botón para la tabla.
     */
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;



        /**
         * Constructor del editor de botones.
         *
         * @param checkBox CheckBox necesario para el DefaultCellEditor.
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(0x39A900));
            button.setForeground(Color.WHITE);


            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();

                    int idAprendizSeleccionado = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
                    String nombre = String.valueOf(table1.getValueAt(selectedRow, 1));
                    String documento = String.valueOf(table1.getValueAt(selectedRow, 2));
                    String ficha = String.valueOf(table1.getValueAt(selectedRow, 3));
                    String evaluadorActual = String.valueOf(table1.getValueAt(selectedRow, 5));

                    int idEvaluadorActual = -1;
                    if (!evaluadorActual.equals("Sin Asignar")) {
                        try {
                            idEvaluadorActual = obtenerIdEvaluador(evaluadorActual);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    GUIEvaluador guiEvaluador = new GUIEvaluador(
                            idAprendizSeleccionado,
                            nombre,
                            documento,
                            ficha,
                            AsignacionGUI.this,
                            idEvaluadorActual
                    );
                    guiEvaluador.ejecutar();
                }
            });
        }

        /**
         * Obtiene el ID del evaluador a partir de su nombre completo.
         *
         * @param nombreEvaluador Nombre completo del evaluador.
         * @return ID del evaluador, o -1 si no se encuentra.
         * @throws SQLException si ocurre un error al consultar la base de datos.
         */
        public int obtenerIdEvaluador(String nombreEvaluador) throws SQLException {
            try (Connection con = conexion.getConnection()) {
                String sql = "SELECT ID_usuarios FROM usuarios WHERE CONCAT(nombres, ' ', apellidos) = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nombreEvaluador);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getInt("ID_usuarios");
                }
            }
            return -1;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            selectedRow = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

    }

    /**
     * Modelo de tabla que permite solo editar la columna de asignación de evaluadores.
     */
    public class NonEditableTableModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int row, int column) {
            return column ==6;
        }
    }



    /**
     * Refresca los datos de la tabla repitiendo la última búsqueda realizada.
     */
    public void refrescarBusqueda() {
        String terminoActual = campoBusqueda.getText().trim();
        try {
            List<Asignacion> resultados = buscarGeneral(terminoActual);
            actualizarTabla(resultados);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
        }
    }

    /**
     * Actualiza la tabla con la lista de asignaciones obtenida.
     *
     * @param asignacion lista de asignaciones a mostrar.
     */
    public void actualizarTabla(List<Asignacion> asignacion) {
        modelo.setRowCount(0);
        if (sorter != null) {
            table1.setRowSorter(null);
        }
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Ficha");
        modelo.addColumn("Programa");
        modelo.addColumn("Evaluador");
        modelo.addColumn("Asignar");
        sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);


        if (!campoBusqueda.getText().trim().isEmpty()) {
            campoBusqueda.setText(campoBusqueda.getText());
        }


        for (Asignacion asignacion1 : asignacion) {

            modelo.addRow(new Object[]{
                    asignacion1.getID_numeroAprendices(),
                    asignacion1.getNombre(),
                    asignacion1.getDocumento(),
                    asignacion1.getFicha(),
                    asignacion1.getPrograma(),
                    asignacion1.getNombre_instructor() != null ? asignacion1.getNombre_instructor() : "Sin Asignar",
                    "➕"
            });
        }
        table1.setModel(modelo);
        table1.getColumn("Asignar").setCellRenderer(new ButtonRenderer());
        table1.getColumn("Asignar").setCellEditor(new ButtonEditor(new JCheckBox()));
        table1.revalidate();
        table1.repaint();

        table1.setRowSorter(sorter);
        table1.revalidate();
        table1.repaint();
    }

    /**
     * Método principal para ejecutar la interfaz gráfica.
     *
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        AsignacionGUI asignacionGUI = new AsignacionGUI(frame);
        JFrame frame = new JFrame("Asignación Instructor");
        frame.setContentPane(asignacionGUI.panelAsignarInstructor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new AsignacionGUI(frame);

        URL iconoURL = AsignacionGUI.class.getClassLoader().getResource("imagenes/sena.jpeg");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }
    }

}
