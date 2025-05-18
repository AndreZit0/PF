package Example_Screen.View.Usuarios_Registrados;

import Example_Screen.Connection.DBConnection;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Example_Screen.View.Administrador.Administrador.verUsuarioPorRol;

public class VerUsuariosRegistrados {
    private JTable table1;
    private JPanel panelVerUsuario;
    private JPanel panelFiltro;
    private JTextField busqueda;
    private JLabel tipoUsuario;
    private JPanel panelTable;
    private JScrollPane scroll;

    public JTable getTable() {
        return table1;
    }

    public JTextField getBusqueda() {
        return busqueda;
    }


    public JPanel getPanel() {
        return panelVerUsuario;
    }


    // Declarar sorter como atributo de clase
    private TableRowSorter<TableModel> sorter;

    public void inicializarFiltro(JTextField busqueda, JTable table1) {
        busqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void removeUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            public void changedUpdate(DocumentEvent e) {
                aplicarFiltro();
            }

            private void aplicarFiltro() {
                String texto = busqueda.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        });
    }

    public void obtenerDatosUsuario() {
        DefaultTableModel model = new DefaultTableModel() {
            // Para evitar que las celdas sean editables
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        model.addColumn("Tipo de Documento");
        model.addColumn("Número");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Email");
        model.addColumn("Ver Perfil");  // Nueva columna

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT tipo_dc, numero, nombres, apellidos, email FROM usuarios WHERE id_rol = " + verUsuarioPorRol);

            while (rs.next()) {
                Object[] dato = new Object[6];
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);
                dato[4] = rs.getString(5);
                dato[5] = "Ver Perfil"; // El texto del botón
                model.addRow(dato);
            }

            table1.setModel(model);
            sorter = new TableRowSorter<>(model);
            table1.setRowSorter(sorter);

            // Renderizador y editor para el botón
            table1.getColumn("Ver Perfil").setCellRenderer(new ButtonRenderer());
            table1.getColumn("Ver Perfil").setCellEditor(new ButtonEditor(new JCheckBox()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Renderiza el botón
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "Ver Perfil" : value.toString());
            return this;
        }
    }

    // Editor con botón de fondo blanco
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String numero;
        private boolean clicked;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                     int row, int column) {
            this.table = table;
            numero = table.getValueAt(row, 1).toString();
            button.setText("Ver Perfil");
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                abrirPerfilUsuario(numero);
            }
            clicked = false;
            return "Ver Perfil";
        }

        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void abrirPerfilUsuario(String numeroDocumento) {
            JOptionPane.showMessageDialog(button, "Abrir perfil de usuario con Número: " + numeroDocumento);
        }
    }

    String rol = null;

    public void tipoDeUsuarioRegistrado(){
        if (verUsuarioPorRol == 1){
            rol = "Aprendices Registrados";
        }
        if (verUsuarioPorRol == 2){
            rol = "Evaluadores Registrados";
        }
        if (verUsuarioPorRol == 3){
            rol = "Empresas Registradas";
        }
        else if (verUsuarioPorRol == 4) {
            rol = "Auxiliares Registrados";
        }

        tipoUsuario.setText(rol);

    }

    public void componentesPersonalizado() {
        Border bottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#39A900"));
        busqueda.setBorder(bottom);
        table1.getTableHeader().setForeground(Color.decode("#ffffff")); // Color del texto
        table1.getTableHeader().setBackground(Color.decode("#39A900"));

        table1.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Cuerpo de la tabla
        table1.setRowHeight(25);

        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Encabezado



        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
        scroll.getViewport().setBackground(Color.decode("#e8e6e8"));
    }
    public void tipoDeUsuarioRegistrado1() {

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rol FROM rol WHERE id_rol = " + verUsuarioPorRol);

            if (rs.next()) {
                rol = rs.getString("rol");
                tipoUsuario.setText(rol);  // mostrar rol en el JLabel
            } else {
                tipoUsuario.setText("Rol no encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tipoUsuario.setText("Error de conexión");
        }
        tipoUsuario.setText(rol + " Registrado");
    }
}
