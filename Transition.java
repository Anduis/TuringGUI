import java.awt.*;
import java.awt.geom.*;

public class Transition {
  Par start;
  Par target;
  char read;
  char write;
  Line2D.Double body_0;
  Path2D.Double body_1;
  int type;

  public Transition(Par start, Par target, int type) {
    this.start = start;
    this.target = target;
    this.type = type;
    read = 'r';
    write = 'w';
  }

  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    String text = read + ", " + write;
    g2d.setStroke(new BasicStroke(3f));

    double x1 = start.getNodo().getCenterX();
    double y1 = start.getNodo().getCenterY();
    double x2 = target.getNodo().getCenterX();
    double y2 = target.getNodo().getCenterY();
    if (type == 0) {
      double t = 1 - (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xf = (int) (x1 + (t * (x2 - x1)));
      int yf = (int) (y1 + (t * (y2 - y1)));
      t = 1 - (60) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xf2 = (int) (x1 + (t * (x2 - x1)));
      int yf2 = (int) (y1 + (t * (y2 - y1)));
      double offs = 45 * Math.PI / 180.0;
      double angle = Math.atan2(y1 - y2, x1 - x2);
      int[] xs = { xf + (int) (15 * Math.cos(angle + offs)), xf, xf + (int) (15 * Math.cos(angle - offs)) };
      int[] ys = { yf + (int) (15 * Math.sin(angle + offs)), yf, yf + (int) (15 * Math.sin(angle - offs)) };
      body_0 = new Line2D.Double(x1, y1, xf, yf);
      g2d.draw(body_0);
      g.drawPolyline(xs, ys, 3);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect(xf2 - 2, yf2 - 10, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, xf2, yf2);
      g2d.setColor(Color.decode("#000000"));
    } else if (type == 1) {
      double t = (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xi = (int) (x1 + (t * (x2 - x1)));
      int yi = (int) (y1 + (t * (y2 - y1)));
      t = 1 - t;
      int xf = (int) (x1 + (t * (x2 - x1)));
      int yf = (int) (y1 + (t * (y2 - y1)));
      double offs = 45 * Math.PI / 180.0;
      double angle = Math.atan2(y1 - y2, x1 - x2);
      int cix1 = xi + (int) (-50 * Math.cos(angle - offs));
      int ciy1 = yi + (int) (-50 * Math.sin(angle - offs));
      int cfx1 = xf + (int) (50 * Math.cos(angle + offs));
      int cfy1 = yf + (int) (50 * Math.sin(angle + offs));

      body_1 = new Path2D.Double();
      body_1.moveTo(xi, yi);
      body_1.curveTo(cix1, ciy1, cfx1, cfy1, xf, yf);
      g2d.draw(body_1);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect(cfx1 - 2, cfy1 - 10, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, cfx1, cfy1);
      g2d.setColor(Color.decode("#000000"));
    } else if (type == 2) {
      double t = (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xi = (int) (x1 + (t * (x2 - x1)));
      int yi = (int) (y1 + (t * (y2 - y1)));
      t = 1 - t;
      int xf = (int) (x1 + (t * (x2 - x1)));
      int yf = (int) (y1 + (t * (y2 - y1)));
      double offs = 45 * Math.PI / 180.0;
      double angle = Math.atan2(y1 - y2, x1 - x2);
      int cix2 = xi + (int) (-50 * Math.cos(angle + offs));
      int ciy2 = yi + (int) (-50 * Math.sin(angle + offs));
      int cfx2 = xf + (int) (50 * Math.cos(angle - offs));
      int cfy2 = yf + (int) (50 * Math.sin(angle - offs));

      body_1 = new Path2D.Double();
      body_1.moveTo(xi, yi);
      body_1.curveTo(cix2, ciy2, cfx2, cfy2, xf, yf);
      g2d.draw(body_1);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect(cfx2 - 2, cfy2 - 10, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, cfx2, cfy2);
      g2d.setColor(Color.decode("#000000"));
    } else if (type == 3) {
      x1 -= 15;
      x2 += 15;
      double t = 1 - (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xf = (int) (x1 + 5 + (t * (x2 - x1))) + 15;
      int yf = (int) (y1 + 10 + (t * (y2 - y1)));
      double x4 = start.getNodo().getCenterX();
      double y4 = start.getNodo().getCenterY() - 150;
      body_1 = new Path2D.Double();
      body_1.moveTo(x1, y1);
      body_1.curveTo(x1, y1, x4, y4, xf, yf);
      g2d.draw(body_1);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect((int) xf + 8, (int) yf - 40, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, (int) xf + 10, (int) yf - 30);
      g2d.setColor(Color.decode("#000000"));
    } else if (type == 4) {
      x1 -= 15;
      x2 += 15;
      double t = 1 - (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xf = (int) (x1 + 5 + (t * (x2 - x1))) + 15;
      int yf = (int) (y1 + 10 + (t * (y2 - y1)));
      double x4 = start.getNodo().getCenterX();
      double y4 = start.getNodo().getCenterY() + 150;
      body_1 = new Path2D.Double();
      body_1.moveTo(x1, y1);
      body_1.curveTo(x1, y1, x4, y4, xf, yf);
      g2d.draw(body_1);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect((int) xf + 8, (int) yf + 20, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, (int) xf + 10, (int) yf + 30);
      g2d.setColor(Color.decode("#000000"));
    } else if (type == 5) {
      y1 -= 15;
      y2 += 15;
      double t = 1 - (25) / Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
      int xf = (int) (x1 + 5 + (t * (x2 - x1)));
      int yf = (int) (y1 + 10 + (t * (y2 - y1))) + 15;
      double x4 = start.getNodo().getCenterX() - 150;
      double y4 = target.getNodo().getCenterY();
      body_1 = new Path2D.Double();
      body_1.moveTo(x1, y1);
      body_1.curveTo(x1, y1, x4, y4, xf, yf);
      g2d.draw(body_1);
      g2d.setColor(Color.decode("#FFFFFF"));
      g.fillRoundRect((int) xf - 52, (int) yf + 10, 25, 12, 3, 3);
      g2d.setColor(Color.decode("#007acc"));
      g.drawString(text, (int) xf - 50, (int) yf + 20);
      g2d.setColor(Color.decode("#000000"));
    }
  }
}