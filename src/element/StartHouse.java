package element;

import javax.swing.*;
import java.awt.*;

public class StartHouse extends Element {
    public StartHouse(int x, int y, int width, int height, int side) {
        super(x, y, width, height);
        this.setTitle("Start");
        this.setColor(new Color(0xFFFFFF));
        this.setId(side);

        newLabel(getTitle()); // create label

        //setImage(new ImageIcon("img/up arrow.png").getImage());
    }

}
