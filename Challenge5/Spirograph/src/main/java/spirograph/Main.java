package spirograph;

import javax.swing.*;
import java.io.IOException;
import java.lang.Math;

public class Main {

  /**
   * Method that finds recursively the Greatest Common Denominator of two numbers.
   *
   * @param a the first number.
   * @param b the second number.
   * @return the result as integer.
   */
  static int gcd(int a, int b) {
    if (a == 0) return b;
    return gcd(b % a, a);
  }

  /**
   * Method that find the Least Common Multiple of two numbers.
   *
   * @param a the first number.
   * @param b the second number.
   * @return the as integer.
   */
  static int lcm(int a, int b) {
    return (a / gcd(a, b)) * b;
  }

  /**
   * Method that orchestrates all the operations to build and display the spirograph image.
   *
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    // Set between 0 and 1, the angle of the small wheel relative to the big one.
    double thetaFraction = 0.44;

    // Set between 50 200, the size of the first spirograph gear.
    int fixedGearSize = 100;

    // Set between -50 100, the size of the second spirograph gear.
    int movingGearSize = 33;

    // Set between 0 and 1
    double penPositionFraction = 0.95;

    // Calculate the radii of the two gears.
    double fixedGearRadius = fixedGearSize / (2 * Math.PI);
    double movingGearRadius = movingGearSize / (2 * Math.PI);

    // Calculate how many rotations there has to be for the spirograph to finish one cycle.
    int rotations = lcm(fixedGearSize, movingGearSize) / fixedGearSize;
    if (rotations < 0) {
      rotations = rotations * -1;
    }

    // The theta period to finish a cycle scaled to generate an appropriate amount of points for the
    // cycle.
    double thetaPeriod = rotations * 2 * Math.PI;
    double scaledTheta = thetaPeriod * thetaFraction * Math.pow(10, thetaFraction - 1);

    // Debug messages
    // System.out.println(rotations);
    // System.out.println(fixedGearRadius);
    // System.out.println(movingGearRadius);

    Hypocycloid hypocycloid =
        new Hypocycloid(fixedGearRadius, movingGearRadius, penPositionFraction, rotations);
    Pair pair = hypocycloid.calculatePoint(scaledTheta);
    hypocycloid.generatePairs();

    // Feel free to comment after the initial run.
    hypocycloid.writeToFile();

    // Debug operation that prints all the pairs generated.
    // hypocycloid.printPairs();

    // Setup and display an image with all the points generated plotted to obtain a spirograph.
    Painter painter = new Painter();
    painter.setPointList(hypocycloid.getPointList());
    JFrame frame = new JFrame("Points");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(painter);
    frame.setSize(1280, 720);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
