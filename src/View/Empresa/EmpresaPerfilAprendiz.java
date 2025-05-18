package View.Empresa;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmpresaPerfilAprendiz {

    private JPanel main;
    private JButton visualizarPerfilButton;
    private JButton XYZButton;
    private JButton button1;
    private JFrame frame;

    public EmpresaPerfilAprendiz() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalAprendizEmpresa aprendizEmpresa = new modalAprendizEmpresa();
                aprendizEmpresa.main();
            }
        });
    }

    public void main() {

        JFrame frame = new JFrame("SAEP");
        frame.setContentPane(this.main);
        ///frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

    }
}
