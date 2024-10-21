
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SVGBrush implements Brush {
    private BufferedImage svgImage;

    public SVGBrush(File svgFile) {
        try {
            svgImage = Utils.renderSVGToImage(svgFile);
        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d, Point position, int size, Color color) {
        if (svgImage != null) {
            AffineTransform transform = new AffineTransform();
            int halfSize = size / 2;
            transform.translate(position.x - halfSize, position.y - halfSize);
            double scaleX = (double) size / svgImage.getWidth();
            double scaleY = (double) size / svgImage.getHeight();
            transform.scale(scaleX, scaleY);
            g2d.drawImage(svgImage, transform, null);
        }
    }
}
