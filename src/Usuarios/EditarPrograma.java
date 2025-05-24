package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarPrograma {

    private JPanel main;
    private JComboBox estado;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    private ProgramasDAO dao = new ProgramasDAO();
    private Programas_getset programaActual;

    public JPanel getMainPanel(){
        return main;
    }

    public EditarPrograma(Programas_getset programa){
        this.programaActual = programa;

        cargarDatosPrograma();

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
    private void cargarDatosPrograma(){
        nombre.setText(programaActual.getNombre_programa());
        estado.setSelectedItem(programaActual.getEstado());
    }
    private void guardarCambios(){
        programaActual.setNombre_programa(nombre.getText());
        programaActual.setEstado((String) estado.getSelectedItem());

        if(dao.actualizarPrograma(programaActual)){
            JOptionPane.showMessageDialog(null, "Programa actualizada correctamente.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar programa.");
        }
    }
}
