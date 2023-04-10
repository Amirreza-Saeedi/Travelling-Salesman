package element;

import javax.swing.*;
import java.awt.*;

public class Treasure extends Element { // contains 8 different valuable treasures
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
    public Treasure(int x, int y, int width, int height, String title) {
        super(x, y, width, height);
        this.setColor(new Color(0x02B151));
        this.setTitle(title);

        newLabel(getTitle()); // create label

    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
