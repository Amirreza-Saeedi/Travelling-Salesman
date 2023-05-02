package main;

import element.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ResultDialog extends JDialog implements KeyListener {
    public ResultDialog(GameFrame parentFrame, Player[] players) {
        super(parentFrame, ModalityType.APPLICATION_MODAL);
        ResultPanel resultPanel = new ResultPanel(parentFrame, this, players);
        add(resultPanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setLayout(null);
        setTitle("Leaderboard");
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }
}
