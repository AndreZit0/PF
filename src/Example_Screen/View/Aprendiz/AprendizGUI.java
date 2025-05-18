package Example_Screen.View.Aprendiz;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Example_Screen.View.Administrador.Administrador.setFrameIcon;
import static Example_Screen.View.Login.LoginGUI.cofigBotonInicioSegunRol;

public class AprendizGUI {
    private JPanel panelAprendiz;

    public JPanel getPanel() {
        return panelAprendiz;
    }

    public void moduloAprendiz() {
        JFrame frame = new JFrame("SAEP");
        frame.setContentPane(this.panelAprendiz);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);

        System.out.println(cofigBotonInicioSegunRol);

        setFrameIcon(frame);
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
