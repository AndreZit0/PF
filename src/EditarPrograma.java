package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la edición de un programa existente.
 * Permite modificar el nombre y estado del programa, y guardar los cambios en la base de datos.
 */
public class EditarPrograma {

    private JPanel main;
    private JComboBox estado;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private ProgramasDAO dao = new ProgramasDAO();
    private Programas_getset programaActual;

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Constructor de la clase EditarPrograma.
     * Inicializa la interfaz gráfica y carga los datos del programa a editar.
     *
     * @param programa El objeto Programas_getset con los datos del programa a editar.
     */
    public EditarPrograma(Programas_getset programa) {
        this.programaActual = programa;
        cargarDatosPrograma();

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
     * Carga los datos del programa en los campos de la interfaz gráfica.
     * Los datos se obtienen del objeto Programas_getset pasado al constructor.
     */
    private void cargarDatosPrograma() {
        nombre.setText(programaActual.getNombre_programa());
        estado.setSelectedItem(programaActual.getEstado());
    }

    /**
     * Guarda los cambios realizados en la interfaz gráfica en la base de datos.
     * Actualiza los datos del programa utilizando el objeto ProgramasDAO.
     */
    private void guardarCambios() {
        programaActual.setNombre_programa(nombre.getText());
        programaActual.setEstado((String) estado.getSelectedItem());

        if (dao.actualizarPrograma(programaActual)) {
            JOptionPane.showMessageDialog(null, "Programa actualizado correctamente.");
            cerrarVentana();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar programa.");
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

