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


public class AsignacionGUI {
    private JPanel main;
    private JButton volverButton;
    private JButton asignarButton;
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
        showdata();

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

        // Editor con ComboBox (cuando haces clic)
        table1.getColumnModel().getColumn(4).setCellEditor(new EstadoCellEditor());
        // Renderizador con ComboBox visible siempre
        table1.getColumnModel().getColumn(4).setCellRenderer(new ComboBoxCellRenderer());

        String[] dato = new String[7];
        Connection con = conexion.getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, documento, ficha, programa, evaluador, fecha_inicial, fecha_final FROM asignacion");

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);

                modelo.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    class EstadoCellEditor extends DefaultCellEditor {
        public EstadoCellEditor() {
            super(new JComboBox<>(new String[]{"Juan Pérez", "María López", "Carlos Ruiz"}));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JComboBox<String> comboBox = (JComboBox<String>) getComponent();
            comboBox.setSelectedItem(value);
            return comboBox;
        }
    }



    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4; // Solo columna "Evaluador" editable
        }
    }

    class ComboBoxCellRenderer extends JComboBox<String> implements TableCellRenderer {

        public ComboBoxCellRenderer() {
            super(new String[]{"Juan Pérez", "María López", "Carlos Ruiz"}); // Tus evaluadores aquí
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setSelectedItem(value);
            return this;
        }
    }

    /************************************************************************************************************/

    public java.util.List<Asignacion> buscarAprendiz(String terminoBusqueda) throws SQLException {

        PreparedStatement consulta = null;
        ResultSet resultado = null;
        java.util.List<Asignacion> asignacion = new ArrayList<>();

        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT * FROM asignacion WHERE documento LIKE ?";
            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%"); // Búsqueda parcial
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Asignacion asg = new Asignacion();
                asg.setId_asignacion(resultado.getInt("id_asignacion"));
                asg.setNombre(resultado.getString("nombre"));
                asg.setDocumento(resultado.getString("documento"));
                asg.setFicha(resultado.getString("ficha"));
                asg.setPrograma(resultado.getString("programa"));
                asg.setEvaluador(resultado.getString("evaluador"));
                asg.setFecha_inicial(resultado.getString("fecha_inicial"));
                asg.setFecha_final(resultado.getString("fecha_final"));

                asignacion.add(asg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return asignacion;
    }


    public java.util.List<Asignacion> buscarFicha(String terminoBusqueda) throws SQLException {
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        java.util.List<Asignacion> asignacion = new ArrayList<>();

        try {
            Connection con = conexion.getConnection();
            String sql = "SELECT * FROM asignacion WHERE ficha LIKE ?";
            consulta = con.prepareStatement(sql);
            consulta.setString(1, "%" + terminoBusqueda + "%"); // Búsqueda parcial
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Asignacion asg = new Asignacion();
                asg.setId_asignacion(resultado.getInt("id_asignacion"));
                asg.setNombre(resultado.getString("nombre"));
                asg.setDocumento(resultado.getString("documento"));
                asg.setFicha(resultado.getString("ficha"));
                asg.setPrograma(resultado.getString("programa"));
                asg.setEvaluador(resultado.getString("evaluador"));
                asg.setFecha_inicial(resultado.getString("fecha_inicial"));
                asg.setFecha_final(resultado.getString("fecha_final"));

                asignacion.add(asg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return asignacion;
    }


    public void actualizarTabla(java.util.List<Asignacion> asignacion) {
        DefaultTableModel modelo = new DefaultTableModel();

        // Definir las columnas
        modelo.addColumn("Nombre");
        modelo.addColumn("Documento");
        modelo.addColumn("Ficha");
        modelo.addColumn("Programa");
        modelo.addColumn("Evaluador");
        modelo.addColumn("Fecha Inicial");
        modelo.addColumn("Fecha Final");

        for (Asignacion asignacion1 : asignacion) {
            Object[] fila = {
                    asignacion1.getNombre(),
                    asignacion1.getDocumento(),
                    asignacion1.getFicha(),
                    asignacion1.getPrograma(),
                    asignacion1.getEvaluador(),
                    asignacion1.getFecha_inicial(),
                    asignacion1.getFecha_final(),
            };
            modelo.addRow(fila);
        }

        // Establecer el modelo en la tabla
        table1.setModel(modelo);
    }







    public static void main(String[] args) {
        JFrame frame = new JFrame("Asignación Instructor");
        new AsignacionGUI(frame);
    }
}




