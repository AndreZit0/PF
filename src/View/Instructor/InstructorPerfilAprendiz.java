package View.Instructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InstructorPerfilAprendiz {
    private JPanel main;
    private JButton visualizarPerfilButton;
    private JButton XYZButton;
    private JButton button1;


    public InstructorPerfilAprendiz() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modalPerfilInstructor perfilInstructor = new modalPerfilInstructor();
                perfilInstructor.main();
            }
        });
    }

    public void main() {

        JFrame frame = new JFrame("Instructor");
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }
}


