package src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que representa la interfaz gráfica para la administración de modalidades.
 * Permite visualizar, filtrar y editar la información de las modalidades.
 */
public class AdminModalidadGUI {
    private JPanel main;
    private JTable tablemodalidad;
    private JTextField textField1;
    private TableRowSorter<TableModel> sorter;

    /**
     * Constructor de la clase {@code AdminModalidadGUI}.
     * Inicializa los componentes de la interfaz, configura la tabla, aplica estilos,
     * inicializa el filtro de búsqueda y carga las modalidades existentes.
     */
    public AdminModalidadGUI() {
        configurarTabla();
        aplicarEstiloTabla();
        aplicarEstiloEncabezado();
        inicializarFiltro();
        cargarModalidades();
    }

    /**
     * Configura la tabla de modalidades, estableciendo las columnas, el modelo de datos
     * y el sorter para la funcionalidad de filtrado. También oculta la columna del ID
     * y configura la columna de acciones (botón de editar).
     */
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablemodalidad.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tablemodalidad.setRowSorter(sorter);

        ocultarColumnaID();
        configurarColumnaAcciones();
    }

    /**
     * Oculta la columna que contiene los IDs de las modalidades en la tabla.
     * Esta columna se mantiene en el modelo de datos pero no se muestra al usuario.
     */
    private void ocultarColumnaID() {
        TableColumn columnaID = tablemodalidad.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setWidth(0);
    }

    /**
     * Aplica estilos a la tabla, como la fuente, la altura de las filas y la alineación
     * del texto en las celdas (centrado).  Excluye la columna de acciones.
     */
    private void aplicarEstiloTabla() {
        tablemodalidad.setFont(new Font("Calibri", Font.PLAIN, 14));
        tablemodalidad.setRowHeight(30);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablemodalidad.getColumnCount(); i++) {
            if (i != 2) {
                tablemodalidad.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    /**
     * Aplica estilos al encabezado de la tabla, como el color de fondo,
     * el color del texto y la fuente.
     */
    private void aplicarEstiloEncabezado() {
        JTableHeader header = tablemodalidad.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    /**
     * Configura la columna de acciones en la tabla, estableciendo su ancho preferido,
     * el renderizador y el editor de celdas.  El editor de celdas se utiliza para
     * mostrar un botón y manejar el evento de edición de una modalidad.
     */
    private void configurarColumnaAcciones() {
        tablemodalidad.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablemodalidad.getColumnModel().getColumn(2).setCellRenderer(new BotonRenderer());
        tablemodalidad.getColumnModel().getColumn(2).setCellEditor(new BotonEditor(tablemodalidad, id -> {
            Modalidad_getset modalidad = obtenerModalidadPorID(id);
            if (modalidad != null) {
                mostrarVentanaEditarModalidad(modalidad);
            } else {
                JOptionPane.showMessageDialog(null, "Modalidad no encontrada.");
            }
        }));
    }

    /**
     * Inicializa el campo de texto utilizado para filtrar la tabla de modalidades.
     * Agrega un {@code DocumentListener} para aplicar el filtro cada vez que el texto cambia.
     * También establece un borde inferior con un color distintivo para el campo de texto.
     */
    public void inicializarFiltro() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        textField1.setBorder(bottom);

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Método llamado cuando se inserta texto en el documento.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }
            /**
             * Método llamado cuando se elimina texto del documento.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }
            /**
             * Método llamado cuando un atributo o conjunto de atributos ha cambiado.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }

            /**
             * Aplica un filtro a la tabla de modalidades basado en el texto ingresado en el campo de texto.
             * Si el campo de texto está vacío, se elimina cualquier filtro aplicado previamente,
             * mostrando todas las filas de la tabla.
             * El filtro es insensible a mayúsculas y busca coincidencias en todas las columnas de la tabla.
             */
            private void aplicarFiltro() {
                String texto = textField1.getText();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    /**
     * Carga las modalidades desde la base de datos y las muestra en la tabla.
     * Utiliza la clase {@code ModalidadDAO} para obtener la lista de modalidades.
     */
    private void cargarModalidades() {
        DefaultTableModel modelo = (DefaultTableModel) tablemodalidad.getModel();
        modelo.setRowCount(0);

        ArrayList<Modalidad_getset> lista = new ModalidadDAO().listarModalidades();

        for (Modalidad_getset modalidad : lista) {
            modelo.addRow(new Object[]{
                    modalidad.getID_modalidad(),
                    modalidad.getModalidad(),
                    null
            });
        }
    }

    /**
     * Obtiene una modalidad por su ID.
     *
     * @param id El ID de la modalidad a buscar.
     * @return La modalidad encontrada, o null si no se encuentra.
     */
    private Modalidad_getset obtenerModalidadPorID(int id) {
        ArrayList<Modalidad_getset> lista = new ModalidadDAO().listarModalidades();
        for (Modalidad_getset m : lista) {
            if (m.getID_modalidad() == id) {
                return m;
            }
        }
        return null;
    }

    /**
     * Muestra la ventana de edición de una modalidad.
     *
     * @param modalidad La modalidad a editar.
     */
    private void mostrarVentanaEditarModalidad(Modalidad_getset modalidad) {
        JFrame frame = new JFrame("Editar Modalidad");
        frame.setContentPane(new EditarModalidad(modalidad).getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Método principal que crea una instancia de {@code AdminModalidadGUI} y la muestra
     * en una ventana maximizada.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Modalidades");
        frame.setContentPane(new AdminModalidadGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     * @return El panel principal.
     */
    public JPanel getMainPanel() {
        return main;
    }
}