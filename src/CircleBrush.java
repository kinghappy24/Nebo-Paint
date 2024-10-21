
import java.awt.*;

public class CircleBrush implements Brush {
    @Override
    public void draw(Graphics2D g2d, Point position, int size, Color color) {
        g2d.setColor(color);
        int radius = size / 2;
        g2d.fillOval(position.x - radius, position.y - radius, size, size);
    }
}
