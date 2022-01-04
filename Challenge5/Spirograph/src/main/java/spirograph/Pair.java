package spirograph;

/** Pair object that will store point coordinates on XY axis. */
public class Pair {
  private double x = 0.0;
  private double y = 0.0;

  public Pair(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // Leave the coordinates to (0, 0) if no coordinates specified for constructor.
  public Pair() {}

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }
}
