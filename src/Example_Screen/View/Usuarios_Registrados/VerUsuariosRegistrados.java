package Example_Screen.View.Usuarios_Registrados;

import Example_Screen.Connection.DBConnection;
import Example_Screen.View.Administrador.Administrador;
import Example_Screen.View.VisualizarPerfilGUI;
import Usuarios.EditarUsuario;
import Usuarios.UsuariosDAO;
import Usuarios.Usuarios_getset;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

import static Example_Screen.View.Administrador.Administrador.datosAprendices;
import static Example_Screen.View.Administrador.Administrador.verUsuarioPorRol;
import static Example_Screen.View.Login.LoginGUI.*;

public class VerUsuariosRegistrados extends Component {
    private JTable table1;
    private JPanel panelVerUsuario;
    private JPanel panelFiltro;
    private JTextField busqueda;
    private JLabel tipoUsuario;
    private JPanel panelTable;
    private JScrollPane scroll;
    private JButton pdfButton;
    private JButton generarExcel;
    private JButton addnovedad;

    public JTable getTable() {
        return table1;
    }

    public JTextField getBusqueda() {
        return busqueda;
    }


    public JPanel getPanel() {
        return panelVerUsuario;
    }

    public void mostrarRol(String texto) {
        tipoUsuario.setText(texto);
    }

    public TableCellRenderer getButtonRenderer() {
        return new ButtonRenderer();
    }

    public DefaultCellEditor getButtonEditor() {
        return new ButtonEditor(new JCheckBox());
    }

    private Administrador admin;

