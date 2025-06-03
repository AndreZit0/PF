package Example_Screen.View;

import Example_Screen.Connection.DBConnection;
import Example_Screen.View.Usuarios_Registrados.VerUsuariosRegistrados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

import static Example_Screen.View.Login.LoginGUI.idUsuarioActual;
import static Example_Screen.View.Login.LoginGUI.rolUsuarioActual;

public class NovedadesAprendices extends Component {

    private VerUsuariosRegistrados verUsuarios;

    public NovedadesAprendices(VerUsuariosRegistrados verUsuarios) {
        this.verUsuarios = verUsuarios;
    }
    /**
     * Método para obtener todas las novedades de los aprendices asignados a un instructor
     * @param idInstructor ID del instructor logueado
     * @param vista Instancia de VerUsuariosRegistrados para mostrar los datos
     */
    public void obtenerNovedadesAprendices(int idInstructor, VerUsuariosRegistrados vista) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==4; // Ninguna celda es editable para consulta
            }
        };

        // Definir las columnas de la tabla
        model.addColumn("Aprendiz");
        model.addColumn("Documento");
        model.addColumn("Novedad");
        model.addColumn("Fecha");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps;
            ResultSet rs;

            // Verifica si el rol es administrador del sistema (6)
            if (rolUsuarioActual == 6) {
                            // Admin ve TODAS las novedades
                            String sqlAdmin = """
                    SELECT u.nombres, u.apellidos, u.numero, n.novedad, n.fecha
                    FROM novedades n
                    INNER JOIN usuarios u ON n.ID_aprendiz = u.ID_usuarios
                    WHERE n.ID_aprendiz = ?
                    ORDER BY n.fecha DESC
                """;

                ps = con.prepareStatement(sqlAdmin);
                ps.setInt(1,idUsuarioActual);

            }else {
                // Evaluador ve SOLO las novedades de sus aprendices asignados
                String sqlEvaluador = """
                    SELECT u.nombres, u.apellidos, u.numero, n.novedad, n.fecha
                    FROM novedades n
                    INNER JOIN usuarios u ON n.ID_aprendiz = u.ID_usuarios
                    INNER JOIN aprendices a ON u.ID_usuarios = a.ID_usuarios
                    WHERE a.ID_instructor = ?
                    ORDER BY n.fecha DESC
                """;

                ps = con.prepareStatement(sqlEvaluador);
                ps.setInt(1, idInstructor);  // Usa tu variable actual para el ID del usuario logueado
            }

            rs = ps.executeQuery();

            // Formatear fecha para mostrar
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            // Llenar el modelo con los datos
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("nombres") + " " + rs.getString("apellidos");
                fila[1] = rs.getString("numero");
                fila[2] = rs.getString("novedad");
                fila[3] = dateFormat.format(rs.getTimestamp("fecha"));

                model.addRow(fila);
            }

            // Configurar la tabla en la vista
            JTable tabla = vista.getTable();
            tabla.setModel(model);

            // Configurar filtro de búsqueda
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            tabla.setRowSorter(sorter);
            vista.setSorter(sorter);

            // Inicializar filtro de búsqueda
            vista.inicializarFiltro(vista.getBusqueda(), tabla);

            agregarBotonComoColumna(model, tabla);

            // Aplicar estilos personalizados
            vista.componentesPersonalizado();

            // Cerrar conexiones
            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las novedades: " + e.getMessage(),
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para agregar el botón como una nueva columna
     */
    private void agregarBotonComoColumna(DefaultTableModel model, JTable tabla) {
        // Agregar la columna del botón
        model.addColumn("Novedades");

        // Asegurar que siempre haya al menos una fila para mostrar el botón
        if (model.getRowCount() == 0) {
            model.addRow(new Object[]{"", "", "", "", "Agregar"});
        } else {
            // Si ya hay datos, agregar el botón solo en la primera fila
            model.setValueAt("Agregar", 0, 4);
        }

        // Configurar el renderer y editor para la columna del botón
        tabla.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tabla.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());

        // Configurar el ancho de la columna
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(4).setMaxWidth(120);
        tabla.getColumnModel().getColumn(4).setMinWidth(120);
    }

    /**
     * Editor para manejar los clicks del botón
     */
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            configurarEstilo();
        }

        private void configurarEstilo() {
            setBackground(new Color(0, 123, 255));
            setForeground(Color.WHITE);
            setFont(new Font("Calibri", Font.BOLD, 14));
            setFocusPainted(false);
            setBorderPainted(false);

            // Para que el botón no ocupe toda la celda
            setPreferredSize(new Dimension(90, 29));
            setMaximumSize(new Dimension(90, 29));
            setMinimumSize(new Dimension(90, 29));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == 0) {
                setText("Agregar");

                // Panel contenedor para centrar el botón
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
                panel.setOpaque(true);
                panel.setBackground(table.getBackground());
                panel.add(this);
                return panel;
            } else {
                // Celdas vacías para otras filas
                JLabel emptyLabel = new JLabel("");
                emptyLabel.setOpaque(true);
                emptyLabel.setBackground(table.getBackground());
                return emptyLabel;
            }
        }
    }

    /**
     * Editor para manejar los clicks del botón
     */
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private JPanel panel;
        private boolean isPushed;

        public ButtonEditor() {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            configurarEstilo();

            // Panel contenedor para centrar el botón
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
            panel.setOpaque(true);
            panel.add(button);

            // Agregar el listener al botón
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarModalAgregarNovedad();
                    fireEditingStopped();
                }
            });
        }

        private void configurarEstilo() {
            button.setBackground(new Color(0, 123, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Calibri", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setBorderPainted(false);

            // Para que el botón no ocupe toda la celda
            button.setPreferredSize(new Dimension(90, 29));
            button.setMaximumSize(new Dimension(90, 29));
            button.setMinimumSize(new Dimension(90, 29));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (row == 0) {
                isPushed = true;
                button.setText("Agregar ➕");
                panel.setBackground(table.getBackground());
                return panel;
            }
            return new JLabel("");
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return "Agregar ➕";
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    /**
     * Método para agregar el botón "Agregar Novedad" en la parte superior de la tabla
     */

    public void mostrarModalAgregarNovedad() {
        // Crear el diálogo modal
        JDialog modal = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Agregar Novedad", true);
        modal.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        modal.setSize(500, 400); // Tamaño más grande
        modal.setLocationRelativeTo(this);
        modal.setLayout(new BorderLayout());

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Agregar Novedad", JLabel.CENTER);
        titulo.setFont(new Font("Calibri", Font.BOLD, 20)); // Calibri BOLD más grande
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Área de texto para escribir la novedad
        JTextArea textAreaNovedad = new JTextArea(8, 30);
        textAreaNovedad.setLineWrap(true);
        textAreaNovedad.setWrapStyleWord(true);
        textAreaNovedad.setFont(new Font("Calibri", Font.BOLD, 16)); // Calibri BOLD más grande
        textAreaNovedad.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Descripción de la novedad:"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(textAreaNovedad);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new BorderLayout()); // Cambio a BorderLayout

        // Botones con tamaño más grande
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setPreferredSize(new Dimension(120, 40)); // Tamaño más grande

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(120, 40)); // Tamaño más grande

        // Estilo de botones
        btnAceptar.setBackground(Color.decode("#39A900")); // Color verde específico
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);
        btnAceptar.setFont(new Font("Calibri", Font.BOLD, 18)); // Calibri BOLD más grande
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de manito

        // Hover effect para btnAceptar
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptar.setBackground(Color.decode("#2E7D00")); // Verde más oscuro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptar.setBackground(Color.decode("#39A900")); // Verde original
            }
        });

        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new Font("Calibri", Font.BOLD, 18)); // Calibri BOLD más grande
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de manito

        // Hover effect para btnCancelar
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(198, 54, 43)); // Rojo más oscuro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(244, 67, 54)); // Rojo original
            }
        });

        // Añadir botones a los extremos
        panelBotones.add(btnAceptar, BorderLayout.EAST);
        panelBotones.add(btnCancelar, BorderLayout.WEST);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        modal.add(panelPrincipal);

        // Acción del botón Aceptar
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String novedad = textAreaNovedad.getText().trim();
                if (novedad.isEmpty()) {
                    JOptionPane.showMessageDialog(modal,
                            "Por favor, ingrese una descripción para la novedad.",
                            "Campo requerido",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Insertar en la base de datos
                if (insertarNovedad(novedad)) {
                    JOptionPane.showMessageDialog(modal,
                            "Novedad agregada exitosamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Llamar al método
                    if (verUsuarios != null) {
                        verUsuarios.cargarNovedadesEnTabla();
                    }

                    modal.dispose();
                } else {
                    JOptionPane.showMessageDialog(modal,
                            "Error al agregar la novedad. Intente nuevamente.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Acción del botón Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarSalida(modal, textAreaNovedad);
            }
        });

        // Acción para el botón X de cerrar
        modal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalida(modal, textAreaNovedad);
            }
        });

        modal.setVisible(true);
    }

    // Método para confirmar salida
    public void confirmarSalida(JDialog modal, JTextArea textArea) {
        if (!textArea.getText().trim().isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(
                    modal,
                    "¿Seguro quieres salir?\nSe perderá lo que hayas escrito.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                modal.dispose();
            }
        } else {
            modal.dispose();
        }
    }

    public boolean insertarNovedad(String novedad) {
        String sql = "INSERT INTO novedades (ID_aprendiz, novedad, fecha) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Aquí debes obtener el ID del aprendiz actual
            // Esto depende de cómo manejes la sesión en tu aplicación

            pstmt.setInt(1, idUsuarioActual);
            pstmt.setString(2, novedad);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }










}