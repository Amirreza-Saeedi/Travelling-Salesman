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
    private Player[] players;
    private JLabel[] playersLabels;
    private JLabel[] treasureLabels;
    private JLabel[] powerLabels;
    private JLabel[] coinLabels;
    private int unit;
    private JLabel[] changesLabels = new JLabel[6]; // number of max changes may be done

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
        newChangesLabels();
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
    private void removeChanges() { // called before updates

        if (changesLabels != null) {
            for (JLabel label : changesLabels) {
                remove(label);
                repaint();
            }
        } else {
            System.out.println("ScoreboardPanel.removeChanges");
            System.err.println("null");
        }
    }

    private void addChanges() {
        if (changesLabels != null) {
            for (JLabel label : changesLabels) {
                add(label);
            }
        } else {
            System.out.println("ScoreboardPanel.addChanges");
            System.err.println("null");
        }
    }

//    private void updateState(int tChanges1, int pChanges1, int cChanges1, int playerID1, // main update method for 2 player
//                             int tChanges2, int pChanges2, int cChanges2, int playerID2, int nChanges) {
//        Player player1 = players[playerID1];
//        Player player2 = players[playerID2];
//        changesLabels = new JLabel[nChanges];
//        int i = 0;
//        if (tChanges1 == 1) { // treasure
//            changesLabels[i++] = newChangeLabel(treasureLabels[playerID1], String.format("%+d", 1), Color.GREEN);
//        }
//        if (pChanges1 != 0) { // power
//            changesLabels[i++] = newChangeLabel(powerLabels[playerID1], String.format("%+d", pChanges1),
//                    (pChanges1 > 0) ? Color.GREEN : Color.RED);
//        }
//        if (cChanges1 != 0) { // coin
//            changesLabels[i++] = newChangeLabel(coinLabels[playerID1], String.format("%+d", pChanges1),
//                    (cChanges1 > 0) ? Color.GREEN : Color.RED);
//        }
//    }
    private void updateState(int tChanges, int pChanges, int cChanges, int playerID, int index) { // main update method
        /*index used for fight calling.
        * updates t, p and c labels,
        * removes last changes labels,
        * create new changes labels,
        * and add them to panel.
        * receives pre-signed values.
        * repaint.*/
        Player player = players[playerID];
        // apply updates
        treasureLabels[playerID].setText("T:" + player.getNumberOfTreasures()); // treasure
        powerLabels[playerID].setText("P:" + player.getPower()); // power
        coinLabels[playerID].setText("C:" + player.getCoin()); // coin

        // show changes
        if (index == 0)
            removeChanges();

        int i = index;
        if (tChanges == 1) { // treasure
            changesLabels[i++] = newChangeLabel(treasureLabels[playerID], String.format("%+d", 1), Color.GREEN);
        }
        if (pChanges != 0) { // power
            changesLabels[i++] = newChangeLabel(powerLabels[playerID], String.format("%+d", pChanges),
                    (pChanges > 0) ? Color.GREEN : Color.RED);
        }
        if (cChanges != 0) { // coin
            changesLabels[i++] = newChangeLabel(coinLabels[playerID], String.format("%+d", cChanges),
                    (cChanges > 0) ? Color.GREEN : Color.RED);
        }

        addChanges(); // add to panel
        repaint();

    }

    public void updateState(Treasure treasure, int playerID) { // called by applyTreasure
        updateState(1, 0, treasure.getValue(), playerID, 0);
    }

    public void updateState(Loot loot, int playerID) {
        updateState(0, 0, loot.getValue(), playerID, 0);
    }

    public void updateState(Trap trap, int playerID) {
        updateState(0, -trap.getPhysicalDamage(), -trap.getFinancialDamage(), playerID, 0);
//        removeChanges();
//        Player player = players[playerID];
//
//        // update current value X2
//        JLabel powerLabel = powerLabels[playerID];
//        powerLabel.setText("P:" + player.getPower());
//        JLabel coinLabel = coinLabels[playerID];
//        coinLabel.setText(("C:" + player.getCoin()));
//
//        // show applied changes X2
//        JLabel pChangesLabel = newChangeLabel(powerLabel, String.format("%+d", -trap.getPhysicalDamage()), Color.RED);
//        JLabel cChangesLabel = newChangeLabel(coinLabel, String.format("%+d", -trap.getPhysicalDamage()), Color.RED);
//
//        // add to changesLabels
//        int i = 0;
//        changesLabels = new JLabel[2];
//        changesLabels[i++] = pChangesLabel;
//        changesLabels[i++] = cChangesLabel;
//        for (JLabel label : changesLabels) { // add to panel
//            add(label);
//        }
    }

    public void updateState(Weapon weapon, int playerID) { // todo
        updateState(0, weapon.getPower(), -weapon.getPrice(), playerID, 0);
    }

//    public void updateState(TreasureMap treasureMap, int playerID) {
//        int treasures[] =
//    }


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
            JLabel treasureLabel = new JLabel("T:" + player.getNumberOfTreasures());
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

    private void newChangesLabels() {
        for (int i = 0; i < changesLabels.length; i++) {
            changesLabels[i] = new JLabel();
            add(changesLabels[i]);
        }
    }
}
