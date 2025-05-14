package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la edición de una sede existente.
 * Permite modificar el nombre, dirección y estado de la sede, y guardar los cambios en la base de datos.
 */
public class EditarSede {
    private JPanel main;
    private JComboBox estado;
    private JTextField direc;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private SedeDAO dao = new SedeDAO();
    private Sede_getset sedeActual;

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Constructor de la clase EditarSede.
     * Inicializa la interfaz gráfica y carga los datos de la sede a editar.
     *
     * @param sede El objeto Sede_getset con los datos de la sede a editar.
     */
    public EditarSede(Sede_getset sede) {
        this.sedeActual = sede;
        cargarDatosSedes();

        // Configuración del botón Confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        // Configuración del botón Cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });
    }

    /**
     * Carga los datos de la sede en los campos de la interfaz gráfica.
     * Los datos se obtienen del objeto Sede_getset pasado al constructor.
     */
    private void cargarDatosSedes() {
        nombre.setText(sedeActual.getNombre_sede());
        direc.setText(sedeActual.getDireccion());
        estado.setSelectedItem(sedeActual.getEstado());
    }

    /**
     * Guarda los cambios realizados en la interfaz gráfica en la base de datos.
     * Actualiza los datos de la sede utilizando el objeto SedeDAO.
     */
    private void guardarCambios() {
        sedeActual.setNombre_sede(nombre.getText());
        sedeActual.setDireccion(direc.getText());
        sedeActual.setEstado((String) estado.getSelectedItem());

        // Guardar en base de datos
        if (dao.actualizarSede(sedeActual)) {
            JOptionPane.showMessageDialog(null, "Sede actualizada correctamente.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar sede.");
        }
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
        frame.dispose();
    }
}

