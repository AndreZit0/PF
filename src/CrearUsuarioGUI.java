package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la creación de un nuevo usuario.
 * Permite ingresar y guardar la información de un usuario en la base de datos,
 * y opcionalmente abrir la interfaz de aprendiz si el rol seleccionado es "Aprendiz".
 */
public class CrearUsuarioGUI {
    private JPanel main;
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

    /**
     * Constructor de la clase CrearUsuarioGUI.
     * Inicializa los componentes de la interfaz gráfica y configura los listeners
     * para los botones Confirmar y Cancelar.
     */
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
                            direccion.getText(),
                            contacto1.getText(),
                            contacto2.getText(),
                            clave.getText(),
                            (String) estado.getSelectedItem()
                    );

                    // Llamar a DAO para agregar usuario
                    UsuariosDAO dao = new UsuariosDAO();
                    if (dao.agregarUsuario(usuario)) {
                        JOptionPane.showMessageDialog(main, "Usuario agregado correctamente");

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
                        direccion.setText("");
                        contacto1.setText("");
                        contacto2.setText("");
                        clave.setText("");
                        rol.setSelectedIndex(0);
                        tipo_doc.setSelectedIndex(0);
                        estado.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar usuario");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar los campos del formulario
                num_doc.setText("");
                nombre.setText("");
                apellido.setText("");
                email.setText("");
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

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Método principal para iniciar la aplicación de creación de usuarios.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Creación de Usuarios");
        frame.setContentPane(new CrearUsuarioGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}

