package spirograph;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Spirograph object that manages output to file and the object constructor. */
public class Spirograph {

  int size;
  double fixedCircleRadius;
  double movingCircleRadius;
  double movingCircleOffset;
  int oscillations;

  ArrayList<Pair> pointList = new ArrayList<Pair>();

  public Spirograph(
      double fixedCircleRadius,
      double movingCircleRadius,
      double movingCircleOffset,
      int oscillations) {
    this.fixedCircleRadius = fixedCircleRadius;
    this.movingCircleRadius = movingCircleRadius;
    this.movingCircleOffset = movingCircleOffset;
    this.oscillations = oscillations;
  }

  /**
   * Getter for the list of points generated.
   *
   * @return an array list of the Point objects.
   */
  public ArrayList<Pair> getPointList() {
    return pointList;
  }

  /**
   * Method that writes to the data.txt file.
   *
   * @throws IOException expception to throw if writing fails.
   */
  public void writeToFile() throws IOException {
    FileWriter fw = new FileWriter("data.txt");

    System.out.println("Writing to file");

    for (Pair i : pointList) {
      fw.write("(" + i.getX() + "," + i.getY() + ")\n");
    }
    fw.close();
  }
}
