package element;

import java.awt.*;

public class Loot extends Element {
    public Loot(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Loot");
        this.setColor(new Color(0x02B1F0));

        newLabel(getTitle()); // create label

    }
}
