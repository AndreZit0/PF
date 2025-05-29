package Usuarios;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class BotonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton btnEditar;
    private JTable tabla;
    private AccionBotonTabla accion;

    public BotonEditor(JTable tabla, AccionBotonTabla accion) {
        this.tabla = tabla;
        this.accion = accion;

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnEditar = new JButton("Editar");

        // Estilo del botón
        btnEditar.setBackground(new Color(0, 122, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setOpaque(true);

        panel.add(btnEditar);

        // Acción que se ejecutará cuando el botón sea presionado
        btnEditar.addActionListener(e -> {
            int filaVista = tabla.getEditingRow(); // Fila que se está editando
            if (filaVista >= 0) {
                int filaModelo = tabla.convertRowIndexToModel(filaVista); // Convertir a índice real del modelo
                Object valorId = tabla.getModel().getValueAt(filaModelo, 0); // Asume que el ID está en la columna 0

                if (valorId instanceof Integer) {
                    int id = (int) valorId;
                    accion.ejecutar(id); // Ejecuta la acción con el ID
                } else {
                    JOptionPane.showMessageDialog(null, "El ID no es válido.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No hay una fila válida seleccionada.");
            }
            fireEditingStopped(); // Detener la edición
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Permitir que la celda sea editable
    }
}
