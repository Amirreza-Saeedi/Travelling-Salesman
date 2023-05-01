package element;

import java.awt.*;

public class Castle extends Element {
    public final static Color COLOR = new Color(0xFFFF02);
    public Castle(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Castle");
        this.setColor(COLOR);
        newLabel(getTitle()); // create label

        this.setVisible(true);
    }
}
