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
 * Clase que representa la interfaz gráfica para visualizar los detalles de una empresa.
 */
public class DetallesGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JPanel main;
    private JTextField textField10;
    private EmpresaDAO empresaDAO = new EmpresaDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    /**
     * Constructor que inicializa la interfaz de detalles con los datos de una empresa específica.
     *
     * @param nitEmpresaSeleccionada NIT de la empresa cuyos datos se desean cargar.
     */
    public DetallesGUI(String nitEmpresaSeleccionada) {
        cargarDatosEmpresa(nitEmpresaSeleccionada);
    }

    /**
     * Carga los datos de la empresa desde la base de datos y los asigna a los campos de texto de la interfaz.
     *
     * @param nit NIT de la empresa a consultar.
     */
    public void cargarDatosEmpresa(String nit) {
        Empresa empresa = empresaDAO.buscarEmpresa(nit);

        if (empresa == null) {
            JOptionPane.showMessageDialog(null, "Empresa no encontrada.");
            return;
        }

        String nombreCoevaluador = "";
        try (Connection con = new ConnectionDB().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT nombres, apellidos FROM usuarios WHERE ID_usuarios = ?")) {
            ps.setInt(1, empresa.getID_usuarios());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombreCoevaluador = rs.getString("nombres") + " " + rs.getString("apellidos");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JTextField[] campos = {textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8, textField9, textField10};

        for (JTextField campo : campos) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            campo.setCaretColor(campo.getBackground());
        }

        textField1.setText(empresa.getNit());
        textField2.setText(empresa.getNombre_empresa());
        textField3.setText(empresa.getDireccion());
        textField4.setText(empresa.getArea());
        textField5.setText(nombreCoevaluador);
        textField6.setText(empresa.getContacto());
        textField7.setText(empresa.getEmail());
        textField8.setText(empresa.getDepartamento());
        textField9.setText(empresa.getCiudad());
        textField10.setText(empresa.getEstado());
    }

    /**
     * Devuelve el panel principal que contiene la interfaz gráfica.
     *
     * @return JPanel principal.
     */
    public JPanel getMainPanel() {
        return main;
    }
}
