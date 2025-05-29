package Usuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField email_insti;

    private UsuariosDAO dao = new UsuariosDAO();
    private Usuarios_getset usuarioActual;

    public JPanel getMainPanel() {
        return main;
    }

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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                frame.dispose();
            }
        });
    }

    private void cargarDatosUsuario() {
        // Supone que tus ComboBox ya tienen los ítems cargados (ej: en el constructor o en otro lugar)
        nombre.setText(usuarioActual.getNombres());
        apellido.setText(usuarioActual.getApellidos());
        num_doc.setText(usuarioActual.getDocumento());
        tipo_doc.setSelectedItem(usuarioActual.getTipo_dc());
        rol.setSelectedIndex(usuarioActual.getID_rol() - 1); // o con nombre si tienes un mapa
        email.setText(usuarioActual.getEmail());
        email_insti.setText(usuarioActual.getEmail_insti());  // Cargar email institucional
        direccion.setText(usuarioActual.getDireccion());
        contacto1.setText(usuarioActual.getContacto1());
        contacto2.setText(usuarioActual.getContacto2());
        clave.setText(usuarioActual.getClave());
        estado.setSelectedItem(usuarioActual.getEstado());
    }

    private void guardarCambios() {
        // Actualizar datos
        usuarioActual.setNombres(nombre.getText());
        usuarioActual.setApellidos(apellido.getText());
        usuarioActual.setDocumento(num_doc.getText());
        usuarioActual.setTipo_dc((String) tipo_doc.getSelectedItem());
        usuarioActual.setID_rol(rol.getSelectedIndex() + 1);
        usuarioActual.setEmail(email.getText());
        usuarioActual.setEmail_insti(email_insti.getText());  // Actualizar email institucional
        usuarioActual.setDireccion(direccion.getText());
        usuarioActual.setContacto1(contacto1.getText());
        usuarioActual.setContacto2(contacto2.getText());
        usuarioActual.setClave(clave.getText());
        usuarioActual.setEstado((String) estado.getSelectedItem());

        if (dao.actualizarUsuario(usuarioActual)) {
            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");

            // Cerrar la ventana actual
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
            frame.dispose();

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
                //JOptionPane.showMessageDialog(null, "Este usuario no está registrado como aprendiz.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario.");
        }
    }

}
