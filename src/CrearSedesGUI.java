package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la creación de una nueva sede.
 * Permite ingresar el nombre, dirección y estado de la sede, y guardarlos en la base de datos.
 */
public class CrearSedesGUI {
    private JPanel main;
    private JPanel panel1;
    private JComboBox estado;
    private JTextField direc;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    /**
     * Constructor de la clase CrearSedesGUI.
     * Inicializa los componentes de la interfaz gráfica y configura los listeners
     * para los botones Confirmar y Cancelar.
     */
    public CrearSedesGUI() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto Sede_getset con los datos del formulario
                    Sede_getset sede = new Sede_getset(
                            nombre.getText(),
                            direc.getText(),
                            (String) estado.getSelectedItem()
                    );

                    // Llamar a DAO para agregar sede
                    SedeDAO dao = new SedeDAO();
                    if (dao.agregarSede(sede)) {
                        JOptionPane.showMessageDialog(main, "Sede agregada correctamente");

                        //limpiar datos
                        nombre.setText("");
                        direc.setText("");
                        estado.setSelectedIndex(0);


                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar sede");
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
                direc.setText("");
                estado.setSelectedIndex(0);
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación de creación de sedes.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Sedes");
        frame.setContentPane(new CrearSedesGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
