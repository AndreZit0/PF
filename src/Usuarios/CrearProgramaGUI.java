package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearProgramaGUI {
    private JPanel pnlCrearPrograma;
    private JComboBox estado;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    public JPanel getPanel(){return pnlCrearPrograma;}

    public CrearProgramaGUI() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Programas_getset programas = new Programas_getset(
                            nombre.getText(),
                            (String) estado.getSelectedItem()
                    );
                    ProgramasDAO dao = new ProgramasDAO();
                    if (dao.agregarPrograma(programas)){
                        JOptionPane.showMessageDialog(pnlCrearPrograma,"Programa agregado correctamente");

                        // limpiar datos
                        nombre.setText("");
                        estado.setSelectedIndex(0);
                    }
                    else{
                        JOptionPane.showMessageDialog(pnlCrearPrograma,"Error al agregar Programa");
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(pnlCrearPrograma,"Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setText("");
                estado.setSelectedIndex(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Programas");
        frame.setContentPane(new CrearProgramaGUI().pnlCrearPrograma);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
