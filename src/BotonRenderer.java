package src;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Clase que extiende JPanel e implementa TableCellRenderer para proporcionar un botón
 * personalizado como renderizador para celdas en una JTable de Swing.
 */
public class BotonRenderer extends JPanel implements TableCellRenderer {
    private final JButton btnEditar;

    /**
     * Constructor de la clase BotonRenderer. Inicializa el panel y el botón "Editar".
     * Establece el diseño del panel y configura la apariencia del botón.
     */
    public BotonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(0, 122, 255)); // Establece el color de fondo del botón a azul.
        btnEditar.setForeground(Color.WHITE);             // Establece el color del texto del botón a blanco.
        btnEditar.setFocusPainted(false);             // Indica que no se debe pintar el rectángulo de enfoque del botón.
        btnEditar.setBorderPainted(false);             // Indica que no se debe pintar el borde del botón.
        btnEditar.setOpaque(true);                    // Indica que el botón es opaco, lo que permite que se muestre el color de fondo.

        add(btnEditar); // Añade el botón al panel.
    }

    /**
     * Devuelve el componente que se utilizará para renderizar la celda de la tabla.
     * En este caso, devuelve el JPanel que contiene el botón "Editar".
     *
     * @param table      La JTable que está renderizando la celda.
     * @param value      El valor del objeto para la celda a renderizar (no se usa aquí).
     * @param isSelected true si la celda está seleccionada, false en caso contrario.
     * @param hasFocus   true si la celda tiene el foco, false en caso contrario (no se usa aquí).
     * @param row        El índice de la fila de la celda que se está renderizando.
     * @param column     El índice de la columna de la celda que se está renderizando.
     * @return El componente (en este caso, el JPanel) que se utilizará para renderizar la celda.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Cambia el color de fondo del panel según si la celda está seleccionada o no.
        if (isSelected) {
            setBackground(table.getSelectionBackground()); // Usa el color de fondo de selección de la tabla.
        } else {
            setBackground(table.getBackground());         // Usa el color de fondo por defecto de la tabla.
        }
        return this; // Devuelve el panel (con el botón) para que se renderice en la celda.
    }
}
