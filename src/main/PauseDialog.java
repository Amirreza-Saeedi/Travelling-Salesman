package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PauseDialog extends JDialog implements KeyListener {
    PauseDialog(JFrame parentFrame) {
        super(parentFrame, "Pause", ModalityType.APPLICATION_MODAL);

        PausePanel pausePanel = new PausePanel(this);
        add(pausePanel);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dispose();
        }
    }
}
