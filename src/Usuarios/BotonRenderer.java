package Usuarios;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BotonRenderer extends JPanel implements TableCellRenderer {
    private final JButton btnEditar;

    public BotonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(0, 122, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);
        btnEditar.setOpaque(true);

        add(btnEditar);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Opcional: cambiar color al seleccionar fila
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}
