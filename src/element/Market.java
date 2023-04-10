package element;


import java.awt.*;

public class Market extends Element {

    public Market(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTitle("Market");
        this.setColor(new Color(0xFFC002));

        newLabel(getTitle()); // create label

    }
}
