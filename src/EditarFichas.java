package src;

import javax.swing.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;

/**
 * Clase que representa la interfaz gráfica para la edición de la información de una ficha.
 * Permite modificar los datos de una ficha existente y guardar los cambios en la base de datos.
 */
public class EditarFichas {
    private JPanel main;
    private JTextField codigo;
    private JTextField fechainicio;
    private JTextField fechafinlectiva;
    private JTextField fechafin;
    private JComboBox<Integer> idprograma;
    private JComboBox<Integer> idsede;
    private JComboBox<String> modalidad;
    private JComboBox<String> jornada;
    private JComboBox<String> nivelformacion;
    private JComboBox<String> estado;
    private JButton confirmarButton;
    private JButton cancelar;

    private FichasDAO dao = new FichasDAO(ConexionBD.getConnection());
    private Fichas_setget fichaActual;

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal (JPanel).
     */
    public JPanel getMainPanel() {
        return main;
    }

    /**
     * Constructor de la clase EditarFichas.
     * Inicializa la interfaz gráfica y carga los datos de la ficha a editar.
     *
     * @param ficha El objeto Fichas_setget con los datos de la ficha a editar.
     */
    public EditarFichas(Fichas_setget ficha) {
        this.fichaActual = ficha;
        cargarDatosFicha();
        cargarCombos();

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                frame.dispose();
            }
        });
    }

    /**
     * Carga los datos de los ComboBox de la interfaz gráfica.
     * Obtiene los datos de la base de datos y los asigna a los ComboBox correspondientes.
     */
    private void cargarCombos() {
        idprograma.removeAllItems();
        idsede.removeAllItems();

        // Cargar programas desde la base de datos
        ProgramasDAO programasDAO = new ProgramasDAO();
        for (Programas_getset programa : programasDAO.listarProgramas()) {
            idprograma.addItem(programa.getID_programas()); // sin convertir a String
        }

        // Cargar sedes desde la base de datos
        SedeDAO sedeDAO = new SedeDAO();
        for (Sede_getset sede : sedeDAO.listarSedes()) {
            idsede.addItem(sede.getID_sede()); // sin convertir a String
        }

        // Otros combos fijos
        modalidad.setModel(new DefaultComboBoxModel<>(new String[]{"Presencial", "Virtual"}));
        jornada.setModel(new DefaultComboBoxModel<>(new String[]{"Diurna", "Nocturna", "Mixta"}));
        nivelformacion.setModel(new DefaultComboBoxModel<>(new String[]{"Técnico", "Tecnólogo"}));
        estado.setModel(new DefaultComboBoxModel<>(new String[]{"Activa", "Inactiva"}));
    }

    /**
     * Carga los datos de la ficha en los campos de la interfaz gráfica.
     * Los datos se obtienen del objeto Fichas_setget pasado al constructor.
     */
    private void cargarDatosFicha() {
        codigo.setText(fichaActual.getCodigo());
        fechainicio.setText(formatoFecha.format(fichaActual.getFecha_inicio()));
        fechafinlectiva.setText(formatoFecha.format(fichaActual.getFecha_fin_lec()));
        fechafin.setText(formatoFecha.format(fichaActual.getFecha_final()));

        idprograma.setSelectedItem(fichaActual.getNombre_programa());
        idsede.setSelectedItem(fichaActual.getNombre_sede());


        modalidad.setSelectedItem(fichaActual.getModalidad());
        jornada.setSelectedItem(fichaActual.getJornada());
        nivelformacion.setSelectedItem(fichaActual.getNivel_formacion());
        estado.setSelectedItem(fichaActual.getEstado());
    }

    /**
     * Guarda los cambios realizados en la interfaz gráfica en la base de datos.
     * Actualiza los datos de la ficha utilizando el objeto FichasDAO.
     */
    private void guardarCambios() {
        try {
            fichaActual.setCodigo(codigo.getText());
            fichaActual.setFecha_inicio(formatoFecha.parse(fechainicio.getText()));
            fichaActual.setFecha_fin_lec(formatoFecha.parse(fechafinlectiva.getText()));
            fichaActual.setFecha_final(formatoFecha.parse(fechafin.getText()));
            fichaActual.setNombre_programa((String) idprograma.getSelectedItem());
            fichaActual.setNombre_sede((String) idsede.getSelectedItem());
            fichaActual.setModalidad((String) modalidad.getSelectedItem());
            fichaActual.setJornada((String) jornada.getSelectedItem());
            fichaActual.setNivel_formacion((String) nivelformacion.getSelectedItem());
            fichaActual.setEstado((String) estado.getSelectedItem());

            if (dao.actualizarFicha(fichaActual)) {
                JOptionPane.showMessageDialog(null, "Ficha actualizada correctamente.");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                frame.dispose();

                FichasDAO fichasDAO = new FichasDAO(ConexionBD.getConnection());
                Fichas_setget fichas = fichasDAO.obtenerFichaPorID(fichaActual.getID_Fichas());

                if (fichas != null) {
                    JFrame frameEditarFichas = new JFrame("Editar Fichas");
                    EditarFichas editarFichas = new EditarFichas(fichas);
                    frameEditarFichas.setContentPane(editarFichas.getMainPanel());
                    frameEditarFichas.pack();
                    frameEditarFichas.setLocationRelativeTo(null);
                    frameEditarFichas.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar ficha.");
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error al parsear la fecha: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + e.getMessage());
        }
    }
}

