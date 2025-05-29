package Empresas.Vista;
import Empresas.ConexionBD.ConnectionDB;
import Empresas.Controlador.EmpresaDAO;
import Empresas.Modelo.Empresa;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa la interfaz gráfica para gestionar empresas.
 */
public class CrearGUI {
    private JTextField Nit;
    private JTextField Contacto;
    private JTextField Nombre;
    private JTextField Email;
    private JTextField Direccion;
    private JComboBox comboBox1;
    private JTextField Area;
    private JTextField Ciudad;
    private JComboBox combo1;
    private JButton cancelarButton;
    private JButton confirmarButton;
    private JPanel pnlCrearEmpresa;
    private EmpresaDAO empresaDAO = new EmpresaDAO();
    private ConnectionDB connectionDB = new ConnectionDB();

    public JPanel getPanel(){return pnlCrearEmpresa;}

    /**
     * Constructor de la clase CrearGUI.
     * Inicializa los componentes de la interfaz y configura los bordes y estilos visuales.
     */
    public CrearGUI () {
        // para cargar los coevaluadores
        cargarUsuariosEnComboBox();
        // Configura el borde inferior de los campos de texto
        Border bottom = BorderFactory.createMatteBorder(0,0,2,0, Color.decode("#39A900"));

        // Aplica el borde a los campos de texto
        Nit.setBorder(bottom);
        Contacto.setBorder(bottom);
        Nombre.setBorder(bottom);
        Area.setBorder(bottom);
        Direccion.setBorder(bottom);
        Email.setBorder(bottom);
        comboBox1.setBorder(bottom);
        combo1.setBorder(bottom);
        Ciudad.setBorder(bottom);
        comboBox1.setSelectedIndex(-1); // Para que no haya selección al principio
        combo1.setSelectedIndex(-1); // Para que no haya selección al principio

        // Configura el evento de clic en el botón 'confirmar'
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpresa();
            }
        });
    }

    /**
     * Agrega una nueva empresa a la base de datos.
     * Primero valida que todos los campos estén completos y que el formato del correo electrónico sea válido.
     * Luego solicita confirmación antes de agregar la empresa.
     */
    public void agregarEmpresa() {
        String nit = Nit.getText().trim();
        String nombre = Nombre.getText().trim();
        String direccion = Direccion.getText().trim();
        String area = Area.getText().trim();
        String contacto = Contacto.getText().trim();
        String email = Email.getText().trim();
        String departamento = comboBox1.getSelectedItem() != null ? comboBox1.getSelectedItem().toString().trim() : "";
        String usuarios = combo1.getSelectedItem() != null ? combo1.getSelectedItem().toString().trim() : "";
        String ciudad = Ciudad.getText().trim();

        if (nit.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || area.isEmpty() ||
                contacto.isEmpty() || email.isEmpty() || departamento.isEmpty() || ciudad.isEmpty() || usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(pnlCrearEmpresa, "Por favor, completa todos los campos antes de continuar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Extraer ID de usuario desde combo1
        int idUsuario = 0;
        try {
            String[] partes = usuarios.split(" - ");
            idUsuario = Integer.parseInt(partes[0]);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pnlCrearEmpresa, "Error al procesar el ID del usuario.");
            return;
        }
        // Validar formato de correo electrónico
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(pnlCrearEmpresa, "Por favor, ingresa un correo electrónico válido (@gmail.com).", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmación antes de agregar la empresa
        int confirmacion = JOptionPane.showConfirmDialog(pnlCrearEmpresa, "¿Estás seguro de que deseas agregar esta empresa?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            return; // Si el usuario selecciona NO, se cancela
        }

        Empresa empresa = new Empresa(0, idUsuario, nit, nombre, direccion, area, contacto, email, departamento, ciudad);
        if (empresaDAO.agregarEmpresa(empresa)) {
            JOptionPane.showMessageDialog(pnlCrearEmpresa, "Empresa agregada exitosamente.");
            // Limpiar los campos
            Nit.setText("");
            Nombre.setText("");
            Direccion.setText("");
            Area.setText("");
            Contacto.setText("");
            Email.setText("");
            comboBox1.setSelectedIndex(-1);
            Ciudad.setText("");
            combo1.setSelectedIndex(-1);

        } else {
            JOptionPane.showMessageDialog(pnlCrearEmpresa, "Error al agregar la empresa.");
        }
    }

    /**
     * Carga los usuarios con rol de "Coevaluador" desde la base de datos y los agrega a un {@link JComboBox}.
     * Cada ítem es el ID del usuario seguido de su nombre y apellido.
     */
    public void cargarUsuariosEnComboBox() {
        try (Connection con = new ConnectionDB().getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT u.ID_usuarios, u.nombres, u.apellidos FROM usuarios u JOIN rol r ON u.ID_rol = r.ID_rol WHERE r.rol = 'Coevaluador'");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID_usuarios");
                String nombre = rs.getString("nombres");
                String apellido = rs.getString("apellidos");
                combo1.addItem(id + " - " + nombre + " " + apellido);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los ID de usuarios.");
        }
    }

    public static void main(String[] args) {
        CrearGUI crearGUI = new CrearGUI();
        JFrame frame = new JFrame("EMPRESA");
        frame.setContentPane(new CrearGUI().pnlCrearEmpresa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
