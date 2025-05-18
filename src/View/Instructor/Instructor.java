package View.Instructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Instructor {
    private JPanel main;
    private JTable table1;
    private JButton button1;

    public Instructor() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalPerfilInstructor perfilInstructor = new modalPerfilInstructor();
                perfilInstructor.main();
            }
        });
    }

    private void cargarTabla() {
        Object[][] datos = {
                {"C.C", "111701673", "Cesar Angulo", "Colombina S.A.S", "3012534945", "👁", "👤"},
                {"C.C", "111701573", "Maikol Castañeda", "Ariel Motors S.A.S", "3012534844", "👁", "👤"},
                {"C.C", "111701473", "Miguel Medina", "IAS SOFTWARE S.A.S", "3012534743", "👁", "👤"},
        };

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Tipo");
        modelo.addColumn("Número");
        modelo.addColumn("Nombre");
        modelo.addColumn("Empresa");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Visualizar");
        modelo.addColumn("Perfil");

        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }

        table1.setModel(modelo);
        table1.setRowHeight(36  ); // Altura de las filas
        modelo.setRowCount(11); // Altura de las filas
        table1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));


    }
    

    public void main() {
        JFrame frame = new JFrame("Instructor");
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        cargarTabla();

    }
}