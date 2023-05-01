package element;

import java.awt.*;

public class Market extends Element {
    public final static Color COLOR = new Color(0xFFC002);
    public Market(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Market");
        this.setColor(COLOR);
        newLabel(getTitle()); // create label

        setVisible(true);
    }
}


