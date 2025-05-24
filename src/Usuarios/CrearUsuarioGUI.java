package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearUsuarioGUI {
    private JPanel pnlCrearUsuario;
    private JComboBox estado;
    private JTextField contacto2;
    private JTextField contacto1;
    private JTextField email;
    private JTextField num_doc;
    private JTextField nombre;
    private JComboBox rol;
    private JComboBox tipo_doc;
    private JButton confirmarButton;
    private JButton cancelar;
    private JTextField clave;
    private JTextField apellido;
    private JTextField direccion;
    private JTextField email_insti;

    public JPanel getPanel(){return pnlCrearUsuario;}

    public CrearUsuarioGUI() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear el objeto usuario con los datos del formulario
                    Usuarios_getset usuario = new Usuarios_getset(
                            rol.getSelectedIndex() + 1,
                            (String) tipo_doc.getSelectedItem(),
                            num_doc.getText(),
                            nombre.getText(),
                            apellido.getText(),
                            email.getText(),
                            email_insti.getText(),
                            direccion.getText(),
                            contacto1.getText(),
                            contacto2.getText(),
                            clave.getText(),
                            (String) estado.getSelectedItem()
                    );

                    // Llamar a DAO para agregar usuario
                    UsuariosDAO dao = new UsuariosDAO();
                    if (dao.agregarUsuario(usuario)) {
                        JOptionPane.showMessageDialog(pnlCrearUsuario, "Usuario agregado correctamente");

                        // Si el rol seleccionado es "Aprendiz", abrir interfaz AprendizGUI
                        if (rol.getSelectedItem().toString().equalsIgnoreCase("aprendiz")) {
                            JFrame aprendizFrame = new JFrame("Interfaz Aprendiz");
                            AprendizGUI aprendizGUI = new AprendizGUI();
                            aprendizFrame.setContentPane(aprendizGUI.getMainPanel()); // asegúrate que este método exista
                            aprendizFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            aprendizFrame.pack();
                            aprendizFrame.setLocationRelativeTo(null);
                            aprendizFrame.setVisible(true);
                        }

                        // Limpiar los campos
                        num_doc.setText("");
                        nombre.setText("");
                        apellido.setText("");
                        email.setText("");
                        email_insti.setText("");
                        direccion.setText("");
                        contacto1.setText("");
                        contacto2.setText("");
                        clave.setText("");
                        rol.setSelectedIndex(0);
                        tipo_doc.setSelectedIndex(0);
                        estado.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(pnlCrearUsuario, "Error al agregar usuario");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pnlCrearUsuario, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num_doc.setText("");
                nombre.setText("");
                apellido.setText("");
                email.setText("");
                email_insti.setText("");
                direccion.setText("");
                contacto1.setText("");
                contacto2.setText("");
                clave.setText("");
                rol.setSelectedIndex(0);
                tipo_doc.setSelectedIndex(0);
                estado.setSelectedIndex(0);
            }
        });
    }


    public JPanel getMainPanel() {
        return pnlCrearUsuario;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Usuarios");
        frame.setContentPane(new CrearUsuarioGUI().pnlCrearUsuario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
