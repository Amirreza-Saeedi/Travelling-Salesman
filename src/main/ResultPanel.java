package main;

import consts.GameConstants;
import element.Player;
import menu.MenuFrame;
import menu.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultPanel extends JPanel implements ActionListener {

    private static class PlayerClass {
        JLabel nameLabel = new JLabel("Name");
        JLabel stateLabel = new JLabel("status: ");
        JLabel pointsLabel = new JLabel("points: ");
        JLabel[] treasuresLabels = new JLabel[8]; // todo 4?
        Player player;

    }


    PlayerClass[] playerObjs;
    JLabel leaderboardLabel = new JLabel("Leaderboard");
    JLabel gameStateLabel = new JLabel("Game State: ");
    JLabel treasuresLabel = new JLabel("Treasures: n/n");
    JButton menuButton = new JButton("To Menu");
    JButton boardButton = new JButton("To Board");
    JButton rematchButton = new JButton("Rematch");
    JButton[] buttons = {menuButton, boardButton, rematchButton};

    GameFrame gameFrame;
    ResultDialog parent;

    ResultPanel(GameFrame gameFrame, ResultDialog parent, Player[] players) {
        super(null);
        setPreferredSize(new Dimension(1000, 700));
        setSize(1000, 700);
        setBackground(Theme.DARK_THEME.backColor);
        this.gameFrame = gameFrame;
        this.parent = parent;

        playerObjs = new PlayerClass[players.length];
        setArrangement(players);

        // leaderboard
        leaderboardLabel.setBounds(20, 20, 500, 50);
        leaderboardLabel.setFont(new Font("ink free", Font.BOLD, leaderboardLabel.getHeight() - 5));
        leaderboardLabel.setForeground(Theme.DARK_THEME.foreColor);
        add(leaderboardLabel);

        // game state
        gameStateLabel.setSize(200, 20);
        gameStateLabel.setLocation(leaderboardLabel.getX(), getHeight() - gameStateLabel.getHeight() - 10);
        gameStateLabel.setFont(new Font("", Font.ITALIC, gameStateLabel.getHeight() - 5));
        gameStateLabel.setForeground(isGameEnded(players) ? Color.RED.brighter() : Color.GREEN);
        gameStateLabel.setText("Status: " + (isGameEnded(players) ? "Ended" : "Running  "));
        add(gameStateLabel);

        // treasures
        treasuresLabel.setBounds(getWidth() / 3, leaderboardLabel.getY() + 20, 200, 20);
        treasuresLabel.setFont(new Font("", Font.PLAIN, treasuresLabel.getHeight() - 3));
        treasuresLabel.setForeground(Theme.DARK_THEME.foreColor);
        treasuresLabel.setText("Treasures " + getNLootedTreasures(players) + "/" + GamePanel.NUMBER_OF_TREASURES);
        add(treasuresLabel);

        // buttons
        if (isGameEnded(players)) {
            setButtons();
        }

        // players:
        int y0 = leaderboardLabel.getY() + leaderboardLabel.getHeight() + 30;
        int d = (getHeight() - y0) / players.length;
        Dimension size = new Dimension(getWidth() / 2, 25);

        for (int i = 0; i < players.length; i++) {
            // name label
            PlayerClass pc = playerObjs[i] = new PlayerClass();
            Player player = pc.player = players[i];
            pc.nameLabel.setText(i + 1 + "- " + player.getTitle());
            pc.nameLabel.setSize(size);
            pc.nameLabel.setLocation(20, i * d + y0);
            pc.nameLabel.setFont(new Font("ink free", Font.BOLD, pc.nameLabel.getHeight()));
            pc.nameLabel.setOpaque(true);
            pc.nameLabel.setBackground(pc.player.getColor());
            pc.nameLabel.setForeground(Theme.DARK_THEME.foreColor);
            add(pc.nameLabel);

            // status
            String s = "STATE";
            if (pc.player.getState() == GameConstants.CONTINUE) {
                s = "State: Playing";
            } else if (pc.player.getState() == GameConstants.LOST) {
                s = "State: Lost";
            } else if (pc.player.getState() == GameConstants.WON) {
                s = "State: Won";
            } else if (pc.player.getState() == GameConstants.DRAWN) {
                s = "State: Drawn";
            }
            pc.stateLabel.setText(s);
            pc.stateLabel.setBounds(pc.nameLabel.getX() + pc.nameLabel.getWidth(), pc.nameLabel.getY(),
                    250, pc.nameLabel.getHeight());
            pc.stateLabel.setOpaque(true);
            pc.stateLabel.setFont(new Font("", Font.BOLD, pc.stateLabel.getHeight() - 5));
            pc.stateLabel.setForeground(Theme.DARK_THEME.foreColor);
            pc.stateLabel.setBackground(pc.player.getColor());
            add(pc.stateLabel);

            // points
            pc.pointsLabel.setBounds(pc.stateLabel.getX() + pc.stateLabel.getWidth(), pc.nameLabel.getY(),
                    getWidth(), size.height);
            pc.pointsLabel.setText("Points: " + player.getPoints());
            pc.pointsLabel.setOpaque(true);
            pc.pointsLabel.setFont(new Font("", Font.BOLD, pc.pointsLabel.getHeight() - 5));
            pc.pointsLabel.setForeground(Theme.DARK_THEME.foreColor);
            pc.pointsLabel.setBackground(pc.player.getColor());
            add(pc.pointsLabel);

            // treasures
            int width = getWidth() / 4;
            int y1 = pc.nameLabel.getY() + pc.nameLabel.getHeight() + 10;
            for (int j = 0; j < pc.treasuresLabels.length; j++) {
                JLabel label = pc.treasuresLabels[j] = new JLabel();
                label.setText("- " +
                        (player.getTreasures(j) != null ? player.getTreasures(j).getTitle() : ""));
                label.setBounds(20 + (j % 4) * width, y1, width, 20);
                label.setFont(new Font("", Font.PLAIN, label.getHeight()));
                label.setForeground(Theme.DARK_THEME.foreColor);
                add(label);
                if (j % 4 == 3) {
                    y1 += 30;
                }
            }
        }

    }

    private void setButtons() {
        int x0 = (int) (getWidth() * 3.0/5);
        int y0 = leaderboardLabel.getY() + 10;
        Dimension size = new Dimension(100, 30);
        int hSpace = size.width + 20;

        for (int i = 0; i < buttons.length; i++) {
            JButton b = buttons[i];
            b.setLocation(x0 + i * hSpace, y0);
            b.setSize(size);
            b.addActionListener(this);
            b.setBackground(Theme.BRIGHT_THEME.backColor);
            add(b);
        }

    }

    private int getNLootedTreasures(Player[] players) {
        int result = 0;
        for (Player player : players) {
            result += player.getNumberOfTreasures();
        }
        return result;
    }

    private boolean isGameEnded(Player[] players) {
        for (Player player : players) {
            if (player.getState() == GameConstants.WON)
                return true;
        }
        return false;
    }

    private void setArrangement(Player[] players) {
        for (int i = 0; i < players.length - 1; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (players[j].getPoints() > players[i].getPoints()) {
                    Player temp = players[i];
                    players[i] = players[j];
                    players[j] = temp;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            parent.dispose();
            gameFrame.dispose();
            new MenuFrame();

        } else if (e.getSource() == boardButton) {
            parent.dispose();

        } else if (e.getSource() == rematchButton) {
            parent.dispose();
            gameFrame.dispose();
            new GameFrame();
        }

    }
}
