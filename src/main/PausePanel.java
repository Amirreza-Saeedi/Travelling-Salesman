package main;

import menu.MenuFrame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PausePanel extends JPanel implements ActionListener {
    private final JLabel titleLabel = new JLabel("Pause");
    private final JButton resumeButton = new JButton("Resume the Game");
    private final JButton restartButton = new JButton("Restart the Game");
    private final JButton menuButton = new JButton("Back to Menu");
    private final JButton exitButton = new JButton("Exit the Game");
    private final JButton[] buttons = {resumeButton, restartButton, menuButton, exitButton};
    private final PauseDialog parentDialog;

    PausePanel(PauseDialog dialog) {
        super();
        Dimension d = new Dimension(300, 400);
        setPreferredSize(d);
        setBackground(new Color(0xEAEAEA));
        setSize(d);
        setLayout(null);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        parentDialog = dialog;

        setTitle();

        setButtons();

    }

    private void setTitle() {
        Dimension labelSize = new Dimension(getWidth(), 40);
        Font font = new Font("ink free", Font.BOLD, labelSize.height);
        int x0 = getXCenter() - labelSize.width / 2;
        int y0 = 30;

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(font);
        titleLabel.setLocation(x0, y0);
        titleLabel.setSize(labelSize);

        add(titleLabel);
    }

    private void setButtons() {
        Dimension size = new Dimension(200, 30);
        int x0 = getXCenter() - size.width / 2;
        int y0 = titleLabel.getY() + titleLabel.getHeight() + 40;
        int vSpace = size.height + 20;

        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button.setSize(size);
            button.setLocation(x0, y0 + i * vSpace);
            button.addActionListener(this);
            button.setBackground(null);
            button.setFocusable(false);
            add(button);
        }

    }

    private int getXCenter() {
        return getWidth() / 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resumeButton) {
            parentDialog.dispose();

        } else if (e.getSource() == restartButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure?",
                    "Restart the Game", JOptionPane.YES_NO_OPTION)){
                parentDialog.getOwner().dispose();
                new GameFrame();
            }
        } else if (e.getSource() == menuButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
                    "Current game will be lost. Are you sure?",
                    "Back to Menu", JOptionPane.YES_NO_OPTION)) {
                parentDialog.getOwner().dispose();
                new MenuFrame();
            }
        } else if (e.getSource() == exitButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
                    "Current game will be lost. Are you sure?",
                    "Exit the Game", JOptionPane.YES_NO_OPTION)) {
                System.exit(0);
            }
        }
    }
}
