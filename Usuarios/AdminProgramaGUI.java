package Usuarios;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AdminProgramaGUI {
    private JPanel adminprograma;
    private JTable tableprograma;
    private JTextField textField1;
    private JButton generarPDFButton;
    private TableRowSorter<TableModel> sorter;

    public JPanel getPanel(){return adminprograma;}

    public AdminProgramaGUI() {
        configurarTabla();
        aplicarEstiloTabla();
        aplicarEstiloEncabezado();
        inicializarFiltro();
        cargarProgramas();
        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BaseColor verdeSena = new BaseColor(57, 169, 0);
                Document documento = new Document(PageSize.A4);

                try {
                    String ruta = System.getProperty("user.home") + "/Downloads/programas.pdf";
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta));

                    documento.open();

                    String imagePath = "src/Empresas/img/fondo.png";
                    File imgFile = new File(imagePath);
                    if (!imgFile.exists()) {
                        JOptionPane.showMessageDialog(null, "Imagen de fondo no encontrada.");
                        return;
                    }

                    com.itextpdf.text.Image background = com.itextpdf.text.Image.getInstance(imagePath);
                    background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                    background.setAbsolutePosition(0, 0);

                    PdfContentByte canvas = writer.getDirectContentUnder();
                    canvas.addImage(background);

                    documento.add(new Paragraph("\n\n\n"));
                    documento.add(new Paragraph("\n\n\n"));

                    Paragraph titulo = new Paragraph("Listado de Programas",
                            FontFactory.getFont("Tahoma", 22, com.itextpdf.text.Font.BOLD, verdeSena));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                    documento.add(new Paragraph("\n\n"));

                    PdfPTable tabla = new PdfPTable(2); // Solo nombre y estado
                    tabla.setWidthPercentage(100);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    String[] headers = {"Nombre del Programa", "Estado"};

                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header,
                                FontFactory.getFont("Calibri", 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE)));
                        cell.setBackgroundColor(verdeSena);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tabla.addCell(cell);
                    }

                    try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                         PreparedStatement pst = cn.prepareStatement("SELECT nombre_programa, estado FROM programas");
                         ResultSet rs = pst.executeQuery()) {

                        while (rs.next()) {
                            tabla.addCell(rs.getString("nombre_programa"));
                            tabla.addCell(rs.getString("estado"));
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al consultar la base de datos: " + ex.getMessage());
                    }

                    documento.add(tabla);
                    documento.close();

                    JOptionPane.showMessageDialog(null, "PDF de programas generado correctamente en la carpeta de descargas.");

                } catch (DocumentException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
                }
            }
        });

    }

    // Configura la estructura de la tabla
    private void configurarTabla() {
        String[] columnas = {"ID", "Nombre", "Version", "Estado", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tableprograma.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        tableprograma.setRowSorter(sorter);

        ocultarColumnaID();
        configurarColumnaAcciones();
    }

    // Oculta la columna del ID
    private void ocultarColumnaID() {
        TableColumn columnaID = tableprograma.getColumnModel().getColumn(0);
        columnaID.setMinWidth(0);
        columnaID.setMaxWidth(0);
        columnaID.setWidth(0);
    }

    // Estilo de celdas y filas
    private void aplicarEstiloTabla() {
        tableprograma.setFont(new Font("Calibri", Font.PLAIN, 14));
        tableprograma.setRowHeight(30);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tableprograma.getColumnCount(); i++) {
            if (i != 4) {
                tableprograma.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }
    }

    // Estilo del encabezado
    private void aplicarEstiloEncabezado() {
        JTableHeader header = tableprograma.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    // Configura la columna de acciones con botón "Editar"
    private void configurarColumnaAcciones() {
        tableprograma.getColumnModel().getColumn(4).setPreferredWidth(100);
        tableprograma.getColumnModel().getColumn(4).setCellRenderer(new BotonRenderer());
        tableprograma.getColumnModel().getColumn(4).setCellEditor(new BotonEditor(tableprograma, id -> {
            Programas_getset programa = obtenerProgramaPorID(id);
            if (programa != null) {
                mostrarVentanaEditarPrograma(programa);
            } else {
                JOptionPane.showMessageDialog(null, "Programa no encontrada.");
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
    private void cargarProgramas() {
        DefaultTableModel modelo = (DefaultTableModel) tableprograma.getModel();
        modelo.setRowCount(0);

        ArrayList<Programas_getset> lista = new ProgramasDAO().listarProgramas();

        for (Programas_getset programa : lista) {
            modelo.addRow(new Object[]{
                    programa.getID_programas(),
                    programa.getNombre_programa(),
                    programa.getVersion_programa(),
                    programa.getEstado(),
                    null // Para el botón Editar
            });
        }
    }

    // Buscar sede por ID
    private Programas_getset obtenerProgramaPorID(int id) {
        ArrayList<Programas_getset> lista = new ProgramasDAO().listarProgramas();
        for (Programas_getset p : lista) {
            if (p.getID_programas() == id) {
                return p;
            }
        }
        return null;
    }

    // Abrir ventana de edición
    private void mostrarVentanaEditarPrograma(Programas_getset programa) {
        JFrame frame = new JFrame("Editar Programa");
        frame.setContentPane(new EditarPrograma(programa).getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MAIN para probar interfaz
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Programas");
        frame.setContentPane(new AdminProgramaGUI().adminprograma);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return adminprograma;
    }
}
