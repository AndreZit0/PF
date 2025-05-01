package AsignacionInstructor;

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AsignacionGUI {
    private JPanel main;
    private JButton volverButton;
    private JTable table1;
    private JTextField campoBusqueda;
    private JComboBox comboBox1;
    private JButton button1;
    private JFrame frame;
    private JFrame parentFrame;
    private Conexion conexion = new Conexion();
    String terminoBusqueda;

    /**
     * This method is used to show the GUI for the view.
     *
     * @param frame
     */
    public AsignacionGUI(JFrame frame) {
        //showdata();

        this.frame = frame;
        this.parentFrame = parentFrame;

        frame.setContentPane(main); // Establece el panel principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Ajusta el tamaño al contenido
        frame.setLocationRelativeTo(null); // Centra la ventana
        frame.setVisible(true); // Muestra la ventana

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentFrame != null){
                    parentFrame.setVisible(true);
                }
                frame.dispose();
            }
        });

        //boton de busqueda por documento o ficha.
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(campoBusqueda.getText().equals("")){
                    campoBusqueda.requestFocus();
                }

                //condicion de documento o ficha:
                if(comboBox1.getSelectedItem().toString().equals("No. Documento")){
                    //JOptionPane.showMessageDialog(null, "Se activó la búsqueda por numero de documento");
                    terminoBusqueda = campoBusqueda.getText();
                    try {
                        java.util.List<Asignacion> asignacion = buscarAprendiz(terminoBusqueda);
                        // Actualizar la tabla con los resultados
                        actualizarTabla(asignacion);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al buscar productos");
                    }
                }else{//la búsqueda esta por ficha
                    terminoBusqueda = campoBusqueda.getText();
                    try {
                        java.util.List<Asignacion> asignacion = buscarFicha(terminoBusqueda);

                        // Actualizar la tabla con los resultados
                        actualizarTabla(asignacion);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al buscar ficha");
                    }
                }

            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedItem().toString().equals("Ficha")){
                    JOptionPane.showMessageDialog(null, "Se ha activado la búsqueda por ficha");
                }else{
                    JOptionPane.showMessageDialog(null, "Se ha activado la búsqueda por número de documento");
                }
            }
        });
    }




    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorderPainted(true);
            setContentAreaFilled(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();


                    String nombre = (String) table1.getValueAt(selectedRow, 0);
                    String documento = (String) table1.getValueAt(selectedRow, 1);

                    GUIEvaluador guiEvaluador = new GUIEvaluador();
                    guiEvaluador.ejecutar();


                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {

            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }


    public void showdata() {
        NonEditableTableModel modelo = new NonEditableTableModel();

        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Ficha");
        modelo.addColumn("Programa");
        modelo.addColumn("Evaluador");
        modelo.addColumn("Fecha Inicial");
        modelo.addColumn("Fecha Final");

        table1.setModel(modelo);

        table1.getColumn("Evaluador").setCellRenderer(new ButtonRenderer());
        table1.getColumn("Evaluador").setCellEditor(new ButtonEditor(new JCheckBox()));

        String[] dato = new String[7];
        Connection con = conexion.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT \n" +
                    "  a.ID_numeroAprendices,\n" +
                    "  u.nombres AS nombre_aprendiz,\n" +
                    "  f.numero_ficha,\n" +
                    "  e.nombre_empresa,\n" +
                    "  i.nombres AS nombre_instructor,\n" +
                    "  m.modalidad\n" +
                    "FROM aprendices a\n" +
                    "JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios\n" +
                    "JOIN fichas f ON a.ID_Fichas = f.ID_fichas\n" +
                    "JOIN empresas e ON a.ID_empresas = e.ID_empresas\n" +
                    "JOIN usuarios i ON a.ID_instructor = i.ID_usuarios\n" +
                    "JOIN modalidad m ON a.ID_modalidad = m.ID_modalidad;");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = "➕";
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);

                modelo.addRow(new Object[]{
                        dato[0], dato[1], dato[2], dato[3], dato[4], dato[5], dato[6]
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4; // Solo columna "Evaluador" editable
        }
    }



    /************************************************************************************************************/

    /**
     * /*
     * buscarAprendiz
     * se busca aprendiz por documento y se muestra la sigueinte informacion: numero (cedula), nombres, ficha, programa.
     * es una busqueda parcial
     *
     */
    public List<Asignacion> buscarAprendiz(String terminoBusqueda) throws SQLException {
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        List<Asignacion> asignacion = new ArrayList<>();

        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT u.tipo_dc, u.numero, CONCAT(u.nombres, ' ', u.apellidos) AS nombre_completo, " +
                    "f.codigo AS ficha, p.nombre_programa " +
                    "FROM aprendices a " +
                    "JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios " +
                    "JOIN fichas f ON a.ID_Fichas = f.ID_Fichas " +
                    "JOIN programas p ON f.ID_programas = p.ID_programas " +
                    "WHERE u.numero LIKE ?";

            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%");
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Asignacion asg = new Asignacion();
                asg.setDocumento(resultado.getString("numero"));
                asg.setNombre(resultado.getString("nombre_completo"));
                asg.setFicha(resultado.getString("ficha"));
                asg.setPrograma(resultado.getString("nombre_programa"));

                asignacion.add(asg);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultado != null) resultado.close();
            if (consulta != null) consulta.close();
        }
        return asignacion;
    }


    /**
     * Búsqueda para ficha.
     * se buscan varios aprendices por ficha y se muestra la sigueinte informacion: numero (cedula), nombres, ficha (importante) y programa.
     *
     */
    public List<Asignacion> buscarFicha(String terminoBusqueda) throws SQLException {
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        List<Asignacion> asignacion = new ArrayList<>();

        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT u.tipo_dc, u.numero, CONCAT(u.nombres, ' ', u.apellidos) AS nombre_completo, " +
                    "f.codigo AS ficha, p.nombre_programa " +
                    "FROM aprendices a " +
                    "JOIN usuarios u ON a.ID_usuarios = u.ID_usuarios " +
                    "JOIN fichas f ON a.ID_Fichas = f.ID_Fichas " +
                    "JOIN programas p ON f.ID_programas = p.ID_programas " +
                    "WHERE f.codigo LIKE ?";

            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%"); // Búsqueda parcial
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Asignacion asg = new Asignacion();
                asg.setDocumento(resultado.getString("numero"));
                asg.setNombre(resultado.getString("nombre_completo"));
                asg.setFicha(resultado.getString("ficha"));
                asg.setPrograma(resultado.getString("nombre_programa"));
                // Si deseas, puedes establecer campos vacíos o nulos para otros atributos
                asignacion.add(asg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (resultado != null) resultado.close();
            if (consulta != null) consulta.close();
        }

        return asignacion;
    }


    /**
     * Se actualiza el modelo de la tabla al realizar la búsqueda
     * funciona para documento y ficha
     */
    public void actualizarTabla(List<Asignacion> asignacion) {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Ficha");
        modelo.addColumn("Programa");
        modelo.addColumn("Evaluador"); // Agregamos la columna que tendrá el botón

        for (Asignacion asignacion1 : asignacion) {
            modelo.addRow(new Object[]{
                    asignacion1.getNombre(),
                    asignacion1.getDocumento(),
                    asignacion1.getFicha(),
                    asignacion1.getPrograma(),
                    "➕" // Acción
            });
        }

        table1.setModel(modelo);
        table1.getColumn("Evaluador").setCellRenderer(new ButtonRenderer());
        table1.getColumn("Evaluador").setCellEditor(new ButtonEditor(new JCheckBox()));
    }













    public static void main(String[] args) {
        JFrame frame = new JFrame("Asignación Instructor");
        new AsignacionGUI(frame);
    }
}




