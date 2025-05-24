package Usuarios;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AprendizGUI {

    private JComboBox<String> estado;
    private JButton confirmarButton;
    private JComboBox<String> aprendiz;    // ID_usuarios
    private JComboBox<String> ficha;       // ID_Fichas
    private JComboBox<String> modalidad;   // ID_modalidad
    private JComboBox<String> evaluador;   // ID_instructor
    private JComboBox<String> empresa;     // ID_empresas
    private JButton cancelar;
    private ArrayList<Usuarios_getset> listaAprendices;
    private ArrayList<Usuarios_getset> listaInstructores;
    private ArrayList<Integer> listaIDEmpresas = new ArrayList<>(); // ID reales de empresas

    private JPanel main;

    public JPanel getMainPanel() {
        return main;
    }

    public AprendizGUI() {
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        ficha.setEditable(true); // importante
        empresa.setEditable(true); // importante

        // Lista de aprendices
        listaAprendices = usuariosDAO.obtenerUltimoAprendiz("aprendiz");
        DefaultComboBoxModel<String> modeloAprendiz = new DefaultComboBoxModel<>();
        for (Usuarios_getset u : listaAprendices) {
            modeloAprendiz.addElement(u.getNombres() + " " + u.getApellidos());
        }
        aprendiz.setModel(modeloAprendiz);

//        // Lista de instructores
//        listaInstructores = usuariosDAO.listarUsuariosPorRol("evaluador");
//        DefaultComboBoxModel<String> modeloEvaluador = new DefaultComboBoxModel<>();
//        for (Usuarios_getset u : listaInstructores) {
//            modeloEvaluador.addElement(u.getNombres() + " " + u.getApellidos());
//        }
//        evaluador.setModel(modeloEvaluador);

        // Cargar empresas, fichas y modalidades
        // Lógica en el constructor
        cargarEmpresas();
        new AutoCompleteComboBox(empresa);
        cargarFichas(); // Se llena el combo
        new AutoCompleteComboBox(ficha); // Y ahora se le aplica el autocompletado
        cargarModalidad();


        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedIndexAprendiz = aprendiz.getSelectedIndex();
                    if (selectedIndexAprendiz == -1 || listaAprendices.isEmpty()) {
                        JOptionPane.showMessageDialog(main, "Por favor, seleccione un aprendiz.");
                        return;
                    }
                    int idUsuario = listaAprendices.get(selectedIndexAprendiz).getID_usuarios();


                    int idFicha = ficha.getSelectedIndex() + 1;
                    int idEmpresa = listaIDEmpresas.get(empresa.getSelectedIndex()); // Obtener el ID real
                    int idModalidad = modalidad.getSelectedIndex() + 1;
                    String estadoSeleccionado = (String) estado.getSelectedItem();

                    Aprendiz_getset aprend = new Aprendiz_getset(
                            0,
                            idUsuario,
                            idFicha,
                            idEmpresa,
                            26,
                            idModalidad,
                            estadoSeleccionado
                    );

                    AprendizDAO dao = new AprendizDAO();
                    if (dao.crearAprendiz(aprend)) {
                        JOptionPane.showMessageDialog(main, "Aprendiz agregado correctamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(main, "Error al agregar aprendiz");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, "Error en los datos ingresados");
                    ex.printStackTrace();
                }
                SwingUtilities.getWindowAncestor(confirmarButton).dispose();
            }

        });
    }

    private void limpiarCampos() {
        if (aprendiz.getItemCount() > 0) aprendiz.setSelectedIndex(0);
        if (empresa.getItemCount() > 0) empresa.setSelectedIndex(0);
        if (modalidad.getItemCount() > 0) modalidad.setSelectedIndex(0);
        if (ficha.getItemCount() > 0) ficha.setSelectedIndex(0);
        if (estado.getItemCount() > 0) estado.setSelectedIndex(0);
    }

    private void cargarEmpresas() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ID_empresas, nombre_empresa FROM empresas")) {

            DefaultComboBoxModel<String> modeloEmpresa = new DefaultComboBoxModel<>();
            listaIDEmpresas.clear();

            while (rs.next()) {
                listaIDEmpresas.add(rs.getInt("ID_empresas"));
                modeloEmpresa.addElement(rs.getString("nombre_empresa"));
            }

            empresa.setModel(modeloEmpresa);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar empresas");
            ex.printStackTrace();
        }
    }

    private void cargarFichas() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT codigo FROM fichas")) {

            DefaultComboBoxModel<String> modeloFichas = new DefaultComboBoxModel<>();
            while (rs.next()) {
                modeloFichas.addElement(rs.getString("codigo"));
            }

            ficha.setModel(modeloFichas);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar fichas");
            ex.printStackTrace();
        }
    }

    private void cargarModalidad() {
        String url = "jdbc:mysql://localhost:3306/saep";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT modalidad FROM modalidad")) {

            DefaultComboBoxModel<String> modeloModalidad = new DefaultComboBoxModel<>();
            while (rs.next()) {
                modeloModalidad.addElement(rs.getString("modalidad"));
            }

            modalidad.setModel(modeloModalidad);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(main, "Error al cargar modalidad");
            ex.printStackTrace();
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




    public static void main(String[] args) {
        JFrame frame = new JFrame("Crear Aprendiz");
        frame.setContentPane(new AprendizGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
