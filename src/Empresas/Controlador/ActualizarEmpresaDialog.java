package Empresas.Controlador;

import Empresas.Modelo.Empresa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase representa un cuadro de diálogo para actualizar la información de una empresa.
 * Permite modificar los campos de la empresa y guardar los cambios en la base de datos.
 */
public class ActualizarEmpresaDialog extends JDialog {

    private JTextField txtNit, txtNombre, txtDireccion, txtArea, txtContacto, txtEmail, txtDepartamento, txtCiudad;
    private JButton btnGuardar;
    private Empresa empresa;
    private EmpresaDAO empresaDAO;
    private Runnable onUpdateCallback;

    /**
     * Constructor para crear el cuadro de diálogo de actualización de empresa.
     *
     * @param parent La ventana principal a la que se adjunta este diálogo.
     * @param empresa La empresa que se va a actualizar.
     * @param empresaDAO La instancia del objeto de acceso a datos de la empresa.
     * @param onUpdateCallback Un callback que se ejecutará después de actualizar la empresa.
     */
    public ActualizarEmpresaDialog(Frame parent, Empresa empresa, EmpresaDAO empresaDAO, Runnable onUpdateCallback) {
        super(parent, "Actualizar Modelo.Empresa", true);
        this.empresa = empresa;
        this.empresaDAO = empresaDAO;
        this.onUpdateCallback = onUpdateCallback;

        // Establece el diseño y el color de fondo
        setLayout(new GridLayout(9, 2));
        getContentPane().setBackground(new Color(57, 169, 0));  // Color de fondo gris claro

        // Campos de entrada con valores prellenados
        txtNit = new JTextField(empresa.getNit());
        txtNombre = new JTextField(empresa.getNombre_empresa());
        txtDireccion = new JTextField(empresa.getDireccion());
        txtArea = new JTextField(empresa.getArea());
        txtContacto = new JTextField(empresa.getContacto());
        txtEmail = new JTextField(empresa.getEmail());
        txtDepartamento = new JTextField(empresa.getDepartamento());
        txtCiudad = new JTextField(empresa.getCiudad());

        // Cambiar colores de fondo y texto de los JTextFields con colores RGB
        txtNit.setBackground(new Color(246, 246, 246));  // Un color lavanda suave
        txtNombre.setBackground(new Color(246, 246, 246));
        txtDireccion.setBackground(new Color(246, 246, 246));
        txtArea.setBackground(new Color(246, 246, 246));
        txtContacto.setBackground(new Color(246, 246, 246));
        txtEmail.setBackground(new Color(246, 246, 246));
        txtDepartamento.setBackground(new Color(246, 246, 246));
        txtCiudad.setBackground(new Color(246, 246, 246));

        // Cambiar color del texto de los JTextFields
        txtNit.setForeground(new Color(2, 6, 0));  // Negro
        txtNombre.setForeground(new Color(2, 6, 0));
        txtDireccion.setForeground(new Color(2, 6, 0));
        txtArea.setForeground(new Color(2, 6, 0));
        txtContacto.setForeground(new Color(2, 6, 0));
        txtEmail.setForeground(new Color(2, 6, 0));
        txtDepartamento.setForeground(new Color(2, 6, 0));
        txtCiudad.setForeground(new Color(2, 6, 0));

        // Configurar el botón de guardar cambios
        btnGuardar = new JButton("Guardar Cambios");
        // Cambiar color del botón con RGB
        btnGuardar.setBackground(new Color(40, 120, 0));  // SteelBlue
        btnGuardar.setForeground(new Color(255, 255, 255));  // Blanco

        // Etiquetas para los campos de texto
        JLabel labelNit = new JLabel("NIT:");
        labelNit.setForeground(new Color(255, 255, 255));  // Blanco
        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setForeground(new Color(255, 255, 255));
        JLabel labelDireccion = new JLabel("Dirección:");
        labelDireccion.setForeground(new Color(255, 255, 255));
        JLabel labelArea = new JLabel("Área:");
        labelArea.setForeground(new Color(255, 255, 255));
        JLabel labelContacto = new JLabel("Contacto:");
        labelContacto.setForeground(new Color(255, 255, 255));
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setForeground(new Color(255, 255, 255));
        JLabel labelDepartamento = new JLabel("Departamento:");
        labelDepartamento.setForeground(new Color(255, 255, 255));
        JLabel labelCiudad = new JLabel("Ciudad:");
        labelCiudad.setForeground(new Color(255, 255, 255));

        // Agregar componentes a la interfaz
        add(labelNit); add(txtNit);
        add(labelNombre); add(txtNombre);
        add(labelDireccion); add(txtDireccion);
        add(labelArea); add(txtArea);
        add(labelContacto); add(txtContacto);
        add(labelEmail); add(txtEmail);
        add(labelDepartamento); add(txtDepartamento);
        add(labelCiudad); add(txtCiudad);
        add(new JLabel("")); add(btnGuardar); // Botón alineado

        // Listener del botón de guardar
        btnGuardar.addActionListener(new ActionListener() {
            /**
             * Este método se ejecuta cuando se hace clic en el botón de guardar.
             * Actualiza los datos de la empresa con los valores ingresados en los campos de texto.
             * Luego, se guarda la empresa actualizada en la base de datos.
             *
             * @param e El evento de acción que ocurre al hacer clic en el botón de guardar.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();

                // Validar que el correo termine en "@gmail.com"
                if (!email.endsWith("@gmail.com")) {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "El correo debe ser de tipo '@gmail.com'");
                    return; // No continúa con la actualización si la validación falla
                }

                // Actualizar los datos de la empresa con los valores de los campos de texto
                empresa.setNit(txtNit.getText());
                empresa.setNombre_empresa(txtNombre.getText());
                empresa.setDireccion(txtDireccion.getText());
                empresa.setArea(txtArea.getText());
                empresa.setContacto(txtContacto.getText());
                empresa.setEmail(email); // Establecer el correo validado
                empresa.setDepartamento(txtDepartamento.getText());
                empresa.setCiudad(txtCiudad.getText());

                // Intentar actualizar la empresa en la base de datos
                if (empresaDAO.actualizarEmpresa(empresa)) {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "Actualización exitosa");
                    dispose(); // Cierra el diálogo
                    if (onUpdateCallback != null) onUpdateCallback.run(); // Ejecuta callback si existe
                } else {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "Error al actualizar");
                }
            }
        });

        pack(); // Ajusta el tamaño del diálogo
        setLocationRelativeTo(parent); // Centra el diálogo sobre la ventana padre
    }
}
