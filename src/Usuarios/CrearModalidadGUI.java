package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearModalidadGUI {
    private JPanel pnlCrearModalidad;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    public JPanel getPanel(){return pnlCrearModalidad;}

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
                        JOptionPane.showMessageDialog(pnlCrearModalidad, "Modalidad agregada correctamente");

                        // Limpiar el campo de texto
                        CrearModalidadGUI.this.nombre.setText("");
                    } else {
                        JOptionPane.showMessageDialog(pnlCrearModalidad, "Error al agregar modalidad");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pnlCrearModalidad, "Error en los datos ingresados");
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

    public static void main(String[] args) {
        // Configuración de la ventana de la interfaz
        JFrame frame = new JFrame("Creación de Modalidad");
        frame.setContentPane(new CrearModalidadGUI().pnlCrearModalidad);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }

    // Método para obtener el panel principal
    public JPanel getMainPanel() {
        return pnlCrearModalidad;
    }
}
