package Usuarios;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        verUsuariosButton.addActionListener(e -> {
            boolean visible = VerUsuarios.isVisible();
            VerUsuarios.setVisible(!visible);

        });
        crearUsuariosButton.addActionListener(e -> {
            boolean visible = CrearUsuarios.isVisible();
            CrearUsuarios.setVisible(!visible);
        });
        asignacionesButton.addActionListener(e -> {
            boolean visible = Asignar.isVisible();
            Asignar.setVisible(!visible);
        });
        fichasButton.addActionListener(e ->{
            boolean visible = Fichas.isVisible();
            Fichas.setVisible(!visible);
        });
        sedesButton.addActionListener(e ->{
            boolean visible = Sedes.isVisible();
            Sedes.setVisible(!visible);
        });
        programasButton.addActionListener(e ->{
            boolean visible = Programas.isVisible();
            Programas.setVisible(!visible);
        });
        modalidadesButton.addActionListener(e ->{
            boolean visible = Modalidades.isVisible();
           Modalidades.setVisible(!visible);
        });

        Crearusuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (panelcrearusuario.isVisible()){
                    panelcrearusuario.setVisible(false);
                }
                else{
                    panelcrearusuario.setVisible(true);
                }
            }
        });
    }



    // Método para mostrar este panel en una ventana
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Usuarios");
        frame.setContentPane(new UsuariosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.setVisible(true);
    }
}

