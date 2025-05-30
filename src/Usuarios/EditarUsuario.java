package Usuarios;

import Example_Screen.View.VisualizarPerfilGUI;
import Example_Screen.View.Login.LoginGUI;


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
    private JComboBox estado_formacion;
    private JLabel estad;

    private UsuariosDAO dao = new UsuariosDAO();
    private Usuarios_getset usuarioActual;
    private boolean esEvaluador = false;


    public JPanel getMainPanel() {
        return main;
    }

    public EditarUsuario(Usuarios_getset usuario) {
        this.usuarioActual = usuario;

        this.esEvaluador = "2".equals(LoginGUI.cofigBotonInicioSegunRol);


        // Cargar datos del usuario en los campos
        cargarDatosUsuario();
        cargarEstadoFormacion();

        if ("Aprendiz".equals(rol.getSelectedItem().toString())) {
            estad.setVisible(true);
            estado_formacion.setVisible(true);
        } else {
            estad.setVisible(false);
            estado_formacion.setVisible(false);
        }

        if (esEvaluador) {
            configurarPermisoEvaluador();
        }

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

    /**
     * Configurar la pantalla para que solo el evaluador pueda editar el estado de formación
     */

    private void configurarPermisoEvaluador() {
        // Deshabilitar todos los campos de texto
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        num_doc.setEnabled(false);
        email.setEnabled(false);
        email_insti.setEnabled(false);
        direccion.setEnabled(false);
        contacto1.setEnabled(false);
        contacto2.setEnabled(false);
        clave.setEnabled(false);

        // Deshabilitar todos los ComboBox excepto estado_formacion
        tipo_doc.setEnabled(false);
        rol.setEnabled(false);
        estado.setEnabled(false);

        // Solo habilitar estado_formacion si el usuario que se está editando es un Aprendiz
        if (usuarioActual.getID_rol() == 1) { // 1 = Aprendiz
            estado_formacion.setEnabled(true);
            estad.setVisible(true);
            estado_formacion.setVisible(true);
        } else {
            estado_formacion.setEnabled(false);
            JOptionPane.showMessageDialog(null,
                    "Solo puede editar el estado de formación de usuarios con rol Aprendiz.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
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

        if (esEvaluador) {
            // Si es evaluador, solo actualizar el estado de formación
            String nuevoEstadoFormacion = (String) estado_formacion.getSelectedItem();

            if (usuarioActual.getID_rol() == 1 && nuevoEstadoFormacion != null) { // Solo si es Aprendiz
                AprendizDAO aprendizDAO = new AprendizDAO();
                if (aprendizDAO.actualizarEstadoFormacion(usuarioActual.getID_usuarios(), nuevoEstadoFormacion)) {
                    JOptionPane.showMessageDialog(null, "Estado de formación actualizado correctamente.");

                    // Cerrar la ventana actual
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el estado de formación.");
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Solo puede actualizar el estado de formación de usuarios Aprendiz.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else{
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

            String nuevoEstadoFormacion = (String) estado_formacion.getSelectedItem();
            if (dao.actualizarUsuario(usuarioActual)) {
                AprendizDAO aprendizDAO1 = new AprendizDAO();
                aprendizDAO1.actualizarEstadoFormacion(usuarioActual.getID_usuarios(), nuevoEstadoFormacion);
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
    private void cargarEstadoFormacion() {
        AprendizDAO aprendizDAO = new AprendizDAO();
        String estado = aprendizDAO.obtenerEstadoFormacionPorUsuario(usuarioActual.getID_usuarios());


        if (estado != null) {
            estado_formacion.setSelectedItem(estado);
        }
    }

}