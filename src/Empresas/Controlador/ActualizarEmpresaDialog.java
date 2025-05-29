package Empresas.Controlador;

import Empresas.Controlador.EmpresaDAO;
import Empresas.Modelo.Empresa;
import Empresas.ConexionBD.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Esta clase representa el cuadro de diálogo para actualizar los datos de una empresa.
 * Permite al usuario modificar diversos campos de la empresa y guardar los cambios.
 */
public class ActualizarEmpresaDialog extends JDialog {

    private JTextField txtNit, txtNombre, txtDireccion, txtArea, txtContacto, txtEmail, txtCiudad;
    private JComboBox<String> comboDepartamento;
    private JButton btnGuardar;
    private Empresa empresa;
    private Empresas.Controlador.EmpresaDAO empresaDAO;
    private Runnable onUpdateCallback;
    private JComboBox<String> comboCoevaluador;

    /**
     * Constructor de la clase que inicializa el diálogo de actualización de la empresa.
     *
     * @param parent El cuadro de diálogo padre
     * @param empresa La empresa que se desea actualizar
     * @param empresaDAO El objeto DAO para acceder a la base de datos
     * @param onUpdateCallback El callback que se ejecuta al actualizar la empresa
     */
    public ActualizarEmpresaDialog(Frame parent, Empresa empresa, EmpresaDAO empresaDAO, Runnable onUpdateCallback) {
        super(parent, "Actualizar Empresa", true);
        this.empresa = empresa;
        this.empresaDAO = empresaDAO;
        this.onUpdateCallback = onUpdateCallback;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().setBackground(new Color(57, 169, 0));

        // Inicialización de los campos de texto con los datos de la empresa
        txtNit = new JTextField(empresa.getNit());
        txtNombre = new JTextField(empresa.getNombre_empresa());
        txtDireccion = new JTextField(empresa.getDireccion());
        txtArea = new JTextField(empresa.getArea());
        txtContacto = new JTextField(empresa.getContacto());
        txtEmail = new JTextField(empresa.getEmail());
        txtCiudad = new JTextField(empresa.getCiudad());

        Color inputColor = new Color(246, 246, 246);
        txtNit.setBackground(inputColor);
        txtNombre.setBackground(inputColor);
        txtDireccion.setBackground(inputColor);
        txtArea.setBackground(inputColor);
        txtContacto.setBackground(inputColor);
        txtEmail.setBackground(inputColor);
        txtCiudad.setBackground(inputColor);

        // ComboBox para Departamento
        comboDepartamento = new JComboBox<>();
        comboDepartamento.setBackground(inputColor);
        comboDepartamento.setForeground(new Color(2, 6, 0));

        // Lista de departamentos
        String[] departamentos = {
                "Amazonas", "Antioquia", "Atlántico", "Bogotá D.C.", "Bolívar", "Boyacá", "Caldas", "Caquetá", "Casanare",
                "Cauca", "Cesar", "Chocó", "Córdoba", "Cundinamarca", "Guaviare", "Guainía", "Huila", "La Guajira",
                "Magdalena", "Meta", "Nariño", "Norte de Santander", "Putumayo", "Quindío", "Risaralda", "San Andrés y Providencia",
                "Santander", "Sucre", "Tolima", "Valle del Cauca", "Vaupés", "Vichada"
        };

        // Añadir los departamentos al ComboBox
        for (String dep : departamentos) {
            comboDepartamento.addItem(dep);
        }
        comboDepartamento.setSelectedItem(empresa.getDepartamento());

        // ComboBox para coevaluador
        comboCoevaluador = new JComboBox<>();
        comboCoevaluador.setBackground(inputColor);
        comboCoevaluador.setForeground(new Color(2, 6, 0));
        cargarCoevaluadores();

        // Botón para guardar cambios
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBackground(new Color(40, 120, 0));
        btnGuardar.setForeground(Color.WHITE);

        // Etiquetas para los campos
        JLabel labelNit = new JLabel("NIT:");
        JLabel labelNombre = new JLabel("Nombre:");
        JLabel labelDireccion = new JLabel("Dirección:");
        JLabel labelArea = new JLabel("Área:");
        JLabel labelCoevaluador = new JLabel("Coevaluador:");
        JLabel labelContacto = new JLabel("Contacto:");
        JLabel labelEmail = new JLabel("Email:");
        JLabel labelDepartamento = new JLabel("Departamento:");
        JLabel labelCiudad = new JLabel("Ciudad:");

        // Fuente y color para las etiquetas
        Color labelColor = Color.WHITE;
        Font fuente = new Font("Calibri", Font.BOLD, 16);
        JLabel[] labels = {labelNit, labelNombre, labelDireccion, labelArea, labelCoevaluador,
                labelContacto, labelEmail, labelDepartamento, labelCiudad};

        // Configuración de las etiquetas
        for (JLabel label : labels) {
            label.setForeground(labelColor);
            label.setFont(fuente);
        }

        // Fuente para los campos de texto
        JTextField[] textFields = {txtNit, txtNombre, txtDireccion, txtArea, txtContacto, txtEmail, txtCiudad};
        for (JTextField tf : textFields) {
            tf.setFont(fuente);
        }
        comboDepartamento.setFont(fuente);
        comboCoevaluador.setFont(fuente);
        btnGuardar.setFont(fuente);

        // Añadir los componentes al layout
        add(labelNit, gbc); gbc.gridx = 1;
        add(txtNit, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelNombre, gbc); gbc.gridx = 1;
        add(txtNombre, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelDireccion, gbc); gbc.gridx = 1;
        add(txtDireccion, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelArea, gbc); gbc.gridx = 1;
        add(txtArea, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelCoevaluador, gbc); gbc.gridx = 1;
        add(comboCoevaluador, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelContacto, gbc); gbc.gridx = 1;
        add(txtContacto, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelEmail, gbc); gbc.gridx = 1;
        add(txtEmail, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelDepartamento, gbc); gbc.gridx = 1;
        add(comboDepartamento, gbc); gbc.gridx = 0; gbc.gridy++;

        add(labelCiudad, gbc); gbc.gridx = 1;
        add(txtCiudad, gbc); gbc.gridx = 0; gbc.gridy++;

        gbc.gridwidth = 2;
        add(btnGuardar, gbc);

        // Acción del botón para guardar los cambios
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                if (!email.endsWith("@gmail.com")) {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "El correo debe ser de tipo '@gmail.com'");
                    return;
                }

