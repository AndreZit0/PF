package Usuarios;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Color;
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
    private JButton generarEXCELButton;

    private TableRowSorter<TableModel> sorter;

    public JPanel getPanel(){return adminfichas;}

    public AdminFichasGUI() {
        inicializarFiltro();
        inicializarTabla();
        cargarTodasLasFichas(); // Carga las fichas en la tabla
        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
                String ruta = System.getProperty("user.home") + "/Downloads/fichas_" + timestamp + ".xlsx";


                try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                     PreparedStatement pst = cn.prepareStatement(
                             "SELECT f.codigo, f.modalidad, f.jornada, f.nivel_formacion, " +
                                     "f.fecha_inicio, f.fecha_fin_lec, f.fecha_final, f.estado, " +
                                     "p.nombre_programa, s.nombre_sede " +
                                     "FROM fichas f " +
                                     "JOIN programas p ON f.ID_programas = p.ID_programas " +
                                     "JOIN sede s ON f.ID_sede = s.ID_sede");
                     ResultSet rs = pst.executeQuery();
                     XSSFWorkbook workbook = new XSSFWorkbook()) {

                    XSSFSheet sheet = workbook.createSheet("Listado de Fichas");

                    String[] headers = {
                            "Programa", "Sede", "Código", "Modalidad", "Jornada",
                            "Nivel Formación", "Fecha Inicio", "Fecha Fin Lectiva", "Fecha Final", "Estado"
                    };

                    // 🎨 Estilo para encabezado
                    XSSFCellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(new XSSFColor(new Color(57, 169, 0), null));
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    XSSFFont headerFont = workbook.createFont();
                    headerFont.setColor(IndexedColors.WHITE.getIndex());
                    headerFont.setBold(true);
                    headerStyle.setFont(headerFont);
                    headerStyle.setAlignment(HorizontalAlignment.CENTER);
                    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                    // Bordes al encabezado
                    headerStyle.setBorderTop(BorderStyle.THIN);
                    headerStyle.setBorderBottom(BorderStyle.THIN);
                    headerStyle.setBorderLeft(BorderStyle.THIN);
                    headerStyle.setBorderRight(BorderStyle.THIN);

                    // 🧱 Crear fila de encabezado
                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                    }

                    // 📅 Estilo para celdas de fecha
                    CellStyle dateStyle = workbook.createCellStyle();
                    CreationHelper createHelper = workbook.getCreationHelper();
                    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

                    int rowIndex = 1;
                    while (rs.next()) {
                        Row row = sheet.createRow(rowIndex++);

                        row.createCell(0).setCellValue(rs.getString("nombre_programa"));
                        row.createCell(1).setCellValue(rs.getString("nombre_sede"));
                        row.createCell(2).setCellValue(rs.getString("codigo"));
                        row.createCell(3).setCellValue(rs.getString("modalidad"));
                        row.createCell(4).setCellValue(rs.getString("jornada"));
                        row.createCell(5).setCellValue(rs.getString("nivel_formacion"));

                        // Formatear fechas
                        for (int i = 0; i < 3; i++) {
                            String campo = switch (i) {
                                case 0 -> "fecha_inicio";
                                case 1 -> "fecha_fin_lec";
                                default -> "fecha_final";
                            };
                            java.sql.Date fecha = rs.getDate(campo);
                            Cell fechaCell = row.createCell(6 + i);
                            if (fecha != null) {
                                fechaCell.setCellValue(fecha);
                                fechaCell.setCellStyle(dateStyle);
                            } else {
                                fechaCell.setCellValue("");
                            }
                        }

                        row.createCell(9).setCellValue(rs.getString("estado"));
                    }

                    // Ajustar ancho de columnas
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    // Guardar archivo
                    try (FileOutputStream out = new FileOutputStream(ruta)) {
                        workbook.write(out);
                    }

                    JOptionPane.showMessageDialog(null, "Excel generado correctamente con estilos en la carpeta de descargas.");

                    File file = new File(ruta);
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el Excel: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });


        generarEXCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta = System.getProperty("user.home") + "/Downloads/fichas.xlsx";

                try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                     PreparedStatement pst = cn.prepareStatement(
                             "SELECT f.codigo, f.modalidad, f.jornada, f.nivel_formacion, " +
                                     "f.fecha_inicio, f.fecha_fin_lec, f.fecha_final, f.estado, " +
                                     "p.nombre_programa, s.nombre_sede " +
                                     "FROM fichas f " +
                                     "JOIN programas p ON f.ID_programas = p.ID_programas " +
                                     "JOIN sede s ON f.ID_sede = s.ID_sede");
                     ResultSet rs = pst.executeQuery();
                     XSSFWorkbook workbook = new XSSFWorkbook()) {

                    XSSFSheet sheet = workbook.createSheet("Listado de Fichas");

                    String[] headers = {
                            "Programa", "Sede", "Código", "Modalidad", "Jornada",
                            "Nivel Formación", "Fecha Inicio", "Fecha Fin Lectiva", "Fecha Final", "Estado"
                    };

                    // Crear estilo para el encabezado
                    XSSFCellStyle headerStyle = workbook.createCellStyle();
                    headerStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 128, 0), new DefaultIndexedColorMap())); // verde oscuro
                    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    // Crear fuente blanca y negrita
                    XSSFFont font = workbook.createFont();
                    font.setColor(IndexedColors.WHITE.getIndex());
                    font.setBold(true);
                    headerStyle.setFont(font);

                    // Crear fila de encabezados con estilo
                    Row headerRow = sheet.createRow(0);
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                    }

                    // Llenar las filas con datos
                    int rowIndex = 1;
                    while (rs.next()) {
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(rs.getString("nombre_programa"));
                        row.createCell(1).setCellValue(rs.getString("nombre_sede"));
                        row.createCell(2).setCellValue(rs.getString("codigo"));
                        row.createCell(3).setCellValue(rs.getString("modalidad"));
                        row.createCell(4).setCellValue(rs.getString("jornada"));
                        row.createCell(5).setCellValue(rs.getString("nivel_formacion"));
                        row.createCell(6).setCellValue(rs.getString("fecha_inicio"));
                        row.createCell(7).setCellValue(rs.getString("fecha_fin_lec"));
                        row.createCell(8).setCellValue(rs.getString("fecha_final"));
                        row.createCell(9).setCellValue(rs.getString("estado"));
                    }

                    // Autoajustar columnas
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    // Guardar archivo
                    try (FileOutputStream out = new FileOutputStream(ruta)) {
                        workbook.write(out);
                    }

                    JOptionPane.showMessageDialog(null, "Excel generado correctamente en la carpeta de descargas.");

                    // Abrir automáticamente
                    File excelFile = new File(ruta);
                    if (excelFile.exists()) {
                        Desktop.getDesktop().open(excelFile);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el Excel: " + ex.getMessage());
                    ex.printStackTrace();
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
