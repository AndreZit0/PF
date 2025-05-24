package Usuarios;

import javax.swing.*;

public class CrearEmpresa {
    private JPanel pnlCrearEmpresa;
    private JTextField nombre;
    private JTextField textField1;
    private JButton confirmarButton;
    private JButton cancelar;
    private JTextField textField2;

    public JPanel getPanel(){return pnlCrearEmpresa;}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Empresa");
        frame.setContentPane(new CrearEmpresa().pnlCrearEmpresa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
