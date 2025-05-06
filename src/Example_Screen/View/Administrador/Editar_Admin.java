package Example_Screen.View.Administrador;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Editar_Admin{

    private JPanel panel1;
    private JButton confirmar️Button;
    private JComboBox estado;
    private JComboBox rol;
    private JTextField nombre;
    private JComboBox tipo_doc;
    private JTextField num_doc;
    private JTextField email;
    private JTextField direc;
    private JTextField conta;
    private JButton editarPerfil️Button;
    private JButton cancelar;


    public JPanel getPanel() {
        return panel1;
    }

    public Editar_Admin(){

        confirmar️Button.setEnabled(false);
        cancelar.setEnabled(false);

        nombre.setText("Miguel Medina Ladino");
        num_doc.setText("1115350402");
        email.setText("miguel11@gmail.com");
        direc.setText("Calle 12 i # 28 f 63");
        conta.setText("3156114912");

        nombre.setEnabled(false);
        tipo_doc.setEnabled(false);
        num_doc.setEnabled(false);
        email.setEnabled(false);
        direc.setEnabled(false);
        conta.setEnabled(false);
        rol.setEnabled(false);
        estado.setEnabled(false);


        JButton[] botones = {editarPerfil️Button, cancelar, confirmar️Button};

        for (JButton btn : botones) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        Color colorHover = new Color(0, 120, 50);
        Color colorBase = new Color(57, 169, 0);
        aplicarEfectoHover(editarPerfil️Button, colorHover, colorBase);
        aplicarEfectoHover(confirmar️Button, colorHover, colorBase);
        aplicarEfectoHover(cancelar, colorHover, colorBase);


        Border bottom1 = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.decode("#39A900"));
        nombre.setBorder(bottom1);
        tipo_doc.setBorder(bottom1);
        num_doc.setBorder(bottom1);
        email.setBorder(bottom1);
        direc.setBorder(bottom1);
        conta.setBorder(bottom1);


        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));

        editarPerfil️Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setBorder(bottom);
                num_doc.setBorder(bottom);
                email.setBorder(bottom);
                direc.setBorder(bottom);
                conta.setBorder(bottom);

                nombre.setEnabled(true);
                tipo_doc.setEnabled(true);
                num_doc.setEnabled(true);
                email.setEnabled(true);
                direc.setEnabled(true);
                conta.setEnabled(true);
                rol.setEnabled(true);
                estado.setEnabled(true);

                editarPerfil️Button.setEnabled(false);
                confirmar️Button.setEnabled(true);
                cancelar.setEnabled(true);
            }
        });
        confirmar️Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setEnabled(false);
                tipo_doc.setEnabled(false);
                num_doc.setEnabled(false);
                email.setEnabled(false);
                direc.setEnabled(false);
                conta.setEnabled(false);
                rol.setEnabled(false);
                estado.setEnabled(false);

                editarPerfil️Button.setEnabled(true);
                cancelar.setEnabled(false);
                confirmar️Button.setEnabled(false);

                nombre.setBorder(bottom1);
                tipo_doc.setBorder(bottom1);
                num_doc.setBorder(bottom1);
                email.setBorder(bottom1);
                direc.setBorder(bottom1);
                conta.setBorder(bottom1);
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setEnabled(false);
                tipo_doc.setEnabled(false);
                num_doc.setEnabled(false);
                email.setEnabled(false);
                direc.setEnabled(false);
                conta.setEnabled(false);
                rol.setEnabled(false);
                estado.setEnabled(false);

                editarPerfil️Button.setEnabled(true);
                cancelar.setEnabled(false);
                confirmar️Button.setEnabled(false);

                nombre.setBorder(bottom1);
                tipo_doc.setBorder(bottom1);
                num_doc.setBorder(bottom1);
                email.setBorder(bottom1);
                direc.setBorder(bottom1);
                conta.setBorder(bottom1);
            }
        });
    }
 
    public void aplicarEfectoHover(JButton boton, Color colorHover, Color colorBase) {
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBase);
            }
        });
    }



}
