package myClasses;
import java.awt.*;

public abstract class MyGraphics2D extends Graphics2D {

    public void fillRect(Rectangle rectangle) {
        fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
