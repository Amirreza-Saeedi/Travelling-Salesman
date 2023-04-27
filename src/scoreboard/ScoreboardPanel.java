package scoreboard;

import element.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreboardPanel extends JPanel {
    final int VERTICAL_UNIT;
    int elapsedTime = 0;
    int second;
    int minute;
    private String secondString = String.format("%02d", second);
    private String minuteString = String.format("%02d", minute);;
    private JLabel timerLabel = new JLabel();
    public Quest quest;
    private int unit;
    private PlayerComponent[] playerComponents;

    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { // update elapsed time
            elapsedTime += 1000;
            second = elapsedTime / 1000 % 60;
            minute = elapsedTime / 60000;
            secondString = String.format("%02d", second);
            minuteString = String.format("%02d", minute);
            timerLabel.setText(minuteString + ":" + secondString);
        }
    });

    public ScoreboardPanel(int x, int y, int width, int height, Player[] players) {
        setBounds(x, y, width, height);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);
        setLayout(null);
        setOpaque(true);
        VERTICAL_UNIT = height / 10;

        newTimer();
        newQuest();
        newPlayerComponents(players);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        int y0 = quest.label.getY() + quest.label.getHeight(); // y start of todo players panel

        for (int i = 0; i < playerComponents.length - 1; i++) { // divider line
            int y = y0 + (i + 1) * unit;
            g2.drawLine(0, y, getWidth(), y);
        }

        for (PlayerComponent pc : playerComponents) { // turn icon
            if (pc.player.isTurn()) {
                Rectangle r = pc.turnBox;
                g2.drawImage(pc.turnImage, r.x, r.y, r.width, r.height, null);
            }
        }
    }

    private JLabel newChangeLabel(JLabel topLabel) { // create a label at bottom of topLabel
        JLabel label = new JLabel();
        label.setBounds(topLabel.getX(), topLabel.getY() + topLabel.getHeight(), // bounds
                topLabel.getWidth(), topLabel.getHeight());
        label.setFont(topLabel.getFont()); // font
        return label;
    }

    public void updateState() { // called in applier methods
        /*sets invisible not-changed values,
        * and vice versa.*/
        for (PlayerComponent pc : playerComponents) {
            Player player = pc.player;

            // update
            pc.treasureLabel.setText("T:" + player.getNumberOfTreasures());
            pc.powerLabel.setText("P:" + player.getPower());
            pc.coinLabel.setText("C:" + player.getCoin()); // coin

            int treasureChanges = player.getNumberOfTreasures() - pc.nTreasure;
            pc.nTreasure = player.getNumberOfTreasures();
            int powerChanges = player.getPower() - pc.nPower;
            pc.nPower = player.getPower();
            int coinChanges = player.getCoin() - pc.nCoin;
            pc.nCoin = player.getCoin();

            // show changes todo setVisible?
            if (treasureChanges == 1) { // treasure
                pc.treasureChangesLabel.setText(String.format("%+d", treasureChanges));
                pc.treasureChangesLabel.setForeground(Color.GREEN);
                pc.treasureChangesLabel.setVisible(true);
            } else {
                pc.treasureChangesLabel.setVisible(false);
            }
            if (powerChanges != 0) { // power
                pc.powerChangesLabel.setText(String.format("%+d", powerChanges));
                pc.powerChangesLabel.setForeground(powerChanges > 0 ? Color.GREEN : Color.RED);
                pc.powerChangesLabel.setVisible(true);
            } else {
                pc.powerChangesLabel.setVisible(false);
            }
            if (coinChanges != 0) { // coin
                pc.coinChangesLabel.setText(String.format("%+d", coinChanges));
                pc.coinChangesLabel.setForeground(coinChanges > 0 ? Color.GREEN : Color.RED);
                pc.coinChangesLabel.setVisible(true);
            } else {
                pc.coinChangesLabel.setVisible(false);
            }
        }

    }

    private void newPlayerComponents(Player[] players) {
        /*new players array,
        * their treasures labels,
        * powers labels,
        * coins labels*/

        // init
        playerComponents = new PlayerComponent[players.length];

        // ingredients
        int y0 = quest.label.getY() + quest.label.getHeight(); // start y
        int mainHeight = getHeight() - y0; // height of players panel
        unit = mainHeight / players.length; // distance between each of two players
        int size = 20; // used for label height and font size

        for (int i = 0; i < playerComponents.length; i++) { // players info
            Player player = players[i];
            PlayerComponent pc = playerComponents[i] = new PlayerComponent(player);

            // player name label
            JLabel playerNameLabel = new JLabel(" " + player.getTitle());
            playerNameLabel.setFont(new Font("Ink free", Font.BOLD, size));
            playerNameLabel.setBounds(20, unit * i + y0 + 5, getWidth(), size);
            playerNameLabel.setOpaque(true);
            playerNameLabel.setBackground(player.getColor());
            add(playerNameLabel);
            pc.playerNameLabel = playerNameLabel; // init

            // turn box
            pc.turnBox = new Rectangle(0, playerNameLabel.getY(),
                    size, size);

            // ingredients
            int vSpace = 10;
            int y1 = playerNameLabel.getY() + playerNameLabel.getHeight() + vSpace;
            int hSpace = 5;
            int x1 = playerNameLabel.getX() + 20;
            int vSize = size;
            int hSize = vSize * 4;

            // treasure
            JLabel treasureLabel = new JLabel("T:" + player.getNumberOfTreasures());
            treasureLabel.setBounds(x1, y1, hSize, vSize);
            treasureLabel.setFont(new Font("", Font.PLAIN, vSize));
            treasureLabel.setOpaque(true);
            treasureLabel.setBackground(new Color(0x30000000, true));
            treasureLabel.setToolTipText("Treasures");

            pc.treasureLabel = treasureLabel; // init
            add(pc.treasureLabel);
            pc.nTreasure = player.getNumberOfTreasures();
            pc.treasureChangesLabel = newChangeLabel(treasureLabel);
            add(pc.treasureChangesLabel);

            // power
            JLabel powerLabel = new JLabel("P:" + player.getPower());
            powerLabel.setBounds(treasureLabel.getX() + treasureLabel.getWidth() + hSpace, y1, hSize, vSize);
            powerLabel.setFont(new Font("", Font.PLAIN, vSize));
            powerLabel.setOpaque(true);
            powerLabel.setBackground(new Color(0x30000000, true));
            powerLabel.setToolTipText("Power");

            pc.powerLabel = powerLabel; // init
            add(pc.powerLabel);
            pc.nPower = player.getPower();
            pc.powerChangesLabel = newChangeLabel(powerLabel);
            add(pc.powerChangesLabel);

            // coin
            JLabel coinLabel = new JLabel("C:" + player.getCoin());
            coinLabel.setBounds(powerLabel.getX() + powerLabel.getWidth() + hSpace, y1, hSize, vSize);
            coinLabel.setFont(new Font("", Font.PLAIN, vSize));
            coinLabel.setOpaque(true);
            coinLabel.setBackground(new Color(0x30000000, true));
            coinLabel.setToolTipText("Coins");

            pc.coinLabel = coinLabel; // init
            add(pc.coinLabel);
            pc.nCoin = player.getCoin();
            pc.coinChangesLabel = newChangeLabel(coinLabel);
            add(pc.coinChangesLabel);
        }
    }

    private void newTimer() {
        timerLabel.setBounds(0, 0, this.getWidth(), VERTICAL_UNIT);
        timerLabel.setText(minuteString + ":" + secondString);
        timerLabel.setFont(new Font("", Font.PLAIN, VERTICAL_UNIT));
        timerLabel.setBorder(new LineBorder(Color.BLACK, 1));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timer.start();
        this.add(timerLabel);
    }

    private void newQuest() {
        quest = new Quest();
        JLabel label = quest.label;
        label.setBounds(0, timerLabel.getY() + VERTICAL_UNIT, this.getWidth(), VERTICAL_UNIT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setFont(new Font("", Font.PLAIN, VERTICAL_UNIT - 20));
        label.setBorder(new LineBorder(Color.BLACK, 1));
        add(label);
    }

    private static class PlayerComponent {
        int unit;
        Player player;
        JLabel playerNameLabel;
        JLabel treasureLabel;
        JLabel treasureChangesLabel;
        JLabel powerLabel;
        JLabel powerChangesLabel;
        JLabel coinLabel;
        JLabel coinChangesLabel;
        Image turnImage = new ImageIcon("img/turn.png").getImage();
        Rectangle turnBox;
        int nTreasure;
        int nPower;
        int nCoin;

        PlayerComponent(Player player) {
            this.player = player;
        }
    }

}
