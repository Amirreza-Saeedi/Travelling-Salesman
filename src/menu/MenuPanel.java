package menu;

import main.GameFrame;
import main.GamePanel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {
    private final JFrame frame;
    private final int PANEL_HEIGHT = 500;
    private final int PANEL_WIDTH = 400;
    JLabel travellingLabel = new JLabel("Travelling");
    JLabel salesmanLabel = new JLabel("Salesman");
    JButton newGameButton = new JButton("New Game");
    JButton settingButton = new JButton("Settings");
    JButton helpButton = new JButton("Help");
    JButton exitButton = new JButton("Exit");
    JButton[] buttons = {newGameButton, settingButton, helpButton, exitButton};
    MenuPanel(JFrame frame) {
        super();
        setLayout(null);
        setBackground(new Color(0xF8D980));
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        this.frame = frame;

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

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setSize(buttonDimension);
            buttons[i].setLocation(xButton, i * vSpace + y0);
            buttons[i].addActionListener(this);
            buttons[i].setFont(buttonFont);
            buttons[i].setBorder(new EtchedBorder());
            buttons[i].setBackground(Theme.DARK_THEME.backColor);
            buttons[i].setForeground(Theme.DARK_THEME.foreColor);
            add(buttons[i]);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            new GameFrame();
            frame.dispose();

        } else if (e.getSource() == settingButton) {
            new SettingDialog(frame);

        } else if (e.getSource() == helpButton) {

        } else if (e.getSource() == exitButton) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure?",
                    "Exit the Game", JOptionPane.YES_NO_OPTION))
                System.exit(0);
        }
    }
}
