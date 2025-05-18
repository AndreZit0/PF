package View.Instructor;

import javax.swing.*;

public class modalAprendizInstructor {
    private JPanel main;
    private JTextField textField1;

    public static void main(String[] args) {
        modalAprendizInstructor modal = new modalAprendizInstructor();
        JFrame frame = new JFrame();
        frame.setContentPane(new modalAprendizInstructor().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,700);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

