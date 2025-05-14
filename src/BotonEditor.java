package src;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * Clase que extiende AbstractCellEditor e implementa TableCellEditor para proporcionar
 * un botón personalizado dentro de una celda de una tabla Swing. Este botón realiza
 * una acción específica al ser ক্লিকado.
 */
public class BotonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton btnEditar;
    private JTable tabla;
    private AccionBotonTabla accion;

    /**
     * Constructor de la clase BotonEditor.
     *
     * @param tabla  La JTable a la que se va a añadir el botón.
     * @param accion La interfaz AccionBotonTabla que define la acción a realizar
     * cuando se ক্লিকa el botón.
     */
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

    /**
     * Devuelve el componente que se mostrará en la celda de la tabla. En este caso,
     * devuelve el JPanel que contiene el botón.
     *
     * @param table      La JTable que está renderizando la celda.
     * @param value      El valor del objeto para la celda a renderizar.
     * @param isSelected true si la celda está seleccionada, false en caso contrario.
     * @param row        El índice de la fila de la celda que se está renderizando.
     * @param column     El índice de la columna de la celda que se está renderizando.
     * @return El componente que se mostrará en la celda de la tabla.
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    /**
     * Devuelve el valor actual de la celda que se está editando. En este caso,
     * como el botón no representa un valor directamente editable, devuelve null.
     *
     * @return El valor de la celda que se está editando.
     */
    @Override
    public Object getCellEditorValue() {
        return null;
    }

    /**
     * Determina si la celda es editable. En este caso, la celda siempre es editable
     * para que el botón responda a los ক্লিকs.
     *
     * @param e El evento que desencadenó la llamada a este método.
     * @return true si la celda es editable, false en caso contrario.
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true; // Permitir que la celda sea editable
    }
}

