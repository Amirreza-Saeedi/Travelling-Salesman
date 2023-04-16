package element;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Loot extends Element implements Lootable{
    private boolean looted;

    public Loot(int x, int y, int width, int height, int id, int _x, int _y, int value) {
        super(x, y, width, height, id, _x, _y, "Loot");
        this.setColor(new Color(0x02B1F0));
        newLabel(getTitle()); // create label

        setVisible(false);
    }

    public Loot(int x, int y, int width, int height, int id, int _x, int _y) {
        this(x, y, width, height, id, _x, _y, 0);
    }

    public static int createValue() {
        return (new Random().nextInt(4) * 50 + 50); // 50, 100, 150, 200
    }


    @Override
    public boolean isLooted() {
        return looted;
    }

    @Override
    public void setLooted(boolean b) {
        looted = b;
    }
}
