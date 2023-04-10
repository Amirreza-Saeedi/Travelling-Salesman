import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DiceMap extends Rectangle {
    // TODO random dice throw
    private Image image;

    private final Rectangle DICE = new Rectangle();

    private final Rectangle numberPlace = new Rectangle();

    public DiceMap(int x, int y, int width, int height) { // TODO add image
        super(x, y, width, height);

        // init dice bounds:
        DICE.width = this.width / 2;
        DICE.height = DICE.width;
        DICE.x = this.width / 2 - DICE.width / 2;
        DICE.y = this.y + DICE.height / 2;

        // init num place bounds:
        numberPlace.width = 50;
        numberPlace.height = 50;
        numberPlace.x = this.width / 2 - numberPlace.width / 2;
        numberPlace.y = DICE.y + DICE.height + 25;

        // init dice img:
        image = new ImageIcon("img/dice.png").getImage();
    }

    public void draw(Graphics g) { // TODO add drawDiceNumberPlace
        drawBackground(g);
        drawDice(g);
        drawNumber(g);
    }

    private void drawBackground(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, this.width, this.height);
        g2.setColor(Color.BLACK);
        g2.drawRect(this.x, this.y, this.width, this.height);

    }

    private void drawDice(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.setColor(new Color(0x2FA25C));
        g2.fillRect(DICE.x, DICE.y, DICE.width, DICE.height);
        g2.setColor(Color.BLACK);
        g2.drawRect(DICE.x, DICE.y, DICE.width, DICE.height);

        g2.drawImage(image, DICE.x, DICE.y, DICE.width, DICE.height, null);
    }

    private void drawNumber(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        g2.setColor(Color.BLACK);
        g2.drawRect(numberPlace.x, numberPlace.y, numberPlace.width, numberPlace.height);
    }

    public int throwDice() { // returns 1_6
        return new Random().nextInt(6) + 1;
    }

    public Rectangle getDICE() {
        return DICE;
    }

    public int getXNumberPlace() {
        return numberPlace.x;
    }

    public int getYNumberPlace() {
        return numberPlace.y;
    }

    public int getWidthNumberPlace() {
        return numberPlace.width;
    }

    public int getHeightNumberPlace() {
        return numberPlace.height;
    }
}
