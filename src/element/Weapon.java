package element;

import java.awt.*;

public class Weapon { // low damage = 25, medium = 50, high = 100
    /*        todo easy means sells all models any time
     *          medium 2 models and hard 1 model randomly*/
    static Weapon[] weapons = {new Weapon(20, 40, "Knife"),
                                new Weapon(50, 100, "Axe"),
                                new Weapon(100, 180, "Hammer")};
    int power;
    Image image; // todo add image
    int price;
    String title;
    private Weapon(int power, int price, String title) {
        this.power = power;
        this.price = price;
        this.title = title;
    }
}

