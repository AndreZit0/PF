package View.Instructor;

import javax.swing.*;

public class modalPerfilInstructor {
    private JPanel main;
    private JButton confirmarButton;
    private JTextField textField1;

    public void main() {
        JFrame frame = new JFrame();
        frame.setContentPane(this.main);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,700);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
