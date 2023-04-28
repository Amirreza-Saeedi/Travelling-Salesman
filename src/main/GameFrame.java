package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame implements WindowListener {
    // TODO set width and height monitor size dependently
    static final int FRAME_WIDTH = 1000;
    static final private float RATIO = 1;
    static final int FRAME_HEIGHT = (int) (FRAME_WIDTH * RATIO);
    public GameFrame() {
        //this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        JPanel panel = new GamePanel();
        // panel.setBorder(new LineBorder(Color.RED, 5));
        this.add(panel);
        this.setTitle("Travelling Salesman");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {


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
