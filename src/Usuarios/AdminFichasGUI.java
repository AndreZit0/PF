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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminFichasGUI {
    private JPanel adminfichas;
    private JTable tablefichas;
    private JTextField textField1;
    private JButton generarPDFButton;

    private TableRowSorter<TableModel> sorter;

    public JPanel getPanel(){return adminfichas;}

    public AdminFichasGUI() {
        inicializarFiltro();
        inicializarTabla();
        cargarTodasLasFichas(); // Carga las fichas en la tabla
        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BaseColor verdeSena = new BaseColor(57, 169, 0);
                Document documento = new Document(PageSize.A4.rotate()); // Orientación horizontal

                try {
                    String ruta = System.getProperty("user.home") + "/Downloads/fichas.pdf";
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta));
                    documento.open();

                    // Fondo
                    String imagePath = "src/Empresas/img/fondo.png";
                    File imgFile = new File(imagePath);
                    if (imgFile.exists()) {
                        com.itextpdf.text.Image background = com.itextpdf.text.Image.getInstance(imagePath);
                        background.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth());
                        background.setAbsolutePosition(0, 0);
                        PdfContentByte canvas = writer.getDirectContentUnder();
                        canvas.addImage(background);
                    }

                    documento.add(new Paragraph("\n\n\n\n"));
                    Paragraph titulo = new Paragraph("Listado de Fichas",
                            FontFactory.getFont("Tahoma", 22, Font.BOLD, verdeSena));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                    documento.add(new Paragraph("\n\n"));

                    // Encabezados como en la imagen 1
                    String[] headers = {
                            "Programa", "Sede", "Código", "Modalidad", "Jornada",
                            "Nivel Formación", "Fecha Inicio", "Fecha Fin Lectiva", "Fecha Final", "Estado"
                    };

                    PdfPTable tabla = new PdfPTable(headers.length);
                    tabla.setWidthPercentage(100);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header,
                                FontFactory.getFont("Calibri", 12, Font.BOLD, BaseColor.WHITE)));
                        cell.setBackgroundColor(verdeSena);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tabla.addCell(cell);
                    }

                    // Consultar la base de datos con JOIN para traer nombres de programa y sede
                    try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                         PreparedStatement pst = cn.prepareStatement(
                                 "SELECT f.codigo, f.modalidad, f.jornada, f.nivel_formacion, " +
                                         "f.fecha_inicio, f.fecha_fin_lec, f.fecha_final, f.estado, " +
                                         "p.nombre_programa, s.nombre_sede " +
                                         "FROM fichas f " +
                                         "JOIN programas p ON f.ID_programas = p.ID_programas " +
                                         "JOIN sede s ON f.ID_sede = s.ID_sede");
                         ResultSet rs = pst.executeQuery()) {

                        while (rs.next()) {
                            tabla.addCell(rs.getString("nombre_programa"));
                            tabla.addCell(rs.getString("nombre_sede"));
                            tabla.addCell(rs.getString("codigo"));
                            tabla.addCell(rs.getString("modalidad"));
                            tabla.addCell(rs.getString("jornada"));
                            tabla.addCell(rs.getString("nivel_formacion"));
                            tabla.addCell(rs.getString("fecha_inicio"));
                            tabla.addCell(rs.getString("fecha_fin_lec"));
                            tabla.addCell(rs.getString("fecha_final"));
                            tabla.addCell(rs.getString("estado"));
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al consultar la base de datos: " + ex.getMessage());
                    }

                    documento.add(tabla);
                    documento.close();
                    JOptionPane.showMessageDialog(null, "PDF generado correctamente como se ve en pantalla.");

                } catch (DocumentException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
                }
            }
        });


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
                "Jornada", "Nivel Formación", "Fecha Inicio", "Fecha Fin Lectiva", "Fecha Final","Tipo de oferta", "Estado", "Acciones"
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
            if (i != 12) { // No centramos la columna de botones
                tablefichas.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }

        // Configurar la columna de los botones
        tablefichas.setRowHeight(30);
        tablefichas.getColumnModel().getColumn(12).setPreferredWidth(100);
        tablefichas.getColumnModel().getColumn(12).setCellRenderer(new BotonRenderer());
        tablefichas.getColumnModel().getColumn(12).setCellEditor(
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
                    f.getTipo_oferta(),
                    f.getEstado(),
                    null // El botón de editar se maneja con el editor
            });
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Fichas");
        frame.setContentPane(new AdminFichasGUI().adminfichas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
