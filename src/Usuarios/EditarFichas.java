package Usuarios;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.text.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditarFichas {
    private JPanel main;
    private JTextField codigo;
    private JTextField fechainicio;
    private JTextField fechafinlectiva;
    private JTextField fechafin;
    private JComboBox<String> idprograma;
    private JComboBox<String> idsede;
    private JComboBox<String> modalidad;
    private JComboBox<String> jornada;
    private JComboBox<String> nivelformacion;
    private JComboBox<String> estado;
    private JButton confirmarButton;
    private JButton cancelar;

    private FichasDAO dao = new FichasDAO(ConexionBD.getConnection());
    private Fichas_setget fichaActual;

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    private HashMap<String, Integer> mapaProgramas;
    private HashMap<String, Integer> mapaSedes;

    public JPanel getMainPanel() {
        return main;
    }

    public EditarFichas(Fichas_setget ficha) {
        this.fichaActual = ficha;
        cargarCombos();
        cargarDatosFicha();
        idprograma.setEditable(true); // importante
        idsede.setEditable(true); // importante
        new AprendizGUI.AutoCompleteComboBox(idprograma);
        new AprendizGUI.AutoCompleteComboBox(idsede);

        cancelar.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
            if (frame != null) {
                frame.dispose();
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
    }

    private void cargarCombos() {
        idprograma.removeAllItems();
        idsede.removeAllItems();

        // --- Programas ---
        try {
            ProgramasDAO programasDAO = new ProgramasDAO();
            mapaProgramas = new HashMap<>();

            for (Programas_getset programa : programasDAO.listarProgramas()) {
                String nombrePrograma = programa.getNombre_programa();
                int idPrograma = programa.getID_programas();

                mapaProgramas.put(nombrePrograma, idPrograma);
                idprograma.addItem(nombrePrograma);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar programas: " + e.getMessage());
        }

        // --- Sedes ---
        try {
            SedeDAO sedeDAO = new SedeDAO();
            mapaSedes = sedeDAO.obtenerMapaSedes(); // <Nombre, ID>

            for (String nombreSede : mapaSedes.keySet()) {
                idsede.addItem(nombreSede);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar sedes: " + e.getMessage());
        }

        // --- Combos fijos ---
        modalidad.setModel(new DefaultComboBoxModel<>(new String[]{"Presencial", "Virtual"}));
        jornada.setModel(new DefaultComboBoxModel<>(new String[]{"Diurna", "Nocturna", "Mixta"}));
        nivelformacion.setModel(new DefaultComboBoxModel<>(new String[]{"Técnico", "Tecnólogo"}));
        estado.setModel(new DefaultComboBoxModel<>(new String[]{"Activa", "Inactiva"}));
    }

    private void cargarDatosFicha() {
        codigo.setText(fichaActual.getCodigo() != null ? fichaActual.getCodigo() : "");

        if (fichaActual.getFecha_inicio() != null) {
            fechainicio.setText(formatoFecha.format(fichaActual.getFecha_inicio()));
        }
        if (fichaActual.getFecha_fin_lec() != null) {
            fechafinlectiva.setText(formatoFecha.format(fichaActual.getFecha_fin_lec()));
        }
        if (fichaActual.getFecha_final() != null) {
            fechafin.setText(formatoFecha.format(fichaActual.getFecha_final()));
        }

        if (fichaActual.getNombre_programa() != null && !fichaActual.getNombre_programa().trim().isEmpty()) {
            idprograma.setSelectedItem(fichaActual.getNombre_programa());
        }

        if (fichaActual.getNombre_sede() != null && !fichaActual.getNombre_sede().trim().isEmpty()) {
            idsede.setSelectedItem(fichaActual.getNombre_sede());
        }

        if (fichaActual.getModalidad() != null) {
            modalidad.setSelectedItem(fichaActual.getModalidad());
        }
        if (fichaActual.getJornada() != null) {
            jornada.setSelectedItem(fichaActual.getJornada());
        }
        if (fichaActual.getNivel_formacion() != null) {
            nivelformacion.setSelectedItem(fichaActual.getNivel_formacion());
        }
        if (fichaActual.getEstado() != null) {
            estado.setSelectedItem(fichaActual.getEstado());
        }
    }

    private void guardarCambios() {
        try {
            if (codigo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El código no puede estar vacío.");
                return;
            }

            if (fechainicio.getText().trim().isEmpty() ||
                    fechafinlectiva.getText().trim().isEmpty() ||
                    fechafin.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todas las fechas son obligatorias.");
                return;
            }

            if (idprograma.getSelectedItem() == null || idsede.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un programa y una sede.");
                return;
            }

            fichaActual.setCodigo(codigo.getText().trim());
            fichaActual.setFecha_inicio(formatoFecha.parse(fechainicio.getText()));
            fichaActual.setFecha_fin_lec(formatoFecha.parse(fechafinlectiva.getText()));
            fichaActual.setFecha_final(formatoFecha.parse(fechafin.getText()));

            String nombrePrograma = (String) idprograma.getSelectedItem();
            String nombreSede = (String) idsede.getSelectedItem();

            fichaActual.setNombre_programa(nombrePrograma);
            fichaActual.setNombre_sede(nombreSede);

            // Establecer ID reales
            if (mapaProgramas.containsKey(nombrePrograma)) {
                fichaActual.setNombre_programa(String.valueOf(mapaProgramas.get(nombrePrograma)));
            } else {
                throw new Exception("No se encontró el ID del programa seleccionado.");
            }

            if (mapaSedes.containsKey(nombreSede)) {
                fichaActual.setNombre_sede(String.valueOf(mapaSedes.get(nombreSede)));
            } else {
                throw new Exception("No se encontró el ID de la sede seleccionada.");
            }

            fichaActual.setModalidad((String) modalidad.getSelectedItem());
            fichaActual.setJornada((String) jornada.getSelectedItem());
            fichaActual.setNivel_formacion((String) nivelformacion.getSelectedItem());
            fichaActual.setEstado((String) estado.getSelectedItem());

            boolean actualizado = dao.actualizarFicha(fichaActual);

            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Ficha actualizada correctamente.");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(main);
                if (frame != null) {
                    frame.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la ficha en la base de datos.");
            }

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error en el formato de fecha. Use el formato: yyyy-MM-dd\n" + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los cambios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class AutoCompleteComboBox {
        private final JComboBox<String> comboBox;
        private final List<String> originalItems = new ArrayList<>();
        private boolean isAdjusting = false;

        public AutoCompleteComboBox(JComboBox<String> comboBox) {
            this.comboBox = comboBox;
            init();
        }

        private void init() {
            // Guardar todos los elementos actuales del ComboBox
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                originalItems.add(comboBox.getItemAt(i));
            }

            comboBox.setEditable(true);
            JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();

            textField.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { updateModel(); }
                public void removeUpdate(DocumentEvent e) { updateModel(); }
                public void changedUpdate(DocumentEvent e) { updateModel(); }

                private void updateModel() {
                    if (isAdjusting) return;

                    SwingUtilities.invokeLater(() -> {
                        String input = textField.getText();
                        isAdjusting = true;

                        DefaultComboBoxModel<String> filteredModel = new DefaultComboBoxModel<>();
                        for (String item : originalItems) {
                            if (item.toLowerCase().contains(input.toLowerCase())) {
                                filteredModel.addElement(item);
                            }
                        }

                        comboBox.setModel(filteredModel);
                        comboBox.setSelectedItem(input);
                        textField.setSelectionStart(input.length());
                        textField.setSelectionEnd(input.length());

                        comboBox.showPopup();
                        isAdjusting = false;
                    });
                }
            });
        }
    }
}
