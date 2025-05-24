package Usuarios;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminFichasGUI {
    private JPanel adminfichas;
    private JTable tablefichas;
    private JTextField textField1;

    private TableRowSorter<TableModel> sorter;

    public JPanel getPanel(){return adminfichas;}

    public AdminFichasGUI() {
        inicializarFiltro();
        inicializarTabla();
        cargarTodasLasFichas(); // Carga las fichas en la tabla
    }

    private void inicializarFiltro() {
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

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

        // Centrar el texto en las celdas
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablefichas.getColumnCount(); i++) {
            if (i != 11) { // No centramos la columna de botones
                tablefichas.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }

        // Configurar la columna de los botones
        tablefichas.setRowHeight(30);
        tablefichas.getColumnModel().getColumn(11).setPreferredWidth(100);
        tablefichas.getColumnModel().getColumn(11).setCellRenderer(new BotonRenderer());
        tablefichas.getColumnModel().getColumn(11).setCellEditor(
                new BotonEditor(tablefichas, id -> {
                    FichasDAO dao = new FichasDAO(ConexionBD.getConnection());
                    Fichas_setget ficha = dao.obtenerFichaPorID(id);
                    if (ficha != null) {
                        JFrame frame = new JFrame("Editar Ficha");
                        frame.setContentPane(new EditarFichas(ficha).getMainPanel());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ficha no encontrada.");
                    }
                })
        );
    }

    private void cargarTodasLasFichas() {
        Connection conexion = ConexionBD.getConnection();
        FichasDAO dao = new FichasDAO(conexion);
        ArrayList<Fichas_setget> lista = (ArrayList<Fichas_setget>) dao.listarFichas();

        DefaultTableModel modelo = (DefaultTableModel) tablefichas.getModel();
        modelo.setRowCount(0);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        for (Fichas_setget f : lista) {
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

    public static void main() {
        JFrame frame = new JFrame("Administración de Fichas");
        frame.setContentPane(new AdminFichasGUI().adminfichas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
