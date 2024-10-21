import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;


public class Utils {
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#8ACE00"));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(150, 30));
        return button;
    }

    public static String[] concatArrays(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];
        int i = 0;
        for (String s : first) {
            result[i++] = s;
        }
        for (String s : second) {
            result[i++] = s;
        }
        return result;
    }

    public static BufferedImage renderSVGToImage(File svgFile) throws IOException, TranscoderException {
        TranscoderInput input = new TranscoderInput(svgFile.toURI().toString());
        PNGTranscoder transcoder = new PNGTranscoder();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        transcoder.transcode(input, null);
        byte[] imageData = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }
}
