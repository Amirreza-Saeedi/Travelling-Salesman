package element;

import java.awt.*;

public class Wall extends Element{
    public Wall(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Wall");
        this.setColor(Color.BLACK);
        newLabel(getTitle()); // create label

        setVisible(true);
    }
}
