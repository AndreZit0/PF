package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la edición de una modalidad existente.
 * Permite modificar el nombre de la modalidad y guardar los cambios en la base de datos.
 */
public class EditarModalidad {
    private JPanel main;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private Modalidad_getset modalidad;

    /**
     * Constructor de la clase EditarModalidad.
     * Inicializa la interfaz gráfica y carga los datos de la modalidad a editar.
     *
     * @param modalidad El objeto Modalidad_getset con los datos de la modalidad a editar.
     */
    public EditarModalidad(Modalidad_getset modalidad) {
        this.modalidad = modalidad;

        // Mostrar el valor actual en el campo de texto
        nombre.setText(modalidad.getModalidad());

        // Configuración del botón Confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevoNombre = nombre.getText().trim();

                // Validación de entrada: el nombre no puede estar vacío
                if (nuevoNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío.");
                    return;
                }

                // Actualizar el objeto Modalidad_getset con el nuevo nombre
                modalidad.setModalidad(nuevoNombre);
                // Llamar al método del DAO para actualizar la modalidad en la base de datos
                boolean actualizado = new ModalidadDAO().actualizarModalidad(modalidad);

                if (actualizado) {
                    JOptionPane.showMessageDialog(null, "Modalidad actualizada correctamente.");
                    cerrarVentana(confirmarButton); // Cerrar la ventana después de la actualización exitosa
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la modalidad.");
                }
            }
        });

        // Configuración del botón Cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana(cancelar); // Cerrar la ventana al cancelar
            }
        });
    }

    /**
     * Método para cerrar la ventana actual.
     *
     * @param component Un componente dentro de la ventana que se va a cerrar.
     */
    private void cerrarVentana(JComponent component) {
        SwingUtilities.getWindowAncestor(component).dispose();
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }
}

