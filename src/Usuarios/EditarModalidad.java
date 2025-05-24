package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarModalidad {
    private JPanel main;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private Modalidad_getset modalidad;

    public EditarModalidad(Modalidad_getset modalidad) {
        this.modalidad = modalidad;

        // Mostrar el valor actual
        nombre.setText(modalidad.getModalidad());

        // Botón confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = nombre.getText().trim();

                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                    return;
                }

                modalidad.setModalidad(nuevoNombre);
                boolean actualizado = new ModalidadDAO().actualizarModalidad(modalidad);

                if (actualizado) {
                    JOptionPane.showMessageDialog(null, "Modalidad actualizada correctamente.");
                    cerrarVentana(confirmarButton);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la modalidad.");
                }
            }
        });

        // Botón cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana(cancelar);
            }
        });
    }

    private void cerrarVentana(JComponent component) {
        SwingUtilities.getWindowAncestor(component).dispose();
    }

    public JPanel getMainPanel() {
        return main;
    }
}
