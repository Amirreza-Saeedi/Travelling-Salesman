package element;

import javax.swing.*;
import java.awt.*;


public abstract class Element extends Rectangle { // Castle, Treasure, Wall, Market, Loot, Trap
//    todo implement exclusive constructor for each subclass that need it
    protected int id;
    protected Color color;
    protected String title;
    protected JLabel label;
    protected Image image;

    private boolean visible;

    public int _x;

    public int _y;


    Element(int x, int y, int width, int height, int id, int _x, int _y, String title) { // constructor 1
        super(x, y, width, height);
        setId(id);
        set_x(_x);
        set_y(_y);
        setTitle(title);
    }

    Element(int x, int y, int width, int height, int id, int _x, int _y) { // constructor 2
        this(x, y, width, height, id, _x, _y, "");
    }

    Element(int x, int y, int width, int height) { // constructor 3
        this(x, y, width, height, 0, 0, 0, ""); // initialize the rect.
    }

    Element() { // temp

    }

    public void draw(Graphics g) { // draws a bordered-filled rectangle & it's image within
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // draw rect
        g2.setColor(this.getColor());
        g2.fillRect(this.x, this.y, this.width, this.height);
        g2.setColor(Color.BLACK);
        g2.drawRect(this.x, this.y, this.width, this.height);

        // todo draw image
        /*int xImg, yImg, widthImg, heightImg;
        xImg = this.x + this.width / 10;
        yImg = this.y + this.height / 10;
        widthImg = this.width - 2 * this.width / 10;
        heightImg = this.height - 2 * this.height / 10;

        g2.drawImage(this.image, xImg, yImg, widthImg, heightImg, null);*/

    }

    public void newLabel(String title) { // initialize label
        label = new JLabel();
        label.setBounds(this);
        label.setToolTipText(title);
    }

    @Override
    public String toString() {
        return title + id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean b) { // for solving spoiling items location issue
        visible = b;
        this.label.setVisible(b);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_x() {
        return _x;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public int get_y() {
        return _y;
    }

}
