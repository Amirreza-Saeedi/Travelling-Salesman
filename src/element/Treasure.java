package element;

import javax.swing.*;
import java.awt.*;

public class Treasure extends Element implements Lootable { // contains 8 different valuable treasures
    private boolean lootedByPlayer1;

    private boolean lootedByPlayer2;

    private int value;

    public static final String[] namesList = {
            "Diamond Ring", "Jeweled Sword",    "Golden Mug",   "Crystal Cup",
            "Wooden Bow",   "Iron Shield",      "Golden Key",   "Dragon Scroll"
    };

    public static final int[] valueList = { // 300_1000
            900,            1000,               400,            600,
            300,            800,                600,            500
    };
    // TODO add specific icon
    public Treasure(int x, int y, int width, int height, int id, int _x, int _y, String title, int value) { // const 1
        super(x, y, width, height, id, _x, _y, title);
        this.setValue(value);
        this.setColor(new Color(0x02B151));
        newLabel(getTitle()); // create label

        setVisible(false);
    }
    public Treasure(int x, int y, int width, int height, String title) { // const 2
        this(x, y, width, height, 0, 0, 0, title, 0);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean isLooted() {
        return looted;
    }

    @Override
    public void setLooted() {

    }
}
