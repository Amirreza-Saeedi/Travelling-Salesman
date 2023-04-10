package element;

import java.awt.*;

public class Trap extends Element {
    public Trap(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Trap");
        this.setColor(new Color(0xFF0202));

        newLabel(getTitle()); // create label

    }
}
