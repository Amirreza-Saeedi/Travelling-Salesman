package main;

import menu.Theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MovePanel extends JPanel {
    private final Rectangle moveBox = new Rectangle();
    private final Rectangle UP = new Rectangle();
    private final Rectangle RIGHT = new Rectangle();
    private final Rectangle DOWN = new Rectangle();
    private final Rectangle LEFT = new Rectangle();
    public JButton upButton;
    public JButton rightButton;
    public JButton downButton;
    public JButton leftButton;

    public MovePanel(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        setBorder(new LineBorder(Color.BLACK, 2));
        setBackground(Theme.BRIGHT_THEME.backColor);
        setLayout(null);

        // init move box
        moveBox.width = getWidth() / 2;
        moveBox.height = moveBox.width;
        moveBox.x = getWidth() / 2 - moveBox.width / 2;
        moveBox.y = getHeight() / 2 - moveBox.height / 2;
        
        // init directions rects
        int length = moveBox.width / 3;
        UP.setBounds(moveBox.x + length, moveBox.y, length, length);
        RIGHT.setBounds(UP.x + length, moveBox.y + length, length, length);
        DOWN.setBounds(UP.x, RIGHT.y + length, length, length);
        LEFT.setBounds(moveBox.x, RIGHT.y, length, length);

        newButtons();
    }

    private void newButtons() {
        upButton = new JButton("U");
        upButton.setToolTipText("Up");
        upButton.setBounds(UP);
        upButton.setBackground(Theme.DARK_THEME.backColor);
        upButton.setForeground(Theme.DARK_THEME.foreColor.brighter());
        add(upButton);
        upButton.setFocusable(false);

        rightButton = new JButton("R");
        rightButton.setToolTipText("Right");
        rightButton.setBounds(RIGHT);
        rightButton.setBackground(Theme.DARK_THEME.backColor);
        rightButton.setForeground(Theme.DARK_THEME.foreColor.brighter());
        add(rightButton);
        rightButton.setFocusable(false);


        downButton = new JButton("D");
        downButton.setToolTipText("Down");
        downButton.setBounds(DOWN);
        downButton.setBackground(Theme.DARK_THEME.backColor);
        downButton.setForeground(Theme.DARK_THEME.foreColor.brighter());
        add(downButton);
        downButton.setFocusable(false);

        leftButton = new JButton("L");
        leftButton.setToolTipText("Left");
        leftButton.setBounds(LEFT);
        leftButton.setBackground(Theme.DARK_THEME.backColor);
        leftButton.setForeground(Theme.DARK_THEME.foreColor.brighter());
        add(leftButton);
        leftButton.setFocusable(false);
    }

    public JButton[] getButtons() {
        return new JButton[]{upButton, rightButton, leftButton, downButton};
    }

}
