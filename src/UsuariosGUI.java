package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que representa la interfaz gráfica para la gestión de usuarios.
 * Permite ver, crear, asignar y acceder a otras funcionalidades relacionadas con usuarios
 * como fichas, sedes, programas y modalidades.
 */
public class UsuariosGUI {
    private JPanel main;
    private JTable table1;
    private JButton verUsuariosButton;
    private JButton miPerfilButton;
    private JButton crearUsuariosButton;
    private JButton asignacionesButton;
    private JButton menuButton;
    private JPanel menu;
    private JButton fichasButton;
    private JButton sedesButton;
    private JButton programasButton;
    private JButton modalidadesButton;
    private JPanel VerUsuarios;
    private JPanel CrearUsuarios;
    private JPanel Asignar;
    private JPanel Fichas;
    private JPanel Sedes;
    private JPanel Programas;
    private JPanel Modalidades;
    private JScrollPane tablaAprendiz;
    private JLabel Aprendiz;
    private JPanel vacio;
    private JLabel Crearusuario;
    private JTextField txtnombre;
    private JTextField txtapellido;
    private JTextField txtTipodoc;
    private JTextField txtnumerodoc;
    private JTextField txtcontacto1;
    private JTextField txtcontacto2;
    private JTextField txtdireccion;
    private JTextField txtemail;
    private JTextField txtclave;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton volverButton;
    private JButton confirmarButton;
    private JPanel panelcrearusuario;

    /**
     * Constructor de la clase UsuariosGUI.
     * Inicializa los componentes de la interfaz gráfica, oculta paneles y configura
     * los listeners para los botones.
     */
    public UsuariosGUI() {

        // Ocultar el panel de menú al iniciar
        menu.setVisible(false);
        VerUsuarios.setVisible(false);
        CrearUsuarios.setVisible(false);
        Asignar.setVisible(false);
        Fichas.setVisible(false);
        Sedes.setVisible(false);
        Programas.setVisible(false);
        Modalidades.setVisible(false);

        panelcrearusuario.setVisible(false);

        // Alternar visibilidad del menú al hacer clic
        menuButton.addActionListener(e -> {
            boolean visible = menu.isVisible();
            menu.setVisible(!visible);
        });

        // Mostrar el panel de Ver Usuarios
        verUsuariosButton.addActionListener(e -> {
            boolean visible = VerUsuarios.isVisible();
            VerUsuarios.setVisible(!visible);

        });
        // Mostrar el panel de Crear Usuarios
        crearUsuariosButton.addActionListener(e -> {
            boolean visible = CrearUsuarios.isVisible();
            CrearUsuarios.setVisible(!visible);
        });
        // Mostrar el panel de Asignar
        asignacionesButton.addActionListener(e -> {
            boolean visible = Asignar.isVisible();
            Asignar.setVisible(!visible);
        });
        // Mostrar el panel de Fichas
        fichasButton.addActionListener(e -> {
            boolean visible = Fichas.isVisible();
            Fichas.setVisible(!visible);
        });
        // Mostrar el panel de Sedes
        sedesButton.addActionListener(e -> {
            boolean visible = Sedes.isVisible();
            Sedes.setVisible(!visible);
        });
        // Mostrar el panel de Programas
        programasButton.addActionListener(e -> {
            boolean visible = Programas.isVisible();
            Programas.setVisible(!visible);
        });
        // Mostrar el panel de Modalidades
        modalidadesButton.addActionListener(e -> {
            boolean visible = Modalidades.isVisible();
            Modalidades.setVisible(!visible);
        });

        // Mostrar el panel de Crear Usuario al hacer clic en la etiqueta
        Crearusuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panelcrearusuario.isVisible()) {
                    panelcrearusuario.setVisible(false);
                } else {
                    panelcrearusuario.setVisible(true);
                }
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación de gestión de usuarios.
     * Crea la ventana principal y muestra la interfaz de usuario.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Usuarios");
        frame.setContentPane(new UsuariosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}

