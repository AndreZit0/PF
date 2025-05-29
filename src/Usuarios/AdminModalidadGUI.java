package Usuarios;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminModalidadGUI {
    private JPanel main;
    private JTable tablemodalidad;
    private JTextField textField1;
    private TableRowSorter<TableModel> sorter;

    public AdminModalidadGUI() {
        configurarTabla();
        aplicarEstiloTabla();
        aplicarEstiloEncabezado();
        inicializarFiltro();
        cargarModalidades();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablemodalidad.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tablemodalidad.setRowSorter(sorter);

        ocultarColumnaID();
        configurarColumnaAcciones();
    }

    private void ocultarColumnaID() {
        TableColumn columnaID = tablemodalidad.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setWidth(0);
    }

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

    private void aplicarEstiloEncabezado() {
        JTableHeader header = tablemodalidad.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));
    }

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

    private Modalidad_getset obtenerModalidadPorID(int id) {
        ArrayList<Modalidad_getset> lista = new ModalidadDAO().listarModalidades();
        for (Modalidad_getset m : lista) {
            if (m.getID_modalidad() == id) {
                return m;
            }
        }
        return null;
    }

    private void mostrarVentanaEditarModalidad(Modalidad_getset modalidad) {
        JFrame frame = new JFrame("Editar Modalidad");
        frame.setContentPane(new EditarModalidad(modalidad).getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Modalidades");
        frame.setContentPane(new AdminModalidadGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return main;
    }
}
