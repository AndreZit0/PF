package Empresas.Vista;

import Empresas.ConexionBD.ConnectionDB;
import Empresas.Controlador.EmpresaDAO;
import Empresas.Modelo.Empresa;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa una ventana de diálogo para actualizar los datos de una empresa.
 * Permite modificar los campos asociados a una empresa y actualizar la información en la base de datos.
 */
public class ActualizarGUI extends JDialog {
    private JPanel main; // <--- este es tu panel principal
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JComboBox comboBoxdep;
    private JComboBox comboBoxest;
    public JComboBox comboBoxcoe;
    private JButton guardarCambiosButton;
    private Empresa empresa;
    private EmpresaDAO empresaDAO;

    /**
     * Constructor de la clase ActualizarGUI.
     *
     * @param owner    Componente padre de la ventana de diálogo.
     * @param empresa  Objeto Empresa que contiene los datos a actualizar.
     * @param empresaDAO Objeto DAO para manejar operaciones con la base de datos.
     * @param onUpdate Callback que se ejecuta después de guardar los cambios (por ejemplo, para refrescar una tabla).
     */
    public ActualizarGUI(Frame owner, Empresa empresa, EmpresaDAO empresaDAO, Runnable onUpdate) {
        super(owner, "Actualizar Empresa", true);
        this.empresa = empresa;
        this.empresaDAO = empresaDAO;

        setContentPane(main);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(owner);

        cargarDatosEmpresa();
        cargarCoevaluadores();
        guardarCambiosButton.addActionListener(e -> {
            if (guardarCambios()) {
                onUpdate.run();
                dispose();
            }
        });
    }

    /**
     * Carga los datos actuales de la empresa en los campos del formulario.
     */
    public void cargarDatosEmpresa() {
        textField1.setText(empresa.getNit());
        textField2.setText(empresa.getNombre_empresa());
        textField3.setText(empresa.getDireccion());
        textField4.setText(empresa.getArea());
        comboBoxcoe.setSelectedItem(empresa.getID_usuarios());
        textField5.setText(empresa.getContacto());
        textField6.setText(empresa.getEmail());
        textField7.setText(empresa.getCiudad());
        comboBoxdep.setSelectedItem(empresa.getDepartamento());
        comboBoxest.setSelectedItem(empresa.getEstado());
    }

    /**
     * Guarda los cambios realizados en el formulario actualizando el objeto empresa
     * y persistiendo los datos mediante el DAO.
     */
    public boolean guardarCambios() {
        String email = textField6.getText();
//        if (!email.endsWith("@gmail.com")) {
//            JOptionPane.showMessageDialog(this, "El correo electrónico debe terminar en '@gmail.com'.", "Correo no válido", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }

        empresa.setNit(textField1.getText());
        empresa.setNombre_empresa(textField2.getText());
        empresa.setDireccion(textField3.getText());
        empresa.setArea(textField4.getText());

        String coevaluadorSeleccionado = (String) comboBoxcoe.getSelectedItem();
        if (coevaluadorSeleccionado != null && !coevaluadorSeleccionado.isEmpty()) {
            int idCoevaluador = Integer.parseInt(coevaluadorSeleccionado.split(" - ")[0]);
            empresa.setID_usuarios(idCoevaluador);
        }

        empresa.setContacto(textField5.getText());
        empresa.setEmail(email);
        empresa.setCiudad(textField7.getText());
        empresa.setDepartamento((String) comboBoxdep.getSelectedItem());
        empresa.setEstado((String) comboBoxest.getSelectedItem());

        empresaDAO.actualizarEmpresa(empresa);
        JOptionPane.showMessageDialog(this, "Empresa actualizada con éxito.");
        return true;
    }


    /**
     * Carga en el ComboBox los usuarios que tienen el rol de "Coevaluador" desde la base de datos.
     */
    public void cargarCoevaluadores() {
        try (Connection con = new ConnectionDB().getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT u.ID_usuarios, u.nombres, u.apellidos " +
                             "FROM usuarios u " +
                             "JOIN rol r ON u.ID_rol = r.ID_rol " +
                             "WHERE r.rol = 'Coevaluador'"
             );
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_usuarios");
                String nombre = rs.getString("nombres");
                String apellido = rs.getString("apellidos");
                comboBoxcoe.addItem(id + " - " + nombre + " " + apellido);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los coevaluadores.");
        }
    }
}
