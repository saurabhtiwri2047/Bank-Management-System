package bank.management.system;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Practice {

    boolean negative;

    public Practice(boolean negative) {
        this.negative = negative;
    }

    public String convert(BufferedImage image) {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                Color pixel = new Color(image.getRGB(x, y));

                // ✅ Correct grayscale formula
                double g = pixel.getRed() * 0.2989 +
                           pixel.getGreen() * 0.5870 +
                           pixel.getBlue() * 0.1140;

                char c = negative ? returnStrNeg(g) : returnStrPos(g);
                sb.append(c);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private char returnStrPos(double g) {
        if (g >= 230) return ' ';
        else if (g >= 200) return '.';
        else if (g >= 180) return '*';
        else if (g >= 160) return ':';
        else if (g >= 130) return 'o';
        else if (g >= 100) return '&';
        else if (g >= 70) return '8';
        else if (g >= 50) return '#';
        else return '@';
    }

    private char returnStrNeg(double g) {
        if (g >= 230) return '@';
        else if (g >= 200) return '#';
        else if (g >= 180) return '8';
        else if (g >= 160) return '&';
        else if (g >= 130) return 'o';
        else if (g >= 100) return ':';
        else if (g >= 70) return '*';
        else if (g >= 50) return '.';
        else return ' ';
    }

    // ✅ Resize Image
    private BufferedImage resize(BufferedImage img, int width) {
        int height = (img.getHeight() * width) / img.getWidth();
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                BufferedImage img = ImageIO.read(file);

                // ✅ Resize for better ASCII
                Practice converter = new Practice(false);
                img = converter.resize(img, 100);

                String ascii = converter.convert(img);

                JTextArea area = new JTextArea(ascii);
                area.setFont(new Font("Monospaced", Font.PLAIN, 8));
                area.setEditable(false);

                JScrollPane pane = new JScrollPane(area);

                JFrame frame = new JFrame("ASCII Art Generator");
                frame.add(pane);
                frame.setSize(600, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
}
