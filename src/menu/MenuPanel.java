package menu;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {
    private final int PANEL_HEIGHT = 500;
    private final int PANEL_WIDTH = 400;
    JLabel travellingLabel = new JLabel("Travelling");
    JLabel salesmanLabel = new JLabel("Salesman");
    JButton newGameButton = new JButton("New Game");
    JButton settingButton = new JButton("Setting");
    JButton helpButton = new JButton("Help");
    JButton exitButton = new JButton("Exit");
    JButton[] buttons = {newGameButton, settingButton, helpButton, exitButton};
    MenuPanel() {
        super();
        setLayout(null);
        setBackground(new Color(0xF8D980));
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        int xCenter = getWidth() / 2;

        // title labels
        travellingLabel.setSize(getWidth(), 50);
        travellingLabel.setFont(new Font("ink free", Font.BOLD, travellingLabel.getHeight()));
        travellingLabel.setLocation(xCenter - travellingLabel.getWidth() / 2, 20);
        travellingLabel.setToolTipText("travelling salesmen");
        travellingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(travellingLabel);

        salesmanLabel.setSize(travellingLabel.getSize());
        salesmanLabel.setFont(travellingLabel.getFont());
        salesmanLabel.setLocation(xCenter - salesmanLabel.getWidth() / 2,
                travellingLabel.getY() + travellingLabel.getHeight());
        salesmanLabel.setToolTipText(travellingLabel.getToolTipText());
        salesmanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(salesmanLabel);

        // ingredients
        Dimension buttonDimension = new Dimension(150, 30);
        Font buttonFont = new Font("", Font.PLAIN, buttonDimension.height / 2);
        int y0 = salesmanLabel.getY() + salesmanLabel.getFont().getSize() + 100;
        int xButton = xCenter - buttonDimension.width / 2;
        int vSpace = buttonDimension.height + 10;

        for (int j = 0; j < buttons.length; j++) {
            buttons[j].setSize(buttonDimension);
            buttons[j].setLocation(xButton, j * vSpace + y0);
            buttons[j].addActionListener(this);
            buttons[j].setFont(buttonFont);
            buttons[j].setBorder(new EtchedBorder());
            add(buttons[j]);
        }
//        newGameButton.setSize(buttonDimension);
//        newGameButton.setLocation(xButton, i++ * vSpace + y0);
//        newGameButton.addActionListener(this);
//        newGameButton.setFont(buttonFont);
//        add(newGameButton);
//
//        settingButton.setSize(buttonDimension);
//        settingButton.setLocation(xButton, i++ * vSpace + y0);
//        settingButton.addActionListener(this);
//        settingButton.setFont(buttonFont);
//        add(settingButton);
//
//        helpButton.setSize(buttonDimension);
//        helpButton.setLocation(xButton, i++ * vSpace + y0);
//        helpButton.addActionListener(this);
//        helpButton.setFont(buttonFont);
//        add(helpButton);
//
//        exitButton.setSize(buttonDimension);
//        exitButton.setLocation(xButton, i++ * vSpace + y0);
//        exitButton.addActionListener(this);
//        exitButton.setFont(buttonFont);
//        add(exitButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {

        } else if (e.getSource() == settingButton) {

        } else if (e.getSource() == helpButton) {

        } else if (e.getSource() == exitButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure?",
                    "Exit the Game", JOptionPane.YES_NO_OPTION))
                System.exit(0);
        }
    }
}
