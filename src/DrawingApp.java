
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DrawingApp extends JFrame {
    private CanvasPanel canvas;
    private MusicPlayer musicPlayer;
    private Brush currentBrush;
    private Color brushColor = Color.BLACK;
    private int brushSize = 10;
    private Color backgroundColor = Color.WHITE;

    public DrawingApp() {
        setTitle("Drawing Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        musicPlayer = new MusicPlayer();

        canvas = new CanvasPanel();
        canvas.setBackground(backgroundColor);

        setupUI();

        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupUI() {
        JToolBar toolbar = new JToolBar();
        toolbar.setBackground(Color.decode("#053602"));
        toolbar.setFloatable(false);

        // Pick Color Button
        JButton pickColorButton = Utils.createButton("Pick Color");
        pickColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Select Brush Color", brushColor);
            if (selectedColor != null) {
                brushColor = selectedColor;
                canvas.setBrushColor(brushColor);
            }
        });
        toolbar.add(pickColorButton);

        // Pick Background Color Button
        JButton pickBgColorButton = Utils.createButton("Pick Background Color");
        pickBgColorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(this, "Select Background Color", backgroundColor);
            if (selectedColor != null) {
                backgroundColor = selectedColor;
                canvas.setBackground(backgroundColor);
            }
        });
        toolbar.add(pickBgColorButton);

        JLabel brushSizeLabel = new JLabel("Brush Size:");
        brushSizeLabel.setForeground(Color.WHITE);
        toolbar.add(brushSizeLabel);

        JSlider brushSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, brushSize);
        brushSizeSlider.addChangeListener(e -> {
            brushSize = brushSizeSlider.getValue();
            canvas.setBrushSize(brushSize);
        });
        toolbar.add(brushSizeSlider);

        JLabel brushShapeLabel = new JLabel("Brush Shape:");
        brushShapeLabel.setForeground(Color.WHITE);
        toolbar.add(brushShapeLabel);

        String[] brushShapes = {"Circle", "Square"};
        File brushesDir = new File("assets/brushes/");
        File[] svgFiles = brushesDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".svg"));
        if (svgFiles != null) {
            String[] svgBrushes = new String[svgFiles.length];
            for (int i = 0; i < svgFiles.length; i++) {
                svgBrushes[i] = svgFiles[i].getName();
            }
            brushShapes = Utils.concatArrays(brushShapes, svgBrushes);
        }
        JComboBox<String> brushShapeComboBox = new JComboBox<>(brushShapes);
        brushShapeComboBox.addActionListener(e -> {
            String selectedShape = (String) brushShapeComboBox.getSelectedItem();
            switch (selectedShape) {
                case "Circle":
                    currentBrush = new CircleBrush();
                    break;
                case "Square":
                    currentBrush = new SquareBrush();
                    break;
                default:
                    currentBrush = new SVGBrush(new File("assets/brushes/" + selectedShape));
                    break;
            }
            canvas.setCurrentBrush(currentBrush);
        });
        toolbar.add(brushShapeComboBox);

        currentBrush = new CircleBrush();
        canvas.setCurrentBrush(currentBrush);
        canvas.setBrushColor(brushColor);
        canvas.setBrushSize(brushSize);

        JButton musicControlButton = Utils.createButton("Music On");
        musicControlButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        musicControlButton.addActionListener(e -> {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                musicControlButton.setText("Music Off");
                musicControlButton.setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                musicPlayer.play();
                musicControlButton.setText("Music On");
                musicControlButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(musicControlButton);

        add(toolbar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingApp());
    }
}
