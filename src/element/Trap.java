package element;

import java.awt.*;
import java.util.Random;

public class Trap extends Element {
    protected int physicalDamage;
    protected int financialDamage;
    private static final int[] physicalDamages = {5, 10};
    private static final int[] financialDamages = {25, 50};
    public Trap(int x, int y, int width, int height, int id, int _x, int _y,
                int physicalDamage, int financialDamage) { // const 1
        super(x, y, width, height, id, _x, _y, "Trap");
        this.physicalDamage = physicalDamage;
        this.financialDamage = financialDamage;
        this.setColor(new Color(0xFF0202));
        newLabel(getTitle()); // create label

        setVisible(false);
    }
    public Trap(int x, int y, int width, int height, int id, int _x, int _y) { // const 2
        this(x, y, width, height, id, _x, _y, 0, 0);
    }

    public Trap() { // void const

    }

    public static int createPhysicalDamage() {
        return physicalDamages[new Random().nextInt(2)];
    }

    public static int createFinancialDamage() {
        return financialDamages[new Random().nextInt(2)];
    }

    public void classify() { // puts trap in one of the 4 kinds
        if (physicalDamage == physicalDamages[1] && financialDamage == financialDamages[1]) {
            this.title = "Malicious Trap";
        } else if (physicalDamage == physicalDamages[1] && financialDamage == financialDamages[0]) {
            this.title = "Deadly Trap";
        } else if (physicalDamage == physicalDamages[0] && financialDamage == financialDamages[1]) {
            this.title = "Greedy Trap";
        } else if (physicalDamage == physicalDamages[0] && financialDamage == financialDamages[0]) {
            this.title = "Minor Trap";
        }
    }

    public int getFinancialDamage() {
        return financialDamage;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }
}