                // Actualización de los datos de la empresa
                empresa.setNit(txtNit.getText());
                empresa.setNombre_empresa(txtNombre.getText());
                empresa.setDireccion(txtDireccion.getText());
                empresa.setArea(txtArea.getText());
                empresa.setContacto(txtContacto.getText());
                empresa.setEmail(email);
                empresa.setDepartamento((String) comboDepartamento.getSelectedItem());
                empresa.setCiudad(txtCiudad.getText());

                // Actualizar el coevaluador seleccionado
                String coevaluadorSeleccionado = (String) comboCoevaluador.getSelectedItem();
                if (coevaluadorSeleccionado != null && !coevaluadorSeleccionado.isEmpty()) {
                    int idCoevaluador = Integer.parseInt(coevaluadorSeleccionado.split(" - ")[0]);
                    empresa.setID_usuarios(idCoevaluador);
                }

                // Intentar actualizar la empresa en la base de datos
                if (empresaDAO.actualizarEmpresa(empresa)) {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "Actualización exitosa");
                    dispose();
                    if (onUpdateCallback != null) onUpdateCallback.run();
                } else {
                    JOptionPane.showMessageDialog(ActualizarEmpresaDialog.this, "Error al actualizar");
                }
            }
        });

        setPreferredSize(new Dimension(400, 500));
        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Carga los coevaluadores desde la base de datos y los agrega al ComboBox de coevaluadores.
     */
    private void cargarCoevaluadores() {
        try (Connection con = new ConnectionDB().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT u.ID_usuarios, u.nombres, u.apellidos " +
                             "FROM usuarios u " +
                             "JOIN rol r ON u.ID_rol = r.ID_rol " +
                             "WHERE r.rol = 'Coevaluador'"
             );
             ResultSet rs = ps.executeQuery()) {

            // Agregar los coevaluadores al ComboBox
            while (rs.next()) {
                int id = rs.getInt("ID_usuarios");
                String nombre = rs.getString("nombres");
                String apellido = rs.getString("apellidos");
                comboCoevaluador.addItem(id + " - " + nombre + " " + apellido);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los coevaluadores.");
        }
    }
}
