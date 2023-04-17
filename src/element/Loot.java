package element;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Loot extends Element implements Lootable{
    private boolean looted;
    protected int value;

    public Loot(int x, int y, int width, int height, int id, int _x, int _y, int value) { // const 1
        super(x, y, width, height, id, _x, _y, "Loot");
        this.value = value;
        this.setColor(new Color(0x02B1F0));
        newLabel(getTitle()); // create label

        setVisible(false);
    }

    public Loot(int x, int y, int width, int height, int id, int _x, int _y) { // const 2
        this(x, y, width, height, id, _x, _y, 0);
    }

    public Loot() {

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

    public int getValue() {
        return value;
    }
}
