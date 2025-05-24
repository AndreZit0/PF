package AsignacionInstructor;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.*;

/**
 * Clase GUIEvaluador que permite la asignación y eliminación de instructores (evaluadores) a aprendices.
 * Muestra una lista de evaluadores disponibles y permite seleccionar uno para asignarlo a un aprendiz específico.
 */
public class GUIEvaluador {

    private JTable table1;
    private JPanel main;
    private JTextField textField1;
    private JButton asignarButton;
    private JButton eliminarButton;
    private DBConnection dbConnection = new DBConnection();
    private AsignacionGUI asignacionGUI;
    private int idEvaluadorActual;
    int filas;

    /**
     * Constructor de la clase GUIEvaluador.
     * Inicializa la interfaz gráfica y carga la lista de evaluadores.
     *
     * @param idaprendiz        Identificador del aprendiz al que se le asignará o eliminará un instructor.
     * @param nombre            Nombre del aprendiz.
     * @param documento         Documento del aprendiz.
     * @param ficha             Ficha del programa del aprendiz.
     * @param parentGUI         Referencia a la interfaz gráfica de asignación (AsignacionGUI) para poder refrescarla.
     * @param idEvaluadorActual Identificador del instructor actualmente asignado al aprendiz (-1 si no hay ninguno).
     */
    public GUIEvaluador(int idaprendiz, String nombre, String documento, String ficha, AsignacionGUI parentGUI, int idEvaluadorActual){
        this.asignacionGUI = parentGUI;
        this.idEvaluadorActual = idEvaluadorActual;

        listaContacto();

        main.setBackground(Color.decode("#F6F6F6"));

        JTableHeader header = table1.getTableHeader();
        header.setBackground(Color.decode("#39A900"));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 15));
        header.setReorderingAllowed(false); // Nueva línea para evitar movimiento de columnas

        asignarButton.setBackground(new Color(0x39A900));
        eliminarButton.setBackground(new Color(0xFF3B30));


        Color colorHover = new Color(0, 120, 50);
        Color colorBase = new Color(57, 169, 0);
        aplicarEfectoHover(asignarButton, colorHover, colorBase);


        Color color1 = new Color(0xCC2F26);
        Color colorBase2 = new Color(0xFF3B30);
        aplicarEfectoHover(eliminarButton, color1, colorBase2);



        Color color3 = new Color(0x0051B8);
        Color colorBase4 = new Color(0x007AFF);




        // Agregar DocumentListener para la autobúsqueda
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                autoBuscar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                autoBuscar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                autoBuscar();
            }

            /**
             * Realiza la búsqueda automática mientras el usuario escribe.
             */
            private void autoBuscar() {
                String texto = textField1.getText().trim();
                if (texto.isEmpty()) {
                    listaContacto(); // Si el campo está vacío, mostrar todos los contactos
                } else {
                    buscarPorNombre(texto); // Buscar por el texto escrito
                }
            }
        });


        asignarButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada al hacer clic en el botón de asignar.
             * Asigna el instructor seleccionado al aprendiz actual.
             *
             * @param e Evento de acción.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int idInstructor = Integer.parseInt(table1.getValueAt(filaSeleccionada, 0).toString());
                    int idAprendiz = idaprendiz;

                    try (Connection con = dbConnection.getConnection()) {
                        String sql = "UPDATE aprendices " +
                                "SET id_instructor = ?, estado = 'activo' " +
                                "WHERE id_numeroAprendices = ?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setInt(1, idInstructor);
                        ps.setInt(2, idAprendiz);

                        int filasActualizadas = ps.executeUpdate();

                        if (filasActualizadas > 0) {
                            JOptionPane.showMessageDialog(null, "Instructor asignado correctamente.");
                            asignacionGUI.refrescarBusqueda();
                            SwingUtilities.getWindowAncestor(main).dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo asignar el instructor.");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al asignar instructor: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un instructor.");
                }

            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            /**
             * Acción realizada al hacer clic en el botón de eliminar.
             * Elimina el instructor asignado al aprendiz actual o un instructor seleccionado de la lista.
             *
             * @param e Evento de acción.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                if (idaprendiz != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Eliminar al evaluador actualmente asignado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION
                    );

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        eliminarEvaluador(idaprendiz);
                    }
                } else {

                    int filaSeleccionada = table1.getSelectedRow();
                    if (filaSeleccionada >= 0) {
                        int idaprendiz = Integer.parseInt(table1.getValueAt(filaSeleccionada, 0).toString());
                        eliminarEvaluador(idaprendiz);
                    }
                }
            }
        });

    }

    /**
     * Elimina un instructor de la base de datos y actualiza la tabla de aprendices removiendo la asignación.
     *
     * @param idAprendiz Identificador del aprendiz para a eliminar la asignación.
     */
    public void eliminarEvaluador(int idAprendiz) {
        try (Connection con = dbConnection.getConnection()) {
            String sql = "UPDATE aprendices SET ID_instructor = NULL WHERE ID_numeroAprendices = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAprendiz);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Evaluador eliminado del aprendiz.");
                asignacionGUI.refrescarBusqueda();
                SwingUtilities.getWindowAncestor(main).dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el evaluador.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        }
    }

    /**
     * Aplica un efecto de cambio de color al pasar el ratón sobre un botón.
     *
     * @param boton       Botón al que se le aplicará el efecto.
     * @param colorHover  Color al que cambiará el botón cuando el ratón esté encima.
     * @param colorBase   Color original del botón.
     */
    public void aplicarEfectoHover(JButton boton, Color colorHover, Color colorBase) {
        boton.addMouseListener(new MouseAdapter() {
            /**
             * Cambia el color de fondo del botón cuando el ratón entra en su área.
             *
             * @param e Evento del ratón.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            /**
             * Restaura el color de fondo original del botón cuando el ratón sale de su área.
             *
             * @param e Evento del ratón.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBase);
            }
        });
    }

    /**
     * Busca evaluadores por nombre en la base de datos y actualiza la tabla con los resultados.
     *
     * @param nombre Nombre o parte del nombre del evaluador a buscar.
     */
    /**
     * Busca evaluadores por nombre en la base de datos y actualiza la tabla con los resultados.
     * Mantiene el ID oculto pero accesible para su uso en las operaciones.
     *
     * @param nombre Nombre o parte del nombre del evaluador a buscar.
     */
    public void buscarPorNombre(String nombre) {
        NonEditableTableModel model = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        model.addColumn("ID_usuarios");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");

        try (Connection con = dbConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id_usuarios, nombres, apellidos FROM usuarios " +
                            "WHERE (nombres LIKE ? OR apellidos LIKE ?) AND ID_rol = 2");
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] fila = new String[3];
                fila[0] = rs.getString("id_usuarios");
                fila[1] = rs.getString("nombres");
                fila[2] = rs.getString("apellidos");
                model.addRow(fila);
            }

            table1.setModel(model);
            table1.setRowHeight(30);


            JTableHeader tableHeader = table1.getTableHeader();
            tableHeader.setReorderingAllowed(false);


            table1.getColumnModel().getColumn(0).setMinWidth(0);
            table1.getColumnModel().getColumn(0).setMaxWidth(0);
            table1.getColumnModel().getColumn(0).setWidth(0);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar: " + e.getMessage());
        }
    }
    /**
     * Carga la lista de todos los evaluadores desde la base de datos y los muestra en la tabla.
     */
    /**
     * Carga la lista de todos los evaluadores desde la base de datos y los muestra en la tabla.
     */
    public void listaContacto() {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        modeloa.addColumn("ID_usuarios");
        modeloa.addColumn("tipo_dc");
        modeloa.addColumn("numero");
        modeloa.addColumn("Nombres");
        modeloa.addColumn("Apellidos");
        modeloa.addColumn("email");
        modeloa.addColumn("direccion");
        modeloa.addColumn("contacto1");
        modeloa.addColumn("contacto2");
        modeloa.addColumn("clave");
        modeloa.addColumn("estado");
        modeloa.addColumn("ID_rol");

        if (idEvaluadorActual != -1) {
            seleccionarEvaluadorActual(idEvaluadorActual);
        }

        table1.setModel(modeloa);
        table1.setRowHeight(30);


        JTableHeader tableHeader = table1.getTableHeader();
        tableHeader.setReorderingAllowed(false);

        String[] dato = new String[12];

        Connection con = dbConnection.getConnection();

        try {
            // Ocultamos todas las columnas excepto "Nombres" (3) y "Apellidos" (4)
            for (int i = 0; i <= 11; i++) {
                if (i != 3 && i != 4) { // Solo mostramos las columnas 3 y 4
                    table1.getColumnModel().getColumn(i).setMinWidth(0);
                    table1.getColumnModel().getColumn(i).setMaxWidth(0);
                    table1.getColumnModel().getColumn(i).setWidth(0);
                }
            }

            Statement stmt = con.createStatement();
            // Modificamos la consulta para no concatenar el nombre completo
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE ID_rol = 2");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString("nombres");  // Nombres
                dato[4] = rs.getString("apellidos"); // Apellidos
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);
                dato[7] = rs.getString(8);
                dato[8] = rs.getString(9);
                dato[9] = rs.getString(10);
                dato[10] = rs.getString(11);
                dato[11] = rs.getString(12);

                modeloa.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Selecciona y hace visible en la tabla el evaluador actualmente asignado al aprendiz.
     *
     * @param idEvaluadorActual Identificador del evaluador actualmente asignado.
     */
    public void seleccionarEvaluadorActual(int idEvaluadorActual) {
        for (int i = 0; i < table1.getRowCount(); i++) {
            int idEnTabla = Integer.parseInt(table1.getValueAt(i, 0).toString());
            if (idEnTabla == this.idEvaluadorActual) {
                table1.setRowSelectionInterval(i, i); // Seleccionar la fila
                table1.scrollRectToVisible(table1.getCellRect(i, 0, true)); // Hacer scroll
                break;
            }
        }
    }


    /**
     * Clase interna que extiende DefaultTableModel para crear una tabla no editable.
     */
    public class NonEditableTableModel extends DefaultTableModel {
        /**
         * Sobreescribe el método isCellEditable para que todas las celdas no sean editables.
         *
         * @param row    Número de fila de la celda.
         * @param column Número de columna de la celda.
         * @return Siempre retorna false, indicando que ninguna celda es editable.
         */
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    /**
     * Método para ejecutar y mostrar la interfaz gráfica del evaluador.
     */
    public  void ejecutar() {

        JFrame frame = new JFrame("Evaluador");
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        URL iconoURL = AsignacionGUI.class.getClassLoader().getResource("imagenes/sena.jpeg");
        if (iconoURL != null) {
            frame.setIconImage(new ImageIcon(iconoURL).getImage());
        }

    }
}