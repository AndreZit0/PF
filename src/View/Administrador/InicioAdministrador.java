package View.Administrador;

import View.Aprendiz.Aprendiz;
import View.Aprendiz.modalPerfilAprendiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InicioAdministrador {
    private JPanel main;
    private JButton verUsuariosButton;
    private JButton crearUsuarioButton;
    private JButton miPerfilButton;
    private JButton button1;

    private JFrame frame;

    public InicioAdministrador() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalPerfilAprendiz aprendiz = new modalPerfilAprendiz();
                aprendiz.main();
            }
        });
    }

    public void main() {
        JFrame frame = new JFrame("SAEP");
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

    }
}
