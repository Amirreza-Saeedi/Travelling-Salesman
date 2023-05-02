package element;

import java.awt.*;

import consts.GameConstants;
import main.*;


public class Player extends Element { // todo extend or not?
    private static final int INITIAL_POWER = 50;
    private static final int INITIAL_CASH = 100;
    public boolean[] lootedTreasures; // determine looted treasures
    public boolean[] locatedTreasures; // determine located treasures
    public boolean[] locatedTraps; // determine located traps
    private Treasure[] treasures = new Treasure[GamePanel.NUMBER_OF_TREASURES];
    private int power;
    private int coin;
    private int numberOfTreasures;
    private int numberOfLocatedTreasure;
    private int numberOfTraps;
    private int numberOfLoots;
    private StartHouse startHouse; // todo create an array for randomizing
    private boolean isTurn = false;
    private byte state = GameConstants.CONTINUE; // won, drawn, lost, continue

    private static final Color[] colors = { // todo football team icons
            new Color(0xC50000),
            new Color(0x003AC0),
            new Color(0xB2B200),
            new Color(0x009A00)
    };

    public Player(int x, int y, int width, int height, int id, int _x, int _y, String title) {
        super(x, y, width, height, id, _x, _y, title);
        this.setColor(colors[id]);
        this.newLabel(getTitle()); // create label

        this.power = INITIAL_POWER;
        this.coin = INITIAL_CASH;
        this.lootedTreasures = new boolean[GamePanel.NUMBER_OF_TREASURES];
        this.locatedTreasures = new boolean[GamePanel.NUMBER_OF_TREASURES];
        this.locatedTraps = new boolean[GamePanel.NUMBER_OF_TRAPS];

        setVisible(true);
    }

    public Player(StartHouse startHouse, int id, String title) {
        this(startHouse.x, startHouse.y, startHouse.width, startHouse.height,
                id, startHouse._x, startHouse._y, title);
        this.startHouse = startHouse;
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

    public boolean fight(Player opponent) {
        System.out.println("Player.fight");
        Player winner, loser;
        boolean result;
        if (this.power >= opponent.power) { // declare winner
            winner = this;
            loser = opponent;
            result = true;
        } else {
            winner = opponent;
            loser = this;
            result = false;
        }
        System.out.println("winner = " + winner);
        System.out.println("loser = " + loser);

        // coin changes
        int coin = (int) ((((double) winner.power - loser.power) / (winner.power + loser.power)) * loser.coin);
        System.out.println("coin = " + coin);
        winner.coin += coin;
        loser.coin -= coin;

        // power changes
        winner.power -= loser.power;
        loser.power = 0;
        System.out.println("winner.power = " + winner.power);

        // revive the loser
        revive(loser);
        return result;
    }

    public void revive(Player player) { // take player to start house
        player.x = startHouse.x;
        player.y = startHouse.y;
        player._x = startHouse._x;
        player._y = startHouse._y;
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

    public void loseCoin(int amount) {
        if (amount > 0) {
            coin -= amount;
            if (coin < 0)
                coin = 0;
            System.out.println("Player.loseCoin");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("coin = " + coin);
            System.out.println();
        } else {
            System.err.println("Player.loseCoin");
            System.out.println("amount = " + amount);
            System.out.println();
        }
    }

    public void gainPower(int amount) {
        if (amount > 0) {
            power += amount;
            System.out.println("Player.losePower");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("power = " + power);
            System.out.println();
        } else {
            System.err.println("Player.losePower");
            System.out.println("amount = " + amount);
            System.out.println();
        }
    }

    public void losePower(int amount) {
        if (amount > 0) {
            power -= amount;
            if (power < 0)
                power = 0;
            System.out.println("Player.losePower");
            System.out.println("title = " + title);
            System.out.println("amount = " + amount);
            System.out.println("power = " + power);
            System.out.println();
        } else {
            System.err.println("Player.losePower");
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
        numberOfLoots++;
    }

    public void applyTrap(Trap trap) {
        System.out.println("Player.applyTrap");
        System.out.println(trap);
        loseCoin(trap.financialDamage);
        losePower(trap.physicalDamage);
        if (power == 0) { // if got killed
            revive(this);
        }
        numberOfTraps++;
    }

    public void applyTreasure(Treasure treasure) {

        System.out.println("Player.applyTreasure");
        System.out.println(treasure);
        gainCoin(treasure.getValue());
        this.lootedTreasures[treasure.getId()] = true;

        treasures[numberOfTreasures] = treasure;
        numberOfTreasures++;
    }

    public void buyWeapon(Weapon weapon) {
        System.out.println("Player.applyWeapon");
        System.out.println(weapon);
        loseCoin(weapon.price);
        gainPower(weapon.power);
    }

    public void buyTreasureMap(Treasure treasure, int price) {
        loseCoin(price);
        this.locatedTreasures[treasure.getId()] = true;
    }

    public int getNumberOfTreasures() {
        return numberOfTreasures;
    }


    public int getNumberOfTraps() {
        return numberOfTraps;
    }

    public int getNumberOfLoots() {
        return numberOfLoots;
    }

    public int getPoints() {
        return numberOfTreasures * 1000 + power;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void toggleTurn() {
        isTurn = !isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public boolean isPlaying() { // if player hasn't lost
        return state != GameConstants.LOST;
    }

    public Treasure getTreasures(int index) {
        return treasures[index];
    }

    @Override
    public String toString() {
        System.out.println("title = " + title);
        System.out.println("id = " + id);
        System.out.println("coin = " + coin);
        System.out.println("power = " + power);
        return "";
    }
}
