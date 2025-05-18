package View;

import View.Administrador.AdministradorAprendiz;
import View.Administrador.InicioAdministrador;
import View.Aprendiz.Aprendiz;
import View.Empresa.Empresa;
import View.Empresa.EmpresaPerfilAprendiz;
import View.Instructor.Instructor;
import View.Instructor.InstructorPerfilAprendiz;
import View.Instructor.modalPerfilInstructor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GestionPantalla {
    private JButton administradorAprendizButton;
    private JPanel main;
    private JButton aprendizButton;
    private JButton inicioAdministradorButton;
    private JButton empresaButton;
    private JButton empresaPerfilAprendizButton;
    private JButton instructorButton;
    private JButton instructorPerfilAprendizButton;

    private JFrame frame;

    public GestionPantalla(JFrame frame) {

        this.frame=frame;

        administradorAprendizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministradorAprendiz administradorAprendiz = new AdministradorAprendiz();
                administradorAprendiz.main();

            }
        });
        inicioAdministradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InicioAdministrador inicioAdministrador = new InicioAdministrador();
                inicioAdministrador.main();
            }
        });
        aprendizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aprendiz aprendiz = new Aprendiz();
            }
        });

        empresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Empresa empresa = new Empresa();
                empresa.main();
            }
        });

        instructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Instructor intructor = new Instructor();
                intructor.main();

            }
        });

        empresaPerfilAprendizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpresaPerfilAprendiz Empresa = new EmpresaPerfilAprendiz();
                Empresa.main();
            }
        });
        instructorPerfilAprendizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstructorPerfilAprendiz perfilAprendiz = new InstructorPerfilAprendiz();
                perfilAprendiz.main();

            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("SAEP");
        GestionPantalla pantalla = new GestionPantalla(frame); // Se pasa el frame al constructor de Menu
        frame.setContentPane(pantalla.main);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(frame, "¿Está seguro de que desea salir?\nCualquier operación que esté realizando y no haya guardado se perderá.","Confirmar Salida",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(option == JOptionPane.YES_OPTION)
                {
                    frame.dispose(); // Cierra la ventana
                    System.exit(0);
                }
            }
        });

    }
}
