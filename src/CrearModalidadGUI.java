package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la creación de una nueva modalidad.
 * Permite ingresar el nombre de la modalidad y guardarlo en la base de datos.
 */
public class CrearModalidadGUI {
    private JPanel main;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    /**
     * Constructor de la clase CrearModalidadGUI.
     * Inicializa los componentes de la interfaz gráfica y configura los listeners
     * para los botones Confirmar y Cancelar.
     */
    public CrearModalidadGUI() {
        // Acción del botón Confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto modalidad con los datos del formulario
                    Modalidad_getset modalidad = new Modalidad_getset(
                            nombre.getText()
                    );

                    // Llamar a DAO para agregar modalidad
                    ModalidadDAO dao = new ModalidadDAO();
                    if (dao.agregarModalidad(modalidad)) {
                        JOptionPane.showMessageDialog(main, "Modalidad agregada correctamente");

                        // Limpiar el campo de texto
                        CrearModalidadGUI.this.nombre.setText("");
                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar modalidad");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón Cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el campo de texto cuando se cancela
                nombre.setText("");
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación de creación de modalidades.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        // Configuración de la ventana de la interfaz
        JFrame frame = new JFrame("Creación de Modalidad");
        frame.setContentPane(new CrearModalidadGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }

    /**
     * Método para obtener el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }
}

