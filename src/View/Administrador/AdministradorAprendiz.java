package View.Administrador;

import View.Aprendiz.Aprendiz;
import View.Aprendiz.modalPerfilAprendiz;
import View.Empresa.modalAprendizEmpresa;
import View.GestionPantalla;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdministradorAprendiz {
    private JPanel main;
    private JButton verUsuariosButton;
    private JButton crearUsuarioButton;
    private JButton miPerfilButton;
    private JTable table1;
    private JTextField textField1;
    private JButton button1;

    private JFrame frame;


    public AdministradorAprendiz() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalPerfilAprendiz Administrador = new modalPerfilAprendiz();
                Administrador.main();
            }
        });
    }

    private void cargarTabla() {
        Object[][] datos = {
                {"C.C", "111701673", "Cesar Angulo",  "3012534945", "👁", "👤"},
                {"C.C", "111701573", "Maikol Castañeda", "3012534844", "👁", "👤"},
                {"C.C", "111701473", "Miguel Medina", "3012534743", "👁", "👤"},
        };

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Tipo");
        modelo.addColumn("Número");
        modelo.addColumn("Nombre");
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

    public void main (){
        JFrame frame = new JFrame("SAEP");
        AdministradorAprendiz administradorAprendiz = new AdministradorAprendiz(); // Se pasa el frame al constructor de Menu
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

    }
}
