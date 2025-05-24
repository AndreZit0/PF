package Usuarios;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminUsuarioGUI {
    private JPanel main;
    private JTable tableusuarios;
    private JComboBox comboBox1;
    private JTextField textField1;


    private TableRowSorter<TableModel> sorter;

    public void inicializarFiltro() {
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
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    public AdminUsuarioGUI(){

     inicializarFiltro();
//Asingacion de los nombres a los encabezados de la tabla
        String[] columnas = {"ID","Tipo de Documento","Número", "Nombre", "Apellido", "Email","Estado", "Acciones"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tableusuarios.setModel(modelo);

        // Asignar el sorter al nuevo modelo
        sorter = new TableRowSorter<>(modelo);
        tableusuarios.setRowSorter(sorter);

        // Ocultar la columna del ID (columna 0)
        tableusuarios.getColumnModel().getColumn(0).setMinWidth(0);
        tableusuarios.getColumnModel().getColumn(0).setMaxWidth(0);
        tableusuarios.getColumnModel().getColumn(0).setWidth(0);

        tableusuarios.setFont(new Font("Calibri", Font.PLAIN, 14)); // Tipografía para los datos que se muestran en la tabla
        tableusuarios.setRowHeight(30);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

// Aplicar el renderer centrado a todas las columnas, excepto la de acciones (columna 6)
        for (int i = 0; i < tableusuarios.getColumnCount(); i++) {
            if (i != 7) { // No centrar la columna de botones
                tableusuarios.getColumnModel().getColumn(i).setCellRenderer(centrado);
            }
        }

        tableusuarios.setRowHeight(30);
        tableusuarios.getColumnModel().getColumn(7).setPreferredWidth(100);
        tableusuarios.getColumnModel().getColumn(7).setCellRenderer(new BotonRenderer());
        tableusuarios.getColumnModel().getColumn(7).setCellEditor(
                new BotonEditor(tableusuarios, id -> {
                    Usuarios_getset usuario = new UsuariosDAO().buscarUsuario(id);
                    if (usuario != null) {
                        JFrame frame = new JFrame("Editar Usuario");
                        frame.setContentPane(new EditarUsuario(usuario).getMainPanel());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                    }
                })
        );



        JTableHeader header = tableusuarios.getTableHeader();
        header.setBackground(new java.awt.Color(57, 169, 0)); // color de fondo verde
        header.setForeground(Color.WHITE); // color del texto blanco
        header.setFont(new Font("Calibri", Font.BOLD, 14));

        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        textField1.setBorder(bottom);



//traemos el metodo de cargarUsuariosPorRol para cuando se elija una opcion del combobox
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboBox1.getSelectedItem();
                if(seleccionado.equals("Aprendices")){
                    cargarUsuariosPorRol("aprendiz");
                }else if(seleccionado.equals("Evaluadores")) {
                    cargarUsuariosPorRol("evaluador");
                }else if(seleccionado.equals("Coevaluadores")) {
                    cargarUsuariosPorRol("coevaluador");
                }else if(seleccionado.equals("Auxiliares")) {
                    cargarUsuariosPorRol("auxiliar");
                }else if(seleccionado.equals("Administrativos")) {
                    cargarUsuariosPorRol("administrador");
                }
            }
        });
    }
    //metodo que nos trae los datos de la bd mediante la clase DAO por el metodo listarUsuariosPorRol
    private void cargarUsuariosPorRol(String rol){
        UsuariosDAO dao = new UsuariosDAO();
        ArrayList<Usuarios_getset> lista = dao.listarUsuariosPorRol(rol);

        DefaultTableModel modelo = (DefaultTableModel) tableusuarios.getModel();
        modelo.setRowCount(0);

        for (Usuarios_getset u : lista){
            modelo.addRow(new Object[]{
                    u.getID_usuarios(),
                    u.getTipo_dc(),
                    u.getDocumento(),
                    u.getNombres(),
                    u.getApellidos(),
                    u.getEmail(),
                    u.getEstado(),
                   null
            });
        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Administración de Usuarios");
        frame.setContentPane(new AdminUsuarioGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
