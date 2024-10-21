
import java.awt.*;

public class SquareBrush implements Brush {
    @Override
    public void draw(Graphics2D g2d, Point position, int size, Color color) {
        g2d.setColor(color);
        int halfSize = size / 2;
        g2d.fillRect(position.x - halfSize, position.y - halfSize, size, size);
    }
}
