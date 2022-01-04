package detector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Class that assembles all the steps of processing the image for circle detection into one single
 * image and displays it.
 */
public class DisplayImage {

  private final BufferedImage tmp1;

  public DisplayImage(String name) throws IOException {
    // Read the initial image and store it into a buffer.
    BufferedImage originalImage = ImageIO.read(new File(name));

    // Convert image to grayscale and make a copy of it to feed to the Sobeler step.
    BufferedImage grayScaleImage = convertToGrayScale(originalImage);
    BufferedImage tmp1 = copyBuffer(grayScaleImage);

    // Apply the Sobeler transformation to the image.
    Sobeler sobeler = new Sobeler(tmp1);

    // Make copies for the original image and the one with Sobel applied
    BufferedImage tmp2 = copyBuffer(sobeler.getSobeleredImage());
    BufferedImage tmp3 = copyBuffer(originalImage);

    // Apply the Circle Hough to find circles in the image.
    CircleHough circleHough = new CircleHough(tmp2, tmp3, tmp2.getWidth(), tmp2.getHeight(), 32);
    circleHough.setAccSize(10);
    circleHough.process();

    // Join the images generated through the process.
    BufferedImage joinedImages =
        joinBufferedImage(
            originalImage, grayScaleImage, sobeler.getSobeleredImage(), circleHough.getOriginal());

    // Display the obtained image.
    ImageIcon icon = new ImageIcon(joinedImages);
    JFrame frame = new JFrame();
    frame.setLayout(new FlowLayout());
    frame.setSize(originalImage.getWidth() * 2 + 15, (originalImage.getHeight() + 27) * 2);
    JLabel lbl = new JLabel();
    lbl.setIcon(icon);
    frame.add(lbl);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Method takes a buffered image and grayscales it.
   *
   * @param image the buffer to apply the gray scale to.
   * @return
   */
  public static BufferedImage convertToGrayScale(BufferedImage image) {
    // Create a grayscale filter.
    BufferedImage result =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

    // Put the image through the filter.
    Graphics g = result.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return result;
  }

  /**
   * Method that copies a buffer to a brand new buffered image object.
   *
   * @param bi the buffered image to copy.
   * @return the copy as buffered image.
   */
  static BufferedImage copyBuffer(BufferedImage bi) {
    // Get the color model, alphaPremultiplied property and raster of the buffered image.
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(null);

    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }

  /**
   * Method joins 4 images given into a single one.
   *
   * @param img1
   * @param img2
   * @param img3
   * @param img4
   * @return the resulting image.
   */
  public static BufferedImage joinBufferedImage(
      BufferedImage img1, BufferedImage img2, BufferedImage img3, BufferedImage img4) {
    // Do some calculations first for the width and height of the image placement.
    int offset = 5;
    int width = img1.getWidth() + img2.getWidth() + offset * 3;
    int height = (img1.getHeight() + offset) * 2;

    // Create a new buffer and draw images into the new image.
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = newImage.createGraphics();
    Color oldColor = g2.getColor();

    // Fill background.
    g2.setPaint(Color.WHITE);
    g2.fillRect(0, 0, width, height);

    // Add the new image to the collage.
    g2.setColor(oldColor);
    g2.drawImage(img1, null, 0, 0);
    g2.drawImage(img2, null, img1.getWidth() + offset, 0);
    g2.drawImage(img3, null, 0, img1.getHeight() + offset);
    g2.drawImage(img4, null, img1.getWidth() + offset, img1.getHeight() + offset);
    g2.dispose();

    return newImage;
  }
}
