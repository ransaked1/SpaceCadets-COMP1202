package spirograph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/** Painter class for displaying the result. Uses a JPanel for display. */
public class Painter extends JPanel {

  ArrayList<Pair> pointList;

  public void setPointList(ArrayList<Pair> pointList) {
    this.pointList = pointList;
  }

  /**
   * Paints the result
   *
   * @param g the graphics object that contains the settings for the window.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    // Setting point color to blue
    g2d.setColor(Color.blue);

    for (int i = 0; i <= pointList.size() - 2; i++) {
      // Getting the size of the window.
      Dimension size = getSize();
      int w = size.width;
      int h = size.height;

      // Scaling and centering the components of two consecutive points.
      int x1 = (int) ((pointList.get(i).getX() * 10) + w / 2);
      int y1 = (int) ((pointList.get(i).getY() * 10) + h / 2);
      int x2 = (int) ((pointList.get(i + 1).getX() * 10) + w / 2);
      int y2 = (int) ((pointList.get(i + 1).getY() * 10) + h / 2);

      // Drawing a line between the two points.
      g2d.drawLine(x1, y1, x2, y2);
    }
  }
}
