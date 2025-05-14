package src;

import javax.swing.*;

/**
 * Clase que representa la interfaz gráfica para la creación de una nueva empresa.
 * Permite ingresar el nombre y otros datos de la empresa a través de campos de texto.
 */
public class CrearEmpresa {
    private JPanel main;
    private JTextField nombre;
    private JTextField textField1;
    private JButton confirmarButton;
    private JButton cancelar;
    private JTextField textField2;

    /**
     * Método principal para iniciar la aplicación de creación de empresas.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Empresa");
        frame.setContentPane(new CrearEmpresa().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
