package Example_Screen.View;

import javax.swing.*;
import java.awt.*;
/**
 * Este panel personalizado es el que dibuja un gráfico circular para mostrar el porcentaje de progreso del aprendiz.
 */
public class GraficoCircular extends JPanel {
    private int progreso;

    /**
     * Constructor que recibe el porcentaje de progreso a mostrar.
     *
     * @param progreso Porcentaje de progreso (0 a 100)
     */
    public GraficoCircular(int progreso) {this.progreso = progreso;
        setPreferredSize(new Dimension(200, 200));
    }
    /**
     * Dibuja el gráfico circular con el progreso actual.
     *
     * @param g Objeto Graphics para dibujar en el panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = Math.min(getWidth(), getHeight()) - 200;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(15));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawOval(x, y, diameter, diameter);

        g2.setColor(Color.GREEN.darker());
        int arcAngle = (int) (progreso * 3.6);
        g2.drawArc(x, y, diameter, diameter, 90, -arcAngle);

        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String texto = progreso + "%";
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(texto)) / 2;
        int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.setColor(Color.BLACK);
        g2.drawString(texto, textX, textY);
    }
}

