package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarSede {
    private JPanel main;
    private JComboBox estado;
    private JTextField direc;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private SedeDAO dao = new SedeDAO();
    private Sede_getset sedeActual;

    public JPanel getMainPanel() {
        return main;
    }

    public EditarSede(Sede_getset sede) {
        this.sedeActual = sede;

        cargarDatosSedes();

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                frame.dispose();
            }
        });
    }
    private void cargarDatosSedes(){
        nombre.setText(sedeActual.getNombre_sede());
        direc.setText(sedeActual.getDireccion());
        estado.setSelectedItem(sedeActual.getEstado());
    }
    private void guardarCambios(){
        sedeActual.setNombre_sede(nombre.getText());
        sedeActual.setDireccion(direc.getText());
        sedeActual.setEstado((String) estado.getSelectedItem());

        // Guardar en base de datos
        if (dao.actualizarSede(sedeActual)) {
            JOptionPane.showMessageDialog(null, "Sede actualizada correctamente.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar sede.");
        }
    }

}
