package element;

import java.awt.*;

public class Trace extends Rectangle {
    public int _x; // board x coordinate
    public int _y; // board y coordinate

    public Trace(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Trace(Dimension d) {
        super(d);
    }

    public void setTrace(int xPixel, int yPixel, int xCoordinate, int yCoordinate) {
        this.x = xPixel;
        this.y = yPixel;
        this._x = xCoordinate;
        this._y = yCoordinate;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0x81000000, true));
        g2.fillRect(x, y, width, height);
    }
}
