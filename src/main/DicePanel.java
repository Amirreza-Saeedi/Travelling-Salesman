package main;

import menu.Theme;
import org.jcp.xml.dsig.internal.dom.DOMManifest;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class DicePanel extends JPanel {
    private JButton button = new JButton("Throw!");
    private JLabel label = new JLabel("Moves: ");
    private Image image;


    public DicePanel(Rectangle r) {
        super(null);
        setBounds(r.x, r.y, r.width, r.height);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Theme.BRIGHT_THEME.backColor);

        // button
        int size = 150;
        button.setBounds(getXCenter() - size / 2, 80, size, size);
        button.setBackground(Theme.DARK_THEME.backColor);
        button.setForeground(Theme.DARK_THEME.foreColor);
        button.setFont(new Font("ink free", Font.BOLD, 25));
        button.setFocusable(false);
        add(button);

        // number place
        Dimension d = new Dimension(getWidth(), 30);
        label.setSize(d);
        label.setLocation(0, button.getY() + button.getHeight() + d.height + 20);
        label.setFont(new Font("", Font.PLAIN, d.height));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        // init dice img:
        image = new ImageIcon("img/dice.png").getImage();
    }
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.WHITE);
        g.drawRect(20, 20, 20, 20);

    }

    public void update(int number) { // TODO add drawDiceNumberPlace
        label.setText("Moves: " + number);

    }

    public int throwDice() { // returns 1_6
        return new Random().nextInt(6) + 1;
    }

    private int getXCenter() {
        return getWidth() / 2;
    }

    public JButton getButton() {
        return button;
    }
}
