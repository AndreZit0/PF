package src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Clase que representa la interfaz gráfica para la administración de fichas de formación.
 * Permite visualizar, filtrar y editar la información de las fichas.
 */
public class AdminFichasGUI {
    private JPanel main;
    private JTable tablefichas;
    private JTextField textField1;

    private TableRowSorter<TableModel> sorter;

    /**
     * Constructor de la clase {@code AdminFichasGUI}. Inicializa los componentes de la interfaz,
     * configura el filtro de búsqueda, inicializa la tabla y carga todas las fichas existentes.
     */
    public AdminFichasGUI() {
        inicializarFiltro();
        inicializarTabla();
        cargarTodasLasFichas(); // Carga las fichas en la tabla
    }

    /**
     * Inicializa el campo de texto utilizado para filtrar la tabla de fichas.
     * Agrega un {@code DocumentListener} para aplicar el filtro cada vez que el texto cambia.
     * También establece un borde inferior con un color distintivo para el campo de texto.
     */
    private void inicializarFiltro() {
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Método llamado cuando se inserta texto en el documento.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            /**
             * Método llamado cuando se elimina texto del documento.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            /**
             * Método llamado cuando un atributo o conjunto de atributos ha cambiado.
             * Llama al método {@code aplicarFiltro()} para actualizar la vista de la tabla.
             * @param e El evento del documento.
             */
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            /**
             * Aplica un filtro a la tabla de fichas basado en el texto ingresado en el campo de texto.
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

        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        textField1.setBorder(bottom);
    }

    /**
     * Inicializa la tabla de fichas, configurando sus columnas, modelo de datos,
     * renderizadores y editores. Oculta la columna del ID de la ficha y añade
     * una columna para las acciones (en este caso, un botón de editar).
     */
    private void inicializarTabla() {
        String[] columnas = {
                "ID Ficha", "Programa", "Sede", "Código", "Modalidad",
                "Jornada", "Nivel Formación", "Fecha Inicio", "Fecha Fin Lectiva", "Fecha Final", "Estado", "Acciones"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablefichas.setModel(modelo);

        sorter = new TableRowSorter<>(modelo);
        tablefichas.setRowSorter(sorter);

        // Ocultar la columna de ID Ficha
        tablefichas.getColumnModel().getColumn(0).setMinWidth(0);
        tablefichas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablefichas.getColumnModel().getColumn(0).setWidth(0);

        tablefichas.setFont(new Font("Calibri", Font.PLAIN, 14));
        tablefichas.setRowHeight(30);

        // Estilo del encabezado de la tabla
        JTableHeader header = tablefichas.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));

        // Centrar el texto en las celdas (excepto la columna de acciones)
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablefichas.getColumnCount(); i++) {
            if (i != 11) { // No centramos la columna de botones
                tablefichas.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }

        // Configurar la columna de los botones de acción (Editar)
        tablefichas.setRowHeight(30);
        tablefichas.getColumnModel().getColumn(11).setPreferredWidth(100);
        tablefichas.getColumnModel().getColumn(11).setCellRenderer(new src.BotonRenderer());
        tablefichas.getColumnModel().getColumn(11).setCellEditor(
                new src.BotonEditor(tablefichas, id -> {
                    src.FichasDAO dao = new src.FichasDAO(src.ConexionBD.getConnection());
                    src.Fichas_setget ficha = dao.obtenerFichaPorID(id);
                    if (ficha != null) {
                        JFrame frame = new JFrame("Editar Ficha");
                        frame.setContentPane(new src.EditarFichas(ficha).getMainPanel());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ficha no encontrada.");
                    }
                })
        );
    }

    /**
     * Carga todas las fichas de formación desde la base de datos y las muestra en la tabla.
     * Utiliza la clase {@code FichasDAO} para acceder a los datos y un {@code SimpleDateFormat}
     * para formatear las fechas antes de mostrarlas en la tabla.
     */
    private void cargarTodasLasFichas() {
        Connection conexion = src.ConexionBD.getConnection();
        src.FichasDAO dao = new src.FichasDAO(conexion);
        ArrayList<src.Fichas_setget> lista = (ArrayList<src.Fichas_setget>) dao.listarFichas();

        DefaultTableModel modelo = (DefaultTableModel) tablefichas.getModel();
        modelo.setRowCount(0);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        for (src.Fichas_setget f : lista) {
            modelo.addRow(new Object[]{
                    f.getID_Fichas(),
                    f.getNombre_programa(),
                    f.getNombre_sede(),
                    f.getCodigo(),
                    f.getModalidad(),
                    f.getJornada(),
                    f.getNivel_formacion(),
                    formatoFecha.format(f.getFecha_inicio()),
                    formatoFecha.format(f.getFecha_fin_lec()),
                    formatoFecha.format(f.getFecha_final()),
                    f.getEstado(),
                    null // El botón de editar se maneja con el editor
            });
        }
    }

    /**
     * Método principal que crea una instancia de {@code AdminFichasGUI} y la muestra
     * en una ventana maximizada.
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Fichas");
        frame.setContentPane(new AdminFichasGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
