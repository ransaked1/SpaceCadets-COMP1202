package detector;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Circle Hough class that searches for circles in the image. Takes an image that had Sobel applied
 * to it. Going through the image, it saved in an accumulator the number of circles that could
 * contain that pixel.
 */
public class CircleHough {

  // Intensity of the pixel to count it in the accumulator.
  final int THRSHLD = 67;
  BufferedImage input;
  BufferedImage original;
  int[][] output;
  int width;
  int height;
  int[][][] accumulator;
  int accSize = 1;
  int[] results;
  int maxR;

  public CircleHough(
      BufferedImage inputIn, BufferedImage originalIn, int widthIn, int heightIn, int maxRadius) {
    System.out.println("Hough Circle Detection...");

    maxR = maxRadius;
    width = widthIn;
    height = heightIn;
    output = new int[width][height];
    input = inputIn;
    original = originalIn;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        output[x][y] = 0xff000000; // Default image background color is black
      }
    }
  }

  public void setAccSize(int accSize) {
    this.accSize = accSize;
  }

  public BufferedImage getInput() {
    return input;
  }

  public BufferedImage getOriginal() {
    return original;
  }

  /**
   * Method goes through the image counting the circles and returns an array of scores for each
   * pixel in the image.
   *
   * @return the bidimensional array.
   */
  public int[][] process() {
    accumulator = new int[width][height][maxR + 1];

    // Do the search for all circles between the sizes 3 and maximum radius given.
    for (int r = 3; r <= maxR; r++) {
      setAccumulatorToZero(r);
      findCircles(r);
      int max = findAccumulatorMaxima(r);
      scaleAccumulatorToMaxima(r, (double) max);
    }

    // Combine the accumulators and print the result.
    combineAccumulators();
    printState(0);

    // Draw the circles.
    findMaxima();
    return output;
  }

  /**
   * Helper method that resets all the values of the accumulator to 0.
   *
   * @param r
   */
  private void setAccumulatorToZero(int r) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        accumulator[x][y][r] = 0;
      }
    }
  }

  /**
   * Method finds all the circles that could contain that pixel and increases the accumulator value
   * for that position.
   *
   * @param r the radius of the circle.
   */
  private void findCircles(int r) {
    int x0, y0;
    double t;

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int color = input.getRGB(x, y);

        // Check that the pixel is part of an edge.
        if ((color & 0xff) > THRSHLD) {

          // Count all the circles that pass through that point.
          for (int theta = 0; theta < 360; theta++) {
            t = (theta * Math.PI) / 180; // Angle value 0 ~ 2*PI.
            x0 = (int) Math.round(x - r * Math.cos(t));
            y0 = (int) Math.round(y - r * Math.sin(t));
            // Check that the point is inside the image and count it.
            if (x0 < width && x0 > 0 && y0 < height && y0 > 0) {
              accumulator[x0][y0][r] += 1;
            }
          }
        }
      }
    }
  }

  /**
   * Get the maximum value in the accumulator.
   *
   * @param r the radius of the circle.
   * @return the maximum found in the accumulator.
   */
  private int findAccumulatorMaxima(int r) {
    int max = 0;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (accumulator[x][y][r] > max) {
          max = accumulator[x][y][r];
        }
      }
    }
    return max;
  }

  /**
   * Scale all the accumulator values to the maxima found previously.
   *
   * @param r the radius of the circle.
   * @param max the maxima found.
   */
  private void scaleAccumulatorToMaxima(int r, double max) {
    int value;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        value = (int) (((double) accumulator[x][y][r] / max) * 255.0);
        accumulator[x][y][r] = 0xff000000 | (value << 16 | value << 8 | value);
      }
    }
  }

  /** Combine the accumulator values for each circle size into one big accumulator. */
  private void combineAccumulators() {
    for (int r = 3; r <= maxR; r++) {
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          accumulator[x][y][0] += accumulator[x][y][r];
        }
      }
    }
  }

  /**
   * Debug method for accumulator values.
   *
   * @param r the radius of the circle.
   */
  private void printState(int r) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if ((accumulator[x][y][r] & 0xff) < 10) {
          System.out.print(" " + (accumulator[x][y][r] & 0xff) + "  ");
        } else if ((accumulator[x][y][r] & 0xff) < 99) {
          System.out.print(" " + (accumulator[x][y][r] & 0xff) + " ");
        } else {
          System.out.print((accumulator[x][y][r] & 0xff) + " ");
        }
      }
      System.out.println();
    }
  }

  /** Find the most likely circles found in the image and draw them. */
  private void findMaxima() {
    // Flatten accumulator.
    results = new int[accSize * 3];

    // Get the maximum number of circle values.
    for (int r = 3; r <= maxR; r++) {
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          int value = (accumulator[x][y][0] & 0xff);

          // If its higher than lowest value add it and then sort.
          if (value > results[(accSize - 1) * 3]) {

            // Add to bottom of array.
            results[(accSize - 1) * 3] = value; // Pixel value
            results[(accSize - 1) * 3 + 1] = x; // Coordinate X
            results[(accSize - 1) * 3 + 2] = y; // Coordinate Y

            // Shift up until in right place.
            int i = (accSize - 2) * 3;
            while ((i >= 0) && (results[i + 3] > results[i])) {
              for (int j = 0; j < 3; j++) {
                int temp = results[i + j];
                results[i + j] = results[i + 3 + j];
                results[i + 3 + j] = temp;
              }
              i = i - 3;
              if (i < 0) break;
            }
          }
        }
      }
      // Draw the circles found.
      for (int i = accSize - 1; i >= 0; i--) {
        drawCircle(results[i * 3 + 1], results[i * 3 + 2], r);
      }
    }
    return output;
  }

  /**
   * Drawer method for the red circle to highlight the find.
   *
   * @param xCenter circle center X coordinate.
   * @param yCenter circle center Y coordinate.
   * @param r circle radius.
   */
  private void drawCircle(int xCenter, int yCenter, int r) {
    int x, y, r2;
    int radius = r;
    r2 = r * r;

    original.setRGB(xCenter, yCenter, Color.RED.getRGB());
  }
}
