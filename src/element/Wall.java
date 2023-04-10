package element;

import java.awt.*;

public class Wall extends Element{
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Wall");
        this.setColor(Color.BLACK);

        newLabel(getTitle()); // create label

    }
}
