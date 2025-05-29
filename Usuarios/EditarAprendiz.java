package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarAprendiz {
    private JPanel main;
    private JComboBox estado;
    private JComboBox aprendiz;
    private JComboBox ficha;
    private JComboBox empresa;
    private JComboBox evaluador;
    private JComboBox modalidad;
    private JButton confirmarButton;
    private JButton cancelar;

    private AprendizDAO dao = new AprendizDAO();
    private Aprendiz_getset aprendizActual;

    public JPanel getMainPanel(){
        return main;
    }

    public EditarAprendiz(Aprendiz_getset aprendiz) {
        this.aprendizActual = aprendiz;
        cargarDatosAprendiz();

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
    private void cargarDatosAprendiz(){
        aprendiz.setSelectedIndex(aprendizActual.getID_usuarios()-1);
        ficha.setSelectedIndex(aprendizActual.getID_Fichas()-1);
        empresa.setSelectedIndex(aprendizActual.getID_empresas()-1);
        evaluador.setSelectedIndex(aprendizActual.getID_instructor()-1);
        modalidad.setSelectedIndex(aprendizActual.getID_modalidad()-1);
        estado.setSelectedItem(aprendizActual.getEstado());
    }

    private void guardarCambios(){
        aprendizActual.setID_usuarios(aprendiz.getSelectedIndex()+1);
        aprendizActual.setID_Fichas(ficha.getSelectedIndex()+1);
        aprendizActual.setID_empresas(empresa.getSelectedIndex()+1);
        aprendizActual.setID_instructor(evaluador.getSelectedIndex()+1);
        aprendizActual.setID_modalidad(modalidad.getSelectedIndex()+1);
        aprendizActual.setEstado((String) estado.getSelectedItem());

        if (dao.actualizarAprendiz(aprendizActual)) {
            JOptionPane.showMessageDialog(null, "Aprendiz actualizado correctamente.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar aprendiz.");
        }
    }
}
