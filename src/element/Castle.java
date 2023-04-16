package element;

import java.awt.*;

public class Castle extends Element {
    public Castle(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Castle");
        this.setColor(new Color(0xFFFF02));
        newLabel(getTitle()); // create label

        this.setVisible(true);
    }
}
