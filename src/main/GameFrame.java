package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame implements WindowListener {
    // TODO set width and height monitor-size-dependent
    static final int FRAME_WIDTH = 1000;
    static final private float RATIO = 1;
    static final int FRAME_HEIGHT = (int) (FRAME_WIDTH * RATIO);
    public GameFrame() {
        JPanel panel = new GamePanel(this);
        this.add(panel);
        this.setTitle("Travelling Salesman");
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
//        this.setFocusable(true);
        this.setVisible(true);
        addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure?",
                "Exit the Game", JOptionPane.YES_NO_OPTION))
            System.exit(0);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
