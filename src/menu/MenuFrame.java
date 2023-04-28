package menu;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuFrame extends JFrame implements WindowListener {

    public MenuFrame() {
        super();
        MenuPanel menuPanel = new MenuPanel(this);
        add(menuPanel);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
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
