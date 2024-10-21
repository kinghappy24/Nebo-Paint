
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {
    private List<DrawnShape> shapes = new ArrayList<>();
    private Brush currentBrush;
    private Color brushColor = Color.BLACK;
    private int brushSize = 10;

    public CanvasPanel() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addShape(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                addShape(e.getPoint());
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private void addShape(Point point) {
        shapes.add(new DrawnShape(currentBrush, point, brushSize, brushColor));
        repaint();
    }

    public void setCurrentBrush(Brush brush) {
        this.currentBrush = brush;
    }

    public void setBrushColor(Color color) {
        this.brushColor = color;
    }

    public void setBrushSize(int size) {
        this.brushSize = size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DrawnShape shape : shapes) {
            shape.draw((Graphics2D) g);
        }
    }

    private static class DrawnShape {
        private Brush brush;
        private Point position;
        private int size;
        private Color color;

        public DrawnShape(Brush brush, Point position, int size, Color color) {
            this.brush = brush;
            this.position = position;
            this.size = size;
            this.color = color;
        }

        public void draw(Graphics2D g2d) {
            brush.draw(g2d, position, size, color);
        }
    }
}