    public VerUsuariosRegistrados(Administrador admin) {
        this.admin = admin;

        if(verUsuarioPorRol==1) {
            generarExcel.setVisible(true);
        } else {
            generarExcel.setVisible(false);
        }

        if("2".equals(cofigBotonInicioSegunRol)){
            generarExcel.setVisible(false);
        }


        pdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombreArchivo = "";  // Este será el prefijo del nombre del archivo
                // colores
                BaseColor verdeSena = new BaseColor(57, 169, 0);
                Document documento = new Document(PageSize.A4);

                try {
                    // 1. Definir el nombre del archivo según el tipo de reporte
                    if ((verUsuarioPorRol >= 1 && verUsuarioPorRol <= 4)) {
                        nombreArchivo = "usuarios_";
                    } else if (datosAprendices == 7) {
                        nombreArchivo = "aprendices_asignados_";
                    } else if (datosAprendices == 8) {
                        nombreArchivo = "aprendices_contratados_";
                    } else if (datosAprendices == 9) {
                        nombreArchivo = "novedades_aprendices_";
                    }else {
                        nombreArchivo = "reporte_"; // nombre por defecto si no coincide nada
                    }

                    // 2. Crear archivo temporal con nombre adecuado
                    File tempFile = File.createTempFile(nombreArchivo, ".pdf");
                    tempFile.deleteOnExit(); // Eliminar al cerrar programa
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(tempFile));

                    documento.open();

                    // 3. Fondo de imagen
                    String imagePath = "src/Empresas/img/fondo.png";
                    File imgFile = new File(imagePath);
                    if (!imgFile.exists()) {
                        JOptionPane.showMessageDialog(null, "Error: Imagen de fondo no encontrada.");
                        return;
                    }

                    com.itextpdf.text.Image background = com.itextpdf.text.Image.getInstance(imagePath);
                    background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                    background.setAbsolutePosition(0, 0);
                    PdfContentByte canvas = writer.getDirectContentUnder();
                    canvas.addImage(background);

                    documento.add(new Paragraph("\n\n\n"));
                    documento.add(new Paragraph("\n\n\n"));

                    // 4. Reporte por Rol
                    if ((verUsuarioPorRol >= 1 && verUsuarioPorRol <= 4)) {
                        String nombrePDF = switch (verUsuarioPorRol) {
                            case 1 -> "Aprendices Registrados";
                            case 2 -> "Evaluadores Registrados";
                            case 3 -> "Coevaluadores Registrados";
                            case 4 -> "Auxiliares Registrados";
                            default -> "Usuarios";
                        };

                        Paragraph titulo = new Paragraph(nombrePDF,
                                FontFactory.getFont("Tahoma", 22, com.itextpdf.text.Font.BOLD, verdeSena));
                        titulo.setAlignment(Element.ALIGN_CENTER);
                        documento.add(titulo);
                        documento.add(new Paragraph("\n\n"));

                        PdfPTable tabla = new PdfPTable(9);
                        tabla.setWidthPercentage(110);
                        tabla.setSpacingBefore(10f);
                        tabla.setSpacingAfter(10f);

                        String[] headers = {"T. De identificacion", "Numero", "Nombre", "Apellido", "Email", "Email inst", "Direccion", "Contacto", "Estado"};
                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header,
                                    FontFactory.getFont("Calibri", 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE)));
                            cell.setBackgroundColor(verdeSena);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tabla.addCell(cell);
                        }

                        try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                             PreparedStatement pst = cn.prepareStatement(
                                     "SELECT tipo_dc, numero, nombres, apellidos, email, email_insti, direccion, contacto1, estado FROM usuarios WHERE id_rol = ?"
                             )) {

                            pst.setInt(1, verUsuarioPorRol);
                            ResultSet rs = pst.executeQuery();

                            if (!rs.isBeforeFirst()) {
                                JOptionPane.showMessageDialog(null, "No se han encontrado los datos.");
                            } else {
                                while (rs.next()) {
                                    for (int i = 1; i <= 9; i++) {
                                        tabla.addCell(rs.getString(i));
                                    }
                                }
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());
                        }

                        documento.add(tabla);
                    }

                    // 5. Aprendices asignados
                    else if (datosAprendices == 7) {
                        Paragraph titulo = new Paragraph("Aprendices Asignados",
                                FontFactory.getFont("Tahoma", 22, com.itextpdf.text.Font.BOLD, verdeSena));
                        titulo.setAlignment(Element.ALIGN_CENTER);
                        documento.add(titulo);
                        documento.add(new Paragraph("\n\n"));

                        PdfPTable tabla = new PdfPTable(9);
                        tabla.setWidthPercentage(110);
                        tabla.setSpacingBefore(10f);
                        tabla.setSpacingAfter(10f);

                        String[] headers = {"T. De identificacion", "Número", "Nombre", "Apellido", "Email", "Email inst", "Dirección", "Contacto", "Estado"};

                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header,
                                    FontFactory.getFont("Calibri", 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE)));
                            cell.setBackgroundColor(verdeSena);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tabla.addCell(cell);
                        }

                        try (Connection con = DBConnection.getConnection()) {
                            String sqlAprendices = "SELECT ID_usuarios FROM aprendices WHERE ID_instructor = ?";
                            PreparedStatement psAprendices = con.prepareStatement(sqlAprendices);
                            psAprendices.setInt(1, traerIDusuario );
                            ResultSet rsAprendices = psAprendices.executeQuery();

                            boolean hayDatos = false;

                            while (rsAprendices.next()) {
                                int idAprendiz = rsAprendices.getInt("ID_usuarios");

                                String sqlUsuario = "SELECT tipo_dc, numero, nombres, apellidos, email, email_insti, direccion, contacto1, estado FROM usuarios WHERE ID_usuarios = ?";
                                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                                psUsuario.setInt(1, idAprendiz);
                                ResultSet rsUsuario = psUsuario.executeQuery();

                                if (rsUsuario.next()) {
                                    for (int i = 1; i <= 9; i++) {
                                        tabla.addCell(rsUsuario.getString(i));
                                    }
                                    hayDatos = true;
                                }

                                psUsuario.close();
                            }

                            if (!hayDatos) {
                                JOptionPane.showMessageDialog(null, "No se encontraron aprendices asignados.");
                            }

                            psAprendices.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());
                        }

                        documento.add(tabla);
                    }

                    // 6. Aprendices contratados
                    else if (datosAprendices == 8) {
                        Paragraph titulo = new Paragraph("Aprendices Contratados",
                                FontFactory.getFont("Tahoma", 22, com.itextpdf.text.Font.BOLD, verdeSena));
                        titulo.setAlignment(Element.ALIGN_CENTER);
                        documento.add(titulo);
                        documento.add(new Paragraph("\n\n"));

                        PdfPTable tabla = new PdfPTable(6);
                        tabla.setWidthPercentage(110);
                        tabla.setSpacingBefore(10f);
                        tabla.setSpacingAfter(10f);

                        String[] headers = {"T. De Documento", "Número", "Nombres", "Apellidos", "Email", "Empresa"};

                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header,
                                    FontFactory.getFont("Calibri", 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE)));
                            cell.setBackgroundColor(verdeSena);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            tabla.addCell(cell);
                        }

                        try (Connection con = DBConnection.getConnection();
                             PreparedStatement psAprendices = con.prepareStatement("""
                             SELECT u.tipo_dc, u.numero, u.nombres, u.apellidos, u.email, e.nombre_empresa
                             FROM aprendices a
                             INNER JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios
                             INNER JOIN empresas e ON a.ID_empresas = e.ID_empresas
                             WHERE a.ID_empresas = ?
                         """)) {

                            psAprendices.setInt(1, traerIDusuario);

                            ResultSet rsAprendices = psAprendices.executeQuery();

                            if (!rsAprendices.isBeforeFirst()) {
                                JOptionPane.showMessageDialog(null, "No se han encontrado aprendices contratados.");
                            } else {
                                while (rsAprendices.next()) {
                                    tabla.addCell(rsAprendices.getString("tipo_dc"));
                                    tabla.addCell(rsAprendices.getString("numero"));
                                    tabla.addCell(rsAprendices.getString("nombres"));
                                    tabla.addCell(rsAprendices.getString("apellidos"));
                                    tabla.addCell(rsAprendices.getString("email"));
                                    tabla.addCell(rsAprendices.getString("nombre_empresa"));
                                }
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());
                        }

                        documento.add(tabla);
                    }


                    else if (datosAprendices == 9) {
                        Paragraph titulo = new Paragraph("Novedades del Aprendiz",
                                FontFactory.getFont("Tahoma", 22, com.itextpdf.text.Font.BOLD, verdeSena));
                        titulo.setAlignment(Element.ALIGN_CENTER);
                        documento.add(titulo);
                        documento.add(new Paragraph("\n\n"));

                        PdfPTable tabla = new PdfPTable(4);
                        tabla.setWidthPercentage(110);
                        tabla.setSpacingBefore(10f);
                        tabla.setSpacingAfter(10f);

                        // Configurar anchos de columnas: Aprendiz más ancho, Novedad más ancho
                        float[] columnWidths = {3f, 2f, 4f, 2f}; // Aprendiz, Documento, Novedad, Fecha
                        tabla.setWidths(columnWidths);

                        String[] headers = {"Nombres", "Apellidos", "Novedad", "Fecha"};

                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header,
                                    FontFactory.getFont("Calibri", 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE)));
                            cell.setBackgroundColor(verdeSena);
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setPadding(8);
                            tabla.addCell(cell);
                        }

                        try (Connection con = DBConnection.getConnection();
                             // Consulta para obtener novedades del aprendiz específico
                             PreparedStatement psNovedades = con.prepareStatement("""            
                                SELECT u.nombres, u.apellidos, u.numero, n.novedad, n.fecha
                                FROM novedades n
                                INNER JOIN usuarios u ON n.ID_aprendiz = u.ID_usuarios
                                WHERE n.ID_aprendiz = ?
                                ORDER BY n.fecha DESC
                            """)) {

                            psNovedades.setInt(1, idUsuarioActual);

                            ResultSet rsNovedades = psNovedades.executeQuery();

                            if (!rsNovedades.isBeforeFirst()) {
                                JOptionPane.showMessageDialog(null, "No se han encontrado novedades.");
                            } else {
                                while (rsNovedades.next()) {
                                    tabla.addCell(rsNovedades.getString("nombres"));
                                    tabla.addCell(rsNovedades.getString("apellidos"));
                                    tabla.addCell(rsNovedades.getString("novedad"));
                                    tabla.addCell(rsNovedades.getString("fecha"));
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error en la base de datos: " + ex.getMessage());

                        }

                        documento.add(tabla);
                    }


                    // 7. Cerrar documento
                    documento.close();

                    // 8. Abrir el PDF
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(tempFile);
                    } else {
                        JOptionPane.showMessageDialog(null, "La apertura automática no es compatible en este sistema.");
                    }

                } catch (DocumentException | IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
                }

            }
        });

        generarExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ruta = System.getProperty("user.home") + "/Downloads/aprendices.xlsx";

                try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                     PreparedStatement pst = cn.prepareStatement(
                             "SELECT \n" +
                                     "    u.numero AS documento,\n" +
                                     "    u.nombres,\n" +
                                     "    u.apellidos,\n" +
                                     "    a.estado,\n" +
                                     "    f.codigo AS ficha,\n" +
                                     "    p.nombre_programa,\n" +
                                     "    e.nombre_empresa,\n" +
                                     "    CONCAT(ui.nombres, ' ', ui.apellidos) AS instructor,\n" +
                                     "    m.modalidad AS tipo_modalidad\n" +
                                     "FROM aprendices a\n" +
                                     "LEFT JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios\n" +
                                     "LEFT JOIN fichas f ON a.ID_Fichas = f.ID_Fichas\n" +
                                     "LEFT JOIN programas p ON f.ID_programas = p.ID_programas\n" +
                                     "LEFT JOIN empresas e ON a.ID_empresas = e.ID_empresas\n" +
                                     "LEFT JOIN usuarios ui ON a.ID_instructor = ui.ID_usuarios\n" +
                                     "LEFT JOIN modalidad m ON a.ID_modalidad = m.ID_modalidad;\n");
                     ResultSet rs = pst.executeQuery();
                     XSSFWorkbook workbook = new XSSFWorkbook()) {

                    XSSFSheet sheet = workbook.createSheet("Listado de Fichas");

                    String[] headers = {
                            "Documento", "Nombres", "Apellidos", "Estado",
                            "Ficha", "Programa", "Empresa", "Instructor", "Modalidad"
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
                        row.createCell(0).setCellValue(rs.getString("documento"));
                        row.createCell(1).setCellValue(rs.getString("nombres"));
                        row.createCell(2).setCellValue(rs.getString("apellidos"));
                        row.createCell(3).setCellValue(rs.getString("estado"));
                        row.createCell(4).setCellValue(rs.getString("ficha"));
                        row.createCell(5).setCellValue(rs.getString("nombre_programa"));
                        row.createCell(6).setCellValue(rs.getString("nombre_empresa"));
                        row.createCell(7).setCellValue(rs.getString("instructor"));
                        row.createCell(8).setCellValue(rs.getString("tipo_modalidad"));


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


    public void cargarNovedadesEnTabla() {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Limpia la tabla

        String sql = """
        SELECT 
            CONCAT(u.nombres, ' ', u.apellidos) AS aprendiz,
            u.numero AS documento,
            n.novedad,
            n.fecha
        FROM novedades n
        INNER JOIN usuarios u ON n.ID_aprendiz = u.ID_usuarios
        WHERE n.ID_aprendiz = ?
        ORDER BY n.fecha DESC
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuarioActual); // o el ID del aprendiz seleccionado
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String aprendiz = rs.getString("aprendiz");
                String documento = rs.getString("documento");
                String novedad = rs.getString("novedad");
                Timestamp fecha = rs.getTimestamp("fecha");

                model.addRow(new Object[]{aprendiz, documento, novedad, fecha});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Declarar sorter como atributo de clase
    private TableRowSorter<DefaultTableModel> sorter;

    public void setSorter(TableRowSorter<DefaultTableModel> sorter) {
        this.sorter = sorter;
    }

    public void inicializarFiltro(JTextField busqueda, JTable table1) {
        busqueda.getDocument().addDocumentListener(new DocumentListener() {
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
                String texto = busqueda.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    public void obtenerDatosUsuario() {
        DefaultTableModel model = new DefaultTableModel() {
            // Para evitar que las celdas sean editables
            public boolean isCellEditable(int row, int column) {
                // Si es aprendiz (rol 1), permitir editar columnas de botones adicionales
                if (verUsuarioPorRol == 1) {
                    return column == 5 || column == 6 || column == 7 || column == 8; // Ver Perfil, Bitácoras, Seguimiento, Editar
                } else {
                    return column == 5 || column == 6; // Ver Perfil, Editar
                }
            }
        };

        model.addColumn("Tipo de Documento");
        model.addColumn("Número");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Email");
        model.addColumn("Ver Perfil");

        // Agregar columnas adicionales solo para aprendices
//        if (verUsuarioPorRol == 1) {
//            model.addColumn("Bitácoras");
//            model.addColumn("Seguimiento");
//        }

        model.addColumn("Editar");

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT tipo_dc, numero, nombres, apellidos, email_insti FROM usuarios WHERE id_rol = " + verUsuarioPorRol);

            while (rs.next()) {
                Object[] dato;

                // Crear array de datos según el tipo de usuario
                if (verUsuarioPorRol == 1) {
                    // Para aprendices: 8 columnas
                    dato = new Object[7];
                    dato[0] = rs.getString(1);
                    dato[1] = rs.getString(2);
                    dato[2] = rs.getString(3);
                    dato[3] = rs.getString(4);
                    dato[4] = rs.getString(5);
                    dato[5] = "Ver Perfil";
                    dato[6] = "Editar";

//                    dato[6] = "Bitácoras";
//                    dato[7] = "Seguimiento";
                    //dato[8] = "Editar";
                } else {
                    // Para otros roles: 7 columnas
                    dato = new Object[7];
                    dato[0] = rs.getString(1);
                    dato[1] = rs.getString(2);
                    dato[2] = rs.getString(3);
                    dato[3] = rs.getString(4);
                    dato[4] = rs.getString(5);
                    dato[5] = "Ver Perfil";
                    dato[6] = "Editar";
                }

                model.addRow(dato);
            }

            table1.setModel(model);
            sorter = new TableRowSorter<>(model);
            table1.setRowSorter(sorter);

            // Configurar renderizadores y editores para botones
            table1.getColumn("Ver Perfil").setCellRenderer(new ButtonRenderer());
            table1.getColumn("Ver Perfil").setCellEditor(new ButtonEditor(new JCheckBox()));

            // Configurar columnas adicionales solo para aprendices
//            if (verUsuarioPorRol == 1) {
//                table1.getColumn("Bitácoras").setCellRenderer(new ButtonRenderer());
//                table1.getColumn("Bitácoras").setCellEditor(new ButtonEditor(new JCheckBox()));
//
//                table1.getColumn("Seguimiento").setCellRenderer(new ButtonRenderer());
//                table1.getColumn("Seguimiento").setCellEditor(new ButtonEditor(new JCheckBox()));
//            }

            table1.getColumn("Editar").setCellRenderer(new ButtonRenderer());
            table1.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Renderiza el botón
    class ButtonRenderer extends JPanel implements TableCellRenderer {

        private JButton button;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Centra el botón sin márgenes
            setOpaque(true);
            setBackground(Color.WHITE);

            button = new JButton();
            button.setPreferredSize(new Dimension(70, 30));
            button.setMargin(new Insets(5, 10, 5, 10));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            add(button);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {

            String text = (value == null) ? "" : value.toString();
            button.setText(text);

            // Colores personalizados
            if (text.equals("Ver Perfil")) {
                button.setBackground(new Color(0, 123, 255));
                button.setForeground(Color.WHITE);
            } else if (text.equals("Editar")) {
                button.setBackground(new Color(0, 123, 255));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }

            return this;
        }
    }

    // Clase ButtonEditor mejorada para manejar diferentes acciones
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;
        private int selectedRow;
        private int selectedColumn;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.table = table;
            this.selectedRow = row;
            this.selectedColumn = column;

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // Obtener los datos de la fila seleccionada
                String tipoDoc = (String) table.getValueAt(selectedRow, 0);
                String numeroDoc = (String) table.getValueAt(selectedRow, 1);
                String nombres = (String) table.getValueAt(selectedRow, 2);
                String apellidos = (String) table.getValueAt(selectedRow, 3);
                String email = (String) table.getValueAt(selectedRow, 4);

                // Determinar qué botón fue presionado
                String columnName = table.getColumnName(selectedColumn);

                if ("Ver Perfil".equals(columnName)) {
                    // Acción para Ver Perfil
                    abrirPerfilUsuario(numeroDoc, tipoDoc);

//                } else if ("Bitácoras".equals(columnName)) {
//                    // Acción para Bitácoras - NUEVA FUNCIONALIDAD
//                    abrirBitacoras(numeroDoc, tipoDoc, nombres, apellidos);
//
//                } else if ("Seguimiento".equals(columnName)) {
//                    // Acción para Seguimiento - NUEVA FUNCIONALIDAD
//                    abrirSeguimiento(numeroDoc, tipoDoc, nombres, apellidos);

                } else if ("Editar".equals(columnName)) {
                    // Acción para Editar Usuario
                    editarUsuario(tipoDoc, numeroDoc);
                }
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        public void abrirPerfilUsuario(String numeroDoc, String tipoDoc) {
            try {
                // Obtener el rol del usuario específico desde la base de datos
                int rolUsuario = obtenerRolUsuario(numeroDoc, tipoDoc);
                int idUsuario = obtenerIdUsuario(numeroDoc, tipoDoc);

                // Crear una nueva ventana modal
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(button),
                        "Perfil de Usuario", true);

                // Crear instancia de VisualizarPerfilGUI con el rol correcto del usuario específico
                VisualizarPerfilGUI perfilGUI = new VisualizarPerfilGUI(idUsuario, rolUsuario, admin);

                // Cargar los datos del usuario específico
                perfilGUI.cargarDatosUsuarioEspecifico(numeroDoc, tipoDoc);

                // Configurar la ventana modal
                dialog.setContentPane(perfilGUI.panel1);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setLocationRelativeTo(button);
                dialog.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(button,
                        "Error al abrir el perfil del usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


        // NUEVA FUNCIONALIDAD: Método para abrir Bitácoras
//        public void abrirBitacoras(String numeroDoc, String tipoDoc, String nombres, String apellidos) {
//            try {
//                // Usamos la clase DAO para obtener el email del aprendiz por su documento
//                UsuariosDAO dao = new UsuariosDAO();
//                String email = dao.obtenerCorreoPorDocumento(numeroDoc, tipoDoc); // Asegúrate de tener este método en tu DAO
//
//                if (email != null && !email.isEmpty()) {
//                    SwingUtilities.invokeLater(() -> {
//                        CodigoGUI2 bitacorasGUI = new CodigoGUI2(email);
//                        bitacorasGUI.setVisible(true);
//                    });
//                } else {
//                    JOptionPane.showMessageDialog(null, "No se encontró el correo del aprendiz.");
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Error al abrir bitácoras: " + ex.getMessage());
//            }
//        }

//        public void abrirSeguimiento(String numeroDoc, String tipoDoc, String nombres, String apellidos) {
//            try {
//                // Usamos la clase DAO para obtener el email del aprendiz por su documento
//                UsuariosDAO dao = new UsuariosDAO();
//                String email = dao.obtenerCorreoPorDocumento(numeroDoc, tipoDoc); // Asegúrate de tener este método en tu DAO
//
//                if (email != null && !email.isEmpty()) {
//                    SwingUtilities.invokeLater(() -> {
//                        CodigoGUI codigoGUI = new CodigoGUI(email);
//                        codigoGUI.setVisible(true);
//                    });
//                } else {
//                    JOptionPane.showMessageDialog(null, "No se encontró el correo del aprendiz.");
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Error al abrir seguimiento: " + ex.getMessage());
//            }
//        }

        private int obtenerRolUsuario(String numeroDoc, String tipoDoc) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT ID_rol FROM usuarios WHERE numero = ? AND tipo_dc = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, numeroDoc);
                stmt.setString(2, tipoDoc);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt("ID_rol");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0; // Valor por defecto si no se encuentra
        }

        // Método auxiliar para obtener el ID del usuario
        private int obtenerIdUsuario(String numeroDoc, String tipoDoc) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT ID_usuarios FROM usuarios WHERE numero = ? AND tipo_dc = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, numeroDoc);
                stmt.setString(2, tipoDoc);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt("ID_usuarios");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0; // Valor por defecto si no se encuentra
        }

        // Método para editar usuario
        private void editarUsuario(String tipoDoc, String numeroDoc) {
            try {
                // Crear instancia del DAO
                UsuariosDAO dao = new UsuariosDAO();

                // Buscar el usuario completo por tipo y número de documento
                Usuarios_getset usuario = dao.obtenerUsuarioPorDocumento(tipoDoc, numeroDoc);

                if (usuario != null) {
                    // Crear y mostrar la ventana de edición
                    JFrame frameEditar = new JFrame("Editar Usuario");
                    EditarUsuario editarUsuario = new EditarUsuario(usuario);
                    frameEditar.setContentPane(editarUsuario.getMainPanel());
                    frameEditar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frameEditar.pack();
                    frameEditar.setLocationRelativeTo(null);
                    frameEditar.setVisible(true);

                    // Opcional: Actualizar la tabla después de cerrar la ventana de edición
//                    frameEditar.addWindowListener(new java.awt.event.WindowAdapter() {
//                        @Override
//                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//                            // Refrescar la tabla
//                            obtenerDatosUsuario();
//                        }
//                    });

                } else {
                    JOptionPane.showMessageDialog(button,
                            "No se pudo encontrar el usuario con los datos proporcionados.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(button,
                        "Error al abrir el formulario de edición: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    String rol = null;

    public void tipoDeUsuarioRegistrado(){
        if (verUsuarioPorRol == 1){
            rol = "Aprendices Registrados";
        }
        if (verUsuarioPorRol == 2){
            rol = "Evaluadores Registrados";
        }
        if (verUsuarioPorRol == 3){
            rol = "Empresas Registradas";
        }
        else if (verUsuarioPorRol == 4) {
            rol = "Auxiliares Registrados";
        }

        tipoUsuario.setText(rol);

    }

    public void componentesPersonalizado() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        busqueda.setBorder(bottom);
        table1.getTableHeader().setForeground(Color.decode("#ffffff")); // Color del texto
        table1.getTableHeader().setBackground(Color.decode("#39A900"));

        table1.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Cuerpo de la tabla
        table1.setRowHeight(35);

        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Encabezado

        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
        table1.getTableHeader().setReorderingAllowed(false);
    }

    public void tipoDeUsuarioRegistrado1() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rol FROM rol WHERE id_rol = " + verUsuarioPorRol);

            if (rs.next()) {
                rol = rs.getString("rol");
                tipoUsuario.setText(rol);  // mostrar rol en el JLabel
            } else {
                tipoUsuario.setText("Rol no encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tipoUsuario.setText("Error de conexión");
        }
        tipoUsuario.setText(rol + " Registrado");
    }
}