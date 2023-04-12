package element;

import java.awt.*;

public class Player extends Element {
    private static final int INITIAL_POWER = 50;
    private static final int INITIAL_CASH = 100;
    private int[] lootedTreasures; // saves treasure ID

    private int power;
    private int coin;

    private static Color[] colors = {
            new Color(0xE00000),
            new Color(0xFF0041D3, true)
    };

    public Player(int x, int y, int width, int height, String title, int id) {
        super(x, y, width, height);
        this.setColor(colors[id]);
        this.setTitle(title);
        this.newLabel(getTitle()); // create label

        this.power = INITIAL_POWER;
        this.coin = INITIAL_CASH;
    }

    public void draw(Graphics g) { // draws a bordered-filled circle & it's image within
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        // draw rect
        g2.setColor(this.getColor());
        g2.fillOval(this.x, this.y, this.width, this.height);
        g2.setColor(Color.BLACK);
        g2.drawOval(this.x, this.y, this.width, this.height);

        // draw image
        int xImg, yImg, widthImg, heightImg;
        xImg = this.x + this.width / 10;
        yImg = this.y + this.height / 10;
        widthImg = this.width - 2 * this.width / 10;
        heightImg = this.height - 2 * this.height / 10;

        g2.drawImage(this.image, xImg, yImg, widthImg, heightImg, null);
    }

    public void fight(Player opponent, StartHouse startHouse) {
        System.out.println("Player.fight");
        Player winner, looser;
        if (this.power >= opponent.power) { // declare winner
            winner = this;
            looser = opponent;
        } else {
            winner = opponent;
            looser = this;
        }
        System.out.println("winner = " + winner);
        System.out.println("looser = " + looser);

        // coin changes
        int coin = (int) (((double) winner.power - looser.power) / (winner.power + looser.power) * looser.coin);
        System.out.println("coin = " + coin);
        winner.coin += coin;
        looser.coin -= coin;

        // power changes
        winner.power -= looser.power;
        looser.power = 0;
        System.out.println("winner.power = " + winner.power);

        // revive the looser
        looser.x = startHouse.x;
        looser.y = startHouse.y;
        looser._x = startHouse._x;
        looser._y = startHouse._y;
    }
}
