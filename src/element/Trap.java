package element;

import java.awt.*;

public class Trap extends Element {
    public Trap(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Trap");
        this.setColor(new Color(0xFF0202));
        newLabel(getTitle()); // create label

        setVisible(false);
    }
}
