package spirograph;

import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

/** Class that adds the calculation of the pairs to the spirograph's object functionality. */
public class Hypocycloid extends Spirograph {

  public Hypocycloid(
      double fixedCircleRadius,
      double movingCircleRadius,
      double movingCircleOffset,
      int oscillations) {
    super(fixedCircleRadius, movingCircleRadius, movingCircleOffset, oscillations);
  }

  /**
   * Method that generates the pairs of coordinates of each point that is part of the spirograph's
   * image.
   */
  public void generatePairs() {
    double x = 0;
    double y = 0;

    // For loops that go through each oscillation of the spirograph and makes 1000 points for each
    // oscillation.
    for (int i = 0; i <= oscillations; i++) {
      for (double j = 0; j <= 1; j = j + 0.001) {

        // Calculate the period for the oscillation and scale it.
        double thetaPeriod = oscillations * 2 * Math.PI;
        double scaledTheta = thetaPeriod * j;

        pointList.add(calculatePoint(scaledTheta));
      }
    }
  }

  /**
   * Calculate the position of the point given the phase from the start.
   *
   * @param theta the phase.
   * @return the Pair object that contains the coordinates calculated.
   */
  public Pair calculatePoint(double theta) {
    // Calculate the centers of the gears and the phase between them.
    double movingCircleCenterX = (fixedCircleRadius - movingCircleRadius) * Math.cos(theta);
    double movingCircleCenterY = (fixedCircleRadius - movingCircleRadius) * Math.sin(theta);
    double movingCirclePhase =
        (fixedCircleRadius - movingCircleRadius) / movingCircleRadius * theta * -1;

    // Claculate the X and Y positions.
    double x =
        movingCircleCenterX + movingCircleOffset * movingCircleRadius * Math.cos(movingCirclePhase);
    double y =
        movingCircleCenterY + movingCircleOffset * movingCircleRadius * Math.sin(movingCirclePhase);

    return (new Pair(x, y));
  }

  /** Output to the terminal the pairs generated. */
  public void printPairs() {
    for (Pair i : pointList) {
      System.out.println("(" + i.getX() + "," + i.getY() + ")");
    }
  }
}
