package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz gráfica para la edición de un usuario existente.
 * Permite modificar los datos de un usuario y guardarlos en la base de datos.
 * Si el usuario es un aprendiz, también abre la ventana para editar los datos del aprendiz.
 */
public class EditarUsuario {
    private JPanel main;
    private JComboBox<String> estado;
    private JTextField contacto2;
    private JTextField direccion;
    private JTextField contacto1;
    private JTextField email;
    private JTextField clave;
    private JTextField num_doc;
    private JTextField nombre;
    private JTextField apellido;
    private JComboBox<String> rol;
    private JComboBox<String> tipo_doc;
    private JButton confirmarButton;
    private JButton cancelar;

    private UsuariosDAO dao = new UsuariosDAO();
    private Usuarios_getset usuarioActual;

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Constructor de la clase EditarUsuario.
     * Inicializa la interfaz gráfica y carga los datos del usuario a editar.
     *
     * @param usuario El objeto Usuarios_getset con los datos del usuario a editar.
     */
    public EditarUsuario(Usuarios_getset usuario) {
        this.usuarioActual = usuario;

        // Cargar datos del usuario en los campos
        cargarDatosUsuario();

        // Acción al confirmar
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        // Acción al cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarVentana();
            }
        });
    }

    /**
     * Carga los datos del usuario en los campos de la interfaz gráfica.
     * Supone que los ComboBox ya tienen los ítems cargados.
     */
    private void cargarDatosUsuario() {
        nombre.setText(usuarioActual.getNombres());
        apellido.setText(usuarioActual.getApellidos());
        num_doc.setText(usuarioActual.getDocumento());
        tipo_doc.setSelectedItem(usuarioActual.getTipo_dc());
        rol.setSelectedIndex(usuarioActual.getID_rol() - 1);
        email.setText(usuarioActual.getEmail());
        direccion.setText(usuarioActual.getDireccion());
        contacto1.setText(usuarioActual.getContacto1());
        contacto2.setText(usuarioActual.getContacto2());
        clave.setText(usuarioActual.getClave());
        estado.setSelectedItem(usuarioActual.getEstado());
    }

    /**
     * Guarda los cambios realizados en la interfaz gráfica en la base de datos.
     * Actualiza los datos del usuario y, si es un aprendiz, abre la ventana para editar los datos del aprendiz.
     */
    private void guardarCambios() {
        // Actualizar datos
        usuarioActual.setNombres(nombre.getText());
        usuarioActual.setApellidos(apellido.getText());
        usuarioActual.setDocumento(num_doc.getText());
        usuarioActual.setTipo_dc((String) tipo_doc.getSelectedItem());
        usuarioActual.setID_rol(rol.getSelectedIndex() + 1);
        usuarioActual.setEmail(email.getText());
        usuarioActual.setDireccion(direccion.getText());
        usuarioActual.setContacto1(contacto1.getText());
        usuarioActual.setContacto2(contacto2.getText());
        usuarioActual.setClave(clave.getText());
        usuarioActual.setEstado((String) estado.getSelectedItem());

        if (dao.actualizarUsuario(usuarioActual)) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");

            cerrarVentana();

            // Buscar si ese usuario también es un aprendiz
            AprendizDAO aprendizDAO = new AprendizDAO();
            Aprendiz_getset aprendiz = aprendizDAO.obtenerAprendizPorUsuario(usuarioActual.getID_usuarios());

            if (aprendiz != null) {
                // Abrir ventana EditarAprendiz
                JFrame frameEditarAprendiz = new JFrame("Editar Aprendiz");
                EditarAprendiz editarAprendiz = new EditarAprendiz(aprendiz);
                frameEditarAprendiz.setContentPane(editarAprendiz.getMainPanel());
                frameEditarAprendiz.pack();
                frameEditarAprendiz.setLocationRelativeTo(null);
                frameEditarAprendiz.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Este usuario no está registrado como aprendiz.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
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

