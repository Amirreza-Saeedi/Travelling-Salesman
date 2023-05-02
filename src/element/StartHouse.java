package element;

import menu.Theme;

import javax.swing.*;
import java.awt.*;

public class StartHouse extends Element {
    public StartHouse(int x, int y, int width, int height, int id, int _x, int _y) {
        super(x, y, width, height, id, _x, _y, "Start");
//        this.setColor(new Color(0xFFFFFF));
        setColor(Theme.BRIGHT_THEME.backColor);
        newLabel(getTitle()); // create label

        setVisible(true);
        //setImage(new ImageIcon("img/up arrow.png").getImage());
    }

}
