package detector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/** Sobel transformation that detects and highlights all the edges found in the image. */
public class Sobeler {

  BufferedImage sobeleredImage;
  double scale;
  int[][] edgeColors;
  int imageWidth;
  int iamgeHeight;

  /**
   * Driver method that applies the Sobel transformation to every pixel and generates the final
   * image with it applied.
   *
   * @param image the image to apply the Sobel operation to.
   */
  public Sobeler(BufferedImage image) {
    System.out.println("Started");

    imageWidth = image.getWidth();
    iamgeHeight = image.getHeight();
    edgeColors = new int[imageWidth][iamgeHeight];
    int maxGradient = -1;

    // Go through the whole image with these two loops.
    for (int i = 1; i < imageWidth - 1; i++) {
      for (int j = 1; j < iamgeHeight - 1; j++) {
        double gval = calculateGradientValue(image, i, j);
        int sobelGradient = (int) gval;

        // Save the highest value to make it the most intense color and scale down from there.
        if (maxGradient < sobelGradient) {
          maxGradient = sobelGradient;
        }
        edgeColors[i][j] = sobelGradient;
      }
    }

    // Calculate the most intense point in the image
    scale = 255.0 / maxGradient;

    // Go through the image and replace the color to white with the intensity given.
    for (int i = 1; i < imageWidth - 1; i++) {
      for (int j = 1; j < iamgeHeight - 1; j++) {
        int edgeColor = edgeColors[i][j];
        edgeColor = (int) (edgeColor * scale);
        edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;
        image.setRGB(i, j, edgeColor);
      }
    }

    // Save the image generated.
    sobeleredImage = image;

    System.out.println("max : " + maxGradient);
    System.out.println("Finished");
  }

  /**
   * Helper method that greyscales the rgb values of a pixel and all of its neighbours then applies
   * the Sobel formula to calculate a value for that pixel.
   *
   * @param image the image to operate on.
   * @param i first coordinate of the pixel.
   * @param j second coordinate of the pixel.
   * @return the calculated value for the point.
   */
  private double calculateGradientValue(BufferedImage image, int i, int j) {
    int val00 = getGrayScale(image.getRGB(i - 1, j - 1));
    int val01 = getGrayScale(image.getRGB(i - 1, j));
    int val02 = getGrayScale(image.getRGB(i - 1, j + 1));

    int val10 = getGrayScale(image.getRGB(i, j - 1));
    int val11 = getGrayScale(image.getRGB(i, j));
    int val12 = getGrayScale(image.getRGB(i, j + 1));

    int val20 = getGrayScale(image.getRGB(i + 1, j - 1));
    int val21 = getGrayScale(image.getRGB(i + 1, j));
    int val22 = getGrayScale(image.getRGB(i + 1, j + 1));

    int gx = ((-1 * val00) + val02) + ((-2 * val10) + (2 * val12)) + ((-1 * val20) + (val22));
    int gy = ((-1 * val00) + (-2 * val01) + (-1 * val02)) + (val20 + (2 * val21) + val22);

    double gval = Math.sqrt((gx * gx) + (gy * gy));
    return gval;
  }

  /**
   * Convert the rgb value to a grayscaled value.
   *
   * @param rgb value to convert.
   * @return the grayscale value of that rgb color.
   */
  private static int getGrayScale(int rgb) {
    // Get each color value from rgb value
    int r = (rgb >> 16) & 0xff;
    int g = (rgb >> 8) & 0xff;
    int b = (rgb) & 0xff;

    return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

  public BufferedImage getSobeleredImage() {
    return sobeleredImage;
  }
}
