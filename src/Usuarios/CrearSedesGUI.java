package Usuarios;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearSedesGUI {
    private JPanel pnlCrearSede;
    private JPanel panel1;
    private JComboBox estado;
    private JTextField direc;
    private JTextField nombre;
    private JButton confirmarButton;
    private JButton cancelar;

    public JPanel getPanel(){return pnlCrearSede;}


    public CrearSedesGUI() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto usuario con los datos del formulario
                    Sede_getset sede = new Sede_getset(
                            nombre.getText(),
                            direc.getText(),
                            (String) estado.getSelectedItem()
                    );

                    // Llamar a DAO para agregar usuario
                    SedeDAO dao = new SedeDAO();
                    if (dao.agregarSede(sede)) {
                        JOptionPane.showMessageDialog(pnlCrearSede, "Sede agregada correctamente");

                        //limpiar datos
                        nombre.setText("");
                        direc.setText("");
                        estado.setSelectedIndex(0);


                    } else {
                        JOptionPane.showMessageDialog(pnlCrearSede, "Error al agregar sede");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pnlCrearSede, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setText("");
                direc.setText("");
                estado.setSelectedIndex(0);
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Sedes");
        frame.setContentPane(new CrearSedesGUI().pnlCrearSede);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}
