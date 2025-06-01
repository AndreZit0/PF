package Empresas.Vista;

import Empresas.ConexionBD.ConnectionDB;
import Empresas.Controlador.EmpresaDAO;
import Empresas.Modelo.Empresa;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Clase que representa la interfaz gráfica para gestionar empresas.
 */
public class AdministrarGUI {
    private JPanel pnlAdministrar;
    private JTable table1;
    private JButton observarButton;
    private JButton actualizarButton;
    private JButton pdfButton;
    private JTextField busqueda;
    private EmpresaDAO empresaDAO = new EmpresaDAO();
    private ConnectionDB connectionDB = new ConnectionDB();
    private TableRowSorter<DefaultTableModel> sorter;
    public JPanel getPanel() {
        return pnlAdministrar;
    }

    /**
     * Constructor de la clase AdministrarGUI.
     * Inicializa los componentes de la interfaz.
     */
    public AdministrarGUI() {
        List<Empresa> empresas = empresaDAO.obtenerEmpresa();
        cargarEmpresas(empresas);
        componentesPersonalizado();

        observarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empresaDetalle();
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEmpresa();
            }
        });

        busqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            private void aplicarFiltro() {
                String texto = busqueda.getText();
                if (sorter != null) {
                    if (texto.trim().length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                    }
                }
            }
        });

        /**
         * Configura el evento para generar un archivo PDF con la lista de empresas registradas.
         * Cuando el botón es presionado, se verifica si hay una fila seleccionada en la tabla.
         * Si no hay una selección, se muestra un mensaje. Si hay una fila seleccionada, se genera un PDF
         * con la información de las empresas almacenadas en la base de datos.
         */
        pdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                // Verificar si hay una fila seleccionada
//                int selectedRow = table1.getSelectedRow();
//                if (selectedRow == -1) {
//                    // mostrar mensaje
//                    JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
//                    return; // Salir
//                }

                //colores
                BaseColor verdeSena = new BaseColor(57, 169, 0);
                Document documento = new Document(PageSize.A4);

                try {
                    File tempFile = File.createTempFile("empresas_", ".pdf");
                    tempFile.deleteOnExit(); // Se elimina automáticamente cuando finalice el programa
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(tempFile));

                    documento.open();

                    String imagePath = "src/Empresas/img/fondo.png";
                    File imgFile = new File(imagePath);
                    if (!imgFile.exists()) {
                        JOptionPane.showMessageDialog(null, "Error: Imagen de fondo no encontrada.");
                        return;
                    }

                    Image background = Image.getInstance(imagePath);
                    background.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                    background.setAbsolutePosition(0, 0);

                    PdfContentByte canvas = writer.getDirectContentUnder();
                    canvas.addImage(background);


                    documento.add(new Paragraph("\n\n\n"));
                    documento.add(new Paragraph("\n\n\n"));

                    Paragraph titulo = new Paragraph("Empresas Registradas",
                            FontFactory.getFont("Tahoma", 22, Font.BOLD, verdeSena));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                    documento.add(new Paragraph("\n\n"));

                    PdfPTable tabla = new PdfPTable(9);
                    tabla.setWidthPercentage(110);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    String[] headers = {"NIT", "Nombre", "Dir", "Area", "Contacto", "Correo", "Depto", "Ciudad", "Estado"};

                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header,
                                FontFactory.getFont("Calibri", 12, Font.BOLD, BaseColor.WHITE)));
                        cell.setBackgroundColor(verdeSena);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tabla.addCell(cell);
                    }

                    try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                         PreparedStatement pst = cn.prepareStatement("SELECT nit, nombre_empresa, direccion, area, contacto, email, departamento, ciudad, estado FROM empresas;");
                         ResultSet rs = pst.executeQuery()) {

                        if (!rs.isBeforeFirst()) {
                            JOptionPane.showMessageDialog(null, "No se han encontrado empresas.");
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
                    documento.close();
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
    }

    /**
     * Actualiza los datos de la empresa seleccionada en la tabla.
     * Si no hay ninguna fila seleccionada, muestra un mensaje solicitando que se seleccione una empresa.
     * Si se encuentra la empresa, abre un cuadro de diálogo para actualizarla.
     */
    public void actualizarEmpresa() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
            return;
        }

        String nit = table1.getValueAt(selectedRow, 0).toString();
        Empresa empresa = empresaDAO.buscarEmpresa(nit);

        if (empresa != null) {
            ActualizarGUI dialog = new ActualizarGUI(
                    null,
                    empresa,
                    empresaDAO,
                    () -> cargarEmpresas(empresaDAO.obtenerEmpresa())
            );
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Empresa no encontrada.");
        }
    }

    /**
     * Muestra los detalles de la empresa seleccionada en la tabla.
     * Si no hay ninguna fila seleccionada, muestra un mensaje solicitando que se seleccione una empresa.
     */
    public void empresaDetalle() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String nitSeleccionado = table1.getValueAt(selectedRow, 0).toString();
            DetallesGUI detalles = new DetallesGUI(nitSeleccionado);
            JFrame frame = new JFrame("Detalles de la Empresa");
            frame.setContentPane(detalles.getMainPanel());
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
        }
    }

    /**
     * Carga y muestra las empresas en la tabla.
     * Configura un modelo para la tabla y agrega las empresas a la vista.
     */
    public void cargarEmpresas(List<Empresa> listaEmpresas) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("NIT");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");
        model.addColumn("Coevaluador");
        model.addColumn("Estado");

        for (Empresa empresa : listaEmpresas) {
            model.addRow(new Object[]{
                    empresa.getNit(),
                    empresa.getNombre_empresa(),
                    empresa.getContacto(),
                    empresa.getNombreCoevaluador(),
                    empresa.getEstado()
            });
        }
        table1.setModel(model);
        sorter = new TableRowSorter<>(model);
        table1.setRowSorter(sorter);
    }

    /**
     * Aplica estilos personalizados a la tabla.
     */
    public void componentesPersonalizado() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        busqueda.setBorder(bottom);
        table1.getTableHeader().setForeground(Color.decode("#ffffff")); // Color del texto
        table1.getTableHeader().setBackground(Color.decode("#39A900"));
        table1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13)); // Cuerpo de la tabla
        table1.setRowHeight(25);
        table1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)); // Encabezado
        table1.getTableHeader().setReorderingAllowed(false);
        table1.setRowHeight(25);
    }

    public static void main(String[] args) {
        AdministrarGUI administrarGUI = new AdministrarGUI();
        JFrame frame = new JFrame("EMPRESA");
        frame.setContentPane(new AdministrarGUI().pnlAdministrar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
