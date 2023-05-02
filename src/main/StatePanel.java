package main;

import element.Castle;
import element.Element;
import element.Player;
import element.Treasure;
import menu.Theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StatePanel extends JPanel {
    Player[] players;

    Quest quest = Quest.getInstance();
    Color stateColor = new Color(0xFF7BC2BB);

    private static class MyClass {
        Image image;
        Rectangle rectangle = new Rectangle();
        JLabel titleLabel = new JLabel();
        JLabel detailsLabel = new JLabel("- No event");
        MyClass(String title) {
            titleLabel.setText(title);
        }
    }

    private MyClass questClass = new MyClass("Quest:");
    private MyClass stateClass = new MyClass("State:");

    private final static int margin = 10;

    StatePanel(Rectangle r, Player[] players) {
        super(null);
        setBounds(r);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Theme.BRIGHT_THEME.backColor);

        newClass(questClass, 30);
        newClass(stateClass, getHeight() / 2);

        this.players = players;
    }

    private void newClass(MyClass obj, int y0) {

        // title
        obj.titleLabel.setBounds(30, y0, getWidth(), 40);
        obj.titleLabel.setFont(new Font("ink free", Font.BOLD, obj.titleLabel.getHeight()));
        add(obj.titleLabel);
        // rect
        Rectangle rectangle = new Rectangle();
        int d = 10;
        rectangle.setBounds(getWidth() - 100, obj.titleLabel.getY() - d,
                2 * d + obj.titleLabel.getHeight(), 2 * d + obj.titleLabel.getHeight());
        obj.rectangle = rectangle;

        obj.detailsLabel.setBounds(obj.titleLabel.getX() - 10,
                obj.titleLabel.getY() + obj.titleLabel.getHeight() + 20,
                getWidth(), 20);
        obj.detailsLabel.setFont(new Font("", Font.PLAIN, obj.detailsLabel.getHeight()));
        add(obj.detailsLabel);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawQuestCircle(g);
        drawStateCircle(g);
    }

    private void drawQuestCircle(Graphics g) { // todo called when?
        /*show quest treasure,
        * or castle to deliver the treasure.*/
        Graphics2D g2 = (Graphics2D) g;

        Player player = getCurPlayer();
        if (player == null) {
            System.out.println("StatePanel.drawQuestCircle");
            System.err.println("null");
            return;
        }
        if (player.locatedTreasures[quest.getTreasure().getId()]) { // if had treasure
            g.setColor(Castle.COLOR);
            g.fillOval(questClass.rectangle.x, questClass.rectangle.y,
                    questClass.rectangle.width, questClass.rectangle.height);
            questClass.detailsLabel.setText("- Go to castle.");
        } else { // if hadn't
            g.setColor(Treasure.COLOR);
            g.fillOval(questClass.rectangle.x, questClass.rectangle.y,
                    questClass.rectangle.width, questClass.rectangle.height);
            questClass.detailsLabel.setText("- Find \"" + quest.getTreasure().getTitle() + "\"");
        }

        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawOval(questClass.rectangle.x, questClass.rectangle.y,
                questClass.rectangle.width, questClass.rectangle.height);
    }

    private void drawStateCircle(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setColor(stateColor);
        g2.fillOval(stateClass.rectangle.x, stateClass.rectangle.y,
                stateClass.rectangle.width, stateClass.rectangle.height);
        g2.setColor(Color.BLACK);
        g2.drawOval(stateClass.rectangle.x, stateClass.rectangle.y,
                stateClass.rectangle.width, stateClass.rectangle.height);
    }

    private Player getCurPlayer() {
        for (Player player : players) {
            if (player.isTurn())
                return player;
        }
        return null;
    }

    public void update(Element element) { // called at end of move
        stateColor = element.getColor();
        stateClass.detailsLabel.setText("- " + element.getTitle());

        repaint();
    }

    public void update() { // called while moving and at next turn
        stateColor = BoardMap.BOARD_COLOR;
        stateClass.detailsLabel.setText("- No event");

        repaint();
    }

    private int getXCenter() {
        return getWidth() / 2;
    }
}
