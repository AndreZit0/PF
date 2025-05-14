package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la creación de un nuevo programa.
 * Permite ingresar el nombre del programa y su estado, y guardarlos en la base de datos.
 */
public class CrearProgramaGUI {
    private JPanel main;
    private JComboBox estado;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    /**
     * Constructor de la clase CrearProgramaGUI.
     * Inicializa los componentes de la interfaz gráfica y configura los listeners
     * para los botones Confirmar y Cancelar.
     */
    public CrearProgramaGUI() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto Programas_getset con los datos del formulario
                    Programas_getset programas = new Programas_getset(
                            nombre.getText(),
                            (String) estado.getSelectedItem()
                    );
                    // Llamar a DAO para agregar programa
                    ProgramasDAO dao = new ProgramasDAO();
                    if (dao.agregarPrograma(programas)) {
                        JOptionPane.showMessageDialog(main, "Programa agregado correctamente");

                        // limpiar datos
                        nombre.setText("");
                        estado.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar Programa");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar los campos del formulario
                nombre.setText("");
                estado.setSelectedIndex(0);
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación de creación de programas.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Programas");
        frame.setContentPane(new CrearProgramaGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
