package Empresas.Vista;
import Empresas.ConexionBD.ConnectionDB;
import Empresas.Controlador.EmpresaDAO;
import Empresas.Controlador.ActualizarEmpresaDialog;
import Empresas.Modelo.Empresa;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfContentByte;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Clase que representa la interfaz gráfica para gestionar empresas.
 */
public class EmpresaGUI {

    private JTabbedPane tabbedPane1;
    private JTextField Nit;
    private JTextField Email;
    private JTextField Direccion;
    private JTextField Area;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JTable table1;
    private JPanel panelEmpresa;
    private JTextField Contacto;
    private JTextField Nombre;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton observarButton;
    private JComboBox comboBox1;
    private JButton pdfButton;
    private JTextField Ciudad;
    private JComboBox combo1;
    private EmpresaDAO empresaDAO = new EmpresaDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    public JPanel getPanel(){return panelEmpresa;}

    /**
     * Constructor de la clase EmpresaGUI.
     * Inicializa los componentes de la interfaz y configura los bordes y estilos visuales.
     */
    public EmpresaGUI() {
        tabbedPane1.setUI(new CustomTabbedPaneUI());
        // Configura el borde inferior de los campos de texto
        Border bottom = BorderFactory.createMatteBorder(0,0,2,0, Color.decode("#39A900"));

        // Aplica el borde a los campos de texto
        Nit.setBorder(bottom);
        Contacto.setBorder(bottom);
        Nombre.setBorder(bottom);
        Area.setBorder(bottom);
        Direccion.setBorder(bottom);
        Email.setBorder(bottom);
        comboBox1.setBorder(bottom);
        combo1.setBorder(bottom);
        Ciudad.setBorder(bottom);
        comboBox1.setSelectedIndex(-1); // Para que no haya selección al principio
        combo1.setSelectedIndex(-1); // Para que no haya selección al principio
        // Configura los oyentes de eventos
        setupListeners();

        // Personalización visual de la JTable
        table1.setBackground(new Color(245, 245, 245));
        table1.setForeground(Color.BLACK);
        table1.setSelectionBackground(new Color(119, 119, 119));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(new Color(220, 220, 220));
        table1.setShowGrid(true);

        // Personaliza la cabecera de la tabla
        JTableHeader header = table1.getTableHeader();
        header.setBackground(new Color(57, 169, 0));
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        // Deshabilita el reordenamiento de las columnas
        header.setReorderingAllowed(false);
        table1.setRowHeight(25);

        // Configura nuevamente los oyentes de eventos
        setupListeners();
        cargarUsuariosEnComboBox();

        // Configura el evento de clic en el botón 'confirmar'
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpresa();
            }
        });

        // Configura el evento de clic en el botón 'eliminar'
        eliminarButton.addActionListener(e -> {
            eliminarEmpresa();
            List<Empresa> empresas = empresaDAO.obtenerEmpresa();
            cargarEmpresas(empresas);
        });

        // Configura el evento de clic en el botón 'observar'
        observarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empresaDetalle();
            }
        });

        // Configura el evento de clic en el botón 'actualizar'
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarEmpresa();
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
                // Verificar si hay una fila seleccionada
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    // mostrar mensaje
                    JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
                    return; // Salir
                }

                //colores
                BaseColor verdeSena = new BaseColor(57, 169, 0);
                Document documento = new Document(PageSize.A4);

                try {
                    String ruta = System.getProperty("user.home") + "/Downloads/empresas.pdf";
                    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta));

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
                    tabla.setWidthPercentage(100);
                    tabla.setSpacingBefore(10f);
                    tabla.setSpacingAfter(10f);

                    String[] headers = {"ID", "NIT", "Nombre", "Dir", "Area", "Contacto", "Correo", "Depto", "Ciudad"};

                    for (String header : headers) {
                        PdfPCell cell = new PdfPCell(new Phrase(header,
                                FontFactory.getFont("Calibri", 12, Font.BOLD, BaseColor.WHITE)));
                        cell.setBackgroundColor(verdeSena);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tabla.addCell(cell);
                    }

                    try (Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/saep", "root", "");
                         PreparedStatement pst = cn.prepareStatement("SELECT ID_empresas, nit, nombre_empresa, direccion, area, contacto, email, departamento, ciudad FROM empresas;");
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

                    JOptionPane.showMessageDialog(null, "PDF generado correctamente en descargas.");

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
            new ActualizarEmpresaDialog(null, empresa, empresaDAO, () -> {
                List<Empresa> empresas = empresaDAO.obtenerEmpresa();
                cargarEmpresas(empresas);
            }).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar la empresa.");
        }
    }

    /**
     * Muestra los detalles de la empresa seleccionada en la tabla.
     * Si no hay ninguna fila seleccionada, muestra un mensaje solicitando que se seleccione una empresa.
     */
    public void empresaDetalle() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String nit = table1.getValueAt(selectedRow, 0).toString();
            String nombre = table1.getValueAt(selectedRow, 1).toString();
            String telefono = table1.getValueAt(selectedRow, 2).toString();

            Empresa empresa = empresaDAO.buscarEmpresa(nit);

            // Obtener el ID del coevaluador
            int idUsuario = empresa.getID_usuarios(); // Suponiendo que tienes este método en tu clase Empresa
            String nombreCoevaluador = "";

            // Obtener el nombre y apellido del coevaluador desde la base de datos
            try (Connection con = new ConnectionDB().getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT nombres, apellidos FROM usuarios WHERE ID_usuarios = ?")) {
                ps.setInt(1, idUsuario);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nombreCoevaluador = rs.getString("nombres") + " " + rs.getString("apellidos");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener el nombre del coevaluador.");
            }

            String mensaje = "NIT: " + empresa.getNit() + "\n"
                    + "Nombre: " + empresa.getNombre_empresa() + "\n"
                    + "Dirección: " + empresa.getDireccion() + "\n"
                    + "Área: " + empresa.getArea() + "\n"
                    + "Coevaluador: " + nombreCoevaluador + "\n"
                    + "Contacto: " + empresa.getContacto() + "\n"
                    + "Email: " + empresa.getEmail() + "\n"
                    + "Departamento: " + empresa.getDepartamento() + "\n"
                    + "Ciudad: " + empresa.getCiudad();

            JOptionPane.showMessageDialog(null, mensaje, "Detalles de la empresa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
        }
    }

    /**
     * Agrega una nueva empresa a la base de datos.
     * Primero valida que todos los campos estén completos y que el formato del correo electrónico sea válido.
     * Luego solicita confirmación antes de agregar la empresa.
     */
    public void agregarEmpresa() {
        String nit = Nit.getText().trim();
        String nombre = Nombre.getText().trim();
        String direccion = Direccion.getText().trim();
        String area = Area.getText().trim();
        String contacto = Contacto.getText().trim();
        String email = Email.getText().trim();
        String departamento = comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString().trim() : "";
        String usuarios = combo1.getSelectedItem() != null ? combo1.getSelectedItem().toString().trim() : "";
        String ciudad = Ciudad.getText().trim();

        if (nit.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || area.isEmpty() ||
                contacto.isEmpty() || email.isEmpty() || departamento.isEmpty() || ciudad.isEmpty() || usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(panelEmpresa, "Por favor, completa todos los campos antes de continuar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Extraer ID de usuario desde combo1
        int idUsuario = 0;
        try {
            String[] partes = usuarios.split(" - ");
            idUsuario = Integer.parseInt(partes[0]);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelEmpresa, "Error al procesar el ID del usuario.");
            return;
        }

        // Validar formato de correo electrónico
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(panelEmpresa, "Por favor, ingresa un correo electrónico válido (@gmail.com).", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmación antes de agregar la empresa
        int confirmacion = JOptionPane.showConfirmDialog(panelEmpresa, "¿Estás seguro de que deseas agregar esta empresa?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            return; // Si el usuario selecciona NO, se cancela
        }

        Empresa empresa = new Empresa(0, idUsuario, nit, nombre, direccion, area, contacto, email, departamento, ciudad);
        if (empresaDAO.agregarEmpresa(empresa)) {
            JOptionPane.showMessageDialog(panelEmpresa, "Empresa agregada exitosamente.");
            // Limpiar los campos
            Nit.setText("");
            Nombre.setText("");
            Direccion.setText("");
            Area.setText("");
            Contacto.setText("");
            Email.setText("");
            comboBox1.setSelectedIndex(-1);
            Ciudad.setText("");
            combo1.setSelectedIndex(-1);

        } else {
            JOptionPane.showMessageDialog(panelEmpresa, "Error al agregar la empresa.");
        }
    }

    /**
     * Elimina la empresa seleccionada de la base de datos.
     * Si no hay ninguna fila seleccionada, muestra un mensaje solicitando que se seleccione una empresa.
     * Luego solicita confirmación antes de eliminar la empresa.
     */
    public void eliminarEmpresa() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una empresa primero.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar esta empresa?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            String nit = table1.getValueAt(selectedRow, 0).toString();
            Empresa empresa = empresaDAO.buscarEmpresa(nit);
            if (empresa != null) {
                empresaDAO.eliminarEmpresa(empresa.getID_empresas());
                JOptionPane.showMessageDialog(null, "Empresa eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Empresa no encontrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }

    /**
     * Carga y muestra las empresas en la tabla.
     * Configura un modelo para la tabla y agrega las empresas a la vista.
     */
    public void cargarEmpresas(List<Empresa> listaEmpresas) {
        DefaultTableModel model = new DefaultTableModel() {
            // para que ninguna celda sea editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda será editable
            }
        };

        model.addColumn("NIT");
        model.addColumn("Nombre");
        model.addColumn("Teléfono");

        for (Empresa empresa : listaEmpresas) {
            model.addRow(new Object[]{
                    empresa.getNit(),
                    empresa.getNombre_empresa(),
                    empresa.getContacto()
            });
        }
        table1.setModel(model);
    }

    /**
     * Configura los listeners para los componentes de la interfaz de usuario.
     * En este caso, se establece un listener para el `tabbedPane1` que carga las empresas
     * cuando se selecciona la segunda pestaña (índice 1).
     */
    public void setupListeners() {
        tabbedPane1.addChangeListener(e -> {
            if (tabbedPane1.getSelectedIndex() == 1) {
                List<Empresa> empresas = empresaDAO.obtenerEmpresa();
                cargarEmpresas(empresas);
            }
        });
    }

    /**
     * Personaliza la apariencia de los componentes de la interfaz de usuario.
     * Se ajustan bordes personalizados en los campos de texto y se ajusta la altura de las filas de la tabla.
     */
    public void perzonalizar(){
        Border bottom = BorderFactory.createMatteBorder(0,0,2,0, Color.decode("#39A900"));
        Contacto.setBorder(bottom);
        Nombre.setBorder(bottom);
        Area.setBorder(bottom);
        Direccion.setBorder(bottom);
        Email.setBorder(bottom);
        table1.setRowHeight(25);
    }

    //Cambiar color de las ventanas administrar y crear
    /**
     * Clase personalizada para modificar el comportamiento de la apariencia de las pestañas de un {@link JTabbedPane}.
     * Cambia el color de las pestañas según su estado (seleccionada o no seleccionada).
     */
    public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            if (isSelected) {
                g.setColor(new Color(57, 169, 0)); // Verde cuando está seleccionado
            } else {
                g.setColor(new Color(119, 119, 119)); // Verde también cuando no está seleccionado
            }
            g.fillRect(x, y, w, h);
        }

        @Override
        protected void paintFocusIndicator(Graphics g, int tabPlacement,
                                           Rectangle[] rects, int tabIndex,
                                           Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement,
                                          int selectedIndex) {
            super.paintContentBorder(g, tabPlacement, selectedIndex);
        }
    }

    /**
     * Carga los usuarios con rol de "Coevaluador" desde la base de datos y los agrega a un {@link JComboBox}.
     * Cada ítem es el ID del usuario seguido de su nombre y apellido.
     */
    public void cargarUsuariosEnComboBox() {
        try (Connection con = new ConnectionDB().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT u.ID_usuarios, u.nombres, u.apellidos FROM usuarios u JOIN rol r ON u.ID_rol = r.ID_rol WHERE r.rol = 'Coevaluador'");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_usuarios");
                String nombre = rs.getString("nombres");
                String apellido = rs.getString("apellidos");
                combo1.addItem(id + " - " + nombre + " " + apellido);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los ID de usuarios.");
        }
    }

    public static void main(String[] args) {
        EmpresaGUI empresaGUI = new EmpresaGUI();
        JFrame frame = new JFrame("EMPRESA");
        frame.setContentPane(new EmpresaGUI().panelEmpresa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        empresaGUI.perzonalizar();
    }
}
