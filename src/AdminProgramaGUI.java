package src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que representa la interfaz gráfica para la administración de programas.
 * Permite visualizar, filtrar y editar la información de los programas.
 */
public class AdminProgramaGUI {
    private JPanel main;
    private JTable tableprograma;
    private JTextField textField1;
    private TableRowSorter<TableModel> sorter;

    /**
     * Constructor de la clase {@code AdminProgramaGUI}.
     * Inicializa los componentes de la interfaz, configura la tabla, aplica estilos,
     * inicializa el filtro de búsqueda y carga los programas existentes.
     */
    public AdminProgramaGUI() {
        configurarTabla();
        aplicarEstiloTabla();
        aplicarEstiloEncabezado();
        inicializarFiltro();
        cargarProgramas();
    }

    /**
     * Configura la estructura de la tabla, estableciendo las columnas, el modelo de datos
     * y el sorter para la funcionalidad de filtrado. También oculta la columna del ID
     * y configura la columna de acciones (botón de editar).
     */
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Estado", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tableprograma.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tableprograma.setRowSorter(sorter);

        ocultarColumnaID();
        configurarColumnaAcciones();
    }

    /**
     * Oculta la columna que contiene los IDs de los programas en la tabla.
     * Esta columna se mantiene en el modelo de datos pero no se muestra al usuario.
     */
    private void ocultarColumnaID() {
        TableColumn columnaID = tableprograma.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setWidth(0);
    }

    /**
     * Aplica estilos a la tabla, como la fuente, la altura de las filas y la alineación
     * del texto en las celdas (centrado). Excluye la columna de acciones.
     */
    private void aplicarEstiloTabla() {
        tableprograma.setFont(new Font("Calibri", Font.PLAIN, 14));
        tableprograma.setRowHeight(30);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tableprograma.getColumnCount(); i++) {
            if (i != 3) { // La columna 3 es la de "Acciones"
                tableprograma.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    /**
     * Aplica estilos al encabezado de la tabla, como el color de fondo,
     * el color del texto y la fuente.
     */
    private void aplicarEstiloEncabezado() {
        JTableHeader header = tableprograma.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    /**
     * Configura la columna de acciones en la tabla, estableciendo su ancho preferido,
     * el renderizador y el editor de celdas.  El editor de celdas se utiliza para
     * mostrar un botón y manejar el evento de edición de un programa.
     */
    private void configurarColumnaAcciones() {
        tableprograma.getColumnModel().getColumn(3).setPreferredWidth(100); // La columna 3 es la de "Acciones"
        tableprograma.getColumnModel().getColumn(3).setCellRenderer(new BotonRenderer());
        tableprograma.getColumnModel().getColumn(3).setCellEditor(new BotonEditor(tableprograma, id -> {
            Programas_getset programa = obtenerProgramaPorID(id);
            if (programa != null) {
                mostrarVentanaEditarPrograma(programa);
            } else {
                JOptionPane.showMessageDialog(null, "Programa no encontrada.");
            }
        }));
    }

    /**
     * Inicializa el campo de texto utilizado para filtrar la tabla de programas.
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
             * Aplica un filtro a la tabla de programas basado en el texto ingresado en el campo de texto.
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
     * Carga los programas desde la base de datos y los muestra en la tabla.
     * Utiliza la clase {@code ProgramasDAO} para obtener la lista de programas.
     */
    private void cargarProgramas() {
        DefaultTableModel modelo = (DefaultTableModel) tableprograma.getModel();
        modelo.setRowCount(0);

        ArrayList<Programas_getset> lista = new ProgramasDAO().listarProgramas();

        for (Programas_getset programa : lista) {
            modelo.addRow(new Object[]{
                    programa.getID_programas(),
                    programa.getNombre_programa(),
                    programa.getEstado(),
                    null // Para el botón Editar
            });
        }
    }

    /**
     * Obtiene un programa por su ID.
     *
     * @param id El ID del programa a buscar.
     * @return El programa encontrado, o null si no se encuentra.
     */
    private Programas_getset obtenerProgramaPorID(int id) {
        ArrayList<Programas_getset> lista = new ProgramasDAO().listarProgramas();
        for (Programas_getset p : lista) {
            if (p.getID_programas() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Muestra la ventana de edición de un programa.
     *
     * @param programa El programa a editar.
     */
    private void mostrarVentanaEditarPrograma(Programas_getset programa) {
        JFrame frame = new JFrame("Editar Programa");
        frame.setContentPane(new EditarPrograma(programa).getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Método principal que crea una instancia de {@code AdminProgramaGUI} y la muestra
     * en una ventana maximizada.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Programas");
        frame.setContentPane(new AdminProgramaGUI().main);
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

