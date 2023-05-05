package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class SettingDialog extends JDialog implements ActionListener, KeyListener {
    SettingDialog(JFrame frame) {
        super(frame, "Settings", ModalityType.APPLICATION_MODAL);

        SettingPanel settingPanel = new SettingPanel(this);
        add(settingPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
            this.dispose();
        }
    }
}
