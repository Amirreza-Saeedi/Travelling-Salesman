package element;

import java.awt.*;

public class Player extends Element {
    // TODO players pieces
    private int[] lootedTreasures; // saves treasure ID

    private int power;

    private static Color[] colors = {
            new Color(0xE00000),
            new Color(0xFF0041D3, true)
    };

    public Player(int x, int y, int width, int height, String title, int id) {
        super(x, y, width, height);
        this.setColor(colors[id]);
        this.setTitle(title);

        this.newLabel(getTitle()); // create label
    }

    public void draw(Graphics g) { // draws a bordered-filled rectangle & it's image within
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
}
