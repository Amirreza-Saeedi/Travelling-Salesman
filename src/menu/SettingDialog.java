package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SettingDialog extends JDialog implements ActionListener {
    SettingDialog(JFrame frame) {
        super(frame, "Settings", ModalityType.APPLICATION_MODAL);

        SettingPanel settingPanel = new SettingPanel(this);
        add(settingPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
