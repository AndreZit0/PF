package AsignacionInstructor;

import Conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class GUIEvaluador {
    private JTable table1;
    private JPanel main;
    private JTextField textField1;
    private JButton button1;
    private JTextField textField2;
    private JButton asignarButton;
    int filas;


    public GUIEvaluador(){
        listaContacto();


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                int selectFilas = table1.getSelectedRow();
                int columnas = table1.getColumnCount();

                if (selectFilas >= 0) {
                    if (columnas == 1) {
                        // Cuando estás viendo solo el nombre del evaluador
                        textField2.setText((String) table1.getValueAt(selectFilas, 0));
                    } else {
                        // Cuando estás viendo toda la tabla completa
                        textField2.setText((String) table1.getValueAt(selectFilas, 5));
                    }

                    filas = selectFilas;
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBuscado = textField1.getText().trim();
                if (!nombreBuscado.isEmpty()) {
                    buscarPorNombre(nombreBuscado);
                } else {
                    listaContacto(); // Si no escribió nada, mostrar todo
                }
            }
        });
    }
    public void buscarPorNombre(String nombre) {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);

        modeloa.addColumn("Nombre del Evaluador"); // Solo una columna

        table1.setModel(modeloa);
        table1.setRowHeight(30);

        Connection con = Conexion.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT evaluador FROM asignacion WHERE evaluador LIKE ?");
            ps.setString(1, "%" + nombre + "%"); // búsqueda flexible
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] fila = new String[1];
                fila[0] = rs.getString("evaluador");
                modeloa.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void listaContacto()
    {
        NonEditableTableModel modeloa = new NonEditableTableModel();
        table1.setDefaultEditor(Object.class, null);


        modeloa.addColumn("id_asignacion"); // la vamos a ocultar
        modeloa.addColumn("Nombre De Los Evaluadores");
        modeloa.addColumn("documento");
        modeloa.addColumn("ficha");
        modeloa.addColumn("programa");
        modeloa.addColumn("evaluador");
        modeloa.addColumn("fecha_inicial");
        modeloa.addColumn("fecha_fin");

        table1.setModel(modeloa);
        table1.setRowHeight(30); // Altura de las filas

        String[] dato = new String[8];

        Connection con = Conexion.getConnection();

        try
        {
            table1.getColumnModel().getColumn(0).setMinWidth(0);
            table1.getColumnModel().getColumn(0).setMaxWidth(0);
            table1.getColumnModel().getColumn(0).setWidth(0);

            table1.getColumnModel().getColumn(1).setMinWidth(0);
            table1.getColumnModel().getColumn(1).setMaxWidth(0);
            table1.getColumnModel().getColumn(1).setWidth(0);

            table1.getColumnModel().getColumn(2).setMinWidth(0);
            table1.getColumnModel().getColumn(2).setMaxWidth(0);
            table1.getColumnModel().getColumn(2).setWidth(0);

            table1.getColumnModel().getColumn(3).setMinWidth(0);
            table1.getColumnModel().getColumn(3).setMaxWidth(0);
            table1.getColumnModel().getColumn(3).setWidth(0);

            table1.getColumnModel().getColumn(4).setMinWidth(0);
            table1.getColumnModel().getColumn(4).setMaxWidth(0);
            table1.getColumnModel().getColumn(4).setWidth(0);


            table1.getColumnModel().getColumn(6).setMinWidth(0);
            table1.getColumnModel().getColumn(6).setMaxWidth(0);
            table1.getColumnModel().getColumn(6).setWidth(0);

            table1.getColumnModel().getColumn(7).setMinWidth(0);
            table1.getColumnModel().getColumn(7).setMaxWidth(0);
            table1.getColumnModel().getColumn(7).setWidth(0);


            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM asignacion");


            while (rs.next())
            {

                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = rs.getString(6);
                dato[6] = rs.getString(7);
                dato[7] = rs.getString(8);

                modeloa.addRow(dato);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public class NonEditableTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }





    public  void ejecutar() {



        JFrame frame = new JFrame("Evaluador");
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 400);
        frame.setResizable(false);
        frame.setVisible(true);
    }



}
