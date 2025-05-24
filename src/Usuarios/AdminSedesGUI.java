package Usuarios;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminSedesGUI {
    private JPanel adminsedes;
    private JTable tablesedes;
    private JTextField textField1;
    private TableRowSorter<TableModel> sorter;

    public JPanel getPanel(){return adminsedes;}

    public AdminSedesGUI() {
        configurarTabla();
        aplicarEstiloTabla();
        aplicarEstiloEncabezado();
        inicializarFiltro();
        cargarSedes();
    }

    // Configura la estructura de la tabla
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Dirección", "Estado", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablesedes.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tablesedes.setRowSorter(sorter);

        ocultarColumnaID();
        configurarColumnaAcciones();
    }

    // Oculta la columna del ID
    private void ocultarColumnaID() {
        TableColumn columnaID = tablesedes.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setWidth(0);
    }

    // Estilo de celdas y filas
    private void aplicarEstiloTabla() {
        tablesedes.setFont(new Font("Calibri", Font.PLAIN, 14));
        tablesedes.setRowHeight(30);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablesedes.getColumnCount(); i++) {
            if (i != 4) {
                tablesedes.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    // Estilo del encabezado
    private void aplicarEstiloEncabezado() {
        JTableHeader header = tablesedes.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    // Configura la columna de acciones con botón "Editar"
    private void configurarColumnaAcciones() {
        tablesedes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablesedes.getColumnModel().getColumn(4).setCellRenderer(new BotonRenderer());
        tablesedes.getColumnModel().getColumn(4).setCellEditor(new BotonEditor(tablesedes, id -> {
            Sede_getset sede = obtenerSedePorID(id);
            if (sede != null) {
                mostrarVentanaEditarSede(sede);
            } else {
                JOptionPane.showMessageDialog(null, "Sede no encontrada.");
            }
        }));
    }

    // Filtro en tiempo real con DocumentListener
    public void inicializarFiltro() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        textField1.setBorder(bottom);

        textField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }

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

    // Carga de datos desde DAO
    private void cargarSedes() {
        DefaultTableModel modelo = (DefaultTableModel) tablesedes.getModel();
        modelo.setRowCount(0);

        ArrayList<Sede_getset> lista = new SedeDAO().listarSedes();

        for (Sede_getset sede : lista) {
            modelo.addRow(new Object[]{
                    sede.getID_sede(),
                    sede.getNombre_sede(),
                    sede.getDireccion(),
                    sede.getEstado(),
                    null // Para el botón Editar
            });
        }
    }

    // Buscar sede por ID
    private Sede_getset obtenerSedePorID(int id) {
        ArrayList<Sede_getset> lista = new SedeDAO().listarSedes();
        for (Sede_getset s : lista) {
            if (s.getID_sede() == id) {
                return s;
            }
        }
        return null;
    }

    // Abrir ventana de edición
    private void mostrarVentanaEditarSede(Sede_getset sede) {
        JFrame frame = new JFrame("Editar Sede");
        frame.setContentPane(new EditarSede(sede).getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MAIN para probar interfaz
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Sedes");
        frame.setContentPane(new AdminSedesGUI().adminsedes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return adminsedes;
    }
}
