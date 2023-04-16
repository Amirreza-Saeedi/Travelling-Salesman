package element;

import java.awt.*;

import consts.GameConstants;
import main.*;


public class Player extends Element { // todo extend or not?
    private static final int INITIAL_POWER = 50;
    private static final int INITIAL_CASH = 100;
    private int[] lootedTreasures; // saves looted treasures ID
    private int[] locatedTreasures; // saves located treasures ID
    private int[] lootedLoots; // saves looted loots ID
    private int[] locatedTraps; // saves located traps ID
    private int power;
    private int coin;
    public int nTreasures;
    public int nLocatedTreasure;
    public int nTraps;
    public int nLoots;

    private static final Color[] colors = {
            new Color(0xE00000),
            new Color(0xFF0041D3, true)
    };

    public Player(int x, int y, int width, int height, int id, int _x, int _y, String title) {
        super(x, y, width, height, id, _x, _y, title);
        this.setColor(colors[id]);
        this.newLabel(getTitle()); // create label

        this.power = INITIAL_POWER;
        this.coin = INITIAL_CASH;
        this.lootedTreasures = new int[GamePanel.NUMBER_OF_TREASURES];
        this.locatedTreasures = new int[GamePanel.NUMBER_OF_TREASURES];
        this.locatedTraps = new int[GamePanel.NUMBER_OF_TRAPS];

        setVisible(true);
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
    
    public void gainCoin(int amount) {
        if (amount > 0) {
            coin += amount;
            System.out.println("Player.gainCoin");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("coin = " + coin);
            System.out.println();
        } else {
            System.err.println("Player.gainCoin");
            System.out.println("amount = " + amount);
            System.out.println();
        } 
    }

    public void looseCoin(int amount) {
        if (amount < 0) {
            coin += amount;
            System.out.println("Player.looseCoin");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("coin = " + coin);
            System.out.println();
        } else {
            System.err.println("Player.looseCoin");
            System.out.println("amount = " + amount);
            System.out.println();
        }
    }

    public void gainPower(int amount) {
        if (amount > 0) {
            power += amount;
            System.out.println("Player.loosePower");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("power = " + power);
            System.out.println();
        } else {
            System.err.println("Player.loosePower");
            System.out.println("amount = " + amount);
            System.out.println();
        }
    }

    public void loosePower(int amount) {
        if (amount < 0) {
            power += amount;
            System.out.println("Player.loosePower");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("power = " + power);
            System.out.println();
        } else {
            System.err.println("Player.loosePower");
            System.out.println("amount = " + amount);
            System.out.println();
        }
    }

    public int getPower() {
        return power;
    }

    public int getCoin() {
        return coin;
    }

    public void applyLoot(Loot loot) {
        System.out.println("Player.applyLoot");
        gainCoin(loot.value);
    }
}
