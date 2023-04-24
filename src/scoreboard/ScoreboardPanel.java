package scoreboard;

import element.Loot;
import element.Player;
import element.Trap;
import element.Treasure;

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
    private Player[] players;
    private JLabel[] playersLabels;
    private JLabel[] treasureLabels;
    private JLabel[] powerLabels;
    private JLabel[] coinLabels;
    private int unit;
    private JLabel[] changesLabels = null;

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
        newPlayers(players);

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        int y0 = quest.label.getY() + quest.label.getHeight();
        for (int i = 0; i < players.length - 1; i++) { // divider line
            int y = y0 + (i + 1) * unit;
            g2.drawLine(0, y, getWidth(), y);
        }
    }

    private JLabel newChangeLabel(JLabel topLabel, String text, Color color) { // copy size & font
        JLabel label = new JLabel(text);
//        label.setOpaque(true);
        label.setBounds(topLabel.getX(), topLabel.getY() + topLabel.getHeight(),
                topLabel.getWidth(), topLabel.getHeight());
        label.setForeground(color);
        label.setFont(topLabel.getFont());
        return label;
    }
    private void removeLastChanges() { // called before updates
        if (changesLabels != null) {
            for (JLabel label : changesLabels) {
                remove(label);
            }
        }
    }

    public void updateState(Treasure treasure, int playerID) { // called by applyTreasure
        removeLastChanges();
        Player player = players[playerID];

        // update current value X2
        JLabel treasureLabel = treasureLabels[playerID];
        treasureLabel.setText("T:" + player.nTreasures);
        JLabel coinLabel = coinLabels[playerID];
        coinLabel.setText(("C:" + player.getCoin()));

        // show applied changes X2
        JLabel tChangesLabel = newChangeLabel(treasureLabel, String.format("%+d", 1), Color.GREEN);
        JLabel cChangesLabel = newChangeLabel(coinLabel, String.format("%+d", treasure.getValue()), Color.GREEN);

        // add to changesLabels
        int i = 0;
        changesLabels = new JLabel[2];
        changesLabels[i++] = tChangesLabel;
        changesLabels[i++] = cChangesLabel;
        for (JLabel label : changesLabels) { // add to panel
            add(label);
        }
    }
    public void updateState(Loot loot, int playerID) {
//        removeLastChanges();
//        Player player = players[playerID];
//
//        // update current value X2
//        JLabel treasureLabel = treasureLabels[playerID];
//        treasureLabel.setText("T:" + player.nTreasures);
//        JLabel coinLabel = coinLabels[playerID];
//        coinLabel.setText(("C:" + player.getCoin()));
//
//        // show applied changes X2
//        JLabel tChangesLabel = newChangeLabel(treasureLabel, String.format("%+d", treasure.getValue()), Color.GREEN);
//        JLabel cChangesLabel = newChangeLabel(coinLabel, String.format("%d", treasure.getValue()), Color.GREEN);
//
//        // add to changesLabels
//        int i = 0;
//        changesLabels = new JLabel[2];
//        changesLabels[i++] = tChangesLabel;
//        changesLabels[i++] = cChangesLabel;
//        for (JLabel label : changesLabels) { // add to panel
//            add(label);
//        }
    }
    public void updateState(Trap trap) {

    }


    private void newPlayers(Player[] players) {
        // init
        this.players = players;
        playersLabels = new JLabel[players.length];
        treasureLabels = new JLabel[players.length];
        powerLabels = new JLabel[players.length];
        coinLabels = new JLabel[players.length];

        int y0 = quest.label.getY() + quest.label.getHeight();
        int mainHeight = getHeight() - y0; // height of players panel
        unit = mainHeight / players.length; // distance between each two players

        for (int i = 0; i < players.length; i++) { // players info
            // player name & logo
            Player player = players[i];
            JLabel playerLabel = new JLabel(" " + player.getTitle());
            int size = 20;
            playerLabel.setFont(new Font("Ink free", Font.BOLD, size));
            playerLabel.setBounds(20, unit * i + y0 + 5, getWidth(), size);
            playerLabel.setOpaque(true);
            playerLabel.setBackground(player.getColor());
            add(playerLabel);
            playersLabels[i] = playerLabel; // init

            int vSpace = 10;
            int y1 = playerLabel.getY() + playerLabel.getHeight() + vSpace;
            int hSpace = 5;
            int x1 = playerLabel.getX() + 20;
            int vSize = size;
            int hSize = vSize * 4;

            // treasure
            JLabel treasureLabel = new JLabel("T:" + player.nTreasures);
            treasureLabel.setBounds(x1, y1, hSize, vSize);
            treasureLabel.setFont(new Font("", Font.PLAIN, vSize));
            treasureLabel.setOpaque(true);
            treasureLabel.setBackground(new Color(0x30000000, true));
            treasureLabel.setToolTipText("Treasures");
            add(treasureLabel);
            treasureLabels[i] = treasureLabel; // init
            // power
            JLabel powerLabel = new JLabel("P:" + player.getPower());
            powerLabel.setBounds(treasureLabel.getX() + treasureLabel.getWidth() + hSpace, y1, hSize, vSize);
            powerLabel.setFont(new Font("", Font.PLAIN, vSize));
            powerLabel.setOpaque(true);
            powerLabel.setBackground(new Color(0x30000000, true));
            powerLabel.setToolTipText("Power");
            add(powerLabel);
            powerLabels[i] = powerLabel; // init
            // coin
            JLabel coinLabel = new JLabel("C:" + player.getCoin());
            coinLabel.setBounds(powerLabel.getX() + powerLabel.getWidth() + hSpace, y1, hSize, vSize);
            coinLabel.setFont(new Font("", Font.PLAIN, vSize));
            coinLabel.setOpaque(true);
            coinLabel.setBackground(new Color(0x30000000, true));
            coinLabel.setToolTipText("Coins");
            add(coinLabel);
            coinLabels[i] = coinLabel; // init
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
}
