package View.Aprendiz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Aprendiz {

    private JPanel main;
    private JButton visualizarPerfilButton;
    private JButton XYZButton;
    private JButton button1;
    private JFrame frame;

    public Aprendiz() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalPerfilAprendiz aprendiz = new modalPerfilAprendiz();
                aprendiz.main();
            }
        });
    }

    public static void  main(String[]srgs) {
        Aprendiz aprendiz = new Aprendiz();
        JFrame frame = new JFrame("SAEP");
        frame.setContentPane(aprendiz.main);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

    }
}
